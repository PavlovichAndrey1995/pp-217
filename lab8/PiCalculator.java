package lab8;

import java.util.concurrent.Callable;

public class PiCalculator implements Callable<Integer> {
  public static final int NUMBER_OF_QUARTERS_PER_CIRCLE = 4;
  public static final int HUNDRED_PERCENTS = 100;
  StatePrinter printer;
  private int count = 0;
  private MySemaphore semaphore = new MySemaphore(1);
  private MyLatch latch = new MyLatch(1);
  private long timeInNs = 0;

  public PiCalculator(int count) {
    setCount(count);
    printer = new StatePrinter(count);
  }

  public static double getPi(int hits, int count) {
    return (double) NUMBER_OF_QUARTERS_PER_CIRCLE * hits / count;
  }

  private int calculateHits(int count) throws InterruptedException {
    int additionDelta = count / HUNDRED_PERCENTS;

    int hits = 0;
    double x, y;
    for (int i = 0; i < count; i++) {
      if (Thread.interrupted()) {
        throw new InterruptedException(Thread.currentThread().getName() + " was interrupted.");
      }
      x = ((Math.random() * 2) - 1);
      y = ((Math.random() * 2) - 1);
      if (Math.pow(x, 2) + Math.pow(y, 2) <= 1) {
        hits++;
      }

      if (i % additionDelta == 0) {
        printer.addToCurrent(additionDelta);
        if (printer.shouldPrintNow()) {
          printer.increaseNextPercent();
          printer.printCurrentPercent();
        }
      }
    }

    return hits;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setSemaphore(MySemaphore semaphore) {
    this.semaphore = semaphore;
  }

  public void setLatch(MyLatch latch) {
    this.latch = latch;
  }

  public long getTimeInNs() {
    return timeInNs;
  }

  public void setPrinter(StatePrinter printer) {
    this.printer = printer;
  }

  public Integer call() throws Exception {
    int hits = 0;
    try {
      semaphore.acquire();
      hits = calculateHits(count);
      timeInNs = System.nanoTime();
    } finally {
      latch.countDown();
      semaphore.release();
    }
    return hits;
  }
}
