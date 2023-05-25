package com.example.pdfreader.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.pdfreader.base.BaseFragment
import com.example.pdfreader.databinding.FragmentHomeBinding
import com.example.pdfreader.helper.NavigationManager
import com.example.pdfreader.view.viewmodel.AppViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: AppViewModel by viewModels()

    override fun initView() {
        binding.tv.setOnClickListener {
            // Kiểm tra quyền truy cập tệp tin
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Nếu quyền chưa được cấp, yêu cầu quyền từ người dùng
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                // Quyền đã được cấp
                // Tiếp tục xử lý tệp tin ở đây
                NavigationManager.getInstance().openFragment(AllFileFragment.newInstance())
            }
        }

        binding.hththt.setOnClickListener {
            NavigationManager.getInstance().openFragment(AllFileFragment.newInstance())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp
                // Tiếp tục xử lý tệp tin ở đây
                NavigationManager.getInstance().openFragment(AllFileFragment.newInstance())
            } else {
                // Quyền bị từ chối
                // Xử lý khi quyền bị từ chối ở đây
            }
        }
    }

    override fun initObserver() {

    }

    override fun getData() {

    }
}