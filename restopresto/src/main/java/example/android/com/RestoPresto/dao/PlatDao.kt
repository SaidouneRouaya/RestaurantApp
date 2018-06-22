package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Plat

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface PlatDao {
    @Query("select * from Plat")
    fun getPlats():List<Plat>

    @Query("select * from Plat where id_plat=:id_plat")
    fun getPlatById(id_plat : Int):Plat

    @Insert
    fun addPlats(vararg Plat: Plat)

    @Update
    fun updatePlat(Plat: Plat)

    @Delete
    fun deletePlat(Plat: Plat)

    @Delete
    fun deleteAllPlats(Plats:List<Plat>)
}