package org.techtown.club.dto;

public class Role {
    private String name;
    private boolean notice_auth;

    public Role(String name, boolean notice_auth) {
        this.name = name;
        this.notice_auth = notice_auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotice_auth() {
        return notice_auth;
    }

    public void setNotice_auth(boolean notice_auth) {
        this.notice_auth = notice_auth;
    }
}
