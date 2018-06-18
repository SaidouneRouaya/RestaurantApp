package example.android.com.RestoPresto

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.ListView
import example.android.com.RestoPresto.entities.Plat


class EntreeFragment : DialogFragment() {
    fun newInstance(): EntreeFragment {
        val frag = EntreeFragment()
        val args = Bundle()
        frag.setArguments(args)
        return frag
    }
    fun loadDataentrees():List<Plat> {
        val nomTab = resources.getStringArray(R.array.entreesbinaire)
        val ingredientsTab = resources.getStringArray(R.array.ingredientsentreebinaire)
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
        val entreeBinaireAdapter = platAdapter(activity!!, loadDataentrees())
        listView.adapter = entreeBinaireAdapter
        return AlertDialog.Builder(activity).setTitle("Entrées").setView(view).create()
    }
}// Required empty public constructor
