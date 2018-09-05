package com.gc.chatapp.dto;

import com.gc.chatapp.util.SearchType;

public class SearchEntity {

	private long id;
    private String name;
    private String email;
    private SearchType type;

    public SearchEntity() {
    }

    public SearchEntity(long id, String name,String email, SearchType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
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

    public SearchType getType() {
        return type;
    }

    public void setType(SearchType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SearchEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
	
}
