package com.heybuddy.safespace

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.heybuddy.safespace.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var bind: ActivityLoginBinding

    private lateinit var emailEt:EditText
    private lateinit var pwEt:EditText
    //private lateinit var googeSignClient: GoogleSignInC
    private lateinit var googeLoginBtn: SignInButton

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.activity_login);

        bind = ActivityLoginBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        val joinBtn = bind.joinBtn
        val loinBtn = bind.loginBtn
        emailEt = bind.emailEt
        pwEt = bind.passwordEt
    }
}