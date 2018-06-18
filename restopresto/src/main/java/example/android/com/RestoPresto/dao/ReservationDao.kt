package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Reservation

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface ReservationDao {
    @Query("select * from reservation")
    fun getReservations():List<Reservation>

    @Query("select * from reservation p natural join restaurant r where r.id_restaurant=:id_restaurant")
    fun getReservationsByRestaurant(id_restaurant:Int):List<Reservation>

    @Query("select * from reservation p natural join user u where u.id_user=:id_user")
    fun getReservationsByUser(id_user:Int):List<Reservation>

    @Insert
    fun addReservations(vararg Reservation: Reservation)

    @Update
    fun updateReservation(Reservation: Reservation)

    @Delete
    fun deleteReservation(Reservation: Reservation)

    @Delete
    fun deleteAllReservations(Reservations:List<Reservation>)
}