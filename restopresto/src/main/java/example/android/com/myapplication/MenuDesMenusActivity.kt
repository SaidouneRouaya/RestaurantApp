package example.android.com.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu_des_menus.*
import org.jetbrains.anko.intentFor

class MenuDesMenusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_des_menus)
        val ab = getSupportActionBar()
        ab?.setDisplayHomeAsUpEnabled(true)
        val nom = intent.getStringExtra("nom")
        val i = intent.getIntExtra("pos",0)
        CV_MenuJour.setOnClickListener({
            startActivity(intentFor<MenuJourActivity>("pos" to i, "nom" to nom))
        })
        CV_menu.setOnClickListener({
            startActivity(intentFor<LesMenusActivity>("nom" to nom))
        })
        CV_formuleBinaire.setOnClickListener({
            startActivity(intentFor<FormuleBinaireActivity>("nom" to nom))
        })
    }
}
