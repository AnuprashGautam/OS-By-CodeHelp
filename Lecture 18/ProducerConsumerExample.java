import java.util.LinkedList;

class SharedBuffer {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int CAPACITY = 5;

    public synchronized void produce(int value) throws InterruptedException {
        // Wait if buffer is full
        while (list.size() == CAPACITY) {
            wait();
        }
        list.add(value);
        System.out.println("Produced: " + value);
        notify(); // Notify consumer thread
    }

    public synchronized void consume() throws InterruptedException {
        // Wait if buffer is empty
        while (list.isEmpty()) {
            wait();
        }
        int value = list.removeFirst();
        System.out.println("Consumed: " + value);
        notify(); // Notify producer thread
    }
}

public class ProducerConsumerExample {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();

        Thread producer = new Thread(() -> {
            int value = 0;
            try {
                while (true) {
                    buffer.produce(value++);
                    Thread.sleep(500); // simulate time to produce
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    buffer.consume();
                    Thread.sleep(1000); // simulate time to consume
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();
    }
}
