package example.android.com.RestoPresto
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.singleton.RoomService
import org.jetbrains.anko.toast

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuVegetarienFragment : Fragment(){

    var pref : SharedPreferences? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_vegetarien, container, false)
        val id_restaurant = activity!!.intent.getIntExtra("id_resto",0)
        val menuModel =  ViewModelProviders.of(this).get(MenuModel::class.java)
        val listView= view.findViewById<ListView>(R.id.list_plats_vegetariens)
        val fav = view.findViewById<FloatingActionButton>(R.id.favorisV)
        pref = activity!!.getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        menuModel.loadDataMenu(id_restaurant,activity!!,"vegetarien",listView)
        val mDb = RoomService.appDatabase.getUserDao()
        val user = mDb.getUsersByID(pref!!.getInt("id_user",0))

        if ( user.preference== "vegetarien") fav.visibility= View.GONE

        else {
            fav.setOnClickListener({
                user.preference="vegetarien"
                pref!!.edit().putString("favoris", "vegetarien").apply()

                mDb.updateUser(user.id_user,user.preference)

                val userM =   ViewModelProviders.of(this).get(UserModel::class.java)
                userM.updateUserRemote (activity!!, user)

                activity?.toast("Catégorie menu Vegetarien marquée favorite ")
                fav.visibility= View.GONE
            })
        }


        return view
    }
    private data class ViewHolder(var num: TextView){
    }

}