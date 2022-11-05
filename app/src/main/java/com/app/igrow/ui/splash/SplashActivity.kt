package com.app.igrow.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.app.igrow.R
import com.app.igrow.base.BaseActivity
import com.app.igrow.databinding.ActivitySplashBinding
import com.app.igrow.ui.dashboard.DashBoardActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        binding.ivLogo.animate().alpha(1F).setDuration(2000).setInterpolator(DecelerateInterpolator())
            .withEndAction(
                Runnable {
                    binding.ivLogo.animate().alpha(0F).setDuration(2000)
                        .setInterpolator(AccelerateInterpolator()).start()

                    startActivity(Intent(this, DashBoardActivity::class.java))
                    finish()
                }).start()
    }
}