package example.android.com.myapplication


import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import kotlinx.android.synthetic.main.activity_reserver.*
import android.R.string.cancel
import android.content.DialogInterface
import android.widget.*


/**
 * A simple [Fragment] subclass.
 */
class nbCmdFragment : DialogFragment(), NumberPicker.OnValueChangeListener {

    private var  numberpicker:NumberPicker= NumberPicker(context)
    private var i:Int=0
companion object {
    fun newInstance(numItem:Int): nbCmdFragment {
        val frag = nbCmdFragment()
        val args = Bundle()
        args.putInt("num", numItem)
                frag.setArguments(args)

        return frag
    }
}
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Use this instance of the interface to deliver action events
    var mListener: NoticeDialogListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nb_cmd, container, false)
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        activity.findViewById<ListView>(R.id.list_plats_diabet).getItemAtPosition(i)

        activity.findViewById<TextView>(R.id.nbCmd).text = (oldVal+newVal).toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args=arguments
        i=args.getInt("num")
        numberpicker= this.numberPicker
        numberPicker.minValue = 1
        numberPicker.maxValue = 10
        AlertDialog.Builder(activity).setTitle("Nombre de plats").setView(view).create()

        val builder = AlertDialog.Builder(activity)

        builder.setPositiveButton(R.string.valider, object : DialogInterface.OnClickListener {
            override  fun onClick(dialog: DialogInterface, id: Int) {
                        onValueChange(numberPicker!!,0, numberpicker.value)

                    }
                })
                .setNegativeButton(R.string.annuler, object : DialogInterface.OnClickListener {
                    override  fun onClick(dialog: DialogInterface, id: Int) {
                    dismiss()
                    }
                })
        return builder.create()

    }
}

// Required empty public constructor
