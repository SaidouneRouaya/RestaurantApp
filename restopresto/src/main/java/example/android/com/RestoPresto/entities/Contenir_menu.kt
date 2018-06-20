package example.android.com.RestoPresto.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey


/**
 * Created by start on 19/06/2018.
 */
@Entity(tableName = "contenir_menu",
        primaryKeys = arrayOf( "id_menu", "id_plat"),
        foreignKeys = arrayOf(
                ForeignKey(entity = Menu::class,
                        parentColumns = arrayOf("id_menu"),
                        childColumns = arrayOf("id_menu")),
                ForeignKey(entity = Plat::class,
                        parentColumns = arrayOf("id_plat"),
                        childColumns = arrayOf("id_plat"))
        ))
class Contenir_menu(val id_menu: Int, val id_plat: Int)