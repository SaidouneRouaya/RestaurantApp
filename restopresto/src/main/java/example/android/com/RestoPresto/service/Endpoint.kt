package example.android.com.RestoPresto.service

import example.android.com.RestoPresto.entities.Formule
import example.android.com.RestoPresto.entities.Menu
import example.android.com.RestoPresto.entities.Restaurant
import example.android.com.RestoPresto.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {
/*
    @GET("getteams")
    fun getTeams():Call<List<Team>>


    @GET("getplayersteam/{name}")
    fun getPlayers(@Path("name")name :String):Call<List<Player>>

    @POST("addTeam")
    fun addTeam(@Body team: Team):Call<String>*/

    @GET("getrestaurants")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("getrestaurantbyname/{id}")
    fun getRestaurantsByName(@Path("id")id_restaurant : Int) : Call<Restaurant>

    @GET("getmenusbyrestaurant/{id}")
    fun getMenusByRestaurant(@Path("id")id_restaurant: Int) : Call<List<Menu>>

    @GET("getformulesbyrestaurant/{id}")
    fun getFormulesByRestaurant(@Path("id")id_restaurant: Int) : Call<List<Formule>>

    @GET("getuserbyemail/{email}")
    fun getUserByEmail(@Path("email")mail:String) : Call<User>

    @POST("addUser")
    fun addUser(@Body user : User):Call<String>
}