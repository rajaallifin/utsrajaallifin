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
import com.uts_rajaallifin.githubuser.databinding.FragmentGithubFollowersBinding
import com.uts_rajaallifin.githubuser.model.Items

class FollowersFragment : Fragment() {
    private var _binding: FragmentGithubFollowersBinding? = null
    private val binding get() = _binding!!
    private val followersModelView by viewModels<MainModelView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGithubFollowersBinding.inflate(inflater, container, false)

        followersModelView.followers.observe(viewLifecycleOwner) { followersData ->
            if (followersData == null) {
                val dataUsers = arguments?.getString(USERNAME)?:""
                followersModelView.getUsersFollowers(requireActivity(), dataUsers)
            } else {
                showFollowers(followersData)
            }
        }
        followersModelView.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        return binding.root
    }
    override fun onResume(){
            super.onResume()
        binding.root.requestLayout()
    }

    private fun showFollowers(dataUsers: List<Items>) {
        binding.rvGitFollowers.layoutManager = LinearLayoutManager(activity)
        val userAdapter = ListGitUserAdapter(dataUsers)
        binding.rvGitFollowers.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Items) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val USERNAME = "a"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}