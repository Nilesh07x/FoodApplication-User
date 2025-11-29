package com.example.foodorderapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderapplication.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val db = FirebaseFirestore.getInstance()

    // Google launcher
    private val googleLoginLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken != null) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential)
                    .addOnSuccessListener { res ->
                        val uid = res.user?.uid ?: return@addOnSuccessListener

                        // Save / Update user info in Firestore
                        val userData = hashMapOf(
                            "name" to (account.displayName ?: ""),
                            "email" to (account.email ?: "")
                        )
                        db.collection("users").document(uid).set(userData)

                        Toast.makeText(this, "Logged in with Google", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
            }
        } catch (e: ApiException) {
            Toast.makeText(this, "Google login failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // -------- Email Login --------
        binding.loginbttn.setOnClickListener {
            val email = binding.editTextTextEmailAddress2.text.toString().trim()
            val password = binding.editTextTextPassword2.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }

        // go to sign up
        binding.dnthavebttn.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }

        // -------- Google Login --------
        setupGoogleSignIn()

        binding.googlebttn.setOnClickListener {
            googleLoginLauncher.launch(googleSignInClient.signInIntent)
        }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // MUST exist
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}
