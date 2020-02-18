package com.angryzpua.testtask;

public class CatListItem {
    private final String ID;
    private final String BREED;

    public CatListItem(String id, String breed) {
        this.ID = id;
        this.BREED = breed;
    }

    public String getID() {
        return ID;
    }

    public String getBREED() {
        return BREED;
    }

}
