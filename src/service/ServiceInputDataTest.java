package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceInputDataTest {

  @DisplayName("checkInputCommand(). Значение min > max")
  @Test
  public void checkInputCommand_minMoreMax() {
    assertFalse(ServiceInputData.checkInputCommand(5, 1, 4));
  }

  @DisplayName("checkInputCommand(). Введенное значение (choice) равно min")
  @Test
  public void checkInputCommand_minEqualsChoice() {
    assertTrue(ServiceInputData.checkInputCommand(1, 4, 1));
  }

  @DisplayName("checkInputCommand(). ВведенноеВведенное значение (choice) равно max")
  @Test
  public void checkInputCommand_maxEqualsChoice() {
    assertTrue(ServiceInputData.checkInputCommand(1, 4, 4));
  }

  @DisplayName("checkInputCommand(). Введенное значение (choice) диапозоне min и max")
  @Test
  public void checkInputCommand_choiceInRange() {
    assertTrue(ServiceInputData.checkInputCommand(1, 4, 3));
  }

  @DisplayName("checkInputCommand(). Введенное значение (choice) не в диапозоне min и max")
  @Test
  public void checkInputCommand_choiceNotInRange() {
    assertFalse(ServiceInputData.checkInputCommand(1, 4, 6));
  }
  @DisplayName("checkInputCommand(). Введенное значение (choice) не в диапозоне min и max")
  @Test
  public void checkInputCommand_choiceIsZero() {
    assertFalse(ServiceInputData.checkInputCommand(1, 4, 0));
  }

  @DisplayName("ВcheckInputCommandStr(). веденное значение (choice) в нижнем регистре")
  @Test
  public void checkInputCommandStr_choiceToLowerCase() {
    assertTrue(ServiceInputData.checkInputCommandStr("y"));
  }

  @DisplayName("checkInputCommandStr(). веденное значение (choice) в верхнем регистре")
  @Test
  public void checkInputCommandStr_choiceToUpperCase() {
    assertTrue(ServiceInputData.checkInputCommandStr("Y"));
  }

  @DisplayName("checkInputCommandStr(). Введенное значение (choice) в верхнем регистре")
  @Test
  public void checkInputCommandStr_choiceEqualsN() {
    assertTrue(ServiceInputData.checkInputCommandStr("N"));
  }

  @DisplayName("inputAndValidationInt(). Введенное значение (choice) не равно значением Y или N")
  @Test
  public void checkInputCommandStr_choiceNotEquals() {
    assertFalse(ServiceInputData.checkInputCommandStr("NUl"));
  }

  @DisplayName("inputAndValidationInt(). Введенное значение не соответствует формату int")
  @Test
  public void inputAndValidationInt_inputDataNotInt() {
    String input = "1a";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    int result = ServiceInputData.inputAndValidationInt();
    assertEquals(-1, result);
  }

  @DisplayName("inputAndValidationInt(). Введенное значение не задано")
  @Test
  public void inputAndValidationInt_inputDataIsEmpty() {
    String input = "";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    int result = ServiceInputData.inputAndValidationInt();
    assertEquals(-1, result);
  }

  @DisplayName("inputAndValidationInt(). Введенное значение  меньше нуля")
  @Test
  public void inputAndValidationInt_inputDataIsNegative() {
    String input = "-1";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    int result = ServiceInputData.inputAndValidationInt();
    assertEquals(-1, result);
  }

  @DisplayName("inputAndValidationInt(). Введенное значение соответстует типу int  и больше нуля ")
  @Test
  public void inputAndValidationInt_inputDataIsPositive() {
    String input = "100";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    int result = ServiceInputData.inputAndValidationInt();
    assertEquals(100, result);
  }

  @DisplayName("inputAndValidationDouble(). Введенное значение не соответствует формату double")
  @Test
  public void inputAndValidationDouble_doubleDataNotDouble() {
    String input = "1a";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    double result = ServiceInputData.inputAndValidationDouble();
    assertEquals(-1, result);
  }

  @DisplayName("inputAndValidationDouble(). Введенное значение не соответствует формату double")
  @Test
  public void inputAndValidationDouble_doubleDataNotDouble2() {
    String input = "100,23";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    double result = ServiceInputData.inputAndValidationDouble();
    assertEquals(-1, result);
  }

  @DisplayName("inputAndValidationDouble(). Введенное значение не задано")
  @Test
  public void inputAndValidationDouble_doubleDataIsEmpty() {
    String input = "";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    Double result = ServiceInputData.inputAndValidationDouble();
    assertEquals(-1, result);
  }

  @DisplayName("inputAndValidationDouble(). Введенное значение  меньше нуля")
  @Test
  public void inputAndValidationDouble_doubleDataIsNegative() {
    String input = "-1.0";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    Double result = ServiceInputData.inputAndValidationDouble();
    assertEquals(-1, result);
  }

  @DisplayName("inputAndValidationDouble(). Введенное значение соответстует типу double  и больше нуля ")
  @Test
  public void inputAndValidationDouble_doubleDataIsPositive() {
    String input = "100.58";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    Double result = ServiceInputData.inputAndValidationDouble();
    assertEquals(100.58, result);
  }

}
