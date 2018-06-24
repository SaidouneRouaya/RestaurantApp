package example.android.com.RestoPresto

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import example.android.com.RestoPresto.entities.Restaurant
import kotlinx.android.synthetic.main.activity_les_menus.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity() {

    var menujourbouton: Button? = null
    var commandebouton: Button? = null
    var reservebouton:Button? = null
    var infosbouton: Button? = null
    var menubouton: Button? = null
    var preferences :SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        menujourbouton =findViewById(R.id.menujour) as? Button
        commandebouton = findViewById(R.id.commande) as? Button
        reservebouton = findViewById(R.id.reserve) as? Button
        infosbouton = findViewById(R.id.infos) as? Button
        menubouton = findViewById(R.id.menus) as? Button


        // View Model instance
        val restaurantModel = ViewModelProviders.of(this).get(RestaurantModel::class.java)
        // Création de l'adapter pour la liste
        restaurantModel.loadData(this)
        setSupportActionBar(toolbar)
        // Action Up
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)

        /* Si la liste des restaurants est vide */
        if (restaurantModel.restaurants==null) {
            restaurantModel.loadData(this)
        }
        else {
            // After the rotation of the screen, use restos of the ViewModel instance
            listrestos.adapter = RestaurantAdapter(this, restaurantModel.restaurants!!)
        }



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
        }

    fun isTwoPane() = findViewById<View>(R.id.fragment2) != null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.logout) {
            preferences!!.edit().putBoolean("connected",false).apply()
            startActivity(intentFor<LoginActivity>())
            return true
        }
        if (id == R.id.favoris)
        {
            val newFragment:FavorisFragment = FavorisFragment.newInstance()
            //newFragment.show(supportFragmentManager,"dialog")
        }

        return super.onOptionsItemSelected(item)
    }
}