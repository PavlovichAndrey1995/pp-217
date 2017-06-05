package lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

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

    Semaphore semaphore = new Semaphore(threadCount / 2);
    CountDownLatch latch = new CountDownLatch(threadCount);
    StatePrinter printer = new StatePrinter(count);

    List<PiCalculator> calculatorList = new ArrayList<PiCalculator>(threadCount);
    List<Future<Integer>> futures = new ArrayList<Future<Integer>>(threadCount);
    ExecutorService service = Executors.newFixedThreadPool(threadCount);
    for (int i = 0; i < threadCount; i++) {
      PiCalculator calculator = new PiCalculator(countPerThread);
      calculator.setSemaphore(semaphore);
      calculator.setLatch(latch);
      calculator.setPrinter(printer);
      calculatorList.add(calculator);

      Future<Integer> future = service.submit(calculator);
      futures.add(future);
    }

    latch.await();
    long mainTimeInNs = System.nanoTime();
    for (int i = 0; i < calculatorList.size(); i++) {
      long timeOfIth = mainTimeInNs - calculatorList.get(i).getTimeInNs();
      System.out.println("Time of " + i + "th thread = " + timeOfIth);
    }

    int hits = 0;
    for (Future<Integer> future : futures) {
      hits += future.get();
    }

    return PiCalculator.getPi(hits, count);
  }
}
