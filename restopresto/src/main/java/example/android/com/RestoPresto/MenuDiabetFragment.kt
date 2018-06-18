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
class MenuDiabetFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_diabet, container, false)
        val listView= view.findViewById<ListView>(R.id.list_plats_diabet)
        var listDiabet = loadDataDiabet()
        val platDiabetAdapter = platMenuAdapter(activity!!, listDiabet)
        listView.adapter = platDiabetAdapter
        listView.setOnItemClickListener({adapterView, view, i, l ->
            var viewHolder: platMenuAdapter.ViewHolder
            val num = view?.findViewById<TextView>(R.id.nbCmd) as TextView
            val nom = view?.findViewById<TextView>(R.id.nom_plat) as TextView
            val ingredients = view?.findViewById<TextView>(R.id.ingredients_plat) as TextView
            val prix = view?.findViewById<TextView>(R.id.prix) as TextView
            if(num.text.toString().toInt()<20)
            {
                /*listDiabet.get(i).nbCmd= (listDiabet.get(i).nbCmd.toInt()+1).toString()
                num.text= listDiabet.get(i).nbCmd*/
             //   listDiabet.get(i).nbCmd= (listDiabet.get(i).nbCmd.toInt()+1).toString()
               num.text= "5"
            }
            else num.text="20"
            viewHolder= platMenuAdapter.ViewHolder(nom, ingredients,prix,num)
            viewHolder.nb.visibility = View.VISIBLE
            view.setTag(viewHolder)
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun loadDataDiabet():List<Plat> {
        val nomTab = resources.getStringArray(R.array.plats_diabet)
        val ingredientsTab = resources.getStringArray(R.array.ingredients_diabet)
        val prixTab = resources.getStringArray(R.array.prix_diabet)
        val list = mutableListOf<Plat>()

        for (i in 0..nomTab.size-1) {
            list.add(
                    Plat(nom = nomTab[i], ingredient = ingredientsTab[i], prix = prixTab[i].toDouble()))
        }
        return  list
    }
    private data class ViewHolder(var num: TextView){
}

}// Required empty public constructor
