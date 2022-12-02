package org.sopt.sample.presentation.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import org.sopt.sample.data.entity.response.ResponseUserInfoDTO
import org.sopt.sample.databinding.ItemGalleryBinding

class UserInfoAdapter(context: Context, userList: List<ResponseUserInfoDTO.UserInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    val userList = userList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemGalleryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.onBind(userList[position])
        }
    }

    override fun getItemCount() = userList.size

    class ViewHolder(
        private val binding: ItemGalleryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseUserInfoDTO.UserInfo) {
            binding.txtUserName.text = data.first_name
            binding.txtUserEmail.text = data.email
//            Glide.with(binding.root).load(data.avatar).into(binding.imgUser)
            binding.imgUser.load(data.avatar)
        }
    }
}
