import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExample {

	public static void main(String[] args) {
		BlockingQueue<Integer> bQueue = new ArrayBlockingQueue<>(1024);
		
		Producer producer = new Producer(bQueue);
		Consumer consumer = new Consumer(bQueue);
		
		new Thread(producer.r1).start();
		new Thread(consumer.r1).start();
		
		System.out.println("main ends");
	}

}

class Producer {
	protected BlockingQueue<Integer> bQueue = null;

	public Producer(BlockingQueue<Integer> bQueue) {
		this.bQueue = bQueue;
	}

	Runnable r1 = () -> {
		try {
			bQueue.put(1);
			Thread.sleep(1000);
			bQueue.put(2);
			Thread.sleep(1000);
			bQueue.put(3);
		} catch (InterruptedException i) {
			i.printStackTrace();
		}
	};
}

class Consumer {
	protected BlockingQueue<Integer> bQueue = null;

	public Consumer(BlockingQueue<Integer> bQueue) {
		this.bQueue = bQueue;
	}

	Runnable r1 = () -> {
		try {
			System.out.println(bQueue.take());
			System.out.println(bQueue.take());
			System.out.println(bQueue.take());
		} catch (InterruptedException i) {
			i.printStackTrace();
		}
	};
}
