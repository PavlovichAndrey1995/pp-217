package lab5;

public class ThreadManager {

    private int sum = 0;
    private long numberAllOperations;

    public ThreadManager (long number) {
        this.numberAllOperations = number;
    }

    public synchronized double getPi () {
        return (double) 4 * sum / numberAllOperations;
    }

    public synchronized void calculateSum (int term) {
        this.sum += term + 1;
    }

    public void printResult () {
        System.out.println ("Пи = " + getPi ());
    }
}
