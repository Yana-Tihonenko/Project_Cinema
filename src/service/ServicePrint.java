package service;

import model.Film;
import model.Session;
import model.Ticket;
import utilPrint.TableGenerator;

import java.util.*;

public class ServicePrint {

  static TableGenerator tableGenerator = new TableGenerator();

  /**
   * Функция осуществляет вывод в консоль список фильмов в табличном представлении.
   *
   * @param filmList список фильмов.
   */
  public static void printFilm(List<Film> filmList) {
    filmList.sort(Comparator.comparing(Film::getIdFilm));
    List<String> headersList = new ArrayList<>();
    headersList.add("Номер фильма");
    headersList.add("Название фильма");
    headersList.add("Описание");
    headersList.add("Жанр");
    headersList.add("Рейтинг");

    List<List<String>> rowsList = new ArrayList<>();
    for (Film film : filmList) {
      List<String> row = new ArrayList<>();
      row.add(String.valueOf(film.getIdFilm()));
      row.add(film.getName());
      row.add(film.getDescription());
      row.add(film.getGenre().getNameGenre());
      if (film.getRating() == null || film.getRating().size() == 0) {
        row.add("нет пока оценок ");
      } else {
        row.add(ServiceLogic.calculationRating(film.getRating()));
      }
      rowsList.add(row);
    }
    System.out.println(tableGenerator.generateTable(headersList, rowsList));
  }

  /**
   * Функция осуществляет вывод в консоль данные о фильме в табличном представлении.
   *
   * @param film список фильмов.
   */
  public static void printFilm(Film film) {

    List<String> headersList = new ArrayList<>();
    headersList.add("Номер фильма");
    headersList.add("Название фильма");
    headersList.add("Описание");
    headersList.add("Жанр");
    headersList.add("Рейтинг");

    List<List<String>> rowsList = new ArrayList<>();
    List<String> row = new ArrayList<>();
    row.add(String.valueOf(film.getIdFilm()));
    row.add(film.getName());
    row.add(film.getDescription());
    row.add(film.getGenre().getNameGenre());
    if (film.getRating() == null || film.getRating().size() == 0) {
      row.add("нет пока оценок ");
    } else {
      row.add(ServiceLogic.calculationRating(film.getRating()));
    }
    rowsList.add(row);
    System.out.println(tableGenerator.generateTable(headersList, rowsList));
  }

  /**
   * Функция осуществляет вывод в консоль список сеансов в табличном представлении.
   *
   * @param session список фильмов.
   */
  public static void printSessionForChoice(Session session) {
    System.out.println("----Расписание сеансов----");
    List<Date> dateList = session.getDataTimesList();
    dateList.stream()
        .map(ServiceData::convertDataToStr)
        .sorted()
        .forEach(System.out::println);
  }

  /**
   * Функция осуществляет вывод в консоль список свободных и забронированных мест на сеанс.
   *
   * @param reservedSeats список забронованных мест на сеанс.
   * @param seatsCinema   список всех мест в зале.
   */
  public static void printSeatsForChoice(List<String> reservedSeats, List<String> seatsCinema) {
    List<String> result = new ArrayList<>();
    if (!reservedSeats.isEmpty()) {
      List<String> combinedList = new ArrayList<>();
      combinedList.addAll(seatsCinema);
      combinedList.addAll(reservedSeats);
      Map<String, Integer> counts = new HashMap<>();
      for (String element : combinedList) {
        counts.put(element, counts.getOrDefault(element, 0) + 1);
      }
      for (int i = 0; i < seatsCinema.size(); i++) {
        String element = combinedList.get(i);
        if (counts.get(element) > 1) {
          result.add("**");
        } else {
          if (element.length() == 1) {
            result.add(element + " ");
          } else {
            result.add(element);
          }
        }
      }
    } else {
      for (String element : seatsCinema) {
        if (element.length() == 1) {
          result.add(element + " ");
        } else {
          result.add(element);
        }
      }
    }
    int count = 0;
    for (String seat : result) {
      if (count % 10 == 0) {
        System.out.println();
      }
      System.out.print(seat + " ");
      count++;
    }

    if (count % 4 != 0) {
      System.out.println();
    }
    System.out.println();
  }

  /**
   * Функция осуществляет вывод в консоль данные о купленном билете.
   *
   * @param ticket объект типа Ticket.
   */
  public static void printTicket(Ticket ticket) {
    System.out.println("----Ваш билет----");
    System.out.println("Дата сеанса: " + ServiceData.convertDataToStr(ticket.getDataFilm()));
    System.out.println("Стоимость билета: " + ticket.getTotalCost() + " EURO");
    System.out.print("Ваши места: ");
    printListSeats(ticket.getSeats());

  }

  /**
   * Функция осуществляет вывод в консоль значения из списка в формате, например: 1,2,3,4.
   *
   * @param list список типа String
   */
  public static void printListSeats(List<String> list) {
    for (int i = 0; i < list.size(); i++) {
      if (i < list.size() - 1) {
        System.out.print(list.get(i) + ", ");
      } else {
        System.out.print(list.get(i) + ".");
      }
    }
    System.out.println();
  }

  /**
   * Функция формирует и выводит в консоль статистику по фильму : даты сенсов, кол-во купленных билетов,
   * сумма продажи билетов.
   *
   * @param film        фильм, по которому необходимо сформировать статистику
   * @param sessionFilm сеансы по фильму
   * @param ticketList  список куленных билеты по фильму
   * @param seatsCinema список мест в зале
   */
  public static void printStatisticFilm(Film film, Session sessionFilm, List<Ticket> ticketList, List<String> seatsCinema) {
    if (sessionFilm != null) {
      List<String> headersList = new ArrayList<>();
      headersList.add("Дата сеанса");
      headersList.add("Количество купленных мест");
      headersList.add("Сумма продажи");
      headersList.add("Количество свободных мест");
      List<List<String>> rowsList = new ArrayList<>();
      sessionFilm.getDataTimesList().sort(Comparator.naturalOrder());
      for (Date date : sessionFilm.getDataTimesList()) {
        List<String> row = new ArrayList<>();
        List<String> reservedSeatSession = ServiceLogic.searchReservedAllSeats(ticketList, date, film.getIdFilm());
        String dataStr = ServiceData.convertDataToStr(date);
        int countSeat = reservedSeatSession.size();
        double amountSaleTicketSession = countSeat * sessionFilm.getPrice();
        int freeSeats = seatsCinema.size() - reservedSeatSession.size();
        row.add(dataStr);
        row.add(String.valueOf(countSeat));
        row.add(String.valueOf(amountSaleTicketSession));
        row.add(String.valueOf(freeSeats));
        rowsList.add(row);
      }
      System.out.println(tableGenerator.generateTable(headersList, rowsList));
    } else {

      System.out.println("Выбранный фильм не имеет сеансов!!!");
    }
  }

  /**
   * Функция формирует и выводит в консоль статистику по всем текущим фильмам : даты сенсов, кол-во купленных билетов,
   * сумма продажи билетов.
   *
   * @param filmList    список фильмов, по которому необходимо сформировать статистику
   * @param sessionList список сеансов
   * @param ticketList  список куленных билеты по фильму
   * @param seatsCinema список мест в зале
   */
  public static void printStatisticAllFilms(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatsCinema) {
    List<String> headersList = new ArrayList<>();
    headersList.add("Номер фильма");
    headersList.add("Название фильма");
    headersList.add("Дата сеанса");
    headersList.add("Количество купленных мест");
    headersList.add("Сумма продажи");
    headersList.add("Количество свободных мест");
    List<List<String>> rowsList = new ArrayList<>();
    for (Film film : filmList) {
      Session session = ServiceLogic.searchSessionFromList(sessionList, film.getIdFilm());
      if (session != null) {
        session.getDataTimesList().sort(Comparator.naturalOrder());
        for (Date date : session.getDataTimesList()) {
          List<String> row = new ArrayList<>();
          List<String> reservedSeatSession = ServiceLogic.searchReservedAllSeats(ticketList, date, film.getIdFilm());
          String dataStr = ServiceData.convertDataToStr(date);
          int countSeat = reservedSeatSession.size();
          double amountSaleTicketSession = countSeat * session.getPrice();
          int freeSeats = seatsCinema.size() - reservedSeatSession.size();
          row.add(String.valueOf(film.getIdFilm()));
          row.add(film.getName());
          row.add(dataStr);
          row.add(String.valueOf(countSeat));
          row.add(String.valueOf(amountSaleTicketSession));
          row.add(String.valueOf(freeSeats));
          rowsList.add(row);
        }
      }
    }
    System.out.println(tableGenerator.generateTable(headersList, rowsList));
  }
}


