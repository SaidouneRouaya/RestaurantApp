package example.android.com.myapplication

import android.app.Activity
import example.android.com.myapplication.entity.Restaurant
import kotlinx.android.synthetic.main.fragment_restaurant.*

/**
 * Created by start on 01/04/2018.
 */
class Util {

    fun displayDetail(act: Activity, resto : Restaurant) {

        act.imagedetail.setImageResource(resto.image)
        act.nomdetail.text = resto.nom
    }
}