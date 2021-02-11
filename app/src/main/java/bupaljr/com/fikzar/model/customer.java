package bupaljr.com.fikzar.model;

public class customer {

    private String profile_pic;
    private String full_name;
    private String user_name;
    private String email;
    private String post_pic;
    private String post_content;
    private String jobs;
    private String jobs_statement;
    private String location;
    private String last_online;
    private String member_since;


    public customer() {
    }

    public customer(String profile_pic, String full_name, String user_name, String email,
                    String post_pic, String post_content, String jobs, String jobs_statement,
                    String location, String last_online, String member_since) {
        this.profile_pic = profile_pic;
        this.full_name = full_name;
        this.user_name = user_name;
        this.email = email;
        this.post_pic = post_pic;
        this.post_content = post_content;
        this.jobs = jobs;
        this.jobs_statement = jobs_statement;
        this.location = location;
        this.last_online = last_online;
        this.member_since = member_since;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getJobs_statement() {
        return jobs_statement;
    }

    public void setJobs_statement(String jobs_statement) {
        this.jobs_statement = jobs_statement;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLast_online() {
        return last_online;
    }

    public void setLast_online(String last_online) {
        this.last_online = last_online;
    }

    public String getMember_since() {
        return member_since;
    }

    public void setMember_since(String member_since) {
        this.member_since = member_since;
    }
}
