package bupaljr.com.fikzar.Utils;

public class Posts {

    private String postTime, postDesc, postImageUrl;
    private String userProfileImageUrl, username;

    public Posts() {
    }

    public Posts(String postTime, String postDesc, String postImageUrl) {
        this.postTime = postTime;
        this.postDesc = postDesc;
        this.postImageUrl = postImageUrl;
    }

    public Posts(String postTime, String postDesc, String postImageUrl, String userProfileImageUrl, String username) {
        this.postTime = postTime;
        this.postDesc = postDesc;
        this.postImageUrl = postImageUrl;
        this.userProfileImageUrl = userProfileImageUrl;
        this.username = username;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public void setUserProfileImageUrl(String userProfileImageUrl) {
        this.userProfileImageUrl = userProfileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}