package com.framework.data;

public class RepositoryBuilder {
    private String name = "default-repo";
    private String owner = "default-owner";
    private boolean isPrivate = false;

    public RepositoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RepositoryBuilder withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public RepositoryBuilder setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    public String buildPath() {
        return String.format("/repos/%s/%s", owner, name);
    }
}