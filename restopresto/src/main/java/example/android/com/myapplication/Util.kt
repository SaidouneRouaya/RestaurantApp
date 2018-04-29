package example.android.com.myapplication

import android.app.Activity
import example.android.com.myapplication.entities.Restaurant
import kotlinx.android.synthetic.main.activity_infos.*
import kotlinx.android.synthetic.main.fragment_restaurant.*

/**
 * Created by start on 01/04/2018.
 */
class Util {

    fun displayDetail(act: Activity, resto : Restaurant) {

        act.imagedetail.setImageResource(resto.image)
        act.nomdetail.text = resto.nom
    }

    fun displayInfos(act: Activity,resto: Restaurant)
    {
        act.imageView3.setImageResource(resto.image)
        act.nom_resto.text = resto.nom
        act.adresse.text = "Adresse : "+resto.adresse
        act.numero_resto.text =resto.numero
        act.email_resto.text =resto.email
        act.note.text = "Note : "+resto.note
    }
}