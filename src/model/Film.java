package model;

import java.util.List;
import java.util.Objects;

public class Film {
  private final int idFilm;
  private final String name;

  private final int year;

  private final String description;
  private final Genre genre;
  private  List<Integer> rating;
  private static int idFilmLast;

  public Film(int idFilm, String name, int year, String description, Genre genre, List<Integer> rating) {
    this.year = year;
    this.idFilm = idFilm;
    this.name = name;
    this.description = description;
    this.genre = genre;
    this.rating = rating;
    idFilmLast++;
  }

  public Film(int idFilm, String name, int year, String description, Genre genre) {
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

  public int getYear() {
    return year;
  }

  public static void setIdFilmLast(int idFilmLast) {
    Film.idFilmLast = idFilmLast;
  }


  public void setRating(List<Integer> ratingList) {
    this.rating =ratingList;

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Film)) return false;
    Film film = (Film) o;
    return idFilm == film.idFilm && year == film.year && name.equals(film.name)
        && Objects.equals(description, film.description) && genre == film.genre;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFilm, name, year, description, genre);
  }
}


