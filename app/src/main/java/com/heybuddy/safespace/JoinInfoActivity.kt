package com.heybuddy.safespace

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivityJoinInfoBinding
import com.heybuddy.safespace.databinding.ActivityLoginBinding
import com.heybuddy.safespace.dto.WorkplaceInformationDto
import com.heybuddy.safespace.service.WorkplaceInformationService
import retrofit2.*

class JoinInfoActivity: AppCompatActivity() {

    private lateinit var workplaceInfoTitle: TextView

    private lateinit var workplaceNumTv:TextView
    private lateinit var workplaceNumEt:EditText

    private lateinit var workplaceNameTv:TextView
    private lateinit var workplaceNameEt:EditText

    private lateinit var workplaceOwnerNameTv:TextView
    private lateinit var workplaceOwnerNameEt:EditText

    private lateinit var workplaceAddressTv:TextView
    private lateinit var workplaceAddressEt:EditText

    private lateinit var workplacePhoneEt:EditText

    private lateinit var workplaceCategoryEt:EditText

    private lateinit var joinFinishBtn:Button

    private lateinit var auth:FirebaseAuth
    private lateinit var retrofit: Retrofit
    private lateinit var workplaceInformationService: WorkplaceInformationService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityJoinInfoBinding.inflate(layoutInflater)
        setContentView(bind.root);

        auth = FirebaseAuth.getInstance()
        retrofit = RetrofitSetting.getRetrofit()
        workplaceInformationService = retrofit.create(WorkplaceInformationService::class.java)

        workplaceInfoTitle = bind.workplaceInfoTitleTv

        workplaceNumTv = bind.workplaceNumTv
        workplaceNumEt = bind.workplaceNumEt

        workplaceNameTv = bind.workplaceNameTv
        workplaceNameEt = bind.workplaceNameEt

        workplaceOwnerNameTv = bind.workplaceOwnerNameTv
        workplaceOwnerNameEt = bind.workplaceOwnerNameEt

        workplaceAddressTv = bind.workplaceAddressTv
        workplaceAddressEt = bind.workplaceAddressEt

        workplacePhoneEt = bind.workplacePhoneEt
        workplaceCategoryEt = bind.workplaceCategoryEt

        joinFinishBtn = bind.joinFinishBtn


        bind.joinFinishBtn.setOnClickListener {
            val num = workplaceNumEt.text.toString()
            val name = workplaceNameEt.text.toString()
            val owner = workplaceOwnerNameEt.text.toString()
            val address = workplaceAddressEt.text.toString()
            val phone = workplacePhoneEt.text.toString()
            val category = workplaceCategoryEt.text.toString()
            val uid: String = auth.uid!!;

            if(num.isEmpty() || name.isEmpty() || owner.isEmpty() || address.isEmpty()
                || phone.isEmpty() || category.isEmpty()){
                Toast.makeText(this, "모든 항목 데이터가 있어야 합니다", Toast.LENGTH_SHORT).show()
            }

            val workplace = WorkplaceInformationDto(
                businessType = category,
                address = address,
                workspacePhone = phone,
                ownerName = owner,
                dancPin = num,
                workspaceName = name,
                uid = uid,
                joinDate = null,
                workspaceIp = null
            )

            val call = workplaceInformationService.joinWorkplace(workplace)

            call.enqueue(object: Callback<Boolean>{
                override fun onResponse(p0: Call<Boolean>, p1: Response<Boolean>) {
                    if(p1.body()?: false){
                        Toast.makeText(this@JoinInfoActivity, "Join Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@JoinInfoActivity, SubscribeListActivity::class.java))
                    }else{
                        Toast.makeText(this@JoinInfoActivity, "Join Fail : " + p1.body(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(p0: Call<Boolean>, p1: Throwable) {
                    Toast.makeText(this@JoinInfoActivity, "Communication Fail", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}