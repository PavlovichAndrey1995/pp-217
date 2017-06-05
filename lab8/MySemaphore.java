package lab8;

public class MySemaphore {

  private Object monitor = new Object();
  private final int permits;
  private volatile int acquiredCount;

  public MySemaphore(int permits) {
    this.permits = permits;
    this.acquiredCount = 0;
  }

  public void acquire() throws InterruptedException {
    acquire(1);
  }

  public void acquire(int permits) throws InterruptedException {
    synchronized (monitor) {
      ensureValidness(permits);
      if (this.acquiredCount + permits > this.permits) {
        monitor.wait();
      }
      this.acquiredCount += permits;
    }
  }

  private void ensureValidness(final int permits) throws IllegalArgumentException {
    if (permits < 0) {
      throw new IllegalArgumentException("permits cannot be negative");
    }
  }

  public void release() {
    release(1);
  }

  public void release(final int permits) {
    synchronized (monitor) {
      ensureValidness(permits);
      this.acquiredCount -= permits;
      monitor.notify();
    }
  }
}
