package initData;


import model.Film;
import model.Genre;

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
    return filmList;
  }

  private static Film parserData(String line) {
    String[] tempArray = Pattern.compile(";")
        .splitAsStream(line)
        .toArray(String[]::new);
    String nameFilm = tempArray[0];
    int year = Integer.parseInt(tempArray[1]);
    String descriptons = tempArray[2];
    Genre genre = Genre.valueOf(tempArray[3]);
    List<Integer> rating = Pattern.compile(",")
        .splitAsStream(tempArray[4])
        .map(i -> Integer.parseInt(i))
        .collect(Collectors.toList());
    Film result = new Film(nameFilm, year, descriptons, genre, rating);

    return result;
  }

}



