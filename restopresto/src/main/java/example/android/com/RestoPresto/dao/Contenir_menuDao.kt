package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Contenir_menu
import example.android.com.RestoPresto.entities.Ligne_commande
import example.android.com.RestoPresto.entities.Plat

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface Contenir_menuDao {
    @Query("select * from contenir_menu")
    fun getContenir_menu():List<Contenir_menu>

    @Query("select * from menu inner join contenir_menu on " +
            "contenir_menu.id_menu = menu.id_menu inner join plat on contenir_menu.id_plat = plat.id_plat " +
            "where contenir_menu.id_menu = :id_menu")
    fun getPlatsByMenu(id_menu:Int):List<Contenir_menu>

    @Insert
    fun addContenir_menus(vararg Contenir_menu: Contenir_menu)

    @Update
    fun updateContenir_menu(Contenir_menu: Contenir_menu)

    @Delete
    fun deleteContenir_menu(Contenir_menu: Contenir_menu)

    @Delete
    fun deleteAllContenir_menus(Contenir_menus:List<Contenir_menu>)
}