package example.android.com.RestoPresto.service

import example.android.com.RestoPresto.entities.*
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {

    @GET("getrestaurants")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("getrestaurantbyid/{id}")
    fun getDetailRestaurant(@Path("id") id:Int):Call<Restaurant>

    @GET("getrestaurantbyname/{id}")
    fun getRestaurantsByName(@Path("id")id_restaurant :  Int) : Call<Restaurant>

    @GET("getmenusbyrestaurantandtype/{id}&{type}")
    fun getMenusByRestaurant(@Path("id")id_restaurant: Int, @Path("type")type:String) : Call<List<Menu>>

    @GET("getmenusjourbyrestaurantanddate/{id}&{date}")
    fun getMenuJourByRestaurant(@Path("id")id_restaurant: Int,@Path("date")date: Int) : Call<List<Menu>>

    @GET("getplatbymenu/{id}")
    fun getPlatByMenu(@Path("id")id_menu: Int) : Call<List<Plat>>

    @GET("getplatbyformule/{id}&{typePlat}")
    fun getPlatByFormule(@Path("id")id_menu: Int, @Path("typePlat")typePlat:String) : Call<List<Plat>>

    @GET("getplats")
    fun getPlats() : Call<List<Plat>>

    @GET("getformulesbyrestaurantandtype/{id}&{type}")
    fun getFormulesByRestaurant(@Path("id")id_restaurant: Int, @Path("type")type:String) : Call<List<Formule>>

    @GET("getuserbyemail/{email}")
    fun getUserByEmail(@Path("email")mail:String) : Call<List<User>>

    @POST("addUser")
    fun addUser(@Body user : User):Call<String>

    @PUT("updateUser")
    fun updateUser( @Body user:User):Call<String>

    @GET("getcontenirmenu")
    fun getContenirMenu(): Call<List<Contenir_menu>>

    @GET("getmenus")
    fun getMenus(): Call<List<Menu>>

    @GET("getformules")
    fun getFormules() : Call<List<Formule>>

    @GET("getcontenirformule")
    fun getContenirFormule(): Call<List<Contenir_formule>>

    @POST("addReservation")
    fun addReservation(@Body reservation: Reservation):Call<String>

    @POST("addCommande")
    fun addCommande(@Body commande:Commande): Call<String>
}