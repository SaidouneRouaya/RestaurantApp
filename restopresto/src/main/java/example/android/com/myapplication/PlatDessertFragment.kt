package example.android.com.myapplication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_plat_dessert.view.*



/**
 * A simple [Fragment] subclass.
 */
class PlatDessertFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_plat_dessert, container, false)
        view.CV_plat?.setOnClickListener({
            val newFragment:PlatFragment = PlatFragment().newInstance()
            newFragment.show(activity?.fragmentManager,"dialog")
        })
        view.CV_dessert?.setOnClickListener({
            val newFragment:DessertFragment = DessertFragment().newInstance()
            newFragment.show(activity?.fragmentManager,"dialog")

        })
        return view
    }


}// Required empty public constructor
