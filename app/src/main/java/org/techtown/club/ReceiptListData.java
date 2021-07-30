package org.techtown.club;

public class ReceiptListData {
    public String plusText;
    public String minusText;
    public String useDate;
    public ReceiptListData(String plusText, String minusText, String useDate) {
        this.plusText = plusText;
        this.minusText = minusText;
        this.useDate =useDate;
    }

    public String getPlusText() {
        return plusText;
    }

    public String getMinusText() {
        return minusText;
    }

    public String getUseDate() {
        return useDate;
    }
}
