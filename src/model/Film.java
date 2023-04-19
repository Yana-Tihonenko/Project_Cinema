package model;

import java.util.ArrayList;
import java.util.List;

public class Film {
  private final int idFilm;
  private final String name;

  private final  int year;

  private final String description;
  private final Genre genre;
  private List<Integer> rating;

  private static int idFilmLast;

  public Film(int idFilm,String name, int year, String description, Genre genre, List<Integer> rating) {
    this.year = year;
    this.idFilm = idFilm;
    this.name = name;
    this.description = description;
    this.genre = genre;
    this.rating = new ArrayList<>();
    idFilmLast++;
  }
  public Film(int idFilm,String name, int year, String description, Genre genre) {
    this.year = year;
    this.idFilm = idFilm;
    this.name = name;
    this.description = description;
    this.genre = genre;
    idFilmLast++;
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

  public static int getIdFilmLast() {
    return idFilmLast;
  }

  public static void setIdFilmLast(int idFilmLast) {
    Film.idFilmLast = idFilmLast;
  }

  @Override
  public String toString() {
    return "Film{" +
        "idFilm=" + idFilm +
        ", name='" + name + '\'' +
        ", year=" + year +
        ", description='" + description + '\'' +
        ", genre=" + genre +
        '}';
  }


}


