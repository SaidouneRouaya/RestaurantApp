package example.android.com.RestoPresto.dao

import android.arch.persistence.room.*
import example.android.com.RestoPresto.entities.User
import java.util.prefs.AbstractPreferences

/**
 * Created by start on 18/06/2018.
 */
@Dao
interface UserDao {
    @Query("select * from user")
    fun getUsers():List<User>

    @Query("select * from user where mail=:email ")
    fun getUsersByEmail(email:String):List<User>

    @Query("select * from user where id_user=:id ")
    fun getUsersByID(id:Int):User

    @Insert
    fun addUsers(vararg User: User)

    @Insert
    fun addUser( User: User)


    @Query ("update user set preference=:pref where id_user=:id_user")
    fun updateUser (id_user:Int, pref:String)

    @Delete
    fun deleteUser(User: User)

    @Delete
    fun deleteAllUsers(Users:List<User>)
}