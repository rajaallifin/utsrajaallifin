package com.uts_rajaallifin.githubuser.ModelView

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uts_rajaallifin.githubuser.fragment.FollowersFragment.Companion.USERNAME
import com.uts_rajaallifin.githubuser.model.Items
import com.uts_rajaallifin.githubuser.model.SearchUserResponses
import com.uts_rajaallifin.githubuser.model.UserDetailResponse
import com.uts_rajaallifin.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModelView: ViewModel() {
    private val _search = MutableLiveData<List<Items>>()
    val search: LiveData<List<Items>> = _search
    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _following = MutableLiveData<List<Items>>(null)
    val following: LiveData<List<Items>?> = _following
    private val _followers = MutableLiveData<List<Items>>(null)
    val followers: LiveData<List<Items>?> = _followers

    fun findItems(context: Context) {
        _isLoading.value = true
        val clients = ApiConfig.getApi(context).getSearchUserData(USERNAME)
        clients.enqueue(object : Callback<SearchUserResponses> {
            override fun onResponse(
                call: Call<SearchUserResponses>,
                response: Response<SearchUserResponses>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponses>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        }) }

    fun searchUser(context: Context, dataUsers: String) {
        _isLoading.value = true
        val clients = ApiConfig.getApi(context).getSearchUserData(dataUsers)
        clients.enqueue(object : Callback<SearchUserResponses> {
            override fun onResponse(
                call: Call<SearchUserResponses>,
                response: Response<SearchUserResponses>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _search.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUserResponses>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailUsers(context: Context, dataUsers: String) {
        _isLoading.value = true
        val clients = ApiConfig.getApi(context).getDetailUserData(dataUsers)
        clients.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUsersFollowers(context: Context, dataUsers: String) {
        _isLoading.value = true
        val clients = ApiConfig.getApi(context).getFollowersData(dataUsers)
        clients.enqueue(object : Callback<List<Items>> {
            override fun onResponse(
                call: Call<List<Items>>,
                response: Response<List<Items>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Items>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}" )
            }
        })
    }

    fun getUserFollowing(context: Context, dataUsers: String) {
        _isLoading.value = true
        val clients = ApiConfig.getApi(context).getFollowingData(dataUsers)
        clients.enqueue(object : Callback<List<Items>> {
            override fun onResponse(
                call: Call<List<Items>>,
                response: Response<List<Items>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Items>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MainModelView"
    }


}