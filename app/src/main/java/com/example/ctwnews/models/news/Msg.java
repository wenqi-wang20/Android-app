package com.example.ctwnews.models.news;


import java.util.List;

public class Msg {
    private int id;
    private int imageResourceID;
    private List<String> imageUrl;
    private List<String> videoUrl;
    private String title;
    private String content;

    private String publishTime;
    private String publisher;
    private String category;

    //每篇新闻的得分数（推荐度排名）
    private int score;

    public Msg(){

    }

    //创建临时数据集
    public Msg(int id, int imageResourceID, String title, String content) {
        this.id = id;
        this.imageResourceID = imageResourceID;
        this.title = title;
        this.content = content;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }
    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUrl(List<String> imageurl){this.imageUrl = imageurl;}
    public List<String> getImageUrl(){return imageUrl;}

    public void setPublishTime(String publishtime){this.publishTime = publishtime;}
    public String getPublishTime(){return publishTime;}

    public void setPublisher(String publisher){this.publisher = publisher;}
    public String getPublisher(){return publisher;}

    public void setCategory(String category1){this.category = category1;}
    public String getCategory(){return category;}

    public void setVideoUrl(List<String> videoUrl1){this.videoUrl = videoUrl1;}
    public List<String> getVideoUrl(){return videoUrl;}

    public void setScore(int score1){this.score = score1;}
    public int getScore(){return score;}



}
