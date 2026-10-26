package com.noid.app

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 123)
        } else {
            startNotchService()
        }
    }

    private fun startNotchService() {
        val intent = Intent(this, NotchService::class.java)
        startService(intent)
        Toast.makeText(this, "Notch NOID Berhasil Aktif!", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startNotchService()
            } else {
                Toast.makeText(this, "Izin overlay dibutuhkan!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
