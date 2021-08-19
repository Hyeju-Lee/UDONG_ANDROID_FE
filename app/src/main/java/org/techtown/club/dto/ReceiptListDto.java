package org.techtown.club.dto;

public class ReceiptListDto {
    private String plus;
    private String minus;
    private String date;

    public ReceiptListDto(String plus, String minus, String date) {
        this.plus = plus;
        this.minus = minus;
        this.date = date;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }

    public String getMinus() {
        return minus;
    }

    public void setMinus(String minus) {
        this.minus = minus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
