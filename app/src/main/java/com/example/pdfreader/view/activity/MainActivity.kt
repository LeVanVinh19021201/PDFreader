package com.example.pdfreader.view.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.common.control.dialog.PermissionSystemDialog
import com.common.control.interfaces.PermissionCallback
import com.common.control.utils.PermissionUtils
import com.example.pdfreader.R
import com.example.pdfreader.databinding.ActivityMainBinding
import com.example.pdfreader.helper.NavigationManager
import com.example.pdfreader.utils.DenyPermissionDialog
import com.example.pdfreader.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationManager.getInstance().init(this, supportFragmentManager, R.id.fragment_container)

        if (!checkPermission()) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                PermissionSystemDialog.start(this, object : PermissionCallback {
                    override fun onPermissionGranted() {

                    }

                    override fun onPermissionDenied() {
                        showDenyDialog(false)
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                DialogUtils.showDialogPermission(this@MainActivity)
            }
        }
    }
    fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun showDenyDialog(isCamera: Boolean) {
        if (!isCamera) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (!PermissionUtils.permissionGranted(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) && !shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    DenyPermissionDialog.start(this, "file")
                }
            }
            return
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (!PermissionUtils.permissionGranted(
                    this, Manifest.permission.CAMERA
                ) && !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
            ) {
                DenyPermissionDialog.start(this, "camera")
            }
        }
    }
}