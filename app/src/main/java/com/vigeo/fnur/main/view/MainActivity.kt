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
import com.vigeo.fnur.main.viewModel.commonCodeObject
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
        .baseUrl("http://fnuri.vigeotech.com/") // http://avcm.vigeotech.com/
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var selectItems : ArrayList<commonCodeObject> = ArrayList()
    var selectItemsName : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Fnur)
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
        var spinner = mainBinding.countrySpinner

        //전체선택
        mainBinding.menuAll.setOnClickListener{
            var selectSpinner = spinner.selectedItem.toString()
            allActiveRemove()
            mainBinding.menuAll.setBackgroundResource(R.drawable.menu_active_border)
            medList(medCountry = selectSpinner);
        }

        mainBinding.menuHeadache.setOnClickListener{
            var selectSpinner = spinner.selectedItem.toString()
            allActiveRemove()
            mainBinding.menuHeadache.setBackgroundResource(R.drawable.menu_active_border)
            if(selectSpinner == "전체"){
                selectSpinner = ""
            }

            medList(medSymptom = "두통", medCountry = selectSpinner)
        }

        mainBinding.menuCold.setOnClickListener{
            var selectSpinner = spinner.selectedItem.toString()
            allActiveRemove()
            mainBinding.menuCold.setBackgroundResource(R.drawable.menu_active_border)
            if(selectSpinner == "전체"){
                selectSpinner = ""
            }

            medList(medSymptom = "감기", medCountry = selectSpinner)
        }

        mainBinding.menuDyspepsia.setOnClickListener{
            var selectSpinner = spinner.selectedItem.toString()
            allActiveRemove()
            mainBinding.menuDyspepsia.setBackgroundResource(R.drawable.menu_active_border)
            if(selectSpinner == "전체"){
                selectSpinner = ""
            }

            medList(medSymptom = "소화불량", medCountry = selectSpinner)
        }

        mainBinding.menuToothache.setOnClickListener{
            var selectSpinner = spinner.selectedItem.toString()
            allActiveRemove()
            mainBinding.menuToothache.setBackgroundResource(R.drawable.menu_active_border)
            if(selectSpinner == "전체"){
                selectSpinner = ""
            }

            medList(medSymptom = "치통", medCountry = selectSpinner)
        }

        mainBinding.menuJoint.setOnClickListener{
            var selectSpinner = spinner.selectedItem.toString()
            allActiveRemove()
            mainBinding.menuJoint.setBackgroundResource(R.drawable.menu_active_border)
            if(selectSpinner == "전체"){
                selectSpinner = ""
            }

            medList(medSymptom = "관절통", medCountry = selectSpinner)
        }

        countryList()

        medList();
    }

    fun allActiveRemove() {
        mainBinding.menuAll.background = null
        mainBinding.menuHeadache.background = null
        mainBinding.menuCold.background = null
        mainBinding.menuDyspepsia.background = null
        mainBinding.menuToothache.background = null
        mainBinding.menuJoint.background = null
    }

    fun spinner() {
        //스피너(select box)
        val myAdapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, selectItemsName)
        var spinner = mainBinding.countrySpinner
        spinner.adapter = myAdapter
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectSpinner = spinner.selectedItem.toString()
                if(selectSpinner == "전체"){
                    selectSpinner = ""
                }

                //나라 이미지 변경
                mainBinding.CountryImg.load(selectItems[position].comImg) {}

                //현재 나라변경시 전체로 이동중
                allActiveRemove()
                mainBinding.menuAll.setBackgroundResource(R.drawable.menu_active_border)

                medList(medCountry = selectSpinner)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
    }

    fun countryList() {
        retrofit.create(MainService::class.java).countryAppList().enqueue(object :
            Callback<MainVO> {
            override fun onResponse(call: Call<MainVO>, response: Response<MainVO>) {

                if(response.isSuccessful && response.body()!!.message != "error" ){
                    // 정상적으로 통신이 성공된 경우
                    var mainVO = response.body()!!
                    Log.d("medicineAppList : ", "onResponse 성공: " + mainVO.toString());
                    //selectItems.add("전체")

                    //나라 이름 넣어주기 작업
                    for (commonCodeObject in mainVO.countryAppList){
                        selectItems.add(commonCodeObject)
                        selectItemsName.add(commonCodeObject.comNm)
                    }

                    spinner();

                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    if(response.isSuccessful){
                        Log.d("MileageList : ", "onResponse 실패")

                    }
                }
            }
            override fun onFailure(call: Call<MainVO>, t: Throwable) {
                Log.d("MileageList : ", "onResponse 실패")
            }
        })
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

                    if(mainVO.medicineList.size > 0){
                        mainBinding.recyclerMedicine.layoutManager = GridLayoutManager(this@MainActivity, 2)
                    }else{
                        mainBinding.recyclerMedicine.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
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

    /* retrofit DB 연결 */
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://fnuri.vigeotech.com/") // http://avcm.vigeotech.com/
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

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

                    medSelect(mainVO.medicineList[position].medNo)
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

    //물품 선택시 상세 팝업
    fun detailDialog(
        medDetailEffects: String,
        medDetailUsage: String,
        medDetailContraindications: String,
        medDetailPrecautions: String,
        medImg: String
    ){

        val detailDialogView : View = layoutInflater.inflate(R.layout.pop_med_detail, null)
        val detailDialog : BottomSheetDialog = BottomSheetDialog(context)
        detailDialog.setContentView(detailDialogView)
        detailDialog.behavior.isHideable = false

        val detailBinding: PopMedDetailBinding by lazy {
            PopMedDetailBinding.bind(detailDialogView)
        }

        var medDetailEffectsTemp : String = ""
        var medDetailUsageTemp : String = ""
        var medDetailContraindicationsTemp : String = ""
        var medDetailPrecautionsTemp : String = ""


        if(medDetailEffects == ""){
            medDetailEffectsTemp = "해당사항 없음"
        }else{
            medDetailEffectsTemp = medDetailEffects
        }

        if(medDetailUsage == ""){
            medDetailUsageTemp = "해당사항 없음"
        }else{
            medDetailUsageTemp = medDetailUsage
        }

        if(medDetailContraindications == ""){
            medDetailContraindicationsTemp = "해당사항 없음"
        }else{
            medDetailContraindicationsTemp = medDetailContraindications
        }

        if(medDetailPrecautions == ""){
            medDetailPrecautionsTemp = "해당사항 없음"
        }else{
            medDetailPrecautionsTemp = medDetailPrecautions
        }

        detailBinding.medDetailImg.load(medImg) {
        }

        detailBinding.medDetailEffects.text = medDetailEffectsTemp
        detailBinding.medDetailUsage.text = medDetailUsageTemp
        detailBinding.medDetailContraindications.text = medDetailContraindicationsTemp
        detailBinding.medDetailPrecautions.text = medDetailPrecautionsTemp

        detailBinding.medDetailClose.setOnClickListener{
            detailDialog.dismiss()
        }

        detailDialog.show()
    }

    fun medSelect(medNo :String = "") {
        retrofit.create(MainService::class.java).medicineAppSelect(medNo = medNo).enqueue(object :
            Callback<MainVO> {
            override fun onResponse(call: Call<MainVO>, response: Response<MainVO>) {

                if(response.isSuccessful && response.body()!!.message != "error" ){
                    // 정상적으로 통신이 성공된 경우
                    var mainVO = response.body()!!
                    Log.d("medicineAppList : ", "onResponse 성공: " + mainVO.toString());

                    detailDialog(mainVO.medicineSelect.medEffects,mainVO.medicineSelect.medUsage,mainVO.medicineSelect.medContraindications,mainVO.medicineSelect.medPrecautions, mainVO.medicineSelect.medImg)

                }else{
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    if(response.isSuccessful){
                        Log.d("MileageList : ", "onResponse 실패")
                        Toast.makeText(context, "통신이 실패했습니다. 관리자에게 문의해주세요.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<MainVO>, t: Throwable) {
                Log.d("MileageList : ", "onResponse 실패")
            }
        })
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