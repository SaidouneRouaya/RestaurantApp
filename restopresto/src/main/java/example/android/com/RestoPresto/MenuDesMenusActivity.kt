package example.android.com.RestoPresto

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.activity_menu_des_menus.*
import org.jetbrains.anko.intentFor
import android.content.Intent



class MenuDesMenusActivity : AppCompatActivity() {

    var i:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_des_menus)
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        val nom = intent.getStringExtra("nom")
       i = intent.getIntExtra("id_resto",0)


        CV_MenuJour.setOnClickListener({
            startActivity(intentFor<MenuJourActivity>("id_resto" to i, "nom" to nom))
        })
        CV_menu.setOnClickListener({

            startActivity(intentFor<LesMenusActivity>("id_resto" to i,"nom" to nom))
        })
        CV_formuleBinaire.setOnClickListener({
            startActivity(intentFor<FormuleBinaireActivity>("id_resto" to i,"nom" to nom))
        })
    }
}
