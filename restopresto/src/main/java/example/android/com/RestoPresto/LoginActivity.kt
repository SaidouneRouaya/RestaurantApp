package example.android.com.RestoPresto


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import example.android.com.RestoPresto.database.AppDatabase
import example.android.com.RestoPresto.entities.User
import example.android.com.RestoPresto.service.RetrofitService
import example.android.com.RestoPresto.singleton.RoomService
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A login screen that offers login via email/password.
 */
//class LoginActivity : AppCompatActivity(), LoaderCallbacks<Cursor> {
class LoginActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private var mAuthTask: UserLoginTask? = null
    private var mDb: AppDatabase? = RoomService.appDatabase
    var preferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Set up the login form.

        preferences = getSharedPreferences("projetMobile", Context.MODE_PRIVATE)
        if (preferences!!.getBoolean("connected",false))
            startActivity(intentFor<MainActivity>())
        email_log_in_button.setOnClickListener {
            val emailStr = email.text.toString()
            val passwordStr = password.text.toString()
            if (!emailStr.isNullOrBlank() && !passwordStr.isNullOrBlank() )
            login(emailStr,passwordStr)

            }
        email_sign_in_button.setOnClickListener{startActivity(intentFor<SignInActivity>())}
    }

    fun login(stremail:String, password:String)
    {
        val user = mDb!!.getUserDao().getUsersByEmail(stremail)
        if (user.isEmpty())
        {
            val call2 = RetrofitService.endpoint.getUserByEmail(stremail)
            call2.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                    Toast.makeText(this@LoginActivity, t!!.message, Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?){
                    if (response?.isSuccessful!!) {
                        val listUser :List<User> = response.body()!!
                        if (listUser.isNotEmpty())
                        {
                            verifyPassword(password,listUser.get(0))
                            mDb!!.getUserDao().addUsers(listUser.get(0))
                        }

                        else
                        {
                            var focusView: View? = email
                            email.error = getString(R.string.error_invalid_email)
                            focusView?.requestFocus()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, response.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        else{
            verifyPassword(password,user.get(0))
        }
    }

    fun verifyPassword(strpassword:String, user:User)
    {
        if (strpassword.equals(user.password))
        {
            preferences!!.edit().putBoolean("connected",true).putInt("id_user",user.id_user)
                    .putString("adresse",user.adresse).putString("email",user.mail)
                    .putString("favoris",user.preference).putString("nom",user.nom)
                    .apply()
            startActivity(intentFor<MainActivity>())
        }
        else
        {
            var focusView: View? = password
            password.error = getString(R.string.error_invalid_password)
            focusView?.requestFocus()
        }
    }

}
