package lab6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadCalculator {

  private int threadCount;

  public ThreadCalculator(final int threadCount) {
    this.threadCount = threadCount;
  }

  public ThreadCalculator() {
    this.threadCount = Runtime.getRuntime().availableProcessors();
  }

  public double calculate(final int count) throws Exception {
    int countPerThread = count / threadCount;
    List<Future<Integer>> futures = new ArrayList<Future<Integer>>(threadCount);
    ExecutorService service = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
      PiCalculator calculator = new PiCalculator();
      calculator.setCount(countPerThread);
      Future<Integer> future = service.submit(calculator);
      futures.add(future);
    }

    int hits = 0;
    for (Future<Integer> future : futures) {
      hits += future.get();
    }

    return PiCalculator.getPi(hits, count);
  }
}
