package com.example.jaloliddin.bestblog.models;

public class ModelRecyclerComments {

    String UserName, UserComment, CommentTime;

    public ModelRecyclerComments(String UserName, String UserComment, String CommentTime) {
        this.UserName = UserName;
        this.UserComment = UserComment;
        this.CommentTime = CommentTime;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserComment() {
        return UserComment;
    }

    public String getCommentTime() {
        return CommentTime;
    }
}
