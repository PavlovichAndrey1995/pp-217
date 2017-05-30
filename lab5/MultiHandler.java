package lab5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiHandler {

    private final long NUMBER_OPERATION = 30000000;
    private int numberThread = 4;
    private ExecutorService executorService;
    private boolean success = true;
    private ThreadManager threadManager;

    public MultiHandler () {
        this.executorService = Executors.newFixedThreadPool (numberThread);
        this.threadManager = new ThreadManager (NUMBER_OPERATION);
    }

    public void startThreads () {
        long timeStart = System.nanoTime ();
        for (int i = 1; i < numberThread + 1; i++) {
            Calculate calculatePi = new Calculate (i, (NUMBER_OPERATION / numberThread),
                threadManager, NUMBER_OPERATION);
            executorService.execute (calculatePi);
        }
        executorService.shutdown ();

        try {
            success = executorService.awaitTermination (1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        long timeFinish = System.nanoTime () - timeStart;

        System.out.println ("Время выполнения: " + timeFinish / 1E9 + " s");

        if (! success) {
            System.out.println ("Не все потоки завершились успешно!");
        }
        threadManager.printResult ();
    }

}
