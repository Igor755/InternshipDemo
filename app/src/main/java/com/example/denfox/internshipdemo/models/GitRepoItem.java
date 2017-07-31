package com.example.denfox.internshipdemo.models;


import org.json.JSONException;
import org.json.JSONObject;

public class GitRepoItem {

    private int id;
    private String name;
    private String url;
    private String description;

    public GitRepoItem(int id, String name, String url, String description) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public GitRepoItem(JSONObject json) {

        try {
            this.id = json.has("id") ? json.getInt("id") : -1;
            this.name = json.has("name") ? json.getString("name") : "";
            this.url = json.has("html_url") ? json.getString("html_url") : null;
            this.description = json.has("description") ? json.getString("description") : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitRepoItem that = (GitRepoItem) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

}
