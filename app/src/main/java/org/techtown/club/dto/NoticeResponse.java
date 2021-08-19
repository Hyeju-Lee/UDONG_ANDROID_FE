package org.techtown.club.dto;

public class NoticeResponse {
    private String title;
    private String name;
    private String date;
    private String content;

    public NoticeResponse(String title, String name, String date, String content) {
        this.title = title;
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
