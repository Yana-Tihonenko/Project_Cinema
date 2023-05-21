package menu;

import model.Film;
import model.Genre;
import model.Session;
import model.Ticket;
import service.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Menu {
  /**
   * Запуск главного меню для роли Admin. Вывод списка доступных операций.
   * Валидация выбранного пунка, переход в соответствующее подменю.
   * При выборе пункта "Выйти из приложения" данные сохраняются в файл.
   *
   * @param filmList,   список фильмов.
   * @param sessionList список сеансов.
   * @param ticketList, список билетов.
   * @param seatList    список мест в зале.
   */
  public static void mainMenuAdmin(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatList) throws IOException, ParseException {
    boolean checkCommand = false;
    int choice;
    System.out.println("""
        --------Доступные действия--------
          1. Добавить новый фильм
          2. Просмотреть статистику
          3. Просмотреть список фильмов
          4. Выйти из приложения""");
    System.out.print("Введите номер пункта меню: ");
    do {
      choice = ServiceInputData.inputAndValidationInt();
      if (choice != -1) {
        checkCommand = ServiceInputData.checkInputCommand(1, 4, choice);
      }
    } while (!checkCommand);
    switch (choice) {
      case 1 -> subMenuAddNewFilm(filmList, sessionList, ticketList, seatList);
      case 2 -> subMenuStatistic(filmList, sessionList, ticketList, seatList);
      case 3 -> {
        ServicePrint.printFilm(filmList);
        mainMenuAdmin(filmList, sessionList, ticketList, seatList);
      }
      case 4 -> {
        ServiceDataDB.saveDateToDB(filmList, sessionList, ticketList);
        System.exit(0);
      }
    }
  }

  /**
   * Запуск подменю "Статистика" для роли Admin. Вывод списка доступных операций.
   * Валидация выбранного пунка, вызов функции, соответствующей пункту меню.
   *
   * @param filmList,   список фильмов.
   * @param sessionList список сеансов.
   * @param ticketList, список билетов.
   * @param seatList    список мест в зале.
   */
  private static void subMenuStatistic(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatList) throws IOException, ParseException {
    System.out.println();
    boolean checkCommand = false;
    int choice;
    System.out.println("""
        --------Доступные действия--------:
          1. Посмотреть статитику по фильму
          2. Посмотреть статистику по всем фильмам
          3. Вернуться в главное меню""");
    System.out.print("Введите номер пункта меню: ");
    do {
      choice = ServiceInputData.inputAndValidationInt();
      if (choice != -1) {
        checkCommand = ServiceInputData.checkInputCommand(1, 3, choice);
      }
    } while (!checkCommand);
    switch (choice) {
      case 1 -> subMenuStatisticToFilm(filmList, sessionList, ticketList, seatList);
      case 2 -> {
        ServicePrint.printStatisticAllFilms(filmList, sessionList, ticketList, seatList);
        subMenuStatistic(filmList, sessionList, ticketList, seatList);
      }
      case 3 -> mainMenuAdmin(filmList, sessionList, ticketList, seatList);
    }
  }

  /**
   * Запуск диалога ввода идентификатора фильма и формирование, вывод в консоль стаститики по фильму.
   * Допупно для роли Admin.
   *
   * @param filmList,   список фильмов.
   * @param sessionList список сеансов.
   * @param ticketList, список билетов.
   */
  private static void subMenuStatisticToFilm(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatsCinema) throws IOException, ParseException {
    System.out.println();
    System.out.println("-----Список текущих фильмов-----");
    ServicePrint.printFilm(filmList);
    System.out.println();
    boolean validInput = true;
    int idFilm;
    do {
      System.out.print("Введите номер фильма: ");
      idFilm = ServiceInputData.inputAndValidationInt();
      if (ServiceLogic.checkIdFilm(filmList, idFilm)) {
        Film film = ServiceLogic.searchFilmFromList(filmList, idFilm);
        Session sessionFilm = ServiceLogic.searchSessionFromList(sessionList, idFilm);
        System.out.println();
        System.out.println("Название фильма: " + film.getName());
        ServicePrint.printStatisticFilm(film, sessionFilm, ticketList, seatsCinema);
        System.out.println();
        subMenuStatistic(filmList, sessionList, ticketList, seatsCinema);
        validInput = false;
      } else {
        System.out.println("Введите корретный номер фильма !!!");
      }
    } while (validInput);
  }

  /**
   * Запуск диалога ввода нового фильма. Валидация введенных параметров фильма.
   * Допупно для роли Admin.
   *
   * @param filmList,   список фильмов.
   * @param sessionList список сеансов.
   * @param ticketList, список билетов.
   */
  private static void subMenuAddNewFilm(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatList) throws IOException, ParseException {
    System.out.print("Введите название фильма ");
    String nameFilmClient = ServiceInputData.inputAndValidationStr();
    System.out.print("Введите год выпуска ");
    int yearClient = ServiceInputData.inputAndValidationYear();
    System.out.print("Введите краткое описание фильма ");
    String descriptionClient = ServiceInputData.inputAndValidationStr();
    System.out.println("""
        --------Введите жанр фильма--------:
          1. Приключения
          2. Комедия
          3. Мелодрама
          4. Триллер""");
    System.out.print("Выберите номер пункта: ");
    Genre genre = ServiceInputData.inputAndValidationGenre();
    Film film = new Film(Film.getIdFilmLast(), nameFilmClient, yearClient, descriptionClient, genre);
    filmList.add(film);
    System.out.print("Введите цену билета (EURO) ");
    double priceClient = ServiceInputData.inputAndValidationPrice();
    System.out.println();
    System.out.println("----Необходимо ввести даты сеансов для фильма----");
    List<Date> sessionDateTemp = new ArrayList<>();
    boolean repeatInputSession = true;
    while (repeatInputSession) {
      System.out.print("Введите дату в формате : dd.mm.yyyy (12.04.2023) ");
      String dateStrClient = ServiceInputData.inputAndValidationData();
      System.out.print("Введите время  чере зяпятую в формате hh:mm (15:00, 17:30) ");
      String listTimeStr = ServiceInputData.inputAndValidationTime();
      List<Date> dataList = ServiceData.convertStrToListData(dateStrClient, listTimeStr);
      sessionDateTemp.addAll(dataList);
      int choiceCommand;
      boolean checkInputCommand = false;
      System.out.println("""
          Добавить еще сеансов?:
            1. Да
            2. Нет""");
      do {
        choiceCommand = ServiceInputData.inputAndValidationInt();
        if (choiceCommand != -1) {
          checkInputCommand = ServiceInputData.checkInputCommand(1, 2, choiceCommand);
        }

      } while (!checkInputCommand);
      if (choiceCommand == 2) {
        repeatInputSession = false;
      }
    }
    Session session = new Session(film.getIdFilm(), priceClient, sessionDateTemp);
    sessionList.add(session);
    System.out.println("Добавления нового фильма прошла успешно");
    System.out.println("----Данные о фильме----");
    ServicePrint.printFilm(film);
    ServicePrint.printSessionForChoice(Objects.requireNonNull(session));
    mainMenuAdmin(filmList, sessionList, ticketList, seatList);

  }

  /**
   * Запуск главного меню для роли Client. Вывод списка доступных операций.
   * Валидация выбранного пунка, переход в соответствующее подменю.
   * При выборе пункта "Выйти из приложения" данные сохраняются в файл.
   *
   * @param filmList,    список фильмов.
   * @param sessionList  список сеансов.
   * @param ticketList,  список билетов.
   * @param seatsCinema, список мест в зале.
   */
  public static void mainMenuClient(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatsCinema) throws IOException, ParseException {
    int choiceCommand;
    boolean checkInputCommand = false;
    System.out.println();
    System.out.println("""
        --------Доступные действия--------
          1. Просмотреть список фильмов
          2. Купить билет
          3. Оценить фильм
          4. Выйти из приложения""");
    System.out.print("Введите номер пункта меню: ");
    do {
      choiceCommand = ServiceInputData.inputAndValidationInt();
      if (choiceCommand != -1) {
        checkInputCommand = ServiceInputData.checkInputCommand(1, 4, choiceCommand);
      }
    } while (!checkInputCommand);
    switch (choiceCommand) {
      case 1 -> {
        ServicePrint.printFilm(filmList);
        mainMenuClient(filmList, sessionList, ticketList, seatsCinema);
      }
      case 2 -> subMenuNewBooking(filmList, sessionList, ticketList, seatsCinema);
      case 3 -> subMenuNewRating(filmList, sessionList, ticketList, seatsCinema);
      case 4 -> {
        ServiceDataDB.saveDateToDB(filmList, sessionList, ticketList);
        System.exit(0);
      }
    }
  }

  /**
   * Запуск диалога ввода идентификтора и оценки фильма.
   * Валидация введенных параметров фильма, сохранение введенной оценки в список.
   * Допупно для роли Client.
   *
   * @param filmList,    список фильмов.
   * @param sessionList  список сеансов.
   * @param ticketList,  список билетов.
   * @param seatsCinema, список билетов.
   */
  private static void subMenuNewRating(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatsCinema) throws IOException, ParseException {
    boolean repeatInput = true;
    System.out.println();
    System.out.println("--------Список фильмов--------");
    ServicePrint.printFilm(filmList);
    System.out.print("Введите номер фильма для оценки: ");
    int idFilmClient = 0;
    while (repeatInput) {
      idFilmClient = ServiceInputData.inputAndValidationInt();
      if (idFilmClient != -1) {
        boolean checkIdFilm = ServiceLogic.checkIdFilm(filmList, idFilmClient);
        if (checkIdFilm) {
          repeatInput = false;
        } else {
          System.out.print("Неправильный номер фильма!!! Повторите ввод снова ");
        }
      }
    }
    System.out.print("Введите свою оценку: ");
    int ratingClient;
    do {
      ratingClient = ServiceInputData.inputAndValidationInt();
    } while (ratingClient == -1);
    Film filmForRating = ServiceLogic.searchFilmFromList(filmList, idFilmClient);
    List<Integer> ratingList = filmForRating.getRating();
    if (ratingList == null) {
      ratingList = new ArrayList<>();
      ratingList.add(ratingClient);
      filmForRating.setRating(ratingList);
    } else {
      ratingList.add(ratingClient);
    }
    System.out.println();
    System.out.print("Спасибо за ваш отзыв!!!");
    System.out.println();
    ServicePrint.printFilm(filmList);
    System.out.println();
    mainMenuClient(filmList, sessionList, ticketList, seatsCinema);
  }

  /**
   * Запуск диалога покупки билета.
   * Валидация введенных параметров, сохранение новых данных в список.
   * Допупно для роли Client.
   *
   * @param filmList,    список фильмов.
   * @param sessionList  список сеансов.
   * @param ticketList,  список билетов.
   * @param seatsCinema, список мест в зале.
   */
  private static void subMenuNewBooking(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList, List<String> seatsCinema) throws IOException, ParseException {
    boolean repeatInput = true;
    System.out.println();
    System.out.println("--------Список фильмов--------");
    ServicePrint.printFilm(filmList);
    int idFilmClient = -1;
    System.out.print("Введите номер фильма для покупки билетов: ");
    while (repeatInput) {
      idFilmClient = ServiceInputData.inputAndValidationInt();
      if (idFilmClient != -1) {
        boolean checkIdFilm = ServiceLogic.checkIdFilm(filmList, idFilmClient);
        if (checkIdFilm) {
          repeatInput = false;
        } else {
          System.out.print("Неправильный номер фильма. Повторите ввод снова. ");
        }
      }
    }
    Film filmForBooking = ServiceLogic.searchFilmFromList(filmList, idFilmClient);
    ServicePrint.printFilm(filmForBooking);
    System.out.println();
    System.out.println("Время сеансов фильма " + "'" + filmForBooking.getName() + "'");
    Session sessionListToFilm = ServiceLogic.searchSessionFromList(sessionList, idFilmClient);
    if (sessionListToFilm == null) {
      System.out.println("К сожалению на данный фильм пока нет сеансов.");
      mainMenuClient(filmList, sessionList, ticketList, seatsCinema);
    } else {
      ServicePrint.printSessionForChoice(sessionListToFilm);
      double priceFilm = ServiceLogic.searchPriceFromSession(sessionList, idFilmClient);
      System.out.println();
      System.out.println("Цена билета составляет " + priceFilm + " EURO");
      System.out.println();
      Date dateBooking;
      do {
        System.out.print("Введите дату в формате : dd.mm.yyyy (12.04.2023) ");
        String dateStrClient = ServiceInputData.inputAndValidationData();
        System.out.print("Введите время  чере зяпятую в формате hh:mm (15:00, 17:30) ");

        String timeStrClient = ServiceInputData.inputAndValidationTime();
        dateBooking = ServiceData.convertStrToData(dateStrClient, timeStrClient);
        boolean checkDataBooking = ServiceLogic.checkDataBooking(dateBooking, sessionListToFilm);
        if (checkDataBooking) {
          repeatInput = true;
        } else {
          System.out.println("На выбранную дату и время нет сеансов. Повторите ввод еще раз.");
        }
      } while (!repeatInput);
      List<String> seatsIsBooked = ServiceLogic.searchReservedAllSeats(ticketList, dateBooking, idFilmClient);
      System.out.println();
      System.out.println("Свободные места на сеанс:");
      ServicePrint.printSeatsForChoice(seatsIsBooked, seatsCinema);
      System.out.println();
      List<String> seatsListForBookingClient;
      boolean validInput = false;
      do {
        System.out.print("Выберите свободные (места через запятую) ");
        String seatsForBookingClient = ServiceInputData.inputAndValidationStr();
        seatsListForBookingClient = ServiceData.convertStrToListStr(seatsForBookingClient);
        List<String> seatsAlreadyBooking = ServiceLogic.checkSeatsForBooking(seatsIsBooked, seatsListForBookingClient);
        if (seatsAlreadyBooking.isEmpty()) {
          validInput = true;
        } else {
          System.out.print("Неправильный ввод свободных мест. Вы выбрали забронированные места(о): ");
          ServicePrint.printListSeats(seatsAlreadyBooking);
          System.out.println("Повторите еще раз попытку !!!");
        }
      } while (!validInput);
      int countSeatBooking = seatsListForBookingClient.size();
      System.out.println();
      System.out.print("Вы выбрали " + countSeatBooking + " место(а): ");
      ServicePrint.printListSeats(seatsListForBookingClient);
      double costTicket = priceFilm * countSeatBooking;
      System.out.println("Стоимость билета составляет: " + costTicket + " EURO");
      System.out.print
          ("Вы подтверждаете покупку билета? (y/n) ");
      boolean checkInputCommand;
      String choiceCommand;
      do {
        choiceCommand = ServiceInputData.inputAndValidationStr();
        checkInputCommand = ServiceInputData.checkInputCommandStr(choiceCommand);
      } while (!checkInputCommand);
      switch (choiceCommand.toUpperCase()) {
        case "Y" -> {
          Ticket newTicket = new Ticket(idFilmClient, dateBooking, costTicket, seatsListForBookingClient);
          ticketList.add(newTicket);
          System.out.println("Покупка успешно завершина! Спасибо за покупку!");
          System.out.println();
          ServicePrint.printTicket(newTicket);
          mainMenuClient(filmList, sessionList, ticketList, seatsCinema);
        }
        case "N" -> {
          System.out.println("Вы отменили покупку");
          mainMenuClient(filmList, sessionList, ticketList, seatsCinema);
        }
      }
    }
  }
}


