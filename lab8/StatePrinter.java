package lab8;

public class StatePrinter {

  private static final int PRINT_EACH_PERCENTS = 10;
  private static final int HUNDRED_PERCENTS = 100;
  private MyLock lock = new MyLock();
  private int current = 0;
  private int nextPrintedPercent = PRINT_EACH_PERCENTS;
  private int count;

  public StatePrinter(final int count) {
    this.count = count;
  }

  public boolean shouldPrintNow() throws InterruptedException {
    lock.lock();
    boolean result;
    try {
      result = getCurrentPercent() >= nextPrintedPercent;
    } finally {
      lock.unlock();
    }
    return result;
  }

  public void increaseNextPercent() throws InterruptedException {
    lock.lock();
    nextPrintedPercent += PRINT_EACH_PERCENTS;
    lock.unlock();
  }

  public void printCurrentPercent() throws InterruptedException {
    lock.lock();
    try {
      int currentPercent = getCurrentPercent();
      System.out.println(Thread.currentThread().getName() + " ready on " + currentPercent + "%");
    } finally {
      lock.unlock();
    }
  }

  public void addToCurrent(final int n) throws InterruptedException {
    lock.lock();
    current += n;
    lock.unlock();
  }

  private int getCurrentPercent() {
    return (int) (((float) current / (float) count) * HUNDRED_PERCENTS);
  }
}
