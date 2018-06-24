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
        val linearLayout = activity!!.findViewById(R.id.layoutFrag) as ConstraintLayout
        val radioGroup = RadioGroup(activity)

        radioGroup.orientation = RadioGroup.VERTICAL

        //This option will be the default value
        val radioButtonDefault = RadioButton(activity)
        radioButtonDefault.text = "normal"
        radioGroup.addView(radioButtonDefault)
        radioGroup.check(radioButtonDefault.id)
        //These options will be the regular radio buttons
        val options = arrayOf("vegetarien", "diabetique")

        //This option can't be unchecked
        val radioButtonAlwaysChecked = RadioButton(activity)
        radioGroup.addView(radioDiabet)
        radioGroup.addView(radioNormal)
        radioGroup.addView(radioVeg)

        var fav  = pref.getString("favoris","")
        if (fav=="normal")
            radioNormal.isChecked = true
        else if (fav=="vegetarien")
            radioVeg.isChecked = true
        else
            radioDiabet.isChecked=true

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
        })
        linearLayout.addView(radioGroup)
        val mDb = RoomService.appDatabase.getUserDao()
        val user = mDb.getUsersByID(pref!!.getInt("id_user",0))
        builder.setView(view).setTitle("Menu favori").setNegativeButton("Annuler", object : DialogInterface.OnClickListener {
            override  fun onClick(dialog: DialogInterface, id: Int) {
               dismiss()
            }
        })
                .setPositiveButton("Confirmer", object : DialogInterface.OnClickListener {
                    override  fun onClick(dialog: DialogInterface, id: Int) {
                        if (radioNormal.isChecked)
                        {
                            user.preference="normal"
                            pref!!.edit().putString("favoris", "normal").apply()
                            mDb.updateUser(user.id_user,user.preference)
                        }
                        else if (radioDiabet.isChecked)
                        {
                            user.preference="diabetique"
                            pref!!.edit().putString("favoris", "diabetique").apply()
                            mDb.updateUser(user.id_user,user.preference)
                        }
                        else if (radioVeg.isChecked)
                        {
                            user.preference="vegetarien"
                            pref!!.edit().putString("favoris", "vegetarie").apply()
                            mDb.updateUser(user.id_user,user.preference)
                        }
                    }
                })

        return builder.create()
    }

}