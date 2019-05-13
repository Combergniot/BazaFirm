package com.bazafirm.scrappers;

import java.util.List;

public class SiteStructure {

    private String name;
    private String link;
    private int number;
    private int lastPaginationNumber;
    private List<String> linkList;

    public SiteStructure() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLastPaginationNumber() {
        return lastPaginationNumber;
    }

    public void setLastPaginationNumber(int lastPaginationNumber) {
        this.lastPaginationNumber = lastPaginationNumber;
    }

    public List<String> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<String> linkList) {
        this.linkList = linkList;
    }
}
