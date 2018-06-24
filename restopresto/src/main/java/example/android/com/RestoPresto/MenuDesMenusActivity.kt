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

            startActivityForResult(intentFor<LesMenusActivity>("id_resto" to i,"nom" to nom),1)
        })
        CV_formuleBinaire.setOnClickListener({
            startActivity(intentFor<FormuleBinaireActivity>("id_resto" to i,"nom" to nom))
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, int: Intent) {
        super.onActivityResult(requestCode, resultCode, int)
        if (requestCode == 1) {
            if (resultCode ==RESULT_OK) {

                i= int.getIntExtra("id_resto",0)
                System.out.println("id resto dans menus des menus  est : "+i)
            }
        }
    }
}
