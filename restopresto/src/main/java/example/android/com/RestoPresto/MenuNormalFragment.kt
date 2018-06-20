package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ArrayBlockingQueue

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuNormalFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_normal, container, false)
        val id_restaurant = activity!!.intent.getIntExtra("id_restaurant",0)
        val menuModel =  ViewModelProviders.of(this).get(MenuModel::class.java)
        val listView= view.findViewById<ListView>(R.id.list_plats_normaux)
        menuModel.loadDataMenu(id_restaurant,activity!!,"Normal",listView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


   /* private data class ViewHolder(var num: TextView){
    }*/

}