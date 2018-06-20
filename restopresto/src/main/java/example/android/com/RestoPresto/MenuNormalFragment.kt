package example.android.com.RestoPresto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuNormalFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val id_restaurant = activity!!.intent.getIntExtra("id_restaurant",0)
        val view = inflater.inflate(R.layout.fragment_menu_normal, container, false)
        val listView= view.findViewById<ListView>(R.id.list_plats_normaux)
        val call = RetrofitService.endpoint.getMenusByRestaurant(id_restaurant+1,"Normal")
        call.enqueue(object : Callback<List<Menu>> {
            override fun onFailure(call: Call<List<Menu>>?, t: Throwable?) {
                Toast.makeText(activity, "echec 1 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Menu>>?, response: Response<List<Menu>>?) {
                if (response?.isSuccessful!!) {
                    val list: List<Menu> = response.body()!!
                    var listNormal = loadDataNormal(list.get(0).id_menu)
                    Toast.makeText(activity, list.get(0).id_menu.toString(), Toast.LENGTH_SHORT).show()
                    val platNormalAdapter = platMenuAdapter(activity!!, listNormal)
                    listView.adapter = platNormalAdapter
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun loadDataNormal(id_menu: Int):List<Plat> {
        var list: List<Plat> = mutableListOf<Plat>()
        val call = RetrofitService.endpoint.getPlatByMenu(id_menu)
        call.enqueue(object : Callback<List<Plat>> {
            override fun onFailure(call: Call<List<Plat>>?, t: Throwable?) {
                Toast.makeText(activity, "echec 2 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Plat>>?, response: Response<List<Plat>>?){
                if (response?.isSuccessful!!) {
                    list = response.body()!!
                    Toast.makeText(activity, list.get(0).nom, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        })

        return  list
    }
   /* private data class ViewHolder(var num: TextView){
    }*/

}