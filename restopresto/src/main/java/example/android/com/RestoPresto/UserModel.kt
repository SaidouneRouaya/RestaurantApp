package example.android.com.RestoPresto

import android.app.Activity
import android.arch.lifecycle.ViewModel
import example.android.com.RestoPresto.entities.User
import example.android.com.RestoPresto.service.RetrofitService
import retrofit2.Call
import org.jetbrains.anko.toast
import retrofit2.Callback
import retrofit2.Response


class UserModel: ViewModel() {

         fun updateUserRemote(act: Activity,user: User) {
            val call = RetrofitService.endpoint.updateUser( user)
            call.enqueue(object:Callback<String>
            {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    //act.toast("failure"+t!!.message.toString())
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                   // if(response?.isSuccessful!!){
                     //  act.toast("Catégorie marquée comme favorite")
                        //act.toast("message du call: "+call.toString())

                   // }else
                       // act.toast("nonsuccess "+response.message())

                }
            })
        }
}