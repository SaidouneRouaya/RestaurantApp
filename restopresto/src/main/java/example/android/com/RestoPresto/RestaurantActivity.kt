package example.android.com.RestoPresto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import example.android.com.RestoPresto.entities.Restaurant
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.intentFor

class RestaurantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        val util = Util()
        val i = intent.getIntExtra("pos",0)
        val imagesTab = arrayOf(R.drawable.ledey, R.drawable.lallamina, R.drawable.eldjenina, R.drawable.lapalmeraie, R.drawable.eldjazair)
        val nomsTab = resources.getStringArray(R.array.restos)
        val resto = Restaurant(nom = nomsTab[i], image = imagesTab[i])
        menujour.setOnClickListener({
            startActivity(intentFor<MenuJourActivity>("pos" to i, "nom" to nomsTab[i]))
        })
        commande.setOnClickListener({
            startActivity(intentFor<CommanderActivity>("pos" to i))
        })
        reserve.setOnClickListener({
            startActivity(intentFor<ReserverActivity>("nom" to nomsTab[i]))
        })
        menus.setOnClickListener({
            startActivity(intentFor<MenuDesMenusActivity>("nom" to nomsTab[i], "pos" to i))
        })
        infos.setOnClickListener({
            startActivity(intentFor<InfosActivity>("pos" to i))
        })
        util.displayDetail(this,resto)
    }
}
