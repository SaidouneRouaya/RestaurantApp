package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.widget.ListView
import android.widget.Toast
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.*
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by start on 21/06/2018.
 */
class FormuleModel: ViewModel() {

    // Gestion de SQLLite
    private var mDb: AppDatabase? = RoomService.appDatabase
    var formules : List<Formule>? = null
    var plats : List<Plat>? = null
    var contenir_formule : List<Contenir_formule>? = null

    fun loadDataFormule(id_restaurant: Int, activity: Activity, typeformule:String,typePlat:String, listviewid: ListView)
    {
        formules = mDb!!.getFormuleDao().getFormulesByRestaurant(id_restaurant,typeformule)

        if (formules!!.isEmpty())
        {
            System.out.println("les formules ne sont pas vides")
            getFormuleFromRemote(id_restaurant,activity,typeformule,listviewid,typePlat)
        }
        else
        {
            System.out.println("les formules  sont vides")
            plats = mDb!!.getContenirFormuleDao().getPlatsByformule(formules!!.get(0).id_formule,typePlat)
            if (plats!!.isEmpty())
            {
                System.out.println("les plats ne sont pas vides")
                getPlatsFromRemote(formules!!.get(0).id_formule,activity,listviewid,typePlat)
            }
            else
            {
                System.out.println("les plats sont vides")
                showFormule(activity,listviewid,plats!!)
            }
        }
    }
    fun getFormuleFromRemote(id_restaurant: Int, activity: Activity, type:String, listviewid:ListView,typePlat:String){
        //remplirRestos()
        val call = RetrofitService.endpoint.getFormulesByRestaurant(id_restaurant+1,type)
        call.enqueue(object : Callback<List<Formule>> {
            override fun onFailure(call: Call<List<Formule>>?, t: Throwable?) {
               // Toast.makeText(activity, "echec 1 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Formule>>?, response: Response<List<Formule>>?) {
                if (response?.isSuccessful!!) {
                    val list: List<Formule> = response.body()!!
                    System.out.println("je passe par menu remote")
                    getPlatsFromRemote(list.get(0).id_formule,activity,listviewid,typePlat)
                } else {
                   // Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        })
    }

    fun getPlatsFromRemote(id_formule: Int, activity: Activity, listviewid:ListView,typePlat:String){
        val call2 = RetrofitService.endpoint.getPlatByFormule(id_formule,typePlat)
        call2.enqueue(object : Callback<List<Plat>> {
            override fun onFailure(call: Call<List<Plat>>?, t: Throwable?) {
                //Toast.makeText(activity!!, "echec 2 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Plat>>?, response: Response<List<Plat>>?){
                if (response?.isSuccessful!!) {
                    val listPlat :List<Plat> = response.body()!!
                    //Toast.makeText(activity, listNormal.toList().get(0).nom, Toast.LENGTH_SHORT).show()
                    System.out.println("je passe par plat remote")
                    showFormule(activity,listviewid,listPlat)
                    //remplirMenus()
                } else {
                    //Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    fun showFormule(activity: Activity,listviewid: ListView,listPlats:List<Plat>)
    {
        val listView= listviewid
        val platFormuleAdapter = platAdapter(activity!!, listPlats)
        listView.adapter = platFormuleAdapter
    }

    fun remplirFormules()
    {
        val formules : List<Formule> = mDb!!.getFormuleDao().getFormules()
        if(formules.isEmpty()) {
            System.out.println("je passe par la aussi")
            val call2 = RetrofitService.endpoint.getFormules()
            call2.enqueue(object : Callback<List<Formule>> {
                override fun onFailure(call: Call<List<Formule>>?, t: Throwable?) {
                    System.out.println("failure")
                }
                override fun onResponse(call: Call<List<Formule>>?, response: Response<List<Formule>>?){
                    if (response?.isSuccessful!!) {
                        val listCM: List<Formule> = response.body()!!
                        System.out.println("je passe par Formules Formules la focntion")
                        for(m in listCM)
                        {
                            mDb!!.getFormuleDao().addFormules(m)
                        }
                        remplirContenirFormule()

                    } else {
                        System.out.println(response.toString())
                    }
                }
            })}
    }

    fun remplirContenirFormule()
    {
        val cFormules : List<Contenir_formule> = mDb!!.getContenirFormuleDao().getContenir_formule()
        if(cFormules.isEmpty()) {
            val call2 = RetrofitService.endpoint.getContenirFormule()
            call2.enqueue(object : Callback<List<Contenir_formule>> {
                override fun onFailure(call: Call<List<Contenir_formule>>?, t: Throwable?) {
                }
                override fun onResponse(call: Call<List<Contenir_formule>>?, response: Response<List<Contenir_formule>>?){
                    if (response?.isSuccessful!!) {
                        val listCM: List<Contenir_formule> = response.body()!!
                        System.out.println("je passe par contenir")
                        for(c in listCM)
                        {
                            mDb!!.getContenirFormuleDao().addContenir_formules(c)
                        }

                    } else {
                    }
                }
            })}
    }
}