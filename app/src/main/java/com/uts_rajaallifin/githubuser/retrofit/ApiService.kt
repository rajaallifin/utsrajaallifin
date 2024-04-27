package com.uts_rajaallifin.githubuser.retrofit

import com.uts_rajaallifin.githubuser.BuildConfig
import com.uts_rajaallifin.githubuser.model.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Path

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.api_key}")
    fun getSearchUserData(
        @Query("q") q: String
    ): Call<SearchUserResponses>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.api_key}")
    fun getDetailUserData(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.api_key}")
    fun getFollowingData(
        @Path("username") username: String
    ): Call<List<Items>>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.api_key}")
    fun getFollowersData(
        @Path("username") username: String
    ): Call<List<Items>>
}