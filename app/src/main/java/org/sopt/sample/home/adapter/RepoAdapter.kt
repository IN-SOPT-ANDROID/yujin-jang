package org.sopt.sample.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.sample.databinding.ItemHomeBodyBinding
import org.sopt.sample.databinding.ItemHomeHeaderBinding
import org.sopt.sample.home.data.Repo

class RepoAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) } // Adapter 객체 생성 시에만 할당되도록
    private var repoList: List<Repo> = emptyList()

    companion object {
        const val HEADER = 0
        const val BODY = 1
    }

    override fun getItemViewType(position: Int): Int { // onCreateViewHolder가 호출되기 전에 먼저 호출
        return when (position) {
            0 -> HEADER
            else -> BODY
        }
    }

    // 필수1. 뷰 객체 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val binding = ItemGithubRepoBinding.inflate(inflater, parent, false)
//        return RepoViewHolder(binding)
        return when (viewType) {
            HEADER -> {
                val itemHomeHeaderBinding = ItemHomeHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(itemHomeHeaderBinding)
            }
            BODY -> {
                val itemHomeBodyBinding = ItemHomeBodyBinding.inflate(inflater, parent, false)
                BodyViewHolder(itemHomeBodyBinding)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    // 필수2. viewholder에 데이터를 binding
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BodyViewHolder) {
            holder.onBind(repoList[position - 1])
        }
    }

    // 필수3. 데이터 셋 크기
    override fun getItemCount() = repoList.size + 1

    fun setRepoList(repoList: List<Repo>) {
        this.repoList = repoList.toList()
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(
        private val binding: ItemHomeHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class BodyViewHolder(
        private val binding: ItemHomeBodyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Repo) {
            binding.imgGithubRepo.setImageResource(data.image)
            binding.txtGithubRepoName.text = data.name
            binding.txtGithubRepoAuthor.text = data.author
        }
    }
}