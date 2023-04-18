package model;

import java.util.ArrayList;
import java.util.List;

public class Film {
  private final int idFilm;
  private final String name;
  private final String description;
  private final Genre genre;
  private List<Integer> rating;

  private int initId = 1;

  public Film(String name, String description, Genre genre) {
    this.idFilm = initId;
    this.name = name;
    this.description = description;
    this.genre = genre;
    this.rating = new ArrayList<>();
    initId++;
  }

  public int getIdFilm() {
    return idFilm;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Genre getGenre() {
    return genre;
  }

  public List<Integer> getRating() {
    return rating;
  }
}


