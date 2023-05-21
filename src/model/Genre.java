package model;

public enum Genre {
  ACTION(1, "Приключения"),
  COMEDY(2, "Комедия"),
  MELODRAMA(3, "Мелодрама"),
  THRILLER(4, "Триллер"),
  ;
  private final int numberGenre;
  private final String nameGenre;

  Genre(int numberGenre, String nameGenre) {
    this.numberGenre = numberGenre;
    this.nameGenre = nameGenre;
  }

  public int getNumberGenre() {
    return numberGenre;
  }

  public String getNameGenre() {
    return nameGenre;
  }
}
