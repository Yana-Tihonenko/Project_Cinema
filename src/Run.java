import menu.Menu;
import model.Film;
import model.Session;
import model.Ticket;
import service.ServiceDataDB;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Run {
  /**
   * Старт программы и иницилизация данных.
   * Проверка и запуск приложения под ролью Admin или Client.
   * Если пи инициализации данных произошла ошибка, формируется соответсвующая запись, программа завершает работу.
   *
   * @param args . Необязательный параметр. Если значение args = Admin, приложение запускается под ролью Admin.
   *               Если входящий параметр не задан, приложение запускается под ролью Client.
   */

  public static void main(String[] args) throws ParseException, IOException {
    ServiceDataDB dataDB = new ServiceDataDB();
    List<Film> filmList = dataDB.initFilmList();
    List<Session> sessionList = dataDB.initSessionsList();
    List<Ticket> ticketList = dataDB.initTicketList();
    List<String> seatsList = dataDB.initSeatsList();
    if (filmList.isEmpty() || sessionList.isEmpty() || ticketList.isEmpty() || seatsList.isEmpty()) {
      System.err.print(" Завершение работы");
    } else {
      if (args.length == 0) {
        Menu.mainMenuClient(filmList, sessionList, ticketList, seatsList);
      } else if (args[0].equals("Admin")) {
        Menu.mainMenuAdmin(filmList, sessionList, ticketList, seatsList);
      } else {
        System.out.println("Неправильно указанны параметры запуска ");
      }
    }
  }
}