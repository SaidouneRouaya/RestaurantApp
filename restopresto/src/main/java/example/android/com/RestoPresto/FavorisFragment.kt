package example.android.com.RestoPresto

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatDialogFragment
import android.widget.RadioButton
import android.widget.RadioGroup
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.fragment_favoris.*
import org.jetbrains.anko.toast


/**
 * Created by start on 23/06/2018.
 */
class FavorisFragment : AppCompatDialogFragment() {

        companion object {
        fun newInstance(): FavorisFragment{
            val frag = FavorisFragment()
            val args = Bundle()
            frag.setArguments(args)
            return frag
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater?.inflate(R.layout.fragment_favoris, null)
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        var pref = activity!!.getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
//        val linearLayout = activity!!.findViewById(R.id.layoutFrag) as ConstraintLayout

        //These options will be the regular radio buttons
        var radioD = view?.findViewById<RadioButton>(R.id.radioDiabet)
        var radioN = view?.findViewById<RadioButton>(R.id.radioNormal)
        var radioV = view?.findViewById<RadioButton>(R.id.radioVeg)
        //This option can't be unchecked
        val radioButtonAlwaysChecked = RadioButton(activity)

        var fav  = pref.getString("favoris","")
        if (fav=="normal")
            radioN!!.isChecked = true
        else if (fav=="vegetarien")
            radioV!!.isChecked = true
        else
            radioD!!.isChecked=true

       // linearLayout.addView(radioGroup)
        val mDb = RoomService.appDatabase.getUserDao()
        val user = mDb.getUsersByID(pref!!.getInt("id_user",0))
        builder.setView(view).setTitle("Menu favori").setNegativeButton("Annuler", object : DialogInterface.OnClickListener {
            override  fun onClick(dialog: DialogInterface, id: Int) {
               dismiss()
            }
        })
                .setPositiveButton("Confirmer", object : DialogInterface.OnClickListener {
                    override  fun onClick(dialog: DialogInterface, id: Int) {
                        if (radioN!!.isChecked)
                        {
                            user.preference="normal"
                            pref!!.edit().putString("favoris", "normal").apply()
                            mDb.updateUser(user.id_user,user.preference)
                        }
                        else if (radioD!!.isChecked)
                        {
                            user.preference="diabetique"
                            pref!!.edit().putString("favoris", "diabetique").apply()
                            mDb.updateUser(user.id_user,user.preference)
                        }
                        else if (radioV!!.isChecked)
                        {
                            user.preference="vegetarien"
                            pref!!.edit().putString("favoris", "vegetarie").apply()
                            mDb.updateUser(user.id_user,user.preference)
                        }
                        activity!!.toast("Favoris retenu !")
                    }
                })

        return builder.create()
    }

}