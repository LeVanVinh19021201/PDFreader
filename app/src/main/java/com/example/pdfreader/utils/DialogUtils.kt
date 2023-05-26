package com.example.pdfreader.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.common.control.utils.PermissionUtils
import com.example.pdfreader.R

object DialogUtils {

    fun showDialogPermission(activity: Activity) {
        val view: View? =
            LayoutInflater.from(activity).inflate(R.layout.layout_permission_all_file, null)
        val dialogBuilder = AlertDialog.Builder(activity, R.style.CustomAlertDialog)
        val dialog: AlertDialog = dialogBuilder.create()
        val btnCancel = view?.findViewById<TextView>(R.id.bt_cancel)
        val btSetting = view?.findViewById<TextView>(R.id.bt_setting)

        btnCancel?.setOnClickListener {
            dialog.dismiss()
        }
        btSetting?.setOnClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivityForResult(intent, 11)
                return@setOnClickListener
            }

            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", activity.packageName))
                activity.startActivityForResult(
                    intent,
                    PermissionUtils.RQC_REQUEST_PERMISSION_ANDROID_11
                )
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                activity.startActivityForResult(
                    intent,
                    PermissionUtils.RQC_REQUEST_PERMISSION_ANDROID_11
                )
            }
            dialog.dismiss()
        }
        dialog.setView(view)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.show()
    }
}