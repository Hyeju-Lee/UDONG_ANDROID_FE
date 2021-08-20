package org.techtown.club.dto;

public class ClubPost {
    String title;
    String content;
    int teamNumber;

    public ClubPost(String title, String content, int teamNumber) {
        this.title = title;
        this.content = content;
        this.teamNumber = teamNumber;
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

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }
}
