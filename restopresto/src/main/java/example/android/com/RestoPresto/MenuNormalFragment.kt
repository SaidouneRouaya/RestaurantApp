package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ArrayBlockingQueue

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuNormalFragment : Fragment() {
    var pref : SharedPreferences? =null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu_normal, container, false)
        val id_restaurant = activity!!.intent.getIntExtra("id_resto",0)

        val menuModel =  ViewModelProviders.of(this).get(MenuModel::class.java)
        val listView= view.findViewById<ListView>(R.id.list_plats_normaux)
        val fav = view.findViewById<FloatingActionButton>(R.id.favorisN)
        pref = activity!!.getSharedPreferences("projetMobile", Context.MODE_PRIVATE)

        menuModel.loadDataMenu(id_restaurant,activity!!,"normal",listView)
        val mDb = RoomService.appDatabase.getUserDao()
        val user = mDb.getUsersByID(pref!!.getInt("id_user",0))
        if ( user.preference== "normal") fav.visibility= View.GONE

        else {
            fav.setOnClickListener({
                user.preference="normal"
                pref!!.edit().putString("favoris", "normal").apply()

                mDb.updateUser(user.id_user,user.preference)

                val userM =   ViewModelProviders.of(this).get(UserModel::class.java)
                userM.updateUserRemote (activity!!, user)
                activity?.toast("Catégorie menu Normal marquée favourite ")
                fav.visibility= View.GONE
            })
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


   /* private data class ViewHolder(var num: TextView){
    }*/

}