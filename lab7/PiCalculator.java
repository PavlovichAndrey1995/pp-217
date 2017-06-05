package lab7;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class PiCalculator implements Callable<Integer> {
  public static final int NUMBER_OF_QUARTERS_PER_CIRCLE = 4;
  public static final int HUNDRED_PERCENTS = 100;
  private int count = 0;
  private Semaphore semaphore = new Semaphore(1);
  private CountDownLatch latch = new CountDownLatch(1);
  private StatePrinter printer;
  private long timeInNs = 0;

  public PiCalculator(final int count) {
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

  public void setSemaphore(Semaphore semaphore) {
    this.semaphore = semaphore;
  }

  public void setLatch(CountDownLatch latch) {
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
