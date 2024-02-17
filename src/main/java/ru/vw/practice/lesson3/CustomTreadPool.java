package ru.vw.practice.lesson3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomTreadPool {
  private static final long IDLE_TTL_MILLIS = 100;
  private final LinkedList<Runnable> tasks;
  private final Thread[] threads;
  private final AtomicBoolean activePool;

  public CustomTreadPool(int treadsCount) {
    activePool = new AtomicBoolean(true);
    tasks = new LinkedList<>();
    threads = new Thread[treadsCount];
    for (int i = 0; i < treadsCount; i++) {
      threads[i] = createPoolThread();
      threads[i].start();
    }
  }

  public void execute(List<Runnable> r) {
    if (!activePool.get()) {
      throw new IllegalStateException("ThreadPool is dead");
    }
    synchronized (tasks) {
      tasks.addAll(r);
      tasks.notifyAll();
    }
  }

  public void execute(Runnable r) {
    execute(List.of(r));
  }

  public void shutdown(boolean forced) {
    if (forced) {
      Arrays.stream(threads).forEach(Thread::interrupt);
    }
    activePool.set(false);
  }

  protected Thread createPoolThread() {
    return new CustomThread(tasks, activePool);
  }

  /** Описание потока в пуле потоков*/
  private static class CustomThread extends Thread {
    private final AtomicBoolean activePool;
    private final LinkedList<Runnable> tasks;

    public CustomThread(LinkedList<Runnable> tasks, AtomicBoolean activePool) {
      this.tasks = tasks;
      this.activePool = activePool;
    }

    @Override
    public void run() {
      try {
        System.out.printf("Thread %s was started%n", this.getName());
        while (activePool.get()) {
          Runnable task;
          synchronized (tasks) {
            try {
              while (tasks.isEmpty() && activePool.get()) {
                tasks.wait(IDLE_TTL_MILLIS);
              }
            } catch (InterruptedException e) {
              throw new ThreadKillingException("Thread %s was interrupted\n".formatted(this.getName()), e);
            }
            task = tasks.pollFirst();
          }
          if (Objects.nonNull(task)) {
            task.run();
          }
        }
      } catch (ThreadKillingException e) {
        System.out.printf(e.toString());
      } finally {
        System.out.printf("Thread %s was finished%n", this.getName());
      }
    }
  }

  /** Исключение для прерывания ожидания задачи в пуле потоков*/
  public static class ThreadKillingException
          extends RuntimeException {
    public ThreadKillingException(String errorMessage, Throwable err) {
      super(errorMessage, err);
    }
  }
}
