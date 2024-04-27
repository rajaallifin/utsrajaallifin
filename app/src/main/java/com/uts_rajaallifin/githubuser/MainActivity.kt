package com.uts_rajaallifin.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.uts_rajaallifin.githubuser.ModelView.MainModelView
import com.uts_rajaallifin.githubuser.adapters.ListGitUserAdapter
import com.uts_rajaallifin.githubuser.databinding.ActivityMainBinding
import com.uts_rajaallifin.githubuser.model.Items

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainModelView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvShowUserList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvShowUserList.addItemDecoration(itemDecoration)

        mainViewModel.search.observe(this) {
            searchData -> setUserData(searchData)
        }

        mainViewModel.isLoading.observe(this) {
            loader -> showLoading(loader)
        }

        mainViewModel.findItems(this)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.searchUser(this@MainActivity, query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setUserData(userItems: List<Items>) {
        val userAdapter = ListGitUserAdapter(userItems)
        binding.rvShowUserList.setHasFixedSize(true)
        binding.rvShowUserList.layoutManager = LinearLayoutManager(this)
        binding.rvShowUserList.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Items) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(EXTRA_DETAIL, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        private const val EXTRA_DETAIL = "extra_detail"
    }


}