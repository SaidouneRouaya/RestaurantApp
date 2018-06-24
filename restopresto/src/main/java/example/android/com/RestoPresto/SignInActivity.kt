package example.android.com.RestoPresto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import example.android.com.RestoPresto.entities.User
import example.android.com.RestoPresto.service.RetrofitService
import kotlinx.android.synthetic.main.activity_signin.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        valider.setOnClickListener({
            if (username.editText!!.text.isNotEmpty() && userprename.editText!!.text.isNotEmpty() &&
                    useradresse.editText!!.text.isNotEmpty() && useremail.editText!!.text.isNotEmpty()
            && userpassword.editText!!.text.isNotEmpty())
            {
                val user = User(0,useremail.editText!!.text.toString(),userpassword.editText!!.text.toString()
                        ,username.editText!!.text.toString(),userprename.editText!!.text.toString(),
                        useradresse.editText!!.text.toString(),"normal")
                val call = RetrofitService.endpoint.addUser(user)
                call.enqueue(object: Callback<String>
                {
                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                       // toast("failure"+t!!.message.toString())
                    }

                    override fun onResponse(call: Call<String>?, response: Response<String>?) {
                        if(response?.isSuccessful!!) {
                            toast("Compte créé !")
                            startActivity(intentFor<LoginActivity>())
                        }
                        /*else
                            toast("nonsuccess"+response.message())*/

                    }

                })
            }
            else
            {
                toast("Veuillez remplir tous les champs !")
            }
        })
    }
}
