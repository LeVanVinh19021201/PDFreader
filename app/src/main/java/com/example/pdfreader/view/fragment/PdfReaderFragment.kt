package com.example.pdfreader.view.fragment

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.text.TextUtils
import android.view.View
import com.common.control.utils.PermissionUtils
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.databinding.FragmentPdfReaderBinding
import com.github.barteksc.pdfviewer.listener.OnErrorListener
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.File
import java.util.Locale


class PdfReaderFragment :
    BaseFragment<FragmentPdfReaderBinding>(FragmentPdfReaderBinding::inflate) {

    private var pathFileCurent: String = ""
    private var uri: Uri? = null

    companion object {
        fun newInstance(pathfile: String) = PdfReaderFragment().apply {
            pathFileCurent = pathfile
            uri = Uri.fromFile(File(pathFileCurent))
        }
    }

    override fun initView() {
        binding.pdfView.keepScreenOn = true
        //        int currentPage = SharePreferenceUtils.getCurrentPageReading(this, pdfFileLocation);
//        if (currentPage == -1) {
//            currentPage = 0;
//        }
        loadSelectedPdfFile("", 0, false, false, false)
    }

    override fun initObserver() {

    }

    override fun getData() {

    }

    @SuppressLint("SetTextI18n")
    fun loadSelectedPdfFile(
        password: String?,
        defaultPage: Int,
        horizontal: Boolean,
        nightMode: Boolean,
        pageFling: Boolean
    ) {
        binding.progressOpenPdf.visibility = View.VISIBLE
        if (!TextUtils.isEmpty(pathFileCurent.trim { it <= ' ' })) {
            val file: File = File(pathFileCurent)
            binding.toolBarDoc.tvTitle.text = file.name
            if (PermissionUtils.isStoragePermissionGranted(requireActivity())) {
                password?.let {
                    showPdfView(null, file, it, defaultPage, horizontal, nightMode, pageFling)
                }
            } else {
                if (uri != null) {
                    if (password != null) {
                        showPdfView(
                            uri,
                            null,
                            password,
                            defaultPage,
                            horizontal,
                            nightMode,
                            pageFling
                        )
                    }
                }
            }
            return
        }
        if (uri != null) {
            try {
                binding.toolBarDoc.tvTitle.text =
                    getFileName(uri!!)?.lowercase(Locale.getDefault())?.replace(".pdf", "")
            } catch (e: Exception) {
                binding.toolBarDoc.tvTitle.text = File(uri!!.getPath()).getName()
                e.printStackTrace()
            }
            password?.let {
                showPdfView(
                    uri, null,
                    it, defaultPage, horizontal, nightMode, pageFling
                )
            }
        }
    }

    private fun showPdfView(
        uri: Uri?,
        file: File?,
        password: String,
        defaultPage: Int,
        horizontal: Boolean,
        nightMode: Boolean,
        pageFling: Boolean
    ) {
        try {
            if (uri != null) {
                binding.pdfView.fromUri(uri)
                    .password(password)
                    .enableAnnotationRendering(true)
                    .pageFitPolicy(FitPolicy.WIDTH).spacing(6)
                    .defaultPage(defaultPage)
                    .swipeHorizontal(horizontal)
                    .pageSnap(horizontal)
                    .nightMode(nightMode)
                    .pageFling(pageFling)
                    .onPageChange(this.onPageChangeListener).onLoad(this.onLoadCompleteListener)
                    .onError(this.onErrorListener).load()
                return
            }
            if (file != null) {
                binding.pdfView.fromFile(file)
                    .password(password)
                    .enableAnnotationRendering(true)
                    .pageFitPolicy(FitPolicy.WIDTH).spacing(6)
                    .defaultPage(defaultPage)
                    .swipeHorizontal(horizontal)
                    .pageSnap(horizontal)
                    .nightMode(nightMode)
                    .pageFling(pageFling)
                    .onPageChange(this.onPageChangeListener).onLoad(this.onLoadCompleteListener)
                    .onError(this.onErrorListener).load()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("Range")
    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? =
                requireContext().getContentResolver().query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    private val onPageChangeListener =
        OnPageChangeListener { i: Int, i2: Int ->
            val sb = (i + 1).toString() +
                    " / " +
                    i2
            binding.tvPdfPageNumbers.text = sb
        }


    private val onLoadCompleteListener =
        OnLoadCompleteListener { i: Int ->
            binding.progressOpenPdf.visibility = View.GONE
            binding.tvPdfPageNumbers.visibility = View.VISIBLE
//            if (passwordDialog != null) {
//                passwordDialog.dismiss()
//                passwordDialog = null
//            }
        }

    private val onErrorListener =
        OnErrorListener { th: Throwable? ->
            try {
//                if (th is PdfPasswordException) {
//                    if (passwordDialog == null) {
//                        enterPasswordDialog()
//                    }
//                    if (enterPass) {
//                        enterPass = false
//                        Toast.makeText(
//                            context,
//                            getString(R.string.wrong_password),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    logEvent("click_openfile_with_password")
//                    setLogEventOpenPWFile()
//                    return@OnErrorListener
//                }
//                binding.progressOpenPdf.visibility = View.GONE
//                BadFileInformDialog.newInstance()
//                    .show(getSupportFragmentManager(), "BadFileInformDialog")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

}