package example.android.com.dao

import android.arch.persistence.room.*
import example.android.com.entities.Restaurant

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface RestaurantDAO {
    @Query("select * from Restaurant")
    fun getRestaurants():List<Restaurant>

    @Query("select * from Restaurant where nom=:RestaurantName ")
    fun getRestaurantsByName(RestaurantName:String):List<Restaurant>

    @Insert
    fun addRestaurants(vararg Restaurant: Restaurant)

    @Update
    fun updateRestaurant(Restaurant: Restaurant)

    @Delete
    fun deleteRestaurant(Restaurant: Restaurant)

    @Delete
    fun deleteAllRestaurants(Restaurants:List<Restaurant>)
}