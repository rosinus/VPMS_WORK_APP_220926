package com.vigeo.fnur.main.view

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.fnur.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    val mainBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /* retrofit DB 연결 */
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://avcm.vigeotech.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        val items = arrayOf("아이템0","아이템1","아이템2","아이템3","아이템4")

        val myAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, items)
        var spinner = mainBinding.countrySpinner
        spinner.adapter = myAdapter

      /*  var list = {'선택', '태국', '말레이시아'}

        */


    }
}