package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import example.android.com.RestoPresto.entities.Restaurant
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.infos_resto.*
import org.jetbrains.anko.toast
import android.view.View
import example.android.com.RestoPresto.R.id.nom
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_restaurant.*
import org.jetbrains.anko.act
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by start on 01/04/2018.
 */
class RestaurantModel: ViewModel() {

    var i:Int = 0
    var restaurant: Restaurant? = null
    var restaurants:List<Restaurant>? = null


    fun loadData(act: Activity) {
       // act.progressBar1.visibility = View.VISIBLE
        // Get restaurants from SQLite DB
        restaurants = RoomService.appDatabase.getRestaurantDao().getRestaurants()

        if (restaurants?.size == 0)
            // If the list of restaurants is empty, load from server and save them in SQLite DB
            getrestaurantsFromRemote(act)

        else {
            //act.progressBar1.visibility = View.GONE
            act.listrestos.adapter = RestaurantAdapter(act, restaurants!!)
        }
}

    private fun getrestaurantsFromRemote(act:Activity) {
        val call = RetrofitService.endpoint.getRestaurants()
        call.enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>?, response: Response<List<Restaurant>>?) {
                //act.progressBar1.visibility = View.GONE
                if (response?.isSuccessful!!) {
                    act.toast("response successful 1")
                    restaurants = response?.body()
                   // act.progressBar1.visibility = View.GONE
                    act.listrestos.adapter= RestaurantAdapter(act, restaurants!!)
                    // save restaurants in SQLite DB
                    for (item in restaurants!!)
                    {
                    RoomService.appDatabase.getRestaurantDao().addRestaurants(item)
                    }
                } else {
                    act.toast("Une erreur s'est produite1")
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>?, t: Throwable?) {
               // act.progressBar1.visibility = View.GONE
                act.toast("Une erreur s'est produite2")
            }


        })
    }

    fun loadDetail(act:Activity,idRestaurant:Int) {
        //act.progressBar2.visibility = View.VISIBLE
        // load Restaurant detail from SQLite DB
        this.restaurant = RoomService.appDatabase.getRestaurantDao().getRestaurantByID (idRestaurant)

        if(this.restaurant==null) {
            // if the Restaurant details don't exist, load the details from server and update SQLite DB

            loadDetailFromRemote(act,idRestaurant)
        }
        else {
            //act.progressBar2.visibility = View.GONE

            displayDatail(act, this.restaurant)
        }

    }

    private fun loadDetailFromRemote(act:Activity,idRestaurant:Int) {
        val call = RetrofitService.endpoint.getDetailRestaurant(idRestaurant)
        call.enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>?, response: Response<Restaurant>?) {
                //act.progressBar2.visibility = View.GONE
                if(response?.isSuccessful!!) {
                    var RestaurantDetail = response?.body()
                    displayDatail(act,RestaurantDetail!!)
                    // update the Restaurant in the SQLite DB to support offline mode
                    RoomService.appDatabase.getRestaurantDao().updateRestaurant(RestaurantDetail)
                    // update ViewModel
                    this@RestaurantModel.restaurant= RestaurantDetail

                }
                else {
                    act.toast("Une erreur s'est produite3")

                }


            }

            override fun onFailure(call: Call<Restaurant>?, t: Throwable?) {
                //act.progressBar2.visibility = View.GONE
                act.toast("Une erreur s'est produite4")

            }
        })
    }

    fun displayDatail(act: Activity,Restaurant: Restaurant?) {
           //   Glide.with(act).load(baseUrl+ Restaurant!!.lien).into(act.imageView3)
        System.out.println("lien glide display detail " +baseUrl+ Restaurant!!.lien)
        if (act==null) System.out.println("act121b820ivity is null Glide ")
        if (act.imagedetail==null) System.out.println("act.imagedetail  is null Glide ")


        Glide.with(act).load(baseUrl+ Restaurant!!.lien).into(act.imagedetail)

        act.nomdetail.text = Restaurant!!.nom

    }



}
