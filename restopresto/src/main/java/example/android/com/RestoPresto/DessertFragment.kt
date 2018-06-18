package example.android.com.RestoPresto

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.ListView
import example.android.com.RestoPresto.entities.Plat


class DessertFragment : DialogFragment() {

    fun newInstance(): DessertFragment {
        val frag = DessertFragment()
        val args = Bundle()
        frag.setArguments(args)
        return frag
    }
    fun loadDatadesserts():List<Plat> {
        val nomTab = resources.getStringArray(R.array.dessertsbinaire)
        val ingredientsTab = resources.getStringArray(R.array.ingredientsdessertbinaire)
        val prixTab = resources.getStringArray(R.array.prix)
        val list = mutableListOf<Plat>()
        for (i in 0..nomTab.size-1) {
            list.add(Plat(nom = nomTab[i], ingredient = ingredientsTab[i], prix = prixTab[i].toDouble()))
        }
        return  list
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_plat, null)
        val listView= view.findViewById<ListView>(R.id.list_plat_fb)
        val dessertBinaireAdapter = platAdapter(activity!!, loadDatadesserts())
        listView.adapter = dessertBinaireAdapter
        return AlertDialog.Builder(activity).setTitle("Desserts").setView(view).create()
    }
}// Required empty public constructor
