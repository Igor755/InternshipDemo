package com.example.denfox.internshipdemo.models;


public class GitRepoItem {

    private int repoId;
    private String repoName;
    private String webUrl;
    private String description;

    public GitRepoItem(int repoId, String repoName, String webUrl, String description) {
        this.repoId = repoId;
        this.repoName = repoName;
        this.webUrl = webUrl;
        this.description = description;
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
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

        return repoId == that.repoId;

    }

    @Override
    public int hashCode() {
        return repoId;
    }

}
