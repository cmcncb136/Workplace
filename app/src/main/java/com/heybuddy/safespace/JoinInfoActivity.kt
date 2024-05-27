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

    private lateinit var joinFinishBtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bind = ActivityJoinInfoBinding.inflate(layoutInflater)
        setContentView(bind.root);

        workplaceInfoTitle = bind.workplaceInfoTitleTv

        workplaceNumTv = bind.workplaceNumTv
        workplaceNumEt = bind.workplaceNumEt

        workplaceNameTv = bind.workplaceNameTv
        workplaceNameEt = bind.workplaceNameEt

        workplaceOwnerNameTv = bind.workplaceOwnerNameTv
        workplaceOwnerNameEt = bind.workplaceOwnerNameEt

        workplaceAddressTv = bind.workplaceAddressTv
        workplaceAddressEt = bind.workplaceAddressEt

        joinFinishBtn = bind.joinFinishBtn


        bind.joinFinishBtn.setOnClickListener {
            Toast.makeText(this, "go Main Page", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}