package com.heybuddy.safespace.basic_component

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.heybuddy.safespace.R

class Login(private var context: Context) {
    private var auth: FirebaseAuth;
    val googeSignClient: GoogleSignInClient;

    init {
        auth = FirebaseAuth.getInstance()
        googeSignClient = GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }
    public fun firebaseAuthWithGoogle(account: GoogleSignInAccount?, move: Intent) {
        //구글로부터 로그인된 사용자의 정보(Credentail)을 얻어온다.
        val credential = GoogleAuthProvider.getCredential(account?.idToken!!, null)
        //그 정보를 사용하여 Firebase의 auth를 실행한다.
        auth?.signInWithCredential(credential)?.addOnCompleteListener {  //통신 완료가 된 후 무슨일을 할지
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login is Successful!", Toast.LENGTH_LONG).show()
                    // 로그인 처리를 해주면 됨!
                    //goMainActivity(task.result?.user)
                    context.startActivity(move);
                } else {
                    // 오류가 난 경우!
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}