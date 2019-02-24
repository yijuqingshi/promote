package com.ztdh.promote.model.bean;

public class LoginRespon {

    private String accessKey;

    private int userAuth;

    private int recomId;

    private String email;

    private String username;

    private String registerDate;

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private UserPush userPush;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }


    public int getRecomId() {
        return recomId;
    }

    public int getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(int userAuth) {
        this.userAuth = userAuth;
    }

    public void setRecomId(int recomId) {
        this.recomId = recomId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public UserPush getUserPush() {
        return userPush;
    }

    public void setUserPush(UserPush userPush) {
        this.userPush = userPush;
    }

    public  static class UserPush {

        private String userPushURL;

        public String getUserPushURL() {
            return userPushURL;
        }

        public void setUserPushURL(String userPushURL) {
            this.userPushURL = userPushURL;
        }
    }

}
