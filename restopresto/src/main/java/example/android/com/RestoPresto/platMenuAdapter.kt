package example.android.com.RestoPresto

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import example.android.com.RestoPresto.entities.Ligne_commande
import example.android.com.RestoPresto.entities.Plat

/**
 * Created by start on 28/04/2018.
 */
class platMenuAdapter (_ctx: Activity, _listplats:List<Plat>, _id_restaurant :Int ): BaseAdapter() {

    var ctx = _ctx
    val listplats = _listplats
    val id_restaurant = _id_restaurant

    override fun getItem(p0: Int) = listplats.get(p0)
    override fun getItemId(p0: Int) = listplats.get(p0).hashCode().toLong()
    override fun getCount()= listplats.size
    override fun getView(position: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var viewHolder: ViewHolder
        if(view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.plat_layout,parent,false)
            val nom = view?.findViewById<TextView>(R.id.nom_plat) as TextView
            val ingredients = view?.findViewById<TextView>(R.id.ingredients_plat) as TextView
            val prix = view?.findViewById<TextView>(R.id.prix) as TextView
            val nb =view?.findViewById<TextView>(R.id.nbCmd)  as TextView
            val image = view?.findViewById<ImageView>(R.id.image_plat) as ImageView
            viewHolder= ViewHolder(nom, ingredients,prix,nb,image)
            view.setTag(viewHolder)
        }
        else {
            viewHolder = view.getTag() as ViewHolder
        }
        val listeCmd = MenuModel().loadNbPlat(id_restaurant,ctx,listplats)

        System.out.println(" Url est : "+baseUrl + listplats.get(position).lien)
        Glide.with(ctx).load(baseUrl + listplats.get(position).lien)
                .into(viewHolder.image)
        viewHolder.nom.setText(listplats.get(position).nom)
        viewHolder.ingredients.setText(listplats.get(position).ingredient)
        viewHolder.prix.setText(" ${listplats.get(position).prix} DZD")
       // viewHolder.nb.setText(listplats.get(position).nbCmd)
        var ligne_commande = listeCmd.get(listplats.get(position))
        if (ligne_commande != null)
        {
            viewHolder.nb.text = ligne_commande.nombre.toString()
        }
        else
            viewHolder.nb.setText("0")


   return view
    }
   /* private */data class ViewHolder(var nom: TextView, var ingredients: TextView, var prix: TextView, var nb: TextView,var image: ImageView)
}