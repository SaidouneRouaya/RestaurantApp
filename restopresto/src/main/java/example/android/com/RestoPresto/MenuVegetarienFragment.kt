package example.android.com.RestoPresto
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import example.android.com.RestoPresto.entities.plat

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuVegetarienFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_vegetarien, container, false)
        val listView= view.findViewById<ListView>(R.id.list_plats_vegetariens)
        val platVegetarienAdapter = platMenuAdapter(activity!!, loadDataVegetarien())
        listView.adapter = platVegetarienAdapter
        listView.setOnItemClickListener({adapterView, view, i, l ->
            var viewHolder:ViewHolder
            val num = view?.findViewById<TextView>(R.id.nbCmd) as TextView

            if(num.text.toString().toInt()<20)
            {                num.text= (num.text.toString().toInt()+1).toString()}
            else num.text="20"
            viewHolder=ViewHolder(num)
            viewHolder.num.visibility = View.VISIBLE
            view.setTag(viewHolder)
        })
        return view
    }
    fun loadDataVegetarien():List<plat> {
        val nomTab = resources.getStringArray(R.array.plats_vegetarien)
        val ingredientsTab = resources.getStringArray(R.array.ingredients_vegetarien)
        val prixTab = resources.getStringArray(R.array.prix_vegetarien)
        val list = mutableListOf<plat>()
        for (i in 0..nomTab.size-1) {
            list.add(plat(nom = nomTab[i], ingredients = ingredientsTab[i], prix = prixTab[i]))
        }
        return  list
    }
    private data class ViewHolder(var num: TextView){
    }

}