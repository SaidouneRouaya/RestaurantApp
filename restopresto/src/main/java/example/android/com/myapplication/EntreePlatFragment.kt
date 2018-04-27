package example.android.com.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_entree_plat.*


class EntreePlatFragment : Fragment(/*var _ctxt: Activity*/) {
   // var context?:Context =_ctxt

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_entree_plat, container, false)
        CV_entree?.setOnClickListener({
            showEntreeDialog()
        })
        return view
    }
    fun showEntreeDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_plat, null)
        dialogBuilder.setView(dialogView)
        val b = dialogBuilder.create()
        b.show()
    }

}// Required empty public constructor
