package lab5;

import java.util.ArrayList;
import java.util.List;

public class ThreadCalculator {
  private int threadCount;

  public ThreadCalculator(int threadCount) {
    this.threadCount = threadCount;
  }

  public ThreadCalculator() {
    this.threadCount = Runtime.getRuntime().availableProcessors();
  }

  public double calculate(final int count) throws InterruptedException {
    int countPerThread = count / threadCount;
    List<PiCalculator> calculatorList = new ArrayList<PiCalculator>(threadCount);
    for (int i = 0; i < threadCount; i++) {
      PiCalculator calculator = new PiCalculator();
      calculator.setCountForRun(countPerThread);
      calculatorList.add(calculator);
      Thread thread = new Thread(calculator);
      thread.setName(Integer.toString(i));
      thread.start();
    }

    int hits = 0;
    for (PiCalculator calculator : calculatorList) {
      hits += calculator.getHitsForRun();
    }

    return PiCalculator.getPi(hits, count);
  }
}
