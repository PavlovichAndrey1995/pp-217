package lab5;

public final class LazyThreadCalculator {
  private static ThreadCalculator instance = null;

  private LazyThreadCalculator() {}

  public static double calculate(final int count) throws InterruptedException {
    if (instance == null) {
      synchronized (LazyThreadCalculator.class) {
        if (instance == null) {
          instance = new ThreadCalculator();
        }
      }
    }
    return instance.calculate(count);
  }
}
