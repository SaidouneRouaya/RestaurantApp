package example.android.com.RestoPresto


import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_reserver.*
import android.content.DialogInterface
import android.content.SharedPreferences
import android.widget.*
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.fragment_nb_cmd.*
import org.jetbrains.anko.act


/**
 * A simple [Fragment] subclass.
 */
class nbCmdFragment : DialogFragment(), NumberPicker.OnValueChangeListener {
    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private var mDb: AppDatabase? = RoomService.appDatabase
    var preferences: SharedPreferences? = null
    private var  nombrecmd:NumberPicker? = null
    private var i:Int=0
    companion object {
    fun newInstance(id_plat:Int): nbCmdFragment {
        val frag = nbCmdFragment()
        val args = Bundle()
        args.putInt("id_plat", id_plat)
                frag.setArguments(args)

        return frag
    }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity.layoutInflater.inflate(R.layout.fragment_nb_cmd, null)
        val args=arguments
        preferences = activity.getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        i=args.getInt("id_plat")
        nombrecmd= view.findViewById<NumberPicker>(R.id.nombreCmd)
        nombrecmd!!.minValue = 1
        nombrecmd!!.maxValue = 10
        val builder = AlertDialog.Builder(activity)
        builder.setPositiveButton(R.string.valider, object : DialogInterface.OnClickListener {
            override  fun onClick(dialog: DialogInterface, id: Int) {
                /*val mDb!!.getCommandeDao().getCommandesByUser(preferences!!.getInt("id_user",1))
                if ()*/
                nombrecmd!!.value

                }
                })
                .setNegativeButton(R.string.annuler, object : DialogInterface.OnClickListener {
                    override  fun onClick(dialog: DialogInterface, id: Int) {
                    dismiss()
                    }
                })
        return builder.setTitle("Nombre de plats").setView(view).create()

    }
}

// Required empty public constructor
