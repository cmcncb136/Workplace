package com.heybuddy.safespace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.heybuddy.safespace.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var bind: ActivityLoginBinding

    private lateinit var emailEt: EditText
    private lateinit var pwEt: EditText
    private lateinit var googeSignClient: GoogleSignInClient;
    private lateinit var googeLoginBtn: SignInButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root);



        auth = FirebaseAuth.getInstance()
        val joinBtn = bind.joinBtn
        val loinBtn = bind.loginBtn
        emailEt = bind.emailEt
        pwEt = bind.passwordEt


        googeSignClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        )

        googeLoginBtn = bind.googleLoginBtn
        googeLoginBtn.setOnClickListener {
            val signInIntent = googeSignClient.signInIntent
            startActivityForResult(signInIntent, 1004) //초괴화한 객체를 통해서 Intent를 만들고 Activity을 실행한다.
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        //구글로부터 로그인된 사용자의 정보(Credentail)을 얻어온다.
        val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
        //그 정보를 사용하여 Firebase의 auth를 실행한다.
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {  //통신 완료가 된 후 무슨일을 할지
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login is Successful!", Toast.LENGTH_LONG).show()
                    // 로그인 처리를 해주면 됨!
                    //goMainActivity(task.result?.user)
                } else {
                    // 오류가 난 경우!
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        //Activity.Result_OK : 정상완료
        //Activity.Result_CANCEL : 중간에 취소 되었음(실패)

        if(requestCode==1004){ //request 코드를 startActivy로 보낼 때 1004번으로 했다.
            if(resultCode == Activity.RESULT_OK){
                //결과 Intent(data 매개변수) 에서 구글로그인 결과 꺼내오기
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)!!

                //정상적으로 로그인되었다면
                if(result.isSuccess){
                    //우리의 Firebase 서버에 사용자 이메일정보보내기
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account)
                }else{
                    Toast.makeText(this, "Login fail!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, " !", Toast.LENGTH_SHORT).show()

            }
        }
    }
}



/*
    private lateinit var requestLauncher: ActivityResultLauncher<Intent>
    //왜 인지는 모르겠으나 AActivityResultLauncher로 실행이 되지 않는다.

      requestLauncher= registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode == Activity.RESULT_OK){
                    //결과 Intent(data 매개변수) 에서 구글로그인 결과 꺼내오기
                    val result = Auth.GoogleSignInApi.getSignInResultFromIntent(it.data!!)!!

                    //정상적으로 로그인되었다면
                    if(result.isSuccess){
                        //우리의 Firebase 서버에 사용자 이메일정보보내기
                        val account = result.signInAccount
                        firebaseAuthWithGoogle(account)
                    }else{
                        Toast.makeText(this, "Login fail!", Toast.LENGTH_SHORT).show()
                    }
                }
                Log.d("callback", "LoginPage CallBack")

            }


            requestLauncher.launch(googeSignClient.signInIntent)
 */