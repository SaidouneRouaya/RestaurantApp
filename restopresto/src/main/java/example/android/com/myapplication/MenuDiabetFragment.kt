package example.android.com.myapplication


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import example.android.com.myapplication.entities.plat

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuDiabetFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_diabet, container, false)
        val listView= view.findViewById<ListView>(R.id.list_plats_diabet)
        val platDiabetAdapter = platMenuAdapter(activity!!, loadDataDiabet())
        listView.adapter = platDiabetAdapter
       listView.setOnItemClickListener({adapterView, view, i, l ->
            var viewHolder: ViewHolder
            val num = view?.findViewById<TextView>(R.id.nbCmd) as TextView

           if(num.text.toString().toInt()<20)
           {                num.text= (num.text.toString().toInt()+1).toString()}
           else num.text="20"
            viewHolder= ViewHolder(num)
           viewHolder.num.visibility = View.VISIBLE
           view.setTag(viewHolder)
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun loadDataDiabet():List<plat> {
        val nomTab = resources.getStringArray(R.array.plats_diabet)
        val ingredientsTab = resources.getStringArray(R.array.ingredients_diabet)
        val prixTab = resources.getStringArray(R.array.prix_diabet)
        val list = mutableListOf<plat>()

        for (i in 0..nomTab.size-1) {
            list.add(
                    plat(nom = nomTab[i], ingredients = ingredientsTab[i], prix = prixTab[i]))
        }
        return  list
    }
    private data class ViewHolder(var num: TextView){
}

}// Required empty public constructor
