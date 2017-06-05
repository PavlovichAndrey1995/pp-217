package lab8;

public class MyLock {

  private volatile int holdCount;
  private volatile long lockedBy;
  private Object monitor = new Object();

  MyLock() {
    holdCount = 0;
  }

  public void lock() throws InterruptedException {
    synchronized (monitor) {
      if (holdCount == 0) {
        holdCount++;
        lockedBy = Thread.currentThread().getId();
      } else if (holdCount > 0 && lockedBy == Thread.currentThread().getId()) {
        holdCount++;
      } else {
        monitor.wait();
        holdCount++;
        lockedBy = Thread.currentThread().getId();
      }
    }
  }

  public void unlock() {
    synchronized (monitor) {
      if (holdCount == 0) {
        throw new IllegalMonitorStateException();
      }

      if (holdCount > 0) {
        holdCount--;
      }
      if (holdCount == 0) {
        monitor.notify();
      }
    }
  }
}
