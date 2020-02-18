package com.angryzpua.testtask;

public class BreedCard {
    private String breed;
    private String weight;
    private String lifetime;
    private String country;
    private String temperament;
    private String wikiUrl;
    private String description;
    private String picture;

    public BreedCard(String breed, String weight, String lifetime, String country, String temperament, String wikiUrl, String description, String picture) {
        this.breed = breed;
        this.weight = weight;
        this.lifetime = lifetime;
        this.country = country;
        this.temperament = temperament;
        this.wikiUrl = wikiUrl;
        this.description = description;
        this.picture = picture;
    }

    public String getBreed() {
        return breed;
    }

    public String getWeight() {
        return weight;
    }

    public String getLifetime() {
        return lifetime;
    }

    public String getCountry() {
        return country;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }
}
