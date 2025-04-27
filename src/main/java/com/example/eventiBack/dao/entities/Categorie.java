package com.example.eventiBack.dao.entities;

public enum Categorie {
    CONFERENCE("conférences"),
    SEMINAIRE("séminaires"),
    ATELIER("ateliers");

    private final String displayName;

    Categorie(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
} 