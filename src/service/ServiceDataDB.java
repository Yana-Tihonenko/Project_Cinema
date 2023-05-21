package service;

import model.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class ServiceDataDB {

  /**
   * Функция инициализирует данные с файла о кинофильмах для дальнейшего использования
   * в работе в программе.
   *
   * @return возвращает массив объектов Film.
   */
  public List<Film> initFilmList() {
    File file = new File("src/source/Films");
    return parserDataFilms(readDateFromFile(file));
  }

  /**
   * Функция инициализирует данные с файла о сеансах кинофильмов для дальнейшего использования
   * в работе в программе.
   *
   * @return возвращает массив объектов Session.
   */
  public List<Session> initSessionsList() {
    File file = new File("src/source/Session");
    return parserDataSessions(readDateFromFile(file));
  }

  /**
   * Функция инициализирует данные с файла о кол-ве мест в зале кинотеатра для дальнейшего использования
   * в работе в программ.
   *
   * @return возвращает массив объектов Session.
   */
  public List<String> initSeatsList() {
    File file = new File("src/source/Seats");
    List<String> seatListCinema = readDateFromFile(file);
    Seats seats = new Seats();
    seats.setSeats(seatListCinema);
    return seats.getSeats();
  }

  /**
   * Функция инициализирует данные с файла о проданных билетах для дальнейшего использования
   * в работе в программ.
   *
   * @return возвращает массив объектов Session.
   */
  public List<Ticket> initTicketList() throws ParseException {
    File file = new File("src/source/Tickets");
    return parserDataTicket(readDateFromFile(file));
  }

  /**
   * Функция парсит строки файла и создает List <String>.
   *
   * @param file параметр типа File.
   * @return возвращает List объектов String.Элемент массива является строкой файла.
   * Если возникают ошибки FileNotFoundException или IOException, возвращается пустой List <String>.
   */
  public static List<String> readDateFromFile(File file) {
    List<String> readFile = new ArrayList<>();
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while ((line = br.readLine()) != null) {
        readFile.add(line);
      }
    } catch (FileNotFoundException e) {
      System.err.println("File exit, program is not initialized");
    } catch (IOException e) {
      System.err.println("Error read data, program is not initialized");
    }
    return readFile;
  }

  /**
   * Функция парсит строку из элементов List <String> и создает объекты типа Film.
   *
   * @param list , List<String> из списка строк.
   * @return возвращает List объектов Film. Если входящий параметр  list пустой(null)
   * или возникает ошибка при парсинге NumberFormatException, возвращается пустой List<Film>.
   */
  private static List<Film> parserDataFilms(List<String> list) {
    List<Film> filmList = new ArrayList<>();
    if (list == null || list.isEmpty()) {
      return filmList;
    } else {
      for (String s : list) {
        String[] tempArray = Pattern.compile(";")
            .splitAsStream(s)
            .toArray(String[]::new);
        try {
          int idFilm = parseInt(tempArray[0]);
          String nameFilm = tempArray[1];
          int year = parseInt(tempArray[2]);
          String descriptions = tempArray[3];
          Genre genre = Genre.valueOf(tempArray[4]);
          if (tempArray.length == 6) {
            List<Integer> rating;
            rating = Pattern.compile(",")
                .splitAsStream(tempArray[5])
                .map(Integer::parseInt)
                .collect(Collectors.toList());
            filmList.add(new Film(idFilm, nameFilm, year, descriptions, genre, rating));
          } else {
            filmList.add(new Film(idFilm, nameFilm, year, descriptions, genre));
          }
        } catch (NumberFormatException e) {
          System.err.print("Ошибка парсинга файла.");
          return new ArrayList<>();
        }
        int lastIdFilm = filmList.stream()
            .mapToInt(Film::getIdFilm)
            .summaryStatistics()
            .getMax();
        Film.setIdFilmLast(lastIdFilm + 1);
        System.out.println();
      }
    }
    return filmList;
  }

  /**
   * Функция парсит строку из элементов List <String> и создает объекты типа Session (сеансы кинофильмов) .
   *
   * @param list , List<String> из списка строк.
   * @return возвращает List объектов Session. Если входящий параметр  list пустой/null
   * или возникают ошибки NumberFormatException,ParseException, возвращается пустой List<Session>.
   */
  private static List<Session> parserDataSessions(List<String> list) {
    List<Session> sessionList = new ArrayList<>();
    if (list == null || list.isEmpty()) {
      return sessionList;
    } else {
      for (String value : list) {
        String[] tempArray = Pattern.compile(";")
            .splitAsStream(value)
            .toArray(String[]::new);
        int idFilm;
        double price;
        try {
          idFilm = parseInt(tempArray[0]);
          price = Double.parseDouble(tempArray[1]);
        } catch (NumberFormatException e) {
          System.err.println("Ошибка парсинга файла.");
          return new ArrayList<>();
        }
        List<Date> date = Pattern.compile(",")
            .splitAsStream(tempArray[2])
            .map(s -> {
              try {
                return new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(s);
              } catch (ParseException e) {
                return null;
              }
            })
            .collect(Collectors.toList());
        boolean isErrorParserDate = date.stream()
            .anyMatch(Objects::isNull);
        if (isErrorParserDate) {
          break;
        } else {
          sessionList.add(new Session(idFilm, price, date));
        }
      }
    }
    return sessionList;
  }

  /**
   * Функция парсит строку из элементов List <String> и создает объекты типа Ticket.
   *
   * @param list , List<String> из списка строк.
   * @return возвращает List объектов Ticket. Если входящий параметр  list пустой/null
   * или возникает ошибка NumberFormatException, возвращается пустой List<Ticket>.
   */
  private static List<Ticket> parserDataTicket(List<String> list) throws ParseException {
    List<Ticket> ticketList = new ArrayList<>();
    if (list == null || list.isEmpty()) {
      return ticketList;
    } else {
      for (String value : list) {
        String[] tempArray = Pattern.compile(";")
            .splitAsStream(value)
            .toArray(String[]::new);
        int idFilm;
        Date dateTicket;
        double cost;
        try {
          idFilm = parseInt(tempArray[0]);
          dateTicket = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(tempArray[1]);
          cost = Double.parseDouble(tempArray[2]);
        } catch (NumberFormatException e) {
          System.err.println("Ошибка парсинга файла.");
          return new ArrayList<>();
        }
        List<String> seatsTicket = Pattern.compile(",")
            .splitAsStream(tempArray[3])
            .collect(Collectors.toList());
        ticketList.add(new Ticket(idFilm, dateTicket, cost, seatsTicket));
      }
    }
    return ticketList;
  }

  /**
   * Функция принимает данные  объектов Film, Session, Ticket и  сохраняет каждый список в отдельный текстовый файл.
   * Если не существует необходимый файл, он создается и тюда заносятся соотвествующие данные из списка  объектов Film, Session, Ticket.
   *
   * @param filmList,sessionList,ticketList список объектов Film, Session, Ticket.
   */
  public static void saveDateToDB(List<Film> filmList, List<Session> sessionList, List<Ticket> ticketList) throws
      IOException {
    File file = new File("src/source/Films");
    if (!file.exists()) {
      System.out.print("Файл не найден.");
      if (file.createNewFile()) {
        System.out.print("Файл создан.");
      }
    }
    FileWriter fileWriter = new FileWriter(file);
    for (Film film : filmList) {
      int idFilm = film.getIdFilm();
      String name = film.getName();
      int year = film.getYear();
      String description = film.getDescription();
      String genre = String.valueOf(film.getGenre());
      List<Integer> ratingList = film.getRating();
      String result;
      String ratingStr="";
      if (ratingList != null) {
        for (int i = 0; i < ratingList.size(); i++) {
          if (i < ratingList.size() - 1) {
            ratingStr += ratingList.get(i) + ",";
          } else {
            ratingStr += ratingList.get(i);
          }
        }
        result = idFilm + ";" + name + ";" + year + ";" + description + ";" + genre + ";" + ratingStr;
      } else {
        result = idFilm + ";" + name + ";" + year + ";" + description + ";" + genre;
      }

      fileWriter.write(result + "\n");
    }
    fileWriter.close();

    file = new File("src/source/Session");
    if (!file.exists()) {
      System.out.print("Файл не найден.");
      if (file.createNewFile()) {
        System.out.print("Файл создан.");
      }
    }
    fileWriter = new FileWriter(file);
    for (Session session : sessionList) {
      int idFilm = session.getIdFilm();
      double price = session.getPrice();
      List<Date> dateList = session.getDataTimesList();
      StringBuilder dataListStr = new StringBuilder();
      for (int s = 0; s < dateList.size(); s++) {
        Date dateSession = dateList.get(s);
        if (s < dateList.size() - 1) {
          dataListStr.append(ServiceData.convertDataToStr(dateSession)).append(",");
        } else {
          dataListStr.append(ServiceData.convertDataToStr(dateSession));
        }
      }
      String result = idFilm + ";" + price + ";" + dataListStr;
      fileWriter.write(result + "\n");
    }
    fileWriter.close();

    file = new File("src/source/Tickets");
    if (!file.exists()) {
      System.out.print("Файл не найден");
      if (file.createNewFile()) {
        System.out.print("Файл создан");
      }
    }
    fileWriter = new FileWriter(file);
    for (Ticket ticket : ticketList) {
      int idFilm = ticket.getIdFilm();
      String dataStr = ServiceData.convertDataToStr(ticket.getDataFilm());
      double totalCost = ticket.getTotalCost();
      List<String> seatsTicket = ticket.getSeats();
      StringBuilder seats = new StringBuilder();
      for (int i = 0; i < seatsTicket.size(); i++) {
        if (i < seatsTicket.size() - 1) {
          seats.append(seatsTicket.get(i)).append(",");
        } else {
          seats.append(seatsTicket.get(i));
        }
      }
      String result = idFilm + ";" + dataStr + ";" + totalCost + ";" + seats;
      fileWriter.write(result + "\n");
    }
    fileWriter.close();
  }
}



