package com.heybuddy.safespace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.heybuddy.safespace.databinding.ActivityJoinBinding

class JoinActivity: AppCompatActivity() {


    private lateinit var joinTitleTv: TextView
    private lateinit var idTv: TextView
    private lateinit var idEt: EditText
    private lateinit var idCheck: TextView

    private lateinit var passwdTv: TextView
    private lateinit var passwdEt: EditText
    private lateinit var joinNextBtn: Button

    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(bind.root)

        auth = FirebaseAuth.getInstance()

        joinTitleTv = bind.joinTitleTv
        //아이디
        idTv = bind.idTv
        idEt = bind.idEt
        idCheck = bind.idCheck

        //비밀번호
        passwdTv = bind.passwdTv
        passwdEt = bind.passwdEt

        //다음 버튼
        joinNextBtn = bind.joinNextBtn

        
        //Join Info 이동 구현
        bind.joinNextBtn.setOnClickListener {
            val email = idEt.text.toString();
            val pw = passwdEt.text.toString();

            val emailPattern = android.util.Patterns.EMAIL_ADDRESS;

            if(!emailPattern.matcher(email).matches()) {
                Toast.makeText(this, "Email 양식이 맞지 않습니다!", Toast.LENGTH_SHORT).show();
            }else{
                join(email, pw);
            }
        }
    }

    private fun join(email: String, pw: String){
        //email과 password를 준다.
        auth?.createUserWithEmailAndPassword(email, pw)
            ?.addOnCompleteListener {  //통신 완료가 된 후 무슨일을 할지
                    task ->
                if(task.isSuccessful){  //회원이 아닌 경우
                    //정상적으로 이메일과 비번이 전달되어
                    //새 유저 계정을 생성과 서버db 저장 완료 및 로그인
                    //즉, 기존에 있는 계정이 아니다!

                    Toast.makeText(this,"Join Success!",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, JoinInfoActivity::class.java))
                }
                else if (!task.exception?.message.isNullOrEmpty()){
                    //예외메세지가 있다면 출력
                    //에러가 났다거나 서버가 연결이 실패했다거나
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                }
                else{ //회원인 경우
                    Toast.makeText(this, "이미 회원가입된 이메일입니다.", Toast.LENGTH_SHORT);
                    //여기가 실행되는 경우는 이미 db에 해당 이메일과 패스워드가 있는 경우
                    //그래서 계정 생성이 아닌 로그인 함수로 이동
                    //signIn()
                }
            }
    }
}