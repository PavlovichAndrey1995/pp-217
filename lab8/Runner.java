package lab8;

public class Runner {

  private static final int iterationCount = 4000000;
  private static final int numberThread = 4;

  private Runner() {}

  public static void main(String[] args) {
    try {
      ThreadCalculator calculator = new ThreadCalculator(numberThread);
      double pi = calculator.calculate(iterationCount);
      System.out.println("pi=" + Double.toString(pi));
    } catch (InterruptedException e) {
      System.err.println("Interrupted.");
    } catch (Exception e) {
      System.err.println("Exception happened : " + e.getCause().getMessage());
    }
  }
}
