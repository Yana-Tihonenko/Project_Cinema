package utilDataDB;

import model.Film;
import model.Genre;
import model.Screening;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class initData {

  public List<Film> initFilmList() {
    File file = new File("src/source/Films");
    List<Film> filmList = parserDataFilms(readDateFromFile(file));
    return filmList;
  }

  public List<Screening> initScreeningList() {
    File file = new File("src/source/Screening");
    List<Screening> screeningList = parserDataScreening(readDateFromFile(file));
    return screeningList;
  }

  public List<String> readDateFromFile(File file) {
    List<String> readFile = new ArrayList<>();
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while ((line = br.readLine()) != null) {
        readFile.add(line);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File exit, program is not initialized");
    } catch (IOException e) {
      System.out.println("Error read data, program is not initialized");
    }
    return readFile;
  }

  private static List<Film> parserDataFilms(List<String> list) {
    List<Film> filmList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      String[] tempArray = Pattern.compile(";")
          .splitAsStream(list.get(i))
          .toArray(String[]::new);
      int idFilm = Integer.parseInt(tempArray[0]);
      String nameFilm = tempArray[1];
      int year = Integer.parseInt(tempArray[2]);
      String descriptions = tempArray[3];
      Genre genre = Genre.valueOf(tempArray[4]);
      if (tempArray.length == 6) {
        List<Integer> rating = Pattern.compile(",")
            .splitAsStream(tempArray[5])
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        filmList.add(new Film(idFilm, nameFilm, year, descriptions, genre, rating));
      } else {
        filmList.add(new Film(idFilm, nameFilm, year, descriptions, genre));
      }
    }
    int lastIdFilm = filmList.stream()
        .mapToInt(Film::getIdFilm)
        .summaryStatistics()
        .getMax();
    Film.setIdFilmLast(lastIdFilm);
    return filmList;
  }

  private static List<Screening> parserDataScreening(List<String> list) {
    List<Screening> screeningList = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      String[] tempArray = Pattern.compile(";")
          .splitAsStream(list.get(i))
          .toArray(String[]::new);
      int idFilm = Integer.parseInt(tempArray[0]);
      double price = Double.parseDouble(tempArray[1]);
      List<Date> date = Pattern.compile(",")
          .splitAsStream(tempArray[2])
          .map(s -> {
            try {
              return new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(s);
            } catch (ParseException e) {
              throw new RuntimeException(e);
            }
          })
          .collect(Collectors.toList());
      screeningList.add(new Screening(idFilm, price, date));
    }
    return screeningList;
  }

}



