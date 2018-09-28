package com.guye.model;

/**
 * Created by suneee on 2018/5/3.
 */
public class Pic {

    private int id;
    private String name;
    private String url;

    public Pic(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Pic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
