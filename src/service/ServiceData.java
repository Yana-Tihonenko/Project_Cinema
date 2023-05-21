package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ServiceData {

  /**
   * Функция возвращает список объектов типа Data в формате dd.MM.yyyy HH:mm
   *
   * @param dateStr строка содержащая дату в формате dd.MM.yyyy
   * @param timeStr строка с перечнем времени через запятую в формате HH:mm
   * @return возвращает список объектов типа Data в формате dd.MM.yyyy HH:mm
   */
  public static List<Date> convertStrToListData(String dateStr, String timeStr) {
    List<Date> dateList = new ArrayList<>();
    ArrayList<String> dateAndTimesStr = new ArrayList<>();
    String[] strSplit = timeStr.split(",");
    for (String s : strSplit) {
      String tempStrLine = dateStr.trim() + " " + s;
      dateAndTimesStr.add(tempStrLine);
    }
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    for (String date : dateAndTimesStr) {
      try {
        dateList.add(format.parse(date));
      } catch (ParseException e) {
        return dateList;
      }
    }
    return dateList;
  }

  /**
   * Функция возвращает объект типа Data в формате dd.MM.yyyy HH:mm
   *
   * @param dateStr строка содержащая дату в формате dd.MM.yyyy
   * @param timeStr строка содежащая временя в формате HH:mm
   * @return возвращает список объектов типа Data в формате dd.MM.yyyy HH:mm
   * или null при ошибке прeобразования String в тип Data
   */
  public static Date convertStrToData(String dateStr, String timeStr) {
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    String tempStrLine = dateStr.trim() + " " + timeStr;
    try {
      return format.parse(tempStrLine);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * Функция возвращает строковую дату в формате dd.MM.yyyy HH:mm
   *
   * @param date строка содержащая дату в формате dd.MM.yyyy
   * @return возвращает дату в строковом формате в виде формата dd.MM.yyyy HH:mm
   */
  public static String convertDataToStr(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    return format.format(date);

  }

  /**
   * Функция возвращает массив объектов String
   *
   * @param line строка cодержащая значения, перечисленные через запятую
   * @return возвращает массив объектов String
   */
  public static List<String> convertStrToListStr(String line) {

    return new ArrayList<>(Arrays.asList(line.split(",")));
  }

}
