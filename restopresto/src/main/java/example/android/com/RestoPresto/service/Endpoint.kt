package example.android.com.RestoPresto.service

import example.android.com.RestoPresto.entities.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Endpoint {

    @GET("getrestaurants")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("getrestaurantbyname/{id}")
    fun getRestaurantsByName(@Path("id")id_restaurant : Int) : Call<Restaurant>

    @GET("getmenusbyrestaurantandtype/{id}&{type}")
    fun getMenusByRestaurant(@Path("id")id_restaurant: Int, @Path("type")type:String) : Call<List<Menu>>

    @GET("getplatbymenu/{id}")
    fun getPlatByMenu(@Path("id")id_menu: Int) : Call<List<Plat>>

    @GET("getformulesbyrestaurant/{id}")
    fun getFormulesByRestaurant(@Path("id")id_restaurant: Int) : Call<List<Formule>>

    @GET("getuserbyemail/{email}")
    fun getUserByEmail(@Path("email")mail:String) : Call<User>

    @POST("addUser")
    fun addUser(@Body user : User):Call<String>
}