package bupaljr.com.fikzar.model;

public class HandymanPost {

    private String profile_pic;
    private String full_name;
    private String post_pic;
    private String post_content;

    public HandymanPost() {
    }

    public HandymanPost(String profile_pic, String full_name, String post_pic, String post_content) {
        this.profile_pic = profile_pic;
        this.full_name = full_name;
        this.post_pic = post_pic;
        this.post_content = post_content;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPost_pic() {
        return post_pic;
    }

    public void setPost_pic(String post_pic) {
        this.post_pic = post_pic;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }
}
