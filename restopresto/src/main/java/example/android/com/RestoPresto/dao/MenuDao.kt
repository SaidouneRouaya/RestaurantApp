package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.Menu

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface MenuDao {
    @Query("select * from menu")
    fun getMenus():List<Menu>

    @Query("select * from menu p natural join restaurant r where r.id_restaurant=:id_restaurant")
    fun getMenusByRestaurant(id_restaurant:Int):List<Menu>

    @Insert
    fun addMenus(vararg Menu: Menu)

    @Update
    fun updateMenu(Menu: Menu)

    @Delete
    fun deleteMenu(Menu: Menu)

    @Delete
    fun deleteAllMenus(Menus:List<Menu>)
}