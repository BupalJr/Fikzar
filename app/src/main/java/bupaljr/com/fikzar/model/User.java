package bupaljr.com.fikzar.model;

public class User {

    String fullName, userName, email, city, jobs, statement;

    public User() {

    }

    public User(String fullName, String userName, String email) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
    }

    public User(String fullName, String userName, String email, String city, String jobs, String statement) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.city = city;
        this.jobs = jobs;
        this.statement = statement;
    }
}

