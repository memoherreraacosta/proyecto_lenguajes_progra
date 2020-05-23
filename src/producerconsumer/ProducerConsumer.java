
package producerconsumer;

public class ProducerConsumer {

    public static void main(String[] args) {
        
        
        GUIFrame frame = new GUIFrame();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        
        
        Buffer buffer = new Buffer();
        int timeout = 1000;
        
        Producer producer = new Producer(buffer, timeout);
        Consumer consumer = new Consumer(buffer, timeout);
        
        producer.start();
        consumer.start();
    }
    
}
