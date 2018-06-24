package example.android.com.RestoPresto

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Plat
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.activity_menu_jour.*
import org.jetbrains.anko.intentFor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MenuJourActivity : AppCompatActivity() {
    private var mDb: AppDatabase? = RoomService.appDatabase
    private var menu : List<Menu>? = null
    var plats : List<Plat>? = null
    var entree : Plat = Plat()
    var plat =Plat()
    var dessert = Plat()
    private var id_restaurant:Int = 0
    private var date: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_jour)
        //Action Up
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        val util = Util()
        val calendar = Calendar.getInstance()
        date=calendar.get(Calendar.DAY_OF_WEEK)
        id_restaurant = intent.getIntExtra("id_resto",0)
        val nom = intent.getStringExtra("nom")
        /*val menusjTab = resources.getStringArray(R.array.menujour)
        //menuj.text = menusjTab[i]*/
        loadDataMenujour()
        reserve.setOnClickListener({
            startActivity(intentFor<ReserverActivity>("id_resto" to id_restaurant, "nom" to nom))
        })
    }

    fun loadDataMenujour()
    {

        menu = mDb!!.getMenuDao().getMenusJourByRestaurantJour(id_restaurant,date)
        if(menu!!.isEmpty())
        {
            getMenuJourFromRemote(id_restaurant)
        }
        else
        {
            plats = mDb!!.getContenirMenuDao().getPlatsByMenu(menu!!.get(0).id_menu)
            if (plats!!.isEmpty())
            {
                getPlatsFromRemote(menu!!.get(0).id_menu)
            }
            else
            {
                showMenuJour()
            }
        }
    }

    fun getMenuJourFromRemote(id_resto : Int)
    {
        val call = RetrofitService.endpoint.getMenuJourByRestaurant(id_resto,date)
        call.enqueue(object : Callback<List<Menu>> {
            override fun onFailure(call: Call<List<Menu>>?, t: Throwable?) {
                Toast.makeText(this@MenuJourActivity, "echec 1 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Menu>>?, response: Response<List<Menu>>?) {
                if (response?.isSuccessful!!) {
                    val list: List<Menu> = response.body()!!
                    System.out.println("je passe par menu remote")
                    getPlatsFromRemote(list.get(0).id_menu)
                } else {
                    Toast.makeText(this@MenuJourActivity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }

        })
    }

    fun getPlatsFromRemote(id_menu: Int){
        val call2 = RetrofitService.endpoint.getPlatByMenu(id_menu)
        call2.enqueue(object : Callback<List<Plat>> {
            override fun onFailure(call: Call<List<Plat>>?, t: Throwable?) {
                Toast.makeText(this@MenuJourActivity, "echec 2 !", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<List<Plat>>?, response: Response<List<Plat>>?){
                if (response?.isSuccessful!!) {
                    val listPlat :List<Plat> = response.body()!!

                    showMenuJour()

                } else {
                    Toast.makeText(this@MenuJourActivity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    fun showMenuJour()
    {
        for (p in plats!!)
        {
            if (p.type.equals("Entree"))
                entree = p
            else if(p.type.equals("Plat"))
                plat = p
            else if (p.type.equals("Dessert"))
                dessert = p
        }
        menuj.text = entree.nom+"\n"+plat.nom+"\n"+dessert.nom
        prix.text = ((entree.prix+plat.prix+dessert.prix)*0.9).toString()+" DA"
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        date_jour.text = currentDate
    }

}
