package org.techtown.club;

public class ListItemDetail_Frag2 {
    public String plusText;
    public String minusText;
    public String useDate;
    public ListItemDetail_Frag2(String plusText, String minusText, String useDate) {
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
