package com.example.jaloliddin.bestblog.iface;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ServerApiR1 {

    @FormUrlEncoded
    @POST("/api/registration.php")
    void registration(
            @Field("username") String username,
            @Field("password") String password,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/api/registration.php")
    void login(
            @Field("usernameLog") String username,
            @Field("passwordLog") String password,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/api/postStory.php")
    void postStory(
            @Field("title") String title,
            @Field("description") String desc,
            @Field("userId") String userId,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/api/postComment.php")
    void postComment(
            @Field("userName") String userName,
            @Field("storyId") int storyId,
            @Field("comment") String comment,
            @Field("userId") String userId,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/api/postComment.php")
    void getComments(
            @Field("storyId") int storyId,
            Callback<Response> responseCallback
    );


    @FormUrlEncoded
    @POST("/api/readRecent.php")
    void readRecent(
            @Field("key") String key,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/api/mostLiked.php")
    void readMostLiked(
            @Field("key") String key,
            Callback<Response> responseCallback
    );

    @FormUrlEncoded
    @POST("/api/postDataById.php")
    void postDataById(
            @Field("postID") int postID,
            @Field("userID") String userId,
            @Field("seeCount") int seeCount,
            Callback<Response> responseCallback
    );



    @FormUrlEncoded
    @POST("/api/checkLike.php")
    void checkLike(
            @Field("likeCCC") int likeCCC,
            @Field("userIdCCC") String userIdCCC,
            @Field("storyIdCCC") int storyIdCCC,
            Callback<Response> responseCallback
    );




}


































