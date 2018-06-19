package example.android.com.RestoPresto.singleton

import android.arch.persistence.room.Room
import android.content.Context
import example.android.com.RestoPresto.database.AppDatabase

object RoomService {

    lateinit var context: Context
    val appDatabase: AppDatabase by lazy {

        Room.databaseBuilder(context, AppDatabase::class.java,"restaurant_bdd").allowMainThreadQueries().build()
    }

}