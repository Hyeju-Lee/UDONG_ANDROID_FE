package org.techtown.club.dto;

public class Club {
    private String name;
    private int generation;
    private String info;
    private String clubCode;

    public Club(String name, int generation, String info ,String clubCode) {
        this.name = name;
        this.generation = generation;
        this.info = info;
        this.clubCode = clubCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getClubCode() {
        return clubCode;
    }

    public void setClubCode(String clubCode) {
        this.clubCode = clubCode;
    }
}
