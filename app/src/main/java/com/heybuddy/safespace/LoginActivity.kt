package com.heybuddy.safespace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.heybuddy.safespace.basic_component.Login
import com.heybuddy.safespace.databinding.ActivityLoginBinding
import java.util.regex.Pattern
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var bind: ActivityLoginBinding

    private lateinit var emailEt: EditText
    private lateinit var pwEt: EditText
    private lateinit var googeSignClient: GoogleSignInClient;
    private lateinit var googeLoginBtn: SignInButton

    private lateinit var login:Login;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root);

        auth = FirebaseAuth.getInstance()
        val joinBtn = bind.joinBtn
        val loginBtn = bind.loginBtn
        emailEt = bind.emailEt
        pwEt = bind.pwEt

        login = Login(this)

        googeSignClient = login.googeSignClient

        googeLoginBtn = bind.googleLoginBtn
        googeLoginBtn.setOnClickListener {
            val signInIntent = googeSignClient.signInIntent
            startActivityForResult(signInIntent, 1004) //초괴화한 객체를 통해서 Intent를 만들고 Activity을 실행한다.
        }

        loginBtn.setOnClickListener{
            val email = emailEt.text.toString();
            val pw = pwEt.text.toString();

            val emailPatern = android.util.Patterns.EMAIL_ADDRESS;
        }
    }


    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1004){ //request 코드를 startActivy로 보낼 때 1004번으로 했다.
            if(resultCode == Activity.RESULT_OK) { //intent가 정상적으로 종료되지 않은 경우
                Toast.makeText(this, " intent Fail!", Toast.LENGTH_SHORT).show()
                return;
            }

            //결과 Intent(data 매개변수) 에서 구글로그인 결과 꺼내오기
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)!!

            //정상적으로 로그인되었다면
            if(result.isSuccess){
                //우리의 Firebase 서버에 사용자 이메일정보보내기
                val account = result.signInAccount
                login.firebaseAuthWithGoogle(account, Intent(this, SubscribeListActivity::class.java))
            }else{
                Toast.makeText(this, "Login fail!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun singIn(email: String, pw: String) {
        auth?.signInWithEmailAndPassword(email, pw)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    Log.w("Login", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "이메일 또는 비밀번호가 틀렸습니다.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }


}


/*
private fun join(){
        //email과 password를 준다.
        auth?.createUserWithEmailAndPassword(emailEt.text.toString(),passwordEt.text.toString())
            ?.addOnCompleteListener {  //통신 완료가 된 후 무슨일을 할지
                    task ->
                if(task.isSuccessful){  //회원이 아닌 경우
                    //정상적으로 이메일과 비번이 전달되어
                    //새 유저 계정을 생성과 서버db 저장 완료 및 로그인
                    //즉, 기존에 있는 계정이 아니다!


                    val user = task.result.user
                    //goMainActivity(task.result?.user)
                    db.getReference("user").child(user?.uid.toString()).setValue(user?.email)
                    Toast.makeText(this,"Join Succuess!",Toast.LENGTH_LONG).show()
                }
                else if (task.exception?.message.isNullOrEmpty()==false){
                    //예외메세지가 있다면 출력
                    //에러가 났다거나 서버가 연결이 실패했다거나
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }
                else{ //회원인 경우
                    //여기가 실행되는 경우는 이미 db에 해당 이메일과 패스워드가 있는 경우
                    //그래서 계정 생성이 아닌 로그인 함수로 이동
                    //signIn()
                }
            }
    }


 */
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