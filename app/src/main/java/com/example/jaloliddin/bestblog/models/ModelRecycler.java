package com.example.jaloliddin.bestblog.models;

public class ModelRecycler {
    String title, desc, see, time;
    int comment_id;
    int like;
    int id;

    public ModelRecycler(int id, String title, String desc, int like, int comment_id, String see, String time) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.like = like;
        this.comment_id = comment_id;
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
        return comment_id;
    }

    public String getSee() {
        return see;
    }

    public String getTime() {
        return time;
    }
}
