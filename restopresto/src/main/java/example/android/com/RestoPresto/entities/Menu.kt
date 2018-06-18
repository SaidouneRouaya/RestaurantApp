package example.android.com.RestoPresto.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by start on 18/06/2018.
 */
@Entity(tableName = "menu",foreignKeys = arrayOf(ForeignKey(entity = Restaurant::class,
        parentColumns = arrayOf("id_restaurant"),
        childColumns = arrayOf("id_restaurant"),
        onDelete = ForeignKey.CASCADE)))
data class Menu (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id_menu")
        var id_menu:Int,
        @ColumnInfo(name = "type")
        var type :String,
        @ColumnInfo(name="id_restaurant")
        var id_restaurant:Int
)