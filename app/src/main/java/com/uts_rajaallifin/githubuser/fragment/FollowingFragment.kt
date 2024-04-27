package com.uts_rajaallifin.githubuser.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.uts_rajaallifin.githubuser.DetailActivity
import com.uts_rajaallifin.githubuser.ModelView.MainModelView
import com.uts_rajaallifin.githubuser.adapters.ListGitUserAdapter
import com.uts_rajaallifin.githubuser.databinding.FragmentGithubFollowingBinding
import com.uts_rajaallifin.githubuser.fragment.FollowersFragment.Companion.EXTRA_DETAIL
import com.uts_rajaallifin.githubuser.fragment.FollowersFragment.Companion.USERNAME
import com.uts_rajaallifin.githubuser.model.Items

class FollowingFragment : Fragment() {
    private var _binding: FragmentGithubFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel by viewModels<MainModelView> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubFollowingBinding.inflate(inflater, container, false)

        followingViewModel.following.observe(viewLifecycleOwner) { followingData ->
            if (followingData == null) {
                val dataUsers = arguments?.getString(USERNAME) ?: ""
                followingViewModel.getUserFollowing(requireActivity(), dataUsers)
            } else {
                showFollowing(followingData)
            }
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }
    override fun onResume(){
        super.onResume()
        binding.root.requestLayout()
    }

    private fun showFollowing(DataUser: List<Items>) {
        binding.rvGitFollowing.layoutManager = LinearLayoutManager(activity)
        val userAdapter = ListGitUserAdapter(DataUser)
        binding.rvGitFollowing.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Items) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
