package com.example.moviemanager.model;

public class MovieInfo {
    private String director;
    private String description;

    public MovieInfo(String director, String description) {
        this.director = director;
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "director='" + director + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
