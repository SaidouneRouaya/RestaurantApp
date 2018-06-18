package example.android.com.RestoPresto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import example.android.com.RestoPresto.entities.Restaurant

/**
 * Created by hakim on 3/3/18.
 */
class RestaurantAdapter(_ctx:Context, _listRestaurants:List<Restaurant>):BaseAdapter() {
    var ctx = _ctx
    val listRestaurants = _listRestaurants


    override fun getItem(p0: Int) = listRestaurants.get(p0)

    override fun getItemId(p0: Int) = listRestaurants.get(p0).hashCode().toLong()

    override fun getCount()= listRestaurants.size

    override fun getView(position: Int, p0: View?, parent: ViewGroup?): View {
    var view = p0
    var viewHolder: ViewHolder
        if(view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.restaurant_layout,parent,false)
            val image = view?.findViewById<ImageView>(R.id.image) as ImageView
            val nom = view?.findViewById<TextView>(R.id.nom) as TextView
            val note = view?.findViewById<TextView>(R.id.note) as TextView
            viewHolder= ViewHolder(image,nom,note)
            view.setTag(viewHolder)
        }
        else {
            viewHolder = view.getTag() as ViewHolder

        }
        viewHolder.image.setImageResource(listRestaurants.get(position).lien.toInt())
        viewHolder.nom.setText(listRestaurants.get(position).nom)
        viewHolder.note.setText("Note : "+listRestaurants.get(position).note)

        return view
    }

    private data class ViewHolder(var image: ImageView, var nom: TextView, var note: TextView)
}