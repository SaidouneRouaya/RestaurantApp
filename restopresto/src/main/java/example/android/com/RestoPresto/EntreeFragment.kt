package example.android.com.RestoPresto

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.ListView


class EntreeFragment : DialogFragment() {
    var typeFormule:String =""
    var formuleModel = FormuleModel()
    var id_restaurant:Int = 0
    fun newInstance(typeformule:String,formuleModel: FormuleModel, id_restaurant:Int): EntreeFragment {
        val frag = EntreeFragment()
        val args = Bundle()
        args.putString("typeformule",typeformule)
        args.putInt("id_restaurant",id_restaurant)
        frag.setArguments(args)
        return frag
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_plat, null)
        val listView= view.findViewById<ListView>(R.id.list_plat_fb)
        typeFormule = arguments.getString("typeformule")
        id_restaurant = arguments.getInt("id_restaurant",0)
        formuleModel?.loadDataFormule(id_restaurant,activity,typeFormule,"Entree",listView)
        return AlertDialog.Builder(activity).setTitle("Entrées").setView(view).create()
    }
}// Required empty public constructor
