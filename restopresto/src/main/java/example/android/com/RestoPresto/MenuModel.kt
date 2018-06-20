package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Room
import android.os.Handler
import android.widget.ListView
import android.widget.Toast
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Contenir_menu
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.entities.Restaurant
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ArrayBlockingQueue
import kotlin.coroutines.experimental.coroutineContext

/**
 * Created by start on 20/06/2018.
 */
class MenuModel:ViewModel() {

    // Gestion de SQLLite
    private var mDb: AppDatabase? = RoomService.appDatabase
    private lateinit var mDbWorkerThread: DbWorkerThread
    var menus : List<Menu>? = null
    var plats : List<Plat>? = null
    var contenir_menu : List<Contenir_menu>? = null

    fun loadDataMenu(id_restaurant: Int, activity: Activity, type:String, listviewid:ListView)
    {
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        menus = mDb!!.getMenuDao().getMenusByRestaurantAndType(id_restaurant,type)

        if (menus!!.isEmpty())
        {
            getMenuFromRemote(id_restaurant,activity,type,listviewid)
        }
        else
        {
            plats = mDb!!.getContnirMenuDao().getPlatsByMenu(menus!!.get(0).id_menu)
            if (plats!!.isEmpty())
            {
                getPlatsFromRemote(menus!!.get(0).id_menu,activity,listviewid)
            }
            else
            {
                showMenu(activity,listviewid,plats!!)
            }
        }
    }

    fun getMenuFromRemote(id_restaurant: Int, activity: Activity, type:String, listviewid:ListView){
        remplirRestos()
        val call = RetrofitService.endpoint.getMenusByRestaurant(id_restaurant+1,type)
        call.enqueue(object : Callback<List<Menu>> {
            override fun onFailure(call: Call<List<Menu>>?, t: Throwable?) {
                Toast.makeText(activity, "echec 1 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Menu>>?, response: Response<List<Menu>>?) {
                if (response?.isSuccessful!!) {
                    val list: List<Menu> = response.body()!!
                    for(m in list)
                    {
                        System.out.println(m.id_restaurant)
                        mDb!!.getMenuDao().addMenus(m)
                    }
                    remplirContenirMenu()
                    getPlatsFromRemote(list.get(0).id_menu,activity,listviewid)
                     //loadDataNormal(list.get(0).id_menu)
                    //Toast.makeText(activity, list.get(0).id_menu.toString(), Toast.LENGTH_SHORT).show()

                    /*listView.setOnItemClickListener({adapterView, view, i, l ->
                        var viewHolder: platMenuAdapter.ViewHolder
                        val num = view?.findViewById<TextView>(R.id.nbCmd) as TextView
                        val nom = view?.findViewById<TextView>(R.id.nom_plat) as TextView
                        val ingredients = view?.findViewById<TextView>(R.id.ingredients_plat) as TextView
                        val prix = view?.findViewById<TextView>(R.id.prix) as TextView
                        if(num.text.toString().toInt()<20)
                        {
                            /*  listNormal.get(i).nbCmd= (listNormal.get(i).nbCmd.toInt()+1).toString()
                              num.text= listNormal.get(i).nbCmd*/
                            num.text= "5"
                        }
                        else num.text="20"
                        viewHolder= platMenuAdapter.ViewHolder(nom, ingredients,prix,num)
                        viewHolder.nb.visibility = View.VISIBLE
                        view.setTag(viewHolder)
                    })*/
                } else {
                    Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        })
    }


    fun getPlatsFromRemote(id_menu: Int, activity: Activity, listviewid:ListView){
        var listPlat :List<Plat>
        val call2 = RetrofitService.endpoint.getPlatByMenu(id_menu)
        call2.enqueue(object : Callback<List<Plat>> {
            override fun onFailure(call: Call<List<Plat>>?, t: Throwable?) {
                Toast.makeText(activity, "echec 2 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Plat>>?, response: Response<List<Plat>>?){
                if (response?.isSuccessful!!) {
                    listPlat=response.body()!!
                    //Toast.makeText(activity, listNormal.toList().get(0).nom, Toast.LENGTH_SHORT).show()
                    showMenu(activity,listviewid,listPlat)
                } else {
                    Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        })
    }
    fun remplirRestos()
    {
        val call2 = RetrofitService.endpoint.getRestaurants()
        System.out.println("khra")
        doAsync {
        call2.enqueue(object : Callback<List<Restaurant>> {
            override fun onFailure(call: Call<List<Restaurant>>?, t: Throwable?) {
                System.out.println("failure")
            }
            override fun onResponse(call: Call<List<Restaurant>>?, response: Response<List<Restaurant>>?){
                if (response?.isSuccessful!!) {
                    val listRestos: List<Restaurant> =  response.body()!!
                    for(r in listRestos)
                    {
                        mDb!!.getRestaurantDao().addRestaurants(r)
                        System.out.println(r.nom)
                    }
                    System.out.println("Zbel")

                } else {
                    System.out.println("non_response")
                }
            }
        })}
    }

    fun remplirContenirMenu()
    {
        val call2 = RetrofitService.endpoint.getContenirMenu()
        call2.enqueue(object : Callback<List<Contenir_menu>> {
            override fun onFailure(call: Call<List<Contenir_menu>>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<List<Contenir_menu>>?, response: Response<List<Contenir_menu>>?){
                if (response?.isSuccessful!!) {
                    val listCM: List<Contenir_menu> = response.body()!!
                    for(c in listCM)
                    {
                        mDb!!.getContnirMenuDao().addContenir_menus(c)
                    }

                } else {
                }
            }
        })
    }

    fun showMenu(activity: Activity,listviewid: ListView,listPlats:List<Plat>)
    {
        val listView= listviewid
        val platNormalAdapter = platMenuAdapter(activity!!, listPlats)
        listView.adapter = platNormalAdapter
    }
}
