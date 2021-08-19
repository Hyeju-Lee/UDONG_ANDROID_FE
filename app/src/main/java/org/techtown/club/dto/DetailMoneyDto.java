package org.techtown.club.dto;

import java.util.Comparator;

public class DetailMoneyDto implements Comparable<Object> {
    private String title;
    private String content;
    private String cost;

    public DetailMoneyDto(String title, String content, String cost) {
        this.title = title;
        this.content = content;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Object o) {
        return title.compareTo(((DetailMoneyDto) o).title);
    }
}
