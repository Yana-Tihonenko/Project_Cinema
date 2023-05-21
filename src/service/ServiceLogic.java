package service;

import model.Film;
import model.Session;
import model.Ticket;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceLogic {
  /**
   * Функция проверяет на существование индетификтора фильма в списке.
   *
   * @param filmList, объект из списка фильмов.
   * @param idFilm    индентификатор фильма.
   * @return возвращает boolean.
   */
  public static boolean checkIdFilm(List<Film> filmList, int idFilm) {
    if (filmList == null || filmList.isEmpty() || idFilm < 0 || idFilm == 0) {
      return false;
    }
    return filmList.stream().anyMatch(id -> id.getIdFilm() == idFilm);
  }

  /**
   * Функция возращает объект типа Film  из списка фильмов по индетификатору фильма.
   *
   * @param filmList список фильмов.
   * @param idFilm   индентификатор фильма.
   * @return возвращает объект типа Film или null.
   */
  public static Film searchFilmFromList(List<Film> filmList, int idFilm) {
    if (filmList == null || filmList.isEmpty()) {
      return null;
    }
    return filmList.stream()
        .filter(film -> film.getIdFilm() == idFilm)
        .findFirst()
        .orElse(null);
  }

  /**
   * Функция осуществляет поиск сеансов по идентификатору фильма.
   *
   * @param sessionList список сеансов.
   * @param idFilm      индентификатор фильма.
   * @return возвращает объект типа Session или null.
   */
  public static Session searchSessionFromList(List<Session> sessionList, int idFilm) {
    if (sessionList == null || sessionList.isEmpty()) {
      return null;
    }
    return sessionList.stream()
        .filter(session -> session.getIdFilm() == idFilm)
        .findFirst()
        .orElse(null);
  }

  /**
   * Функция осуществляет поиск и возвращает цену билета.
   *
   * @param idFilm индентификатор фильма.
   * @return возвращает цену или 0.
   */
  public static double searchPriceFromSession(List<Session> sessionList, int idFilm) {
    if (sessionList == null || sessionList.isEmpty()) {
      return 0;
    }
    return sessionList.stream()
        .filter(session -> session.getIdFilm() == idFilm)
        .findFirst()
        .map(Session::getPrice)
        .orElse(0.0);
  }

  /**
   * Функция расчитывает рейтинг фильма на основании списка отдельных  оценок.
   *
   * @param listRating список оценок по фильму.
   * @return возвращает среднее значение оценок типа String.
   */
  public static String calculationRating(List<Integer> listRating) {
    if (listRating == null || listRating.isEmpty()) {
      return "Нет оценок";
    }
    String result;
    double average = listRating.stream()
        .mapToInt(Integer::intValue)
        .average()
        .orElse(0.0);
    result = String.valueOf(Math.floor(average));
    return result;
  }

  /**
   * Функция формирует список сводобных мест на сеанс.
   *
   * @param ticketList  список купленных билетов с местами.
   * @param dateBooking дата сеанса.
   * @param idFilm      индетификатор фильма.
   * @return возвращает список свободных мест в формате List<String>.
   */
  public static List<String> searchReservedAllSeats(List<Ticket> ticketList, Date dateBooking, int idFilm) {
    if (ticketList == null || ticketList.isEmpty()|| dateBooking==null) {
      return  new ArrayList<>();
    }
    return ticketList.stream()
        .filter(id -> id.getIdFilm() == idFilm)
        .filter(data -> data.getDataFilm().equals(dateBooking))
        .map(Ticket::getSeats)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  /**
   * Функция проверяет на дубликаты выбранных и ранее забронированных мест.
   *
   * @param choiceSeats   список выб.
   * @param reservedSeats дата сеанса.
   * @return возвращает список мест в формате List<String>.
   */
  public static List<String> checkSeatsForBooking(List<String> reservedSeats, List<String> choiceSeats) {
    List<String> duplicatesSeats = new ArrayList<>();
    for (String i : reservedSeats) {
      for (String j : choiceSeats) {
        if (i.equals(j)) {
          duplicatesSeats.add(i);
        }
      }
    }
    return duplicatesSeats;
  }

  /**
   * Функция проверяет введенную дату по списку сеансов фильма
   *
   * @param dateBooking       дата .
   * @param sessionListToFilm сеансы фильма.
   * @return возвращает значение типа boolean.
   */
  public static boolean checkDataBooking(Date dateBooking, Session sessionListToFilm) {
    List<Date> dateList = sessionListToFilm.getDataTimesList();
    return dateList.stream().anyMatch(date -> date.equals(dateBooking));

  }
}