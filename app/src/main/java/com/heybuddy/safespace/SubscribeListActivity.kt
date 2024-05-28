package com.heybuddy.safespace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.heybuddy.safespace.basic_component.AdapterSetting
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivitySubscribeListBinding
import com.heybuddy.safespace.dto.SubscribeInformationDto
import com.heybuddy.safespace.dto.WorkplaceInformationDto
import com.heybuddy.safespace.service.SubscribeInformationService
import com.heybuddy.safespace.service.WorkplaceInformationService
import com.heybuddy.safespace.subscribe.SubscribeInfo
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SubscribeListActivity: AppCompatActivity() {
    private lateinit var bind: ActivitySubscribeListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var workplaceInformationService: WorkplaceInformationService
    private lateinit var subscribeInformationService: SubscribeInformationService
    private lateinit var auth: FirebaseAuth

    private lateinit var adapter: SubscribesListAdapter

    private lateinit var companyNameTv: TextView
    private lateinit var ipTv: TextView
    private lateinit var emailTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySubscribeListBinding.inflate(layoutInflater)
        setContentView(bind.root)

        auth = FirebaseAuth.getInstance()
        if(auth.uid == null) { //만약 로그인이 안되어 있는 경우
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        retrofit = RetrofitSetting.getRetrofit()
        workplaceInformationService = retrofit.create(WorkplaceInformationService::class.java)
        subscribeInformationService = retrofit.create(SubscribeInformationService::class.java)

        ipTv = bind.ipTv
        emailTv = bind.emailTv
        companyNameTv = bind.companyNameTv


        adapter = SubscribesListAdapter()
        bind.subscribeListView.adapter = adapter
        AdapterSetting.setListViewHeightBaseOnChildren(bind.subscribeListView)
        bind.productBuyBtn.setOnClickListener{
            startActivity(Intent(this, ProductListActivity::class.java))
        }

        loginCheckAndSetting(workplaceInformationService,
            subscribeInformationService)
    }

    private fun loginCheckAndSetting(workplaceInformationService: WorkplaceInformationService,
                                     subscribeInformationService: SubscribeInformationService) {
        val call = workplaceInformationService.existWorkplace(auth.uid!!)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(p0: Call<Boolean>, rps: Response<Boolean>) {
                Toast.makeText(this@SubscribeListActivity, "Communication Success! : " + rps.body(), Toast.LENGTH_SHORT)
                    .show()
                if (rps.body() ?: false) {
                    emailTv.text = auth.currentUser!!.email
                    getWorkplaceInformation(workplaceInformationService)
                    setSubscribeInformationList(subscribeInformationService, auth.uid!!)
                } else {
                    //로그인 정보는 있으나 DB 테이블에 없는 경우
                    startActivity(Intent(this@SubscribeListActivity, JoinInfoActivity::class.java))
                    this@SubscribeListActivity.finish()
                }
            }

            override fun onFailure(p0: Call<Boolean>, p1: Throwable) {
                Toast.makeText(this@SubscribeListActivity, "Communication Fail! : ", Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun setSubscribeInformationList(subscribeInformationService: SubscribeInformationService, uid: String){
        subscribeInformationService.subscribe_infoFindByUid(uid).enqueue(
            object: Callback<List<SubscribeInformationDto>>{
                override fun onResponse(
                    p0: Call<List<SubscribeInformationDto>>,
                    body: Response<List<SubscribeInformationDto>>
                ) {
                    if(body.body() == null || body.body()!!.isEmpty()) {
                        bind.emptyTv.visibility = View.VISIBLE
                        return
                    }

                    bind.emptyTv.visibility = View.GONE
                    val list = body.body()!!

                    for(s in list)
                        adapter.addItem(s)

                    Toast.makeText(this@SubscribeListActivity,
                        "subscribe info : " + list.size,Toast.LENGTH_SHORT).show()


                    adapter.notifyDataSetChanged()
                    AdapterSetting.setListViewHeightBaseOnChildren(bind.subscribeListView)
                }

                override fun onFailure(p0: Call<List<SubscribeInformationDto>>, p1: Throwable) {
                    Toast.makeText(this@SubscribeListActivity, "Communication Fail! : " + p1.message,Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
    private fun getWorkplaceInformation(workplaceInformationService: WorkplaceInformationService){
        val call = workplaceInformationService.findWorkplace(auth.uid!!)

        call.enqueue(object: Callback<WorkplaceInformationDto>{
            override fun onResponse(p0: Call<WorkplaceInformationDto>, rps: Response<WorkplaceInformationDto>) {
                Toast.makeText(this@SubscribeListActivity, "Communication Success! : " + rps.body(), Toast.LENGTH_SHORT).show()
                if(rps.body() != null){
                    val workplace = rps.body()!!
                    ipTv.text = workplace.workspaceIp?: ""
                    companyNameTv.text = workplace.workspaceName
                }else {
                    //로그인 정보는 있으나 DB 테이블에 없는 경우
                    startActivity(Intent(this@SubscribeListActivity, JoinInfoActivity::class.java))
                    this@SubscribeListActivity.finish()
                }
            }

            override fun onFailure(p0: Call<WorkplaceInformationDto>, p1: Throwable) {
                Toast.makeText(this@SubscribeListActivity, "Communication Fail! : " + p1.message,Toast.LENGTH_SHORT).show()
                Log.d("fail", p1.message!!)
            }
        })
    }




}

class SubscribesListAdapter: BaseAdapter(){
    val subscribes: ArrayList<SubscribeInformationDto> = ArrayList()
    override fun getCount(): Int {
        return subscribes.size
    }

    override fun getItem(position: Int): Any {
        return subscribes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0; //position as Long;
    }

    fun addItem(s: SubscribeInformationDto){
        subscribes.add(s)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent!!.context
        val s = subscribes[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.listview_list_subscribe, parent, false)
        view.findViewById<TextView>(R.id.subscribe_nameTv).text = s.provider.name
        Glide.with(view) //이미지 서버로부터 가져오기
            .load(RetrofitSetting.URL +s.product.imgPath)
            .into(view.findViewById(R.id.subscribeIv))
        view.findViewById<TextView>(R.id.subscribe_next_paymentTv).text = s.nextPaymentDate

        //클릭시 구독정보 상세 페이지로 이동
        view.setOnClickListener {
            val intent = Intent(context, SubscribeInformationDetailActivity::class.java)
            intent.putExtra("subscribeId", s.id)
            context.startActivity(intent)
        }
        return view
    }

}