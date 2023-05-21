package model;

import java.util.Date;
import java.util.List;

public class Ticket {
  private final int idFilm;
  private final Date dataFilm;
  private final double totalCost;
  private final List<String> seats;

  public Ticket(int idFilm, Date dataFilm, double totalCost, List<String> seats) {
    this.idFilm = idFilm;
    this.dataFilm = dataFilm;
    this.totalCost = totalCost;
    this.seats = seats;
  }

  public int getIdFilm() {
    return idFilm;
  }

  public Date getDataFilm() {
    return dataFilm;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public List<String> getSeats() {
    return seats;
  }
}

