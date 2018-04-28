package example.android.com.myapplication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import example.android.com.myapplication.entity.plat

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuNormalFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_menu_normal, container, false)
        val listView= view.findViewById<ListView>(R.id.list_plats_normaux)
        val platNormalAdapter = platMenuAdapter(activity!!, loadDataNormal())
        listView.adapter = platNormalAdapter
        return view
    }
    fun loadDataNormal():List<plat> {
        val nomTab = resources.getStringArray(R.array.plats)
        val ingredientsTab = resources.getStringArray(R.array.ingredients)
        val prixTab = resources.getStringArray(R.array.prix)
        val list = mutableListOf<plat>()
        for (i in 0..nomTab.size-1) {
            list.add(plat(nom = nomTab[i], ingredients = ingredientsTab[i], prix = prixTab[i]))
        }
        return  list
    }
}