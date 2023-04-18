package model;

import java.util.Date;
import java.util.List;

public class Ticket {
  private Film idFilm;
  private Date dataFilm;
  private double totalCost;
  private List<Seats> seats;

  public Ticket(Film idFilm, Date dataFilm, double totalCost, List<Seats> seats) {
    this.idFilm = idFilm;
    this.dataFilm = dataFilm;
    this.totalCost = totalCost;
    this.seats = seats;
  }

  public Film getIdFilm() {
    return idFilm;
  }

  public Date getDataFilm() {
    return dataFilm;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public List<Seats> getSeats() {
    return seats;
  }
}
