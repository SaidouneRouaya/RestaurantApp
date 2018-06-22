package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Formule

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface FormuleDao {
    @Query("select * from formule")
    fun getFormules():List<Formule>

    @Query("select * from formule p natural join restaurant r where r.id_restaurant=:id_restaurant and p.type=:type")
    fun getFormulesByRestaurant(id_restaurant:Int, type : String):List<Formule>

    @Insert
    fun addFormules(vararg Formule: Formule)

    @Update
    fun updateFormule(Formule: Formule)

    @Delete
    fun deleteFormule(Formule: Formule)

    @Delete
    fun deleteAllFormules(Formules:List<Formule>)
}