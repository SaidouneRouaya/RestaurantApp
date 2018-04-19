package example.android.com.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import example.android.com.myapplication.entity.Restaurant
import kotlinx.android.synthetic.main.activity_menu_jour.*
import org.jetbrains.anko.intentFor

class MenuJourActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_jour)
        val util = Util()
        val i = intent.getIntExtra("pos",0)
        val menusjTab = resources.getStringArray(R.array.menujour)
        menuj.text = menusjTab[i]
        reserve.setOnClickListener({
            startActivity(intentFor<ReserverActivity>())
        })

    }
}
