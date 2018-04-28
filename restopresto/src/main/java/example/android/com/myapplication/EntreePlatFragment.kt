package example.android.com.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_entree_plat.*
import kotlinx.android.synthetic.main.fragment_entree_plat.view.*
import org.jetbrains.anko.toast


class EntreePlatFragment : Fragment(/*var _ctxt: Activity*/) {
   // var context?:Context =_ctxt

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_entree_plat, container, false)
        view.CV_plat?.setOnClickListener({
            val newFragment:PlatFragment = PlatFragment().newInstance()
            newFragment.show(activity?.fragmentManager,"dialog")
        })
        view.CV_entree?.setOnClickListener({
            val newFragment:EntreeFragment = EntreeFragment().newInstance()
            newFragment.show(activity?.fragmentManager,"dialog")
        })
        return view
    }


}// Required empty public constructor
