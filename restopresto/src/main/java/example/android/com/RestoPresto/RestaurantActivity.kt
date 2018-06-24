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
        val restaurantModel = ViewModelProviders.of(this).get(RestaurantModel::class.java)

        val resto = intent.getSerializableExtra("resto") as Restaurant?
        restaurantModel.restaurant=resto!!

        restaurantModel.displayDetail(this,resto!!)

        menujour.setOnClickListener({
            startActivity(intentFor<MenuJourActivity>("id_resto" to resto!!.id_restaurant))
        })
        commande.setOnClickListener({
            startActivity(intentFor<CommanderActivity>("id_resto" to resto!!.id_restaurant))
        })
        reserve.setOnClickListener({
            startActivity(intentFor<ReserverActivity>("id_resto" to resto!!.id_restaurant))
        })
        menus.setOnClickListener({
            startActivity(intentFor<MenuDesMenusActivity>("id_resto" to resto!!.id_restaurant))
        })
        infos.setOnClickListener({
            startActivity(intentFor<InfosActivity>("resto" to resto ))
        })

        if (restaurantModel.restaurant!=null) {
            restaurantModel.displayDetail(this,restaurantModel.restaurant!!)
        }
        else {
            restaurantModel.loadDetail(this,resto!!.id_restaurant)
        }

    }
}
