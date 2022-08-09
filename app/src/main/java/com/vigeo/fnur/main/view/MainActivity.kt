package com.vigeo.fnur.main.view

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
        val items = arrayOf("전체","태국","말레이시아")

        val myAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, items)
        var spinner = mainBinding.countrySpinner
        spinner.adapter = myAdapter

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // TODO: 클릭했을때 변경되는 로직
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })


    }
}