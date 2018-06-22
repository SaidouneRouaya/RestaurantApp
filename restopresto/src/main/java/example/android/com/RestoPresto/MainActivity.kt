package example.android.com.RestoPresto

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.Restaurant
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    var detailImagesTab = arrayOf<Int>()
    var detailNomsTab = arrayOf<String>()
    var menujourbouton: Button? = null
    var commandebouton: Button? = null
    var reservebouton:Button? = null
    var infosbouton: Button? = null
    var menubouton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menujourbouton =findViewById(R.id.menujour) as? Button
        commandebouton = findViewById(R.id.commande) as? Button
        reservebouton = findViewById(R.id.reserve) as? Button
        infosbouton = findViewById(R.id.infos) as? Button
        menubouton = findViewById(R.id.menus) as? Button
        // Instance of Uiil class............. rouya

        // View Model instance
        val restaurantModel = ViewModelProviders.of(this).get(RestaurantModel::class.java)
        // Création de l'adapter pour la liste
        val restoAdapter = RestaurantAdapter(this, loadData())
        listrestos.adapter = restoAdapter



        if (isTwoPane()  && restaurantModel.restaurant!=null) {

              //  restaurantModel.displayDatail (this,restaurantModel.restaurant!!)
            restaurantModel.loadDetail(this,restaurantModel.restaurant!!.id_restaurant)

            menujourbouton?.setOnClickListener({
                startActivity(intentFor<MenuJourActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
            })
            commandebouton?.setOnClickListener({
                startActivity(intentFor<CommanderActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
            })
            reservebouton?.setOnClickListener({
                startActivity(intentFor<ReserverActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
            })
            menubouton?.setOnClickListener({
                startActivity(intentFor<MenuDesMenusActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
            })
            infosbouton?.setOnClickListener({
                startActivity(intentFor<InfosActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
            }
            )}
            else
            {
                restaurantModel.loadDetail(this,0)

                System.out.println("contenu du intent est 0")


                menujourbouton?.setOnClickListener({
                    startActivity(intentFor<MenuJourActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
                })
                commandebouton?.setOnClickListener({
                    startActivity(intentFor<CommanderActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
                })
                reservebouton?.setOnClickListener({
                    startActivity(intentFor<ReserverActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
                })
                menubouton?.setOnClickListener({
                    startActivity(intentFor<MenuDesMenusActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
                })
                infosbouton?.setOnClickListener({
                    startActivity(intentFor<InfosActivity>("id_resto" to restaurantModel.restaurant!!.id_restaurant))
                })
            }



        // Listner pour les éléments de la liste
        listrestos.setOnItemClickListener { adapterView, view, i, l ->
            val resto = (adapterView.getItemAtPosition(i) as Restaurant)
            if (isTwoPane()) {
                // display detail data
                /*restaurantModel.restaurant = Restaurant(nom = detailNomsTab[i], lien = detailImagesTab[i].toString())
                restaurantModel.i = i*/

                restaurantModel.displayDetail(this,resto)

                menujourbouton?.setOnClickListener({
                    startActivity(intentFor<MenuJourActivity>("id_resto" to resto.id_restaurant))
                })
                commandebouton?.setOnClickListener({
                    startActivity(intentFor<CommanderActivity>("id_resto" to resto.id_restaurant))
                })
                reservebouton?.setOnClickListener({
                    startActivity(intentFor<ReserverActivity>("id_resto" to resto.id_restaurant))
                })
                menubouton?.setOnClickListener({
                    startActivity(intentFor<MenuDesMenusActivity>("id_resto" to resto.id_restaurant))
                })
                infosbouton?.setOnClickListener({
                    startActivity(intentFor<InfosActivity>("resto" to resto))
                })
            }
            else {
                // send the position to the detail activity
                startActivity(intentFor<RestaurantActivity>("resto" to resto))
            }
            }
       // remplirRestos()
        }

    fun loadData(): List<Restaurant> {
        detailImagesTab = arrayOf(R.drawable.ledey, R.drawable.lallamina, R.drawable.eldjenina, R.drawable.lapalmeraie, R.drawable.eldjazair)
        detailNomsTab=resources.getStringArray(R.array.restos)
        val imagesTab = arrayOf(R.drawable.ledey, R.drawable.lallamina, R.drawable.eldjenina, R.drawable.lapalmeraie, R.drawable.eldjazair)
        val nomsTab = resources.getStringArray(R.array.restos)
        val notesTab = resources.getStringArray(R.array.notes)
        val list = mutableListOf<Restaurant>()
        for (i in 0..imagesTab.size - 1) {
            list.add(Restaurant(nom = nomsTab[i], lien = imagesTab[i].toString(), note = notesTab[i]))

        }
        return list
    }
   /* fun remplirRestos()
    {
        var mDb: AppDatabase? = RoomService.appDatabase
        var restos : List<Restaurant> = mDb!!.getRestaurantDao().getRestaurants()
        if(restos.isEmpty()) {
            val call2 = RetrofitService.endpoint.getRestaurants()
            System.out.println("juste avant la fonction enqueue ta3 remplir restos")
            call2.enqueue(object : Callback<List<Restaurant>> {
                override fun onFailure(call: Call<List<Restaurant>>?, t: Throwable?) {
                    System.out.println("failure")
                }

                override fun onResponse(call: Call<List<Restaurant>>?, response: Response<List<Restaurant>>?) {
                    if (response?.isSuccessful!!) {
                        val listRestos: List<Restaurant> = response.body()!!
                        for (r in listRestos) {
                            mDb!!.getRestaurantDao().addRestaurants(r)
                            System.out.println(r.nom)
                        }
                        System.out.println("dakhel enqueue")

                    } else {
                        System.out.println("non_response")
                    }
                }
            })
        }
    }*/
    fun isTwoPane() = findViewById<View>(R.id.fragment2) != null
}