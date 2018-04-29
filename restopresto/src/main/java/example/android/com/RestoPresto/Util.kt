package example.android.com.RestoPresto

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import example.android.com.RestoPresto.entities.Restaurant
import kotlinx.android.synthetic.main.activity_infos.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.browse

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
        act.email_resto.text ="Email : "+resto.email
        act.note.text = "Note : "+resto.note
    }
    fun openFacebookPage(ctx: Context, facebookUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl))
        ctx.startActivity(intent)
    }

    /**
     * This function opens a web page
     */

    fun browseUrl(ctx:Context,url:String){
        ctx.browse(url)
    }

}