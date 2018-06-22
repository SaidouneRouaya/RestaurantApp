package example.android.com.RestoPresto

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import example.android.com.RestoPresto.entities.Restaurant
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.intentFor

class RestaurantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        val util = Util()
        val i = intent.getIntExtra("id_resto",0)

        System.out.println ("valeur de i "+i)

        val restaurantModel = ViewModelProviders.of(this).get(RestaurantModel::class.java)

        //val resto= intent.getSerializableExtra("resto") as Restaurant?

        restaurantModel.loadDetail(this, i)

        val resto = restaurantModel.restaurant

        menujour.setOnClickListener({
            startActivity(intentFor<MenuJourActivity>("pos" to i, "nom" to resto!!.nom))
        })
        commande.setOnClickListener({
            startActivity(intentFor<CommanderActivity>("pos" to i))
        })
        reserve.setOnClickListener({
            startActivity(intentFor<ReserverActivity>("nom" to resto!!.nom))
        })
        menus.setOnClickListener({
            startActivity(intentFor<MenuDesMenusActivity>("nom" to resto!!.nom, "pos" to i))
        })
        infos.setOnClickListener({
            startActivity(intentFor<InfosActivity>("nom" to resto!!.nom, "pos" to i))
        })

        if (restaurantModel.restaurant!=null) {
            restaurantModel.displayDatail(this,restaurantModel.restaurant!!)
        }
        else {
            restaurantModel.loadDetail(this,resto!!.id_restaurant)
        }

    }
}
