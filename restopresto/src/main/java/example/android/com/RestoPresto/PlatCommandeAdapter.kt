package example.android.com.RestoPresto

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import example.android.com.RestoPresto.entities.plat

/**
 * Created by start on 29/04/2018.
 */
class PlatCommandeAdapter(_ctx : Activity, _listplats:List<plat>): BaseAdapter(){
    var ctx = _ctx
    val listplats = _listplats

    override fun getItem(p0: Int) = listplats.get(p0)
    override fun getItemId(p0: Int) = listplats.get(p0).hashCode().toLong()
    override fun getCount()= listplats.size
    override fun getView(position: Int, p0: View?, parent: ViewGroup?): View {
        var view = p0
        var viewHolder: ViewHolder
        var pTotal:Double
        if(view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.plat_commande_layout,parent,false)
            val nom = view?.findViewById<TextView>(R.id.nom_plat_cmd) as TextView
            val prix = view?.findViewById<TextView>(R.id.prix_plat_cmd) as TextView
            val nb =view?.findViewById<TextView>(R.id.nbplats_cmd)  as TextView
            val prixtotal = view?.findViewById<TextView>(R.id.prix_total_plat_cmd) as TextView
            viewHolder= ViewHolder(nom,prix,nb,prixtotal)
            view.setTag(viewHolder)
        }
        else {
            viewHolder = view.getTag() as ViewHolder
        }

        viewHolder.nom.setText(listplats.get(position).nom)
        viewHolder.prix.setText(" ${listplats.get(position).prix} DZD")
        viewHolder.nb.setText("x"+listplats.get(position).nbCmd)
        pTotal = listplats.get(position).prix.toDouble() * listplats.get(position).nbCmd.toInt()
        viewHolder.prixTotal.setText(pTotal.toString())
        return view
    }
    private data class ViewHolder(var nom: TextView, var prix: TextView, var nb: TextView, var prixTotal : TextView)
}