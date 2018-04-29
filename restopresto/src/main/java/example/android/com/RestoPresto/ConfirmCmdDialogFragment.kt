package example.android.com.RestoPresto

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatDialogFragment
import kotlinx.android.synthetic.main.fragment_confirm_cmd_dialog.*
import org.jetbrains.anko.toast


class ConfirmCmdDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.fragment_confirm_cmd_dialog, null)
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        var nom = view?.findViewById<TextInputLayout>(R.id.textNom)
        var adresse = view?.findViewById<TextInputLayout>(R.id.TextAdresse)
        builder.setView(view).setTitle("Confirmation de la commande").setNegativeButton("Annuler", object : DialogInterface.OnClickListener {
            override  fun onClick(dialog: DialogInterface, id: Int) {

            }
        })
                .setPositiveButton("Confirmer", object : DialogInterface.OnClickListener {
                    override  fun onClick(dialog: DialogInterface, id: Int) {
                        if (nom!!.equals("") || adresse!!.equals(""))
                            activity?.toast("Veuillez remplir les champs")
                        else
                        {
                        activity?.finish()
                        activity?.toast("Votre commande a bien été enregistrée")}
                    }
                })
        return builder.create()
    }

}// Required empty public constructor
