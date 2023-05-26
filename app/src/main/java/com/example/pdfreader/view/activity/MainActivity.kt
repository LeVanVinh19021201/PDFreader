package com.example.pdfreader.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.common.control.dialog.PermissionSystemDialog
import com.common.control.interfaces.PermissionCallback
import com.common.control.utils.PermissionUtils
import com.example.pdfreader.R
import com.example.pdfreader.database.DataFile
import com.example.pdfreader.databinding.ActivityMainBinding
import com.example.pdfreader.helper.NavigationManager
import com.example.pdfreader.helper.PreferenceHelper
import com.example.pdfreader.task.ICallbackLoadFile
import com.example.pdfreader.task.LoadPdfFileTask
import com.example.pdfreader.task.TagLoadfile
import com.example.pdfreader.utils.Const
import com.example.pdfreader.utils.Const.IS_ADD_DATA
import com.example.pdfreader.utils.DenyPermissionDialog
import com.example.pdfreader.utils.DialogUtils
import com.example.pdfreader.view.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var loadPdfFile: LoadPdfFileTask? = null
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationManager.getInstance().init(this, supportFragmentManager, R.id.fragment_container)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.color_primary)
        }

        if (!checkPermission()) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                PermissionSystemDialog.start(this, object : PermissionCallback {
                    override fun onPermissionGranted() {
                        getDataDevice()
                    }

                    override fun onPermissionDenied() {
                        showDenyDialog(false)
                    }
                }, Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                DialogUtils.showDialogPermission(this@MainActivity)
            }
        } else {
            getDataDevice()
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

    private fun getDataDevice() {
//        if(PreferenceHelper.getInstance().get(IS_ADD_DATA, false)){
            loadPdfFile = LoadPdfFileTask(this,viewModel)
            loadPdfFile?.execute()
//        }
    }
}