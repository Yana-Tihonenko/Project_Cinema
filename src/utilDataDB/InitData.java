package utilDataDB;


import model.Film;
import model.Genre;
import model.Screening;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InitData {

  public List<Film> initFilmsFromFile() {
    List<Film> filmList = new ArrayList<>();
    try {
      File file = new File("src/source/Films");
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      while ((line = br.readLine()) != null) {
        filmList.add(parserData(line));
      }
    } catch (FileNotFoundException e) {
      System.out.println("File exit, program is not initialized");
    } catch (IOException e) {
      System.out.println("Error read data, program is not initialized");
    }
    int lastIdFilm = filmList.stream()
        .mapToInt(Film::getIdFilm)
        .summaryStatistics()
        .getMax();
    Film.setIdFilmLast(lastIdFilm);

    return filmList;
  }

  private static Film parserData(String line) {
    String[] tempArray = Pattern.compile(";")
        .splitAsStream(line)
        .toArray(String[]::new);
    int idFilm = Integer.parseInt(tempArray[0]);
    String nameFilm = tempArray[1];
    int year = Integer.parseInt(tempArray[2]);
    String descriptions = tempArray[3];
    Genre genre = Genre.valueOf(tempArray[4]);
    List<Integer> rating = Pattern.compile(",")
        .splitAsStream(tempArray[5])
        .map(Integer::parseInt)
        .collect(Collectors.toList());
    return new Film(idFilm, nameFilm, year, descriptions, genre, rating);
  }

  public static List<Screening> initScreeningFromFile() {
    List<Screening> screeningList = new ArrayList<>();

    return screeningList;
  }

}



