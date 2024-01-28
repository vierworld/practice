package ru.vw.practice.lesson1;

import ru.vw.practice.lesson1.annotation.AfterSuite;
import ru.vw.practice.lesson1.annotation.BeforeSuite;
import ru.vw.practice.lesson1.annotation.CsvSource;
import ru.vw.practice.lesson1.annotation.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/** Простой класс для проверки обработки аннотаций. */
public class SimpleClass {
  private static final String FIRST_LINE = "First line";
  private static final String LAST_LINE = "Last line";
  private static final String HOLIDAY_LINE = "isHoliday(3): Date: %s is holiday: %s%n";
  private static final String TEST_CSV_LINE = "%s a: %s, b: %s, c: %s, d: %s%n";

  @BeforeSuite
  public static void showFirstLine() {
    System.out.println(FIRST_LINE);
  }

  @AfterSuite
  public static void showLastLine() {
    System.out.println(LAST_LINE);
  }

  @Test(priority = 1)
  private void testMethod1() {
    System.out.println("testMethod1");
  }

  @Test(priority = 3)
  @CsvSource("2022-01-01")
  public boolean isHoliday(Date date) {
    if (Objects.isNull(date)) {
      return false;
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    boolean result = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

    System.out.printf((HOLIDAY_LINE), date, result);

    return result;
  }

  @Test(priority = 2)
  @CsvSource("1,Hello,88,true")
  public void testMethod2(int a, String b, int c, boolean d) {
    System.out.printf((TEST_CSV_LINE), "testMethod2", a, b, c, d);
  }

  @Test(priority = 9)
  @CsvSource("1,true,Hello world!,88")
  public void testMethod9(int a, boolean b, String c, int d) {
    System.out.printf((TEST_CSV_LINE), "testMethod9", a, b, c, d);
  }

  @Test(priority = 8)
  @CsvSource("77,true,Final,12.33")
  public void testMethod8(int a, boolean b, String c, float d) {
    System.out.printf((TEST_CSV_LINE), "testMethod8", a, b, c, d);
  }

  @Test(priority = 7)
  public void testMethod7() {
    System.out.println("testMethod7");
  }
}
