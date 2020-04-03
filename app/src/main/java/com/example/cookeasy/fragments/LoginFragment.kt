package com.example.cookeasy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookeasy.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.email
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_signup.*

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()


        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in
            val i = Intent(this@LoginFragment.context, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        } else {
            // User is signed out
        }

        return inflater.inflate(com.example.cookeasy.R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener {

            if(password.text.isEmpty()) {
                loginStatusMsg.text = "Please enter a password."
            }
            else if(email.text.isEmpty()) {
                loginStatusMsg.text = "Please enter an email."
            }
            else {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@LoginFragment.context, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            loginStatusMsg.text = "Please double check your username and password."
                        }
                    }
            }

        }


    }
}