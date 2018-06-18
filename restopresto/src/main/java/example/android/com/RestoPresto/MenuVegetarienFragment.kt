package example.android.com.RestoPresto
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import example.android.com.RestoPresto.entities.Plat

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuVegetarienFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_vegetarien, container, false)
        val listView= view.findViewById<ListView>(R.id.list_plats_vegetariens)
        var listveg = loadDataVegetarien()
        val platVegetarienAdapter = platMenuAdapter(activity!!, listveg)
        listView.adapter = platVegetarienAdapter
        listView.setOnItemClickListener({adapterView, view, i, l ->
            var viewHolder: platMenuAdapter.ViewHolder
            val num = view?.findViewById<TextView>(R.id.nbCmd) as TextView
            val nom = view?.findViewById<TextView>(R.id.nom_plat) as TextView
            val ingredients = view?.findViewById<TextView>(R.id.ingredients_plat) as TextView
            val prix = view?.findViewById<TextView>(R.id.prix) as TextView
            if(num.text.toString().toInt()<20)
            {
              /* listveg.get(i).nbCmd= (listveg.get(i).nbCmd.toInt()+1).toString()
                num.text= listveg.get(i).nbCmd*/
                num.text= "5"
            }
            else num.text="20"
            viewHolder= platMenuAdapter.ViewHolder(nom, ingredients,prix,num)
            viewHolder.nb.visibility = View.VISIBLE
            view.setTag(viewHolder)
        })
        return view
    }
    fun loadDataVegetarien():List<Plat> {
        val nomTab = resources.getStringArray(R.array.plats_vegetarien)
        val ingredientsTab = resources.getStringArray(R.array.ingredients_vegetarien)
        val prixTab = resources.getStringArray(R.array.prix_vegetarien)
        val list = mutableListOf<Plat>()
        for (i in 0..nomTab.size-1) {
            list.add(Plat(nom = nomTab[i], ingredient = ingredientsTab[i], prix = prixTab[i].toDouble()))
        }
        return  list
    }
    private data class ViewHolder(var num: TextView){
    }

}