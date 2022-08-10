package com.vigeo.fnur.main.view

import android.content.Context
import com.vigeo.fnur.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vigeo.fnur.databinding.*
import com.vigeo.fnur.main.service.MainService
import com.vigeo.fnur.main.viewModel.MainVO
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    val mainBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /* retrofit DB 연결 */
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.193:8080/") // http://avcm.vigeotech.com/
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        // 메뉴를 받아와서 뿌린다면 아래 주석처리된 소스를 사용하면됨.
        // 특히 이미지를 같이 받아와서 쓴다면 더좋다. 글자만 받아온다면 문제가 생길수 있음.
        /*
        /* 여백, 너비에 대한 정의 */
        val screenWidth = resources.displayMetrics.widthPixels // 스마트폰의 너비 길이를 가져옴
        val offsetPx = screenWidth - 100
        val menuItems = arrayOf("전체", "#두통", "#감기", "#소화불량", "#치통", "#관절통")
         mainBinding.viewPagerMenu.setPageTransformer { page, position ->
             page.translationX = position * -offsetPx
         }

         //뷰페이저 작업 상단 슬라이드 메뉴
         mainBinding.viewPagerMenu.offscreenPageLimit = 6
         mainBinding.viewPagerMenu.adapter = viewPagerMenuAdapter(layoutInflater, menuItems) // 어댑터 생성
         mainBinding.viewPagerMenu.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        */


        val selectItems = arrayOf("전체", "태국", "말레이시아")
        //스피너(select box)
        val myAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, selectItems)
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

        medList();
    }

    fun medList(medSymptom :String = "", medCountry :String = "") {
        retrofit.create(MainService::class.java).medicineAppList(medSymptom = medSymptom, medCountry = medCountry).enqueue(object :
            Callback<MainVO> {
            override fun onResponse(call: Call<MainVO>, response: Response<MainVO>) {

                if(response.isSuccessful && response.body()!!.message != "error" ){
                    // 정상적으로 통신이 성공된 경우
                    var mainVO = response.body()!!
                    Log.d("medicineAppList : ", "onResponse 성공: " + mainVO.toString());

                    val adapter = mainAdapter(layoutInflater, mainVO, this@MainActivity)
                    adapter.notifyDataSetChanged()
                    mainBinding.recyclerMedicine.adapter = adapter
                    mainBinding.recyclerMedicine.layoutManager = GridLayoutManager(this@MainActivity, 2)

                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    if(response.isSuccessful){
                        Log.d("MileageList : ", "onResponse 실패")
                        var mileageInfoVo = response.body()!!

                        val adapter = mainAdapter(layoutInflater, mileageInfoVo, this@MainActivity)
                        adapter.notifyDataSetChanged()
                        mainBinding.recyclerMedicine.adapter = adapter
                        mainBinding.recyclerMedicine.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }
            override fun onFailure(call: Call<MainVO>, t: Throwable) {
                Log.d("MileageList : ", "onResponse 실패")
            }
        })
    }

}



/* 어뎁터 */
class mainAdapter(
    val layoutInflater: LayoutInflater,
    var mainVO: MainVO,
    var context: Context
) : RecyclerView.Adapter<mainAdapter.ViewHolder>() {

    class ViewHolder(
        recyclerMainbinding: RecyclerMainMedBinding,
        recyclerMainNoDatabinding: RecyclerMainNoMedBinding,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val recyclerMainNoDatabinding = recyclerMainNoDatabinding
        val recyclerMainbinding = recyclerMainbinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var recyclerMainbinding = RecyclerMainMedBinding.inflate(layoutInflater, parent, false)
        var recyclerMainNoDatabinding =
            RecyclerMainNoMedBinding.inflate(layoutInflater, parent, false)

        if (mainVO.medicineListCnt == 0) {
            return ViewHolder(
                recyclerMainbinding,
                recyclerMainNoDatabinding,
                recyclerMainNoDatabinding.root
            )
        } else {
            return ViewHolder(
                recyclerMainbinding,
                recyclerMainNoDatabinding,
                recyclerMainbinding.root
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mainVO.medicineListCnt != 0) {
            holder.recyclerMainbinding.recyclerMedicineImg.load(mainVO.medicineList[position].medImg) {
            }

            holder.recyclerMainbinding.recyclerMedicineImg.apply {
                setOnClickListener {
                    //mainVO.medicineList[position].medNo
                    var str : String = "엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다.엄청긴 문장을 테스트해봅니다."
                    detailDialog(str,str,str,str)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (mainVO.medicineListCnt == 0) {
            return 1
        } else {
            return mainVO.medicineListCnt
        }
    }

    //오류 시 팝업
    fun detailDialog(medDetailEffects: String, medDetailUsage: String, medDetailContraindications: String , medDetailPrecautions: String){

        //val errorDialogView : View = layoutInflater.inflate(R.layout., null)
        val detailDialogView : View = layoutInflater.inflate(R.layout.pop_med_detail, null)
        val detailDialog : BottomSheetDialog = BottomSheetDialog(context)
        detailDialog.setContentView(detailDialogView)
        detailDialog.behavior.isHideable = false

        val detailBinding: PopMedDetailBinding by lazy {
            PopMedDetailBinding.bind(detailDialogView)
        }

        detailBinding.medDetailEffects.text = medDetailEffects
        detailBinding.medDetailUsage.text = medDetailUsage
        detailBinding.medDetailContraindications.text = medDetailContraindications
        detailBinding.medDetailPrecautions.text = medDetailPrecautions

        detailDialog.show()
    }
}

/* 메뉴어뎁터 현재 사용안함. */
class viewPagerMenuAdapter(
    val layoutInflater: LayoutInflater,
    var menuItems: Array<String>
) : RecyclerView.Adapter<viewPagerMenuAdapter.ViewHolder>() {

    class ViewHolder(
        recyclerMainMenutextBinding: RecyclerMainMenuTextBinding,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val recyclerMainMenutextBinding = recyclerMainMenutextBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewPagerMenuAdapter.ViewHolder {
        var recyclerMainMenutextBinding = RecyclerMainMenuTextBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(recyclerMainMenutextBinding, recyclerMainMenutextBinding.root)
    }

    override fun onBindViewHolder(holder: viewPagerMenuAdapter.ViewHolder, position: Int) {
        holder.recyclerMainMenutextBinding.menuText.text = menuItems[position]
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}