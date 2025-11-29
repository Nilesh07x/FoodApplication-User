package com.example.foodorderapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderapplication.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    private lateinit var googleSignInClient: GoogleSignInClient

    // launcher for Google sign-in result
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken == null) {
                Toast.makeText(this, "ID token is null", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }

            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnSuccessListener { res ->
                    val uid = res.user?.uid ?: return@addOnSuccessListener

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
                    Toast.makeText(this, "Firebase auth failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }

        } catch (e: ApiException) {
            Toast.makeText(this, "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // ---------- EMAIL + PASSWORD SIGNUP ----------
        binding.loginbttn.setOnClickListener {
            val name = binding.editTextText.text.toString().trim()
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid ?: return@addOnSuccessListener
                    val userData = hashMapOf(
                        "name" to name,
                        "email" to email
                    )
                    db.collection("users").document(uid).set(userData)

                    Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }

        // ---------- GOOGLE SIGN-IN ----------
        setupGoogleSignIn()

        binding.googlebttn.setOnClickListener {
            Toast.makeText(this, "Google button clicked", Toast.LENGTH_SHORT).show()
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // from google-services.json
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}
