package lab5;

public class Runner {
  private static final int count = 2000000;

  private Runner() {}

  public static void main(String[] args) {
    try {
      double pi = LazyThreadCalculator.calculate(count);
      System.out.println("pi=" + Double.toString(pi));
    } catch (InterruptedException e) {
      System.err.println("Interrupted.");
    }
  }
}
