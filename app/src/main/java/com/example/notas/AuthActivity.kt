package com.example.notas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setUp()
    }
    private fun setUp() {
        val registrobtn = findViewById<Button>(R.id.Cerrar_btn);
        val emailtv = findViewById<TextView>(R.id.Email_edt);
        val clavetv = findViewById<TextView>(R.id.Clave_edt);
        registrobtn.setOnClickListener {
            if (emailtv.text.isNotEmpty() && clavetv.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailtv.text.toString(),
                    clavetv.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        mostrarHome(it.result?.user?.email ?: "")
                    } else {
                        mostrarAlerta()
                    }
                }
            }
        }
        val ingresobtn = findViewById<Button>(R.id.IniciarSesion_btn);
        ingresobtn.setOnClickListener {
            if (emailtv.text.isNotEmpty() && clavetv.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailtv.text.toString(),
                    clavetv.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        mostrarHome(it.result?.user?.email ?: "")
                    } else {
                        mostrarAlerta()
                    }
                }
            }
        }
    }

    private fun mostrarAlerta(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha produciido un error")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun mostrarHome(email:String){
        val homeIntent = Intent(this,MainActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(homeIntent)
    }
}