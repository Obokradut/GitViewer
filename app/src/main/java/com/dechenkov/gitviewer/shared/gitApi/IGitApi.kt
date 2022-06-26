package com.dechenkov.gitviewer.shared.gitApi

import com.dechenkov.gitviewer.shared.models.GitRepositoryFullInfo
import com.dechenkov.gitviewer.shared.models.GitRepositoryShortInfo
import com.dechenkov.gitviewer.shared.models.Readme
import com.dechenkov.gitviewer.shared.models.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface IGitApi {
    @GET("/user")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ) : UserInfoResponse

    @GET("/user/repos?per_page=10&page=1&type=all&sort=created&direction=desc")
    suspend fun getRepositories(
        @Header("Authorization") token: String
    ) : List<GitRepositoryShortInfo>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ) : GitRepositoryFullInfo

    @GET("/repos/{owner}/{repo}/contents/{path}")
    suspend fun getContent(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String
    ) : String

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ) : Readme

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadme(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("ref") branch: String
    ) : Readme
}