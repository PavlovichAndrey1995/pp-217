package lab5;

public class Calculate implements Runnable {

    private int numberThread;
    private long countOperation;
    private long allOperation;
    private ThreadManager threadManager = new ThreadManager (this.allOperation);

    public Calculate (int numberThread, long countOperation, ThreadManager threadManager,
        long allOperations) {
        this.allOperation = allOperations;
        this.numberThread = numberThread;
        this.countOperation = countOperation;
        this.threadManager = threadManager;
    }

    public void calculationPi () {
        int innerCount = 0;
        int counts = (int) (numberThread * countOperation);
        int startCount = (int) (counts - countOperation);
        double x, y;
        for (int i = startCount; i < counts; i++) {
            x = ((Math.random () * 2) - 1);
            y = ((Math.random () * 2) - 1);
            if (Math.pow (x, 2) + Math.pow (y, 2) <= 1) {
                 threadManager.calculateSum (innerCount);
            }
        }
    }

    public void run () {
        calculationPi ();
    }

}
