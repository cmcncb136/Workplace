package com.heybuddy.safespace

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.heybuddy.safespace.basic_component.RetrofitSetting
import com.heybuddy.safespace.databinding.ActivityJoinInfoBinding
import com.heybuddy.safespace.databinding.ActivityLoginBinding
import com.heybuddy.safespace.dto.CommonResult
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
    private lateinit var workplaceIpEt: EditText
    private lateinit var workplaceIpCheckTv: TextView


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

        workplaceIpEt = bind.workplaceIpEt
        workplaceIpCheckTv = bind.workplaceIpCheckTv

        joinFinishBtn = bind.joinFinishBtn

        workplaceInformationService.getPublicIp().enqueue(
            object: Callback<String>{
                override fun onResponse(p0: Call<String>, body: Response<String>) {
                    val ip = body.body()
                    if(ip == null) {
                        workplaceIpCheckTv.text = workplaceIpCheckTv.text.toString() + "Communication Fail"
                        return
                    }else{
                        workplaceIpCheckTv.text = workplaceIpCheckTv.text.toString() + ip
                        workplaceIpEt.setText(ip)
                    }
                }
                override fun onFailure(p0: Call<String>, p1: Throwable) {
                    Log.d("Communication fail!", p1.message?:"")
                    Toast.makeText(this@JoinInfoActivity, p1.message, Toast.LENGTH_SHORT).show()
                    workplaceIpCheckTv.text = workplaceIpCheckTv.text.toString() + "Communication Fail"
                }
            }
        )


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
                workspaceIp = workplaceIpEt.text.toString()
            )

            val call = workplaceInformationService.joinWorkplace(workplace)

            call.enqueue(object: Callback<CommonResult>{
                override fun onResponse(p0: Call<CommonResult>, p1: Response<CommonResult>) {
                    val rst = p1.body();
                    if(rst == null){
                        Toast.makeText(this@JoinInfoActivity, "Communication Exception", Toast.LENGTH_SHORT).show()
                        return
                    }

                    if(rst!!.success){
                        Toast.makeText(this@JoinInfoActivity, "Join Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@JoinInfoActivity, SubscribeListActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@JoinInfoActivity, "Join Fail : " + rst!!.msg, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(p0: Call<CommonResult>, p1: Throwable) {
                    Toast.makeText(this@JoinInfoActivity, "Communication Fail", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}