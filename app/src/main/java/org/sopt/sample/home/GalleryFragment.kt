package com.sopt.seminar2_test.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sopt.sample.data.ResponseUserInfoDTO
import org.sopt.sample.data.ServicePool
import org.sopt.sample.databinding.FragmentGalleryBinding
import org.sopt.sample.gallery.UserInfoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding
        get() = requireNotNull(_binding) { "galleryFragment _binding 오류" }

    private val userInfoService = ServicePool.userInfoService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getUserInfo() {
        userInfoService.getUserInfo().enqueue(object : Callback<ResponseUserInfoDTO> {
            override fun onResponse(
                call: Call<ResponseUserInfoDTO>,
                response: Response<ResponseUserInfoDTO>
            ) {
                Log.d("USERINFO-RESPONSE/SUCCESS", "USERINFO 성공!! $response")

                val adapter = UserInfoAdapter(requireContext(), response.body()!!.data)
                binding.rvUserInfo.adapter = adapter
            }

            override fun onFailure(call: Call<ResponseUserInfoDTO>, t: Throwable) {
                Log.d("USERINFO-RESPONSE/FAILURE", "USERINFO 실패 ㅠㅠ, ${t.message}")
            }
        })
    }
}
