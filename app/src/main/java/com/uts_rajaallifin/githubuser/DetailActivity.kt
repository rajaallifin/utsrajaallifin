package com.uts_rajaallifin.githubuser

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.uts_rajaallifin.githubuser.ModelView.MainModelView
import com.uts_rajaallifin.githubuser.adapters.SectionsPagerAdapter
import com.uts_rajaallifin.githubuser.databinding.ActivityDetailBinding
import com.uts_rajaallifin.githubuser.fragment.FollowersFragment.Companion.EXTRA_DETAIL
import com.uts_rajaallifin.githubuser.model.UserDetailResponse

class DetailActivity: AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val detailMainModelView by viewModels<MainModelView> (  )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailUser = intent.extras?.get(EXTRA_DETAIL) as String
        val sectionsPagerAdapter = SectionsPagerAdapter(this, detailUser)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabsFollow: TabLayout = binding.tabFollow
        TabLayoutMediator(tabsFollow, viewPager) {tabs, position->
            tabs.text = resources.getString(TITLE_TAB[position])
        }.attach()

        detailMainModelView.detailUser.observe(this) { detailUser ->
            getDetailsUserData(detailUser)
        }

        detailMainModelView.isLoading.observe(this) { loader ->
            showLoading(loader)
        }

        detailMainModelView.getDetailUsers(this, detailUser)

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun ImageView.loadImage(avatarUrl: String?) { Glide.with(this.context).load(avatarUrl) .apply(
        RequestOptions().override(200, 200)) .centerCrop() .circleCrop() .into(this)}


    private fun getDetailsUserData(UserItem: UserDetailResponse) {
        binding.apply {
            tvUsername.text = UserItem.login
            tvUsernameValue.text = UserItem.login
            tvRepositoryValue.text = UserItem.public_repos.toString()
            tvName.text = UserItem.name
            tvFollowersValue.text = UserItem.followers.toString()
            tvFollowingValue.text = UserItem.following.toString()
            ivUser.loadImage(UserItem.avatar_url)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val TITLE_TAB = intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }
}