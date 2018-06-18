package example.android.com.RestoPresto.entities
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by start on 18/06/2018.
 */
@Entity(tableName = "user")
data class User (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name="id_user")
        var id_user:Int,
        @ColumnInfo(name = "mail")
        var mail:String,
        @ColumnInfo(name = "password")
        var password:String,
        @ColumnInfo(name = "nom")
        var nom:String,
        @ColumnInfo(name = "prenom")
        var prenom:String,
        @ColumnInfo(name = "adresse")
        var adresse:String,
        @ColumnInfo(name ="preference")
        var preference:Double
)