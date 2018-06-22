package example.android.com.RestoPresto

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.intentFor

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val handler = Handler()
        val pref = getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        handler.postDelayed(Runnable {
            if (pref.getBoolean("connected",false))
                startActivity(intentFor<MainActivity>())
            else
                    startActivity(intentFor<LoginActivity>()) }, 2000)
    }
}
