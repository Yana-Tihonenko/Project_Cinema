package service;

import model.Genre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.stream.IntStream;

public class ServiceInputData {
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  /**
   * Функция валидирует целевое значение соответствие диапозону допустимых min и max.
   *
   * @param min    значение разрешенного диапозона.
   * @param max    значение разрешенного диапозона.
   * @param choice введенные проверяемое значение.
   * @return тип boolean. Возвращает false если входящие параметры меньше или равно 0.
   */
  public static boolean checkInputCommand(int min, int max, int choice) {
    if (min <= 0 || choice <= 0) {
      System.err.print("Некорретные входящие параметры.");
      return false;
    } else {
      if (IntStream.rangeClosed(min, max).noneMatch(i -> i == choice)) {
        System.err.print("Вы ввели неправильный пункт меню!!! Повторите ввод еще раз.");
        return false;
      } else {
        return true;
      }
    }
  }

  /**
   * Функция валидирует целевое значение на соответствие значениям "Y" или "N".
   *
   * @param choice введенные проверяемое значение.
   * @return тип boolean. Возвращает false если входящий параметр null или пустая строка.
   */
  public static boolean checkInputCommandStr(String choice) {
    if (choice == null || choice.equals("")) {
      System.err.print("Некорретные входящие параметры.");
      return false;
    } else {
      if (!"Y".equalsIgnoreCase(choice) && !"N".equalsIgnoreCase(choice)) {
        System.err.print("Вы ввели неправильный пункт меню!!! Повторите ввод еще раз.");
        return false;
      } else {
        return true;
      }
    }
  }

  /**
   * Функция запращивает ввод значения типа int. Валидация на формат.
   *
   * @return введенное значение типа int. Возращает -1 если значение не являеется int.
   */
  public static int inputAndValidationInt() {
    int inputInt;
    try {
      inputInt = Integer.parseInt(br.readLine());
    } catch (IOException | NumberFormatException e) {
      System.err.print("Возможен ввод только числовых символов!!! Повторите ввод еще раз. ");
      return -1;
    }
    if (inputInt < 0) {
      System.err.print("Введенное значение не может быть отрицательным. ");
    }
    return inputInt;
  }

  /**
   * Функция запращивает ввод значения типа Double. Валидация на формат.
   *
   * @return введенное значение типа Double. Возращает -1.0 если значение не являеется Double.
   */
  public static Double inputAndValidationDouble() {
    double inputDouble;
    try {
      inputDouble = Double.parseDouble(br.readLine());
    } catch (IOException | NumberFormatException | NullPointerException e) {
      System.err.print("Возможен ввод только числовых символов через точку.  !!! Повторите ввод еще раз. ");
      return -1.0;
    }
    if (inputDouble < 0) {
      System.err.print("Введенное значение не может быть отрицательным. ");
    }
    return inputDouble;
  }

  /**
   * Функция запращивает ввод года, тип int. Валидирует введенное значение:
   * - на формат;
   * - введенное значение не может быть больше текущего года.
   *
   * @return введенное значение типа int.
   */
  public static int inputAndValidationYear() {
    int yearInput;
    int checkCorrectYear = 0;
    do {
      yearInput = inputAndValidationInt();
      if (yearInput != -1) {
        Calendar calendar = Calendar.getInstance();
        int yearCurrent = calendar.get(Calendar.YEAR);
        checkCorrectYear = yearCurrent - yearInput;
        if (checkCorrectYear < 0) {
          System.err.print("Год выпуска фильма не может быть больше текущего года!!! Повторите ввод. ");
        }
      }
    } while (yearInput == -1 || checkCorrectYear < 0);
    return yearInput;
  }

  /**
   * Функция запращивает ввод жанра фильма. Валидирует введенное значение на соответствие значениям из  enum Genre.
   *
   * @return введенное значение типа Genre.
   */
  public static Genre inputAndValidationGenre() {
    boolean checkInputCommand = false;
    Genre genre = null;
    do {
      int inputGenre = inputAndValidationInt();
      if (inputGenre == -1) {
        continue;
      }
      checkInputCommand = checkInputCommand(1, 4, inputGenre);
      if (checkInputCommand) {
        for (Genre gen : Genre.values()) {
          if (gen.getNumberGenre() == inputGenre) {
            genre = gen;
          }
        }
      }
    } while (!checkInputCommand);
    return genre;
  }

  /**
   * Функция запращивает ввод жанра фильма. Валидирует введенное значение на формат и на значение 0.
   *
   * @return введенное значение типа double.
   */
  public static double inputAndValidationPrice() {
    boolean checkInputPrice = false;
    double inputPrice;
    do {
      inputPrice = inputAndValidationDouble();
      if (inputPrice == -1) {
        continue;
      }
      if (inputPrice <= 0) {
        System.err.print("Цена билета не можеть быть  меньше или равна нулю!!! Повторите ввод еще раз ");
      } else {
        checkInputPrice = true;
      }
    } while (!checkInputPrice);
    return inputPrice;
  }

  /**
   * Функция запращивает ввод даты в формате dd.MM.yyyy. Валидирует введенное значение:
   * - на формат;
   * - день (число) месяца;
   * - число месяца.
   *
   * @return введенное значение даты формате dd.MM.yyyy типа String.
   */
  public static String inputAndValidationData() {
    String inputDataStr = null;
    boolean check = false;
    do {
      try {
        inputDataStr = br.readLine();
        if (inputDataStr.length() == 0) {
          System.err.print("Поле не можеть быть пустым!!! Повторите еще раз ");
          continue;
        }
        if (inputDataStr.length() > 10) {
          System.err.print("Некорретный формат ввода!!! Повторите еще раз ");
          continue;
        }
        String[] strSplit = inputDataStr.split("\\.");
        if (IntStream.rangeClosed(1, 30).noneMatch(i -> i == Integer.parseInt(strSplit[0].trim()))) {
          System.err.print("Неправильный ввод дней!!! Повторите еще раз ");
          continue;
        }
        if (IntStream.rangeClosed(1, 12).noneMatch(i -> i == Integer.parseInt(strSplit[1].trim()))) {
          System.err.print("Неправильный ввод месяца!!!. Повторите еще раз ");
        }
        if (!strSplit[2].matches("[0-9]+")) {
          System.err.print("Неправильный ввод года!!!. Повторите еще раз ");
        } else {
          check = true;
        }
      } catch (IOException | NumberFormatException | NullPointerException e) {
        System.err.print("Некорретный формат ввода!!! Повторите еще раз ");

      }
    } while (!check);
    return inputDataStr;
  }

  /**
   * Функция запращивает ввод времени в формат HH:mm. Валидирует введенное значение:
   * - на формат;
   * - часы (формат 0-24);
   * - минуты.
   *
   * @return введенное значение даты формате HH:mm типа String.
   */
  public static String inputAndValidationTime() {
    String inputTimeStr = null;
    boolean check = false;
    do {
      try {
        inputTimeStr = br.readLine();
        String[] strSplit = inputTimeStr.split(",");
        for (String s : strSplit) {
          String timeStr = s.trim();
          if (timeStr.length() == 0) {
            System.err.print("Поле не можеть быть пустым!!! Повторите еще раз ");
            check = false;
            break;
          }
          if (timeStr.length() != 5) {
            System.err.print("Некорретный формат ввода!!! Повторите еще раз ");
            check = false;
            break;
          }
          String[] strSplitElement = timeStr.split(":");
          if (IntStream.rangeClosed(0, 24).noneMatch(j -> j == Integer.parseInt(strSplitElement[0].trim()))) {
            System.err.print("Неправильный ввод часов!!! Повторите еще раз ");
            check = false;
            break;
          }
          if (IntStream.rangeClosed(0, 60).noneMatch(j -> j == Integer.parseInt(strSplitElement[1].trim()))) {
            System.err.print("Неправильный ввод минут!!!. Повторите еще раз ");
            check = false;
            break;
          } else {
            check = true;
          }
        }
      } catch (IOException | NumberFormatException | NullPointerException e) {
        System.err.print("Некорретный формат ввода!!! Повторите еще раз ");
      }
    } while (!check);
    return inputTimeStr;
  }

  /**
   * Функция запращивает ввод значения тип String. Валидирует на пустое значение
   *
   * @return введенное значение типа String.
   */
  public static String inputAndValidationStr() {
    String inputStr = null;
    boolean check = false;
    do {
      try {
        inputStr = br.readLine();
        if (inputStr.length() == 0) {
          System.err.print("Поле не может быть пустым!!!. Повторите ввод еще раз");
        } else {
          check = true;
        }

      } catch (IOException e) {
        System.err.print("Некорретный формат ввода!!! Повторите еще раз ");
      }
    } while (!check);
    return inputStr;
  }


}
