package ru.vw.practice.lesson3;

import java.util.List;

public class ThirdLesson {
  public static Runnable getTestCycled(String name) {
    return new Runnable() {
      @Override
      public String toString() {
        return name;
      }

      @Override
      public void run() {
        System.out.printf("getTestCycled start: %s\n", this);
        while (true) {
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            throw new RuntimeException("getTestCycled %s was interrupted".formatted(this));
          }
          System.out.printf("getTestCycled working: %s\n", this);
        }
      }
    };
  }

  public static Runnable getTestSimple(String name) {
    return new Runnable() {
      @Override
      public String toString() {
        return name;
      }

      @Override
      public void run() {
        System.out.printf("getTestSimple start: %s\n", this);

        try {
          Thread.sleep(1500);
        } catch (InterruptedException e) {
          throw new RuntimeException("getTestSimple %s was interrupted".formatted(this));
        }
        System.out.printf("getTestSimple working: %s\n", this);
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
          throw new RuntimeException("getTestSimple %s was interrupted".formatted(this));
        }

        System.out.printf("getTestSimple finished: %s\n", this);
      }
    };
  }


  public static void runTestsSimple() {
    CustomTreadPool customTreadPool = new CustomTreadPool(5);

    customTreadPool.execute(List.of(
            getTestSimple("Thread 1.1"),
            getTestSimple("Thread 1.2"),
            getTestSimple("Thread 1.3"),
            getTestSimple("Thread 1.4")
    ));
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    customTreadPool.execute(List.of(
            getTestSimple("Thread 2.1"),
            getTestSimple("Thread 2.2"),
            getTestSimple("Thread 2.3"),
            getTestSimple("Thread 2.4")
    ));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    customTreadPool.shutdown(false);

    try {
      customTreadPool.execute(getTestCycled("Thread 3.1"));
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }

  }

  public static void runTestsForced() {
    CustomTreadPool customTreadPool = new CustomTreadPool(5);

    customTreadPool.execute(List.of(
            getTestSimple("Thread 1.1"),
            getTestCycled("Thread 1.2"),
            getTestSimple("Thread 1.3"),
            getTestSimple("Thread 1.4")
    ));
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    customTreadPool.execute(List.of(
            getTestCycled("Thread 2.1"),
            getTestCycled("Thread 2.2"),
            getTestSimple("Thread 2.3"),
            getTestCycled("Thread 2.4")
    ));
    try {
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    customTreadPool.shutdown(true);

    try {
      customTreadPool.execute(getTestCycled("Thread 3.1"));
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }

}
