@file:Suppress("NAME_SHADOWING")

package com.example.michael.proiectunitbv
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class RegisterActivity : AppCompatActivity() {

    lateinit var mRegMail: TextView
    lateinit var mRegPassword: TextView
    lateinit var mAuth: FirebaseAuth
    lateinit var mRegister: TextView
    lateinit var mDatabase: DatabaseReference
    lateinit var mProgressLogin : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mRegister = findViewById(R.id.register_button)
        mRegMail = findViewById(R.id.login_mail)
        mRegPassword = findViewById(R.id.login_password)
        mAuth = FirebaseAuth.getInstance()
        mProgressLogin = findViewById(R.id.register_progress)
        var email_student = "@student.unitbv.ro"
        var email_personal = "@unitbv.ro"


        // /register error messages
        mRegister.setOnClickListener {
            val email = mRegMail.text.toString().trim()
            val password = mRegPassword.text.toString().trim()

            //in cazul in care nu completeaza email
            if (TextUtils.isEmpty(email)) {

                mRegMail.error = "Enter email"
                return@setOnClickListener
            }
            //in cazul in care nu completeaza pass`ul
            if (TextUtils.isEmpty(password)) {

                mRegPassword.error = "Enter password"
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(applicationContext, "Parola prea scurta! Minim 6 caractere.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //mai necesita putina munca!!!! !#!#!!#!@ (TEST IT AND YOU`LL SEE)
            //comentariu block este pus pentru beta testing cu conturi de gmail.
           /* if(email.toLowerCase().indexOf(email_personal.toLowerCase()) != -1){
                mProgressLogin.visibility = View.VISIBLE
                mRegister.visibility = View.INVISIBLE
                registerUser(email, password)
                Toast.makeText(applicationContext, "Bun venit ADMIN", Toast.LENGTH_SHORT).show()

            }

            if(email.toLowerCase().indexOf(email_student.toLowerCase()) != -1){

            }else {
                mRegMail.error = "Email constitutional obligatoriu"
                return@setOnClickListener

            }*/


            mProgressLogin.visibility = View.VISIBLE
            mRegister.visibility = View.INVISIBLE
            registerUser(email, password)

        }
    }

    private fun registerUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                         sendEmailVerificaton()

                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val uid = currentUser!!.uid

                        val userMap = HashMap<String, String>()
                        userMap["email"] = email

                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid)
//FMMMMMM DE ACTIVITY + IF CA NU MERGI
                        mDatabase.setValue(userMap).addOnCompleteListener { task ->
                            val Intent = Intent(applicationContext, SpinnerChoise::class.java)
                            startActivity(Intent)
                            finish()
                        }

                    } else {
                        Toast.makeText(this, "Password must have at least 6 characters.",
                                Toast.LENGTH_SHORT).show()

                    }// end else
                }//task add user
    } //private fun register

    private fun sendEmailVerificaton() {
        val user  = FirebaseAuth.getInstance().currentUser
       user?.sendEmailVerification()?.addOnCompleteListener { task ->
            Toast.makeText(this, "Verification email sent. Check your inbox.", Toast.LENGTH_SHORT).show()

        }
    }//email verification








}//clasa mare register









































