package example.android.com.RestoPresto

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_entree_plat.view.*


class EntreePlatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_entree_plat, container, false)
        val formuleModel =  ViewModelProviders.of(this).get(FormuleModel::class.java)
        val id_restaurant = activity!!.intent.getIntExtra("id_resto",0)
        view.CV_plat?.setOnClickListener({
            val newFragment:PlatFragment = PlatFragment().newInstance("entree_plat",formuleModel,id_restaurant)
            newFragment.show(activity?.fragmentManager,"dialog")
        })
        view.CV_entree?.setOnClickListener({
            val newFragment:EntreeFragment = EntreeFragment().newInstance("entree_plat",formuleModel,id_restaurant)
            newFragment.show(activity?.fragmentManager,"dialog")
        })
        return view
    }


}// Required empty public constructor
