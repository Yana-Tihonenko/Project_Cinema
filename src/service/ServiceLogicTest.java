package service;

import model.Film;
import model.Genre;
import model.Session;
import model.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceLogicTest {

  @DisplayName("checkIdFilm(). IdFilm есть в списке фильмов ")
  @Test
  public void checkIdFilm_idFilmIsValid() {
    List<Film> filmList = createTestDataListFilm();
    boolean result = ServiceLogic.checkIdFilm(filmList, 3);
    assertTrue(result);
  }

  @DisplayName("checkIdFilm(). IdFilm нет в списке фильмов ")
  @Test
  public void checkIdFilm_idFilmIsNotInList() {
    List<Film> filmList = createTestDataListFilm();
    boolean result = ServiceLogic.checkIdFilm(filmList, 2);
    assertFalse(result);
  }

  @DisplayName("checkIdFilm(). Список фильмов пустой")
  @Test
  public void checkIdFilm_filmListIsEmpty() {
    List<Film> filmList = new ArrayList<>();
    boolean result = ServiceLogic.checkIdFilm(filmList, 2);
    assertFalse(result);
  }

  @DisplayName("checkIdFilm(). Пустая ссылка на список фильмов")
  @Test
  public void checkIdFilm_filmListIsNull() {
    List<Film> filmList = null;
    boolean result = ServiceLogic.checkIdFilm(filmList, 2);
    assertFalse(result);
  }

  @DisplayName("checkIdFilm(). IdFilm меньше нуля")
  @Test
  public void checkIdFilm_idFilmLessZero() {
    List<Film> filmList = createTestDataListFilm();
    boolean result = ServiceLogic.checkIdFilm(filmList, -1);
    assertFalse(result);
  }

  @DisplayName("checkIdFilm(). IdFilm равен нулю")
  @Test
  public void checkIdFilm_idFilmIsZero() {
    List<Film> filmList = createTestDataListFilm();
    boolean result = ServiceLogic.checkIdFilm(filmList, 0);
    assertFalse(result);
  }

  @DisplayName("searchFilmFromList(). Фильм есть в списке фильмов")
  @Test
  public void searchFilmFromList_filmInList() {
    Film film = new Film(1, "Name film1", 2012, "Description film1", Genre.MELODRAMA);
    List<Film> filmList = createTestDataListFilm();
    Film result = ServiceLogic.searchFilmFromList(filmList, 1);
    assertEquals(film, result);
  }

  @DisplayName("searchFilmFromList(). В списке фильмов отсутствует необходимый фильм")
  @Test
  public void searchFilmFromList_filmIsNotInList() {
    List<Film> filmList = createTestDataListFilm();
    Film result = ServiceLogic.searchFilmFromList(filmList, 2);
    assertNull(result);
  }

  @DisplayName("searchSessionFromList(). В списке сеансов есть по фильму сеансы")
  @Test
  public void searchSessionFromList_sessionIsInList() throws ParseException {
    List<Session> sessionList = createTestDataListSession();
    Session session = new Session(1, 123, createTestListWithData());
    Session result = ServiceLogic.searchSessionFromList(sessionList, 1);
    assertEquals(session, result);
  }

  @DisplayName("searchSessionFromList(). В списке сеансов нет по фильму сеансы")
  @Test
  public void searchSessionFromList_sessionIsNotInList() throws ParseException {
    List<Session> sessionList = createTestDataListSession();
    Session result = ServiceLogic.searchSessionFromList(sessionList, 3);
    assertNull(result);
  }

  @DisplayName("searchSessionFromList(). Список сеансов пустой")
  @Test
  public void searchSessionFromList_sessionListIsEmpty() {
    List<Session> sessionList = new ArrayList<>();
    Session result = ServiceLogic.searchSessionFromList(sessionList, 2);
    assertNull(result);
  }

  @DisplayName("searchSessionFromList(). Пустая ссылка на список сеансов")
  @Test
  public void searchSessionFromList_sessionListIsNull() {
    List<Session> sessionList = null;
    Session result = ServiceLogic.searchSessionFromList(sessionList, 2);
    assertNull(result);
  }

  @DisplayName("searchSessionFromList. IdFilm равен нулю")
  @Test
  public void searchSessionFromList_idFilmIsZero() throws ParseException {
    List<Session> sessionList = createTestDataListSession();
    Session result = ServiceLogic.searchSessionFromList(sessionList, 0);
    assertNull(result);
  }

  @DisplayName("searchPriceFromSession(). IdFilm есть в списке сеансов ")
  @Test
  public void searchPriceFromSession_idFilmIsValid() throws ParseException {
    List<Session> sessionList = createTestDataListSession();
    double result = ServiceLogic.searchPriceFromSession(sessionList, 2);
    Session session = ServiceLogic.searchSessionFromList(sessionList, 2);
    assertEquals(session.getPrice(), result);
  }

  @DisplayName("searchPriceFromSession(). IdFilm нет в списке фильмов ")
  @Test
  public void searchPriceFromSession_idFilmIsNotInList() throws ParseException {
    List<Session> sessionList = createTestDataListSession();
    double result = ServiceLogic.searchPriceFromSession(sessionList, 5);
    assertEquals(0, result);
  }

  @DisplayName("searchPriceFromSession(). Список сеансов пустой")
  @Test
  public void searchPriceFromSession_sessionListIsEmpty() {
    List<Session> sessionList = new ArrayList<>();
    double result = ServiceLogic.searchPriceFromSession(sessionList, 2);
    assertEquals(0, result);
  }

  @DisplayName("searchPriceFromSession(). Пустая ссылка на список сеансов")
  @Test
  public void searchPriceFromSession_sessionListIsNull() {
    List<Session> sessionList = null;
    double result = ServiceLogic.searchPriceFromSession(sessionList, 2);
    assertEquals(0, result);
  }

  @DisplayName("calculationRating. Расчет среднего значения")
  @Test
  public void calculationRating_positive() {
    List<Integer> listRating1 = new ArrayList<>(Arrays.asList(1, 3, 5, 10));
    List<Integer> listRating2 = new ArrayList<>(Arrays.asList(1, 3, 5, 8));
    String result1 = ServiceLogic.calculationRating(listRating1);
    String result2 = ServiceLogic.calculationRating(listRating2);
    assertEquals("4.0", result1);
    assertEquals("4.0", result2);
  }

  @DisplayName("calculationRating(). Список оценок пустой")
  @Test
  public void calculationRating_ratingListIsEmpty() {
    List<Integer> ratingList = new ArrayList<>();
    String result = ServiceLogic.calculationRating(ratingList);
    assertEquals("Нет оценок", result);
  }

  @DisplayName("searchPriceFromSession(). Пустая ссылка на список оценок")
  @Test
  public void calculationRating_ratingListIsNull() {
    List<Integer> ratingList = null;
    String result = ServiceLogic.calculationRating(ratingList);
    assertEquals("Нет оценок", result);
  }

  @DisplayName("searchReservedAllSeats(). Пустая ссылка на список билетов")
  @Test
  public void searchReservedAllSeats_ticketListIsNull() throws ParseException {
    List<Ticket> ticketList = null;
    Date dateBooking = createDateTicket("12.04.2023 17:00");
    List<String> result = ServiceLogic.searchReservedAllSeats(ticketList, dateBooking, 1);
    assertTrue(result.isEmpty());
  }

  @DisplayName("searchReservedAllSeats(). Пустой список билетов")
  @Test
  public void searchReservedAllSeats_ticketListIsEmpty() throws ParseException {
    List<Ticket> ticketList = new ArrayList<>();
    Date dateBooking = createDateTicket("12.04.2023 17:00");
    List<String> result = ServiceLogic.searchReservedAllSeats(ticketList, dateBooking, 1);
    assertTrue(result.isEmpty());
  }

  @DisplayName("searchReservedAllSeats(). Дата бронирования null")
  @Test
  public void searchReservedAllSeats_dataBookingIsEmpty() throws ParseException {
    List<Ticket> ticketList = createTestDateListTicket();
    Date dateBooking = null;
    List<String> result = ServiceLogic.searchReservedAllSeats(ticketList, dateBooking, 1);
    assertTrue(result.isEmpty());
  }

  @DisplayName("searchReservedAllSeats(). Дата бронирования не совпадает с датой купленных билетов")
  @Test
  public void searchReservedAllSeats_dateBookingAbsent() throws ParseException {
    List<Ticket> ticketList = createTestDateListTicket();
    Date dateBooking = createDateTicket("12.06.2023 17:00");
    List<String> result = ServiceLogic.searchReservedAllSeats(ticketList, dateBooking, 1);
    assertTrue(result.isEmpty());
  }

  @DisplayName("searchReservedAllSeats(). Индетификатор фильма отсутствует в списке билетов")
  @Test
  public void searchReservedAllSeats_idFilmAbsent() throws ParseException {
    List<Ticket> ticketList = createTestDateListTicket();
    Date dateBooking = createDateTicket("12.04.2023 17:00");
    List<String> result = ServiceLogic.searchReservedAllSeats(ticketList, dateBooking, 2);
    assertTrue(result.isEmpty());
  }

  @DisplayName("searchReservedAllSeats(). В списке билетов есть купленные места соответстующие дате и фильму")
  @Test
  public void searchReservedAllSeats_positive() throws ParseException {
    List<Ticket> ticketList = createTestDateListTicket();
    Date dateBooking = createDateTicket("12.04.2023 17:00");
    List<String> result = ServiceLogic.searchReservedAllSeats(ticketList, dateBooking, 1);
    Ticket ticket1 = ticketList.get(0);
    Ticket ticket2 = ticketList.get(1);
    List<String> seatsTicket1 = ticket1.getSeats();
    List<String> seatsTicket2 = ticket2.getSeats();
    List<String> expected = new ArrayList<>(seatsTicket1);
    expected.addAll(seatsTicket2);
    assertEquals(expected, result);
  }

  List<Film> createTestDataListFilm() {
    Film film1 = new Film(1, "Name film1", 2012, "Description film1", Genre.MELODRAMA);
    Film film2 = new Film(3, "Name film2", 2000, "Description film2", Genre.THRILLER);
    List<Film> filmList = new ArrayList<>();
    filmList.add(film1);
    filmList.add(film2);
    return filmList;
  }

  List<Session> createTestDataListSession() throws ParseException {
    List<Date> dateList = createTestListWithData();
    Session session1 = new Session(1, 123, dateList);
    Session session2 = new Session(2, 100, dateList);
    List<Session> sessionList = new ArrayList<>();
    sessionList.add(session1);
    sessionList.add(session2);
    return sessionList;
  }

  List<Date> createTestListWithData() throws ParseException {
    String dateString1 = "12.04.2023 17:00";
    String dateString2 = "12.05.2023 17:00";
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    Date date1 = dateFormat.parse(dateString1);
    Date date2 = dateFormat.parse(dateString2);
    List<Date> dateList = new ArrayList<>();
    dateList.add(date1);
    dateList.add(date2);
    return dateList;
  }

  Date createDateTicket(String date) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    Date result = dateFormat.parse(date);
    return result;
  }

  List<Ticket> createTestDateListTicket() throws ParseException {
    List<String> seatsTicket1 = new ArrayList<>(Arrays.asList("1", "2"));
    List<String> seatsTicket2 = new ArrayList<>(Arrays.asList("3", "4"));
    Ticket ticket1 = new Ticket(1, createDateTicket("12.04.2023 17:00"), 123, seatsTicket1);
    Ticket ticket2 = new Ticket(1, createDateTicket("12.04.2023 17:00"), 123, seatsTicket2);
    Ticket ticket3 = new Ticket(1, createDateTicket("12.05.2023 17:00"), 123, seatsTicket1);
    List<Ticket> ticketList = new ArrayList<>();
    ticketList.add(ticket1);
    ticketList.add(ticket2);
    ticketList.add(ticket3);
    return ticketList;
  }
}
