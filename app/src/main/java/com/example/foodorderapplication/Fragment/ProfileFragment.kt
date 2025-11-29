package com.example.foodorderapplication.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodorderapplication.LoginActivity
import com.example.foodorderapplication.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupLogoutButton()
        setupSaveButton()
        loadUserProfile()

        return binding.root
    }

    private fun loadUserProfile() {
        val user = auth.currentUser ?: return   // not logged in

        db.collection("users")
            .document(user.uid)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    binding.etName.setText(doc.getString("name") ?: "")
                    binding.etAddress.setText(doc.getString("address") ?: "")
                    binding.etEmail.setText(doc.getString("email") ?: user.email.orEmpty())
                    binding.etPhone.setText(doc.getString("phone") ?: "")
                }
                // if doc doesn’t exist -> first time signup -> fields stay empty
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupSaveButton() {
        binding.btnSaveInfo.setOnClickListener {
            val user = auth.currentUser
            if (user == null) {
                Toast.makeText(requireContext(), "Please login again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = binding.etName.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()

            // you can add validation here if you want

            val data = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone
            )

            db.collection("users")
                .document(user.uid)
                .set(data, SetOptions.merge())   // merge so old fields aren’t lost
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Profile saved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
