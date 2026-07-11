package com.absolutecinema.model;

public class Movie {
    // Atributos

    private String id;
    private String title;
    private String synopsis;
    private Genre genre;
    private int durationMinutes;
    private String rating;
    private double score;
    private String imagePath;
    private String bannerPath;
    private MovieStatus status;

    // Constructor

    public Movie(String id, String title, String synopsis, Genre genre, int durationMinutes, String rating, double score, String imagePath, String bannerPath, MovieStatus status) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.rating = rating;
        this.score = score;
        this.imagePath = imagePath;
        this.bannerPath = bannerPath;
        this.status = status;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getRating() {
        return rating;
    }

    public double getScore() {
        return score;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }
}
