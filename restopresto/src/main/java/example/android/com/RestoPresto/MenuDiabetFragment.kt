package example.android.com.RestoPresto


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabItem
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ListView
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.fragment_menu_diabet.*

/**
 * Created by er_sa on 4/22/2018.
 */
class MenuDiabetFragment: Fragment() {
    var pref : SharedPreferences? =null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val view = inflater.inflate(R.layout.fragment_menu_diabet, container, false)
        val id_restaurant = activity!!.intent.getIntExtra("id_resto",0)
        val menuModel =  ViewModelProviders.of(this).get(MenuModel::class.java)
        val listView= view.findViewById<ListView>(R.id.list_plats_diabet)

        val fav = view.findViewById<FloatingActionButton>(R.id.favoris)

        pref = activity!!.getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        menuModel.loadDataMenu(id_restaurant,activity!!,"Diabete",listView)
        val mDb = RoomService.appDatabase.getUserDao()

        val user = mDb.getUsersByID(pref!!.getInt("id_user",0))
        // ligne suivante à enlever
        System.out.println("user name : "+user.nom)
        System.out.println("user id : "+user.id_user)
        System.out.println("user preference : "+user.preference)

        if ( user.preference== "diabetique") fav.visibility=GONE

        else {
            fav.setOnClickListener({
                user.preference="diabetique"
                pref!!.edit().putString("favoris", "diabetique").apply()

                mDb.updateUser(user.id_user,user.preference)

                val userM =   ViewModelProviders.of(this).get(UserModel::class.java)
                userM.updateUserRemote (activity!!, user)

                fav.visibility=GONE
            })
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}// Required empty public constructor
