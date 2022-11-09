package org.sopt.sample.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.home.adapter.RepoAdapter
import org.sopt.sample.home.data.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "HomeFragment _binding 오류" }

    private val viewModel by viewModels<HomeViewModel>()

//    private val mockRepoList = listOf<Repo>(
//        Repo(
//            image = R.drawable.github,
//            name = "FILL-IN",
//            author = "누누"
//        ),
//        Repo(
//            image = R.drawable.github,
//            name = "Hacker",
//            author = "이수현"
//        ),
//        Repo(
//            image = R.drawable.github,
//            name = "FILL-IN",
//            author = "채승훈"
//        ),
//        Repo(
//            image = R.drawable.github,
//            name = "FILL-IN",
//            author = "김수빈"
//        )
//    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RepoAdapter(requireContext())
        binding.rvRepo.adapter = adapter
        adapter.setRepoList(viewModel.mockRepoList)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}