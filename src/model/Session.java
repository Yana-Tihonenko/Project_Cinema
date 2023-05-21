package model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Session {
  private final int idFilm;
  private final double price;
  private final List<Date> dataTimesList;


  public Session(int idFilm, double price, List<Date> dataTimes) {
    this.idFilm = idFilm;
    this.dataTimesList = dataTimes;
    this.price = price;
  }

  public int getIdFilm() {
    return idFilm;
  }

  public List<Date> getDataTimesList() {
    return dataTimesList;
  }

  public double getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Session)) return false;
    Session session = (Session) o;
    return idFilm == session.idFilm && Double.compare(session.price, price) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFilm, price);
  }
}
