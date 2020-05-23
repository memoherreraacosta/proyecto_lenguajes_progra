
package producerconsumer;

public class ProducerConsumer {
    
    Producer[] producers;
    Consumer[] consumers;
    Buffer buffer;

    public static void main(String[] args) {
        
        
        GUIFrame frame = new GUIFrame();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        
        Buffer buffer = new Buffer();
        // Parameters for producer / consumers
        int timeout_producer = 50;
        int timeout_consumer = 150;
        int nProducers = 1;
        int nConsumers = 1;
        
        // Run producers
        for (int i = 0; i < nProducers; i++)
            (new Producer(buffer,timeout_producer)).start();
        
        // Run consumers
        for (int i = 0; i < nProducers; i++)
            (new Consumer(buffer,timeout_consumer)).start();
    }
}
