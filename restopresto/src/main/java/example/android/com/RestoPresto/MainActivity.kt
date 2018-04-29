package example.android.com.RestoPresto

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import example.android.com.RestoPresto.entities.Restaurant
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.intentFor


class MainActivity : AppCompatActivity() {

    var detailImagesTab = arrayOf<Int>()
    var detailNomsTab = arrayOf<String>()
    var menujourbouton: Button? = null
    var commandebouton: Button? = null
    var reservebouton:Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menujourbouton =findViewById(R.id.menujour) as? Button
        commandebouton = findViewById(R.id.commande) as? Button
        reservebouton = findViewById(R.id.reserve) as? Button
        // Instance of Uiil class............. rouya
        val util = Util()
        // View Model instance
        val restaurantModel = ViewModelProviders.of(this).get(RestaurantModel::class.java)
        // Création de l'adapter pour la liste
        val restoAdapter = RestaurantAdapter(this, loadData())
        listrestos.adapter = restoAdapter
        restaurantModel.restaurant = Restaurant(nom = detailNomsTab[0], image = detailImagesTab[0])

        if (isTwoPane()) {
            util.displayDetail(this,restaurantModel.restaurant)
            menujourbouton?.setOnClickListener({
                startActivity(intentFor<MenuJourActivity>("pos" to 0))
            })
            commandebouton?.setOnClickListener({
                startActivity(intentFor<CommanderActivity>("pos" to 0))
            })
            reservebouton?.setOnClickListener({
                startActivity(intentFor<ReserverActivity>("nom" to detailNomsTab[0]))
            })
        }

        // Listner pour les éléments de la liste
        listrestos.setOnItemClickListener { adapterView, view, i, l ->

            if (isTwoPane()) {
                // display detail data
                restaurantModel.restaurant = Restaurant(nom = detailNomsTab[i], image = detailImagesTab[i])
                util.displayDetail(this,restaurantModel.restaurant)
                menujourbouton?.setOnClickListener({
                    startActivity(intentFor<MenuJourActivity>("pos" to i))
                })
                commandebouton?.setOnClickListener({
                    startActivity(intentFor<CommanderActivity>("pos" to i))
                })
                reservebouton?.setOnClickListener({
                    startActivity(intentFor<ReserverActivity>("nom" to detailNomsTab[i]))
                })
                menus.setOnClickListener({
                    startActivity(intentFor<LesMenusActivity>("nom" to detailNomsTab[i]))
                })
            }
            else {
                // send the position to the detail activity
                startActivity(intentFor<RestaurantActivity>("pos" to i))
            }
            }
        }

    fun loadData(): List<Restaurant> {
        detailImagesTab = arrayOf(R.drawable.ledey, R.drawable.lallamina, R.drawable.eldjenina, R.drawable.lapalmeraie, R.drawable.eldjazair)
        detailNomsTab=resources.getStringArray(R.array.restos)
        val imagesTab = arrayOf(R.drawable.ledey, R.drawable.lallamina, R.drawable.eldjenina, R.drawable.lapalmeraie, R.drawable.eldjazair)
        val nomsTab = resources.getStringArray(R.array.restos)
        val notesTab = resources.getStringArray(R.array.notes)
        val list = mutableListOf<Restaurant>()
        for (i in 0..imagesTab.size - 1) {
            list.add(Restaurant(nom = nomsTab[i], image = imagesTab[i], note = notesTab[i]))

        }
        return list
    }
    fun isTwoPane() = findViewById<View>(R.id.fragment2) != null
}