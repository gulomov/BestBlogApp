package com.example.jaloliddin.bestblog.models;

public class ModelRecycler {
    String title, desc,  time;
    int commentCount;
    int like;
    int id;
    int see;

    public ModelRecycler(int id, String title, String desc, int like, int commentCount, int see, String time) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.like = like;
        this.commentCount = commentCount;
        this.see = see;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getLike() {
        return like;
    }

    public int getComment() {
        return commentCount;
    }

    public int getSee() {
        return see;
    }

    public String getTime() {
        return time;
    }
}
