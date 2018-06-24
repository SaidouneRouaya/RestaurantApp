package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import android.widget.ListView
import android.widget.Toast
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Contenir_menu
import example.android.com.RestoPresto.entities.Ligne_commande
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    var preferences: SharedPreferences? = null

    fun loadDataMenu(id_restaurant: Int, activity: Activity, type:String, listviewid:ListView)
    {
        menus = mDb!!.getMenuDao().getMenusByRestaurantAndType(id_restaurant,type)

        if (menus!!.isEmpty())
        {
            getMenuFromRemote(id_restaurant,activity,type,listviewid)
        }
        else
        {
            plats = mDb!!.getContenirMenuDao().getPlatsByMenu(menus!!.get(0).id_menu)
            if (plats!!.isEmpty())
            {
                getPlatsFromRemote(menus!!.get(0).id_menu,activity,listviewid,id_restaurant)
            }
            else
            {
                showMenu(activity,listviewid,plats!!,id_restaurant)
            }
        }
    }

    fun getMenuFromRemote(id_restaurant: Int, activity: Activity, type:String, listviewid:ListView){
        //remplirRestos()
        val call = RetrofitService.endpoint.getMenusByRestaurant(id_restaurant,type)

        call.enqueue(object : Callback<List<Menu>> {
            override fun onFailure(call: Call<List<Menu>>?, t: Throwable?) {
                Toast.makeText(activity!!, "echec 1 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Menu>>?, response: Response<List<Menu>>?) {
                if (response?.isSuccessful!!) {
                    System.out.println("je passe par menu remote")
                    val list: List<Menu> = response.body()!!
                    getPlatsFromRemote(list.get(0).id_menu,activity,listviewid,id_restaurant)
                } else {
                    Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        })
    }


    fun getPlatsFromRemote(id_menu: Int, activity: Activity, listviewid:ListView, id_restaurant: Int){
        val call2 = RetrofitService.endpoint.getPlatByMenu(id_menu)
        call2.enqueue(object : Callback<List<Plat>> {
            override fun onFailure(call: Call<List<Plat>>?, t: Throwable?) {
                Toast.makeText(activity!!, "echec 2 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Plat>>?, response: Response<List<Plat>>?){
                if (response?.isSuccessful!!) {
                    val listPlat :List<Plat> = response.body()!!

                    showMenu(activity,listviewid,listPlat,id_restaurant)
                    //remplirMenus()
                } else {
                    Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        })
    }


    fun remplirContenirMenu()
    {
        val cmenus : List<Contenir_menu> = mDb!!.getContenirMenuDao().getContenir_menu()
        if(cmenus.isEmpty()) {
        val call2 = RetrofitService.endpoint.getContenirMenu()
        call2.enqueue(object : Callback<List<Contenir_menu>> {
            override fun onFailure(call: Call<List<Contenir_menu>>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<List<Contenir_menu>>?, response: Response<List<Contenir_menu>>?){
                if (response?.isSuccessful!!) {
                    val listCM: List<Contenir_menu> = response.body()!!

                    for(c in listCM)
                    {
                        mDb!!.getContenirMenuDao().addContenir_menus(c)
                    }

                } else {
                }
            }
        })}
    }

    fun remplirMenus()
    {
        val menus : List<Menu> = mDb!!.getMenuDao().getMenus()
        if(menus.isEmpty()) {
        val call2 = RetrofitService.endpoint.getMenus()
        call2.enqueue(object : Callback<List<Menu>> {
            override fun onFailure(call: Call<List<Menu>>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<List<Menu>>?, response: Response<List<Menu>>?){
                if (response?.isSuccessful!!) {
                    val listCM: List<Menu> = response.body()!!
                    for(m in listCM)
                    {
                        mDb!!.getMenuDao().addMenus(m)
                    }
                    remplirPlats()

                } else {
                }
            }
        })}
    }
    fun remplirPlats()
    {
        val plats : List<Plat> = mDb!!.getPlatDao().getPlats()
        if(plats.isEmpty()) {
        val call3 = RetrofitService.endpoint.getPlats()
        call3.enqueue(object : Callback<List<Plat>> {
            override fun onFailure(call: Call<List<Plat>>?, t: Throwable?) {
            }
            override fun onResponse(call: Call<List<Plat>>?, response: Response<List<Plat>>?){
                if (response?.isSuccessful!!) {
                    val listPlats: List<Plat> = response.body()!!

                    for(p in listPlats)
                    {

                        mDb!!.getPlatDao().addPlats(p)
                    }
                    remplirContenirMenu()

                } else {
                }
            }
        })}
    }

    fun loadNbPlat(id_restaurant: Int, activity: Activity, listPlats: List<Plat>):  MutableMap<Plat,Ligne_commande> {
        preferences = activity.getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        val commandes = mDb!!.getCommandeDao().getCommandesByUserByRestaurant(preferences!!.getInt("id_user",1),id_restaurant)
        var plat : Plat
        var liste_commandes = mutableMapOf<Plat,Ligne_commande>()
        var ligne_commande : List<Ligne_commande> = mutableListOf()

        if (commandes!!.isNotEmpty())
        {
            for (p in listPlats)
            {
                ligne_commande = mDb!!.getLigneCommandeDao().getLigne_commandeByCommandeByPlat(commandes.get(0).id_cmd, p.id_plat)
                if (ligne_commande.isNotEmpty())
                {
                    liste_commandes.put(p,ligne_commande.get(0))
                }
            }
        }
        else
        {
           liste_commandes = mutableMapOf()
        }
        return liste_commandes
    }

    fun showMenu(activity: Activity,listviewid: ListView,listPlats:List<Plat>,id_restaurant: Int)
    {
        val listView= listviewid
        val id_user = activity.getSharedPreferences("projetMobile", Context.MODE_PRIVATE).getInt("id_user",1)
        //var liste_commandes = loadNbPlat(id_restaurant,activity,listPlats)
        val platNormalAdapter = platMenuAdapter(activity!!, listPlats,id_restaurant)
        listView.adapter = platNormalAdapter
        listView.setOnItemClickListener({adapterView, view, i, l ->
            val newFragment:nbCmdFragment = nbCmdFragment.newInstance(listPlats[i].id_plat,id_restaurant)
            newFragment.show(activity?.fragmentManager,"dialog")
        })
    }
}
