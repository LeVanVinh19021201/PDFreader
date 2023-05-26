package com.example.pdfreader.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.common.control.utils.PermissionUtils
import com.example.pdfreader.R
import com.example.pdfreader.base.BaseActivity

class DenyPermissionDialog(override val layoutId: Int = R.layout.activity_permission_when_deny) :
    BaseActivity() {
    val icon = findViewById<AppCompatImageView>(R.id.icon)
    val content = findViewById<AppCompatTextView>(R.id.content)
    val bt_setting = findViewById<TextView>(R.id.bt_setting)
    val bt_cancel = findViewById<TextView>(R.id.bt_cancel)

    var isCamera: Boolean = false
    override fun initView() {
        val type = intent.getStringExtra("type")
        if (type == "file") {
            isCamera = false
            icon.setImageResource(R.drawable.ic_file_per)
            content.text =
                "Please grant Document Scanner to access Storage permissions so you can manage, edit, and save all files"
        } else {
            isCamera = true
            icon.setImageResource(R.drawable.ic_cam_per)
            content.text =
                "Please grant Document Scanner to access Camera permissions so you can scan documents"
        }
    }

    override fun addEvent() {
        bt_setting.setOnClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q || isCamera) {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, 11)
                finish()
                return@setOnClickListener
            }

            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", this.packageName))
                this.startActivityForResult(
                    intent,
                    PermissionUtils.RQC_REQUEST_PERMISSION_ANDROID_11
                )
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                this.startActivityForResult(
                    intent,
                    PermissionUtils.RQC_REQUEST_PERMISSION_ANDROID_11
                )
            }
//            finish()
        }
        bt_cancel.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ACTION_FINISH: String = "ACTION_FINISH_DIALOG_FILE"

        @JvmStatic
        fun start(context: Context, type: String) {
            val starter = Intent(context, DenyPermissionDialog::class.java)
            starter.putExtra("type", type)
            context.startActivity(starter)
        }
    }

    override fun onBackPressed() {
    }

}