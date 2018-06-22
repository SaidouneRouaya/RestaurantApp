package example.android.com.RestoPresto

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import example.android.com.RestoPresto.entities.Restaurant
import kotlinx.android.synthetic.main.infos_resto.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.browse

/**
 * Created by start on 01/04/2018.
 */
class Util {

    fun displayDetail(act: Activity, resto : Restaurant) {

        act.imagedetail?.setImageResource(resto.lien.toInt())
        act.nomdetail.text = resto.nom
    }

    fun displayInfos(act: Activity,resto: Restaurant)
    {
        act.imageView3.setImageResource(resto.lien.toInt())
        act.nom_resto.text = resto.nom
        act.adresse.text = "Adresse : "+resto.adresse
        act.numero_resto.text =resto.n_tel
        act.email_resto.text ="Email : "+resto.email
        act.note.text = "Note : "+resto.note
        act.description.text = resto.description
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