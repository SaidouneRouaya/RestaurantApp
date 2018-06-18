package example.android.com.RestoPresto.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by start on 18/06/2018.
 */
@Entity(tableName="plat")
data class Plat (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name ="id_plat")
        var id_plat:Int=1,
        @ColumnInfo(name ="nom")
        var nom : String="",
        @ColumnInfo(name ="ingredient")
        var ingredient : String="",
        @ColumnInfo(name = "type")
        var type: String="",
        @ColumnInfo(name ="prix")
        var prix : Double=0.0,
        @ColumnInfo(name ="lien")
        var lien : String=""
)