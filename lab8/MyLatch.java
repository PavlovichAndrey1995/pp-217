package lab8;

public class MyLatch {

  private volatile int count;
  private Object monitor = new Object();

  public MyLatch(int count) {
    this.count = count;
  }

  public void await() throws InterruptedException {
    synchronized (monitor) {
      while (count > 0) {
        monitor.wait();
      }
    }
  }

  public void countDown() {
    synchronized (monitor) {
      if (count > 0) {
        count--;
      }

      if (count == 0) {
        monitor.notifyAll();
      }
    }
  }
}
