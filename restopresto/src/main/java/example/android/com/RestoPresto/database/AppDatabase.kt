package example.android.com.RestoPresto.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import example.android.com.RestoPresto.dao.*
import example.android.com.RestoPresto.entities.*

/**
 * Created by start on 23/04/2018.
 */
@Database(entities = arrayOf(Restaurant::class,Formule::class,Commande::class, Ligne_commande::class,
        Menu::class, Plat::class, Reservation::class, User::class),version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getRestaurantDao() : RestaurantDao

    abstract fun getCommandeDao() : CommandeDao

    abstract fun getFormuleDao() : FormuleDao

    abstract fun getLigneCommandeDao() : Ligne_commandeDao

    abstract fun getMenuDao() : MenuDao

    abstract fun getPlatDao() : PlatDao

    abstract fun getReservationDao() : ReservationDao

    abstract fun getUserDao() : UserDao

}