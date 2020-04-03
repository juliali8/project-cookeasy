package com.example.cookeasy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookeasy.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        return inflater.inflate(com.example.cookeasy.R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerBtn.setOnClickListener {

            if(password.text.isEmpty()) {
                signupStatusMsg.text = "Please enter a password."
            }
            else if(email.text.isEmpty()) {
                signupStatusMsg.text = "Please enter an email."
            }
            else {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            addNewUser(email.text.toString())
                            signupStatusMsg.text = "New user created."
                        } else {
                            signupStatusMsg.text = "Could not create new user."
                        }
                    }
            }

        }
    }

    private fun addNewUser(email: String) {
        val uid = FirebaseAuth.getInstance().uid?: ""
        val user = User(uid, email)
        FirebaseDatabase.getInstance().getReference("/users/$uid").setValue(user)
    }

}