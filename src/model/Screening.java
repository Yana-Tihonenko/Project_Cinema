package model;

import java.util.Date;
import java.util.List;

public class Screening {
  private  Film idFilm;
  private List<Date> dataTimesListScr;
  private  double price;

  public Screening(Film idFilm, List<Date> dataTimes, double price) {
    this.idFilm = idFilm;
    this.dataTimesListScr = dataTimes;
    this.price = price;
  }

  public Film getIdFilm() {
    return idFilm;
  }

  public List<Date> getDataTimesListScr() {
    return dataTimesListScr;
  }

  public double getPrice() {
    return price;
  }
}
