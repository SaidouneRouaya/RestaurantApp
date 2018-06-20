package example.android.com.RestoPresto.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    val endpoint:Endpoint by lazy {
        Retrofit.Builder().baseUrl("http://192.168.0.239:8082/").
                addConverterFactory(GsonConverterFactory.create()).build().create(Endpoint::class.java)
    }
}