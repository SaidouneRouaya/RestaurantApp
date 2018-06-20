package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.widget.ListView
import android.widget.Toast
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ArrayBlockingQueue

/**
 * Created by start on 20/06/2018.
 */
class MenuModel:ViewModel() {

    var plats :List<Plat>? = null
    fun loadDataMenu(id_restaurant: Int, activity: Activity, type:String, listviewid:ListView){
        val listView= listviewid
        val call = RetrofitService.endpoint.getMenusByRestaurant(id_restaurant+1,type)
        call.enqueue(object : Callback<List<Menu>> {
            override fun onFailure(call: Call<List<Menu>>?, t: Throwable?) {
                Toast.makeText(activity, "echec 1 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Menu>>?, response: Response<List<Menu>>?) {
                if (response?.isSuccessful!!) {
                    val list: List<Menu> = response.body()!!
                    var listNormal :List<Plat> //loadDataNormal(list.get(0).id_menu)
                    //Toast.makeText(activity, list.get(0).id_menu.toString(), Toast.LENGTH_SHORT).show()
                    val call2 = RetrofitService.endpoint.getPlatByMenu(list.get(0).id_menu)
                    call2.enqueue(object : Callback<List<Plat>> {
                        override fun onFailure(call: Call<List<Plat>>?, t: Throwable?) {
                            Toast.makeText(activity, "echec 2 !", Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<List<Plat>>?, response: Response<List<Plat>>?){
                            if (response?.isSuccessful!!) {
                                listNormal=response.body()!!
                                val platNormalAdapter = platMenuAdapter(activity!!, listNormal)
                                listView.adapter = platNormalAdapter
                                //Toast.makeText(activity, listNormal.toList().get(0).nom, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                            }
                        }
                    })
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
}
