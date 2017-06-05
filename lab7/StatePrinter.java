package lab7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StatePrinter {

  private static final int HUNDRED_PERCENTS = 100;
  private static final int PRINT_EACH_PERCENTS = 10;
  private Lock lock = new ReentrantLock();
  private int current = 0;
  private int nextPrintedPercent = PRINT_EACH_PERCENTS;
  private int count;

  public StatePrinter(final int count) {
    this.count = count;
  }

  public boolean shouldPrintNow() {
    lock.lock();
    boolean result;
    try {
      result = getCurrentPercent() >= nextPrintedPercent;
    } finally {
      lock.unlock();
    }
    return result;
  }

  public void increaseNextPercent() {
    lock.lock();
    nextPrintedPercent += PRINT_EACH_PERCENTS;
    lock.unlock();
  }

  public void printCurrentPercent() {
    lock.lock();
    try {
      int currentPercent = getCurrentPercent();
      System.out.println(Thread.currentThread().getName() + " ready on " + currentPercent + "%");
    } finally {
      lock.unlock();
    }
  }

  public void addToCurrent(final int n) {
    lock.lock();
    current += n;
    lock.unlock();
  }

  private int getCurrentPercent() {
    return (int) (((float) current / (float) count) * HUNDRED_PERCENTS);
  }
}
