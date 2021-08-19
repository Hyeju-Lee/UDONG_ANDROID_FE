package org.techtown.club.dto;

public class Receipt {
    private String cost;
    private String title;
    private String content;
    private String useDate;

    public Receipt(String cost, String title, String content, String useDate) {
        this.cost = cost;
        this.title = title;
        this.content = content;
        this.useDate = useDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }
}
