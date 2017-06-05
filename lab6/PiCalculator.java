package lab6;

import java.util.concurrent.Callable;

public class PiCalculator implements Callable<Integer> {

  public static final int NUMBER_OF_QUARTERS_PER_CIRCLE = 4;
  public static final int ITERATIONS_PER_PRINT = 20;
  public static final int PERCENT_DELTA = 5;
  private int count = 0;

  public static double getPi(final int hits, final int count) {
    return (double) NUMBER_OF_QUARTERS_PER_CIRCLE * hits / count;
  }

  private int calculateHits(final int count) throws InterruptedException {
    Thread current = Thread.currentThread();
    int hits = 0;
    int currentCalculation = 0;
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

      if (i == (count / ITERATIONS_PER_PRINT) * currentCalculation) {
        System.out.println(
            "Текущее состояние выполнения потока "
                + current.getName()
                + ": "
                + (currentCalculation * PERCENT_DELTA)
                + "%");
        currentCalculation++;
      }
    }
    return hits;
  }

  public void setCount(final int count) {
    this.count = count;
  }

  public Integer call() throws Exception {
    return calculateHits(count);
  }
}
