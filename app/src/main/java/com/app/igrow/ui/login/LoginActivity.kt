package com.app.igrow.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.igrow.databinding.ActivityLoginBinding
import com.app.igrow.ui.admin.AdminActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val adminEmail: String = "test@test.com"
    private val adminPassword: String = "123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etEmail.setText(adminEmail)
        binding.etPassword.setText(adminPassword)
        activateListener()
    }

    private fun activateListener() {
        binding.btnLogin.setOnClickListener {
            if (validation()) {
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid username/password", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validation(): Boolean {
        //Todo()
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.tlEmail.error = "Required"
            return false
        }
        if (binding.etPassword.text.toString().isEmpty()) {
            binding.tlPassword.error = "Required"
            return false
        }
        if (binding.etEmail.text.toString() != adminEmail || binding.etPassword.text.toString() != adminPassword) {
            return false
        }
        return true
    }
}