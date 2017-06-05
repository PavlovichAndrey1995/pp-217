package lab5;

public class PiCalculator implements Runnable {

  public static final int NUMBER_OF_QUARTERS_PER_CIRCLE = 4;
  public static final int ITERATIONS_PER_PRINT = 1000;
  private double hitsForRun;
  private boolean wasCalculated = false;
  private int countForRun = 0;

  public static double getPi(final int hits, final int count) {
    return (double) NUMBER_OF_QUARTERS_PER_CIRCLE * hits / count;
  }

  private int calculateHits(final int count) throws InterruptedException {
    Thread current = Thread.currentThread();
    int hits = 0;
    double x, y;
    for (int i = 0; i < count; i++) {
      if (current.isInterrupted()) {
        throw new InterruptedException(current.getName() + " was interrupted.");
      }
      x = ((Math.random() * 2) - 1);
      y = ((Math.random() * 2) - 1);
      if (Math.pow(x, 2) + Math.pow(y, 2) <= 1) {
        hits++;
      }

      if (i % ITERATIONS_PER_PRINT == 0) {
        System.out.println(current.getName() + " Iteration number " + Integer.toString(i));
      }
    }
    return hits;
  }

  public void setCountForRun(final int count) {
    this.countForRun = count;
  }

  public void run() {
    synchronized (this) {
      try {
        wasCalculated = false;
        hitsForRun = calculateHits(countForRun);
        wasCalculated = true;
        this.notify();
      } catch (InterruptedException e) {
        System.err.println(Thread.currentThread().getName() + " was interrupted.");
      }
    }
  }

  public double getHitsForRun() throws InterruptedException {
    synchronized (this) {
      while (!wasCalculated) {
        this.wait();
      }
    }
    return hitsForRun;
  }
}
