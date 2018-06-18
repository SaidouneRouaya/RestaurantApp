package example.android.com.RestoPresto

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.ListView
import example.android.com.RestoPresto.entities.Plat


class PlatFragment : DialogFragment() {

    fun newInstance(): PlatFragment {
        val frag = PlatFragment()
        val args = Bundle()
        frag.setArguments(args)
        return frag
    }
  /*  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plat, container, false)
        val listView= view.findViewById<ListView>(R.id.list_plat_fb)
        val platVegetarienAdapter = platAdapter(activity!!, loadDataVegetarien())
        listView.adapter = platVegetarienAdapter
        return view
    }*/
    fun loadDataPlats():List<Plat> {
        val nomTab = resources.getStringArray(R.array.platsbinaire)
        val ingredientsTab = resources.getStringArray(R.array.ingredientsbinaire)
        val prixTab = resources.getStringArray(R.array.prixbinaire)
        val list = mutableListOf<Plat>()
        for (i in 0..nomTab.size-1) {
            list.add(Plat(nom = nomTab[i], ingredient = ingredientsTab[i], prix = prixTab[i].toDouble()))
        }
        return  list
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_plat, null)
        val listView= view.findViewById<ListView>(R.id.list_plat_fb)
        val platBinaireAdapter = platAdapter(activity!!, loadDataPlats())
        listView.adapter = platBinaireAdapter
        return AlertDialog.Builder(activity).setTitle("Plats").setView(view).create()
    }

}// Required empty public constructor
