package example.android.com.RestoPresto
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import example.android.com.RestoPresto.entities.Plat

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuVegetarienFragment : Fragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_vegetarien, container, false)
        val id_restaurant = activity!!.intent.getIntExtra("id_restaurant",0)
        val menuModel =  ViewModelProviders.of(this).get(MenuModel::class.java)
        val listView= view.findViewById<ListView>(R.id.list_plats_vegetariens)
        menuModel.loadDataMenu(id_restaurant,activity!!,"Vegetarien",listView)
        return view
    }
    private data class ViewHolder(var num: TextView){
    }

}