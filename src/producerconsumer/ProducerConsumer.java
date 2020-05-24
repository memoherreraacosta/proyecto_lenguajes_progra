
package producerconsumer;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProducerConsumer {
    
    private static GUIFrame get_frame(){
        GUIFrame frame = new GUIFrame();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        
        return frame;
    }
    
    private static void stop(ArrayList<Producer> producers, ArrayList<Consumer> consumers) {
        while (!producers.isEmpty()) {
            producers.get(0).stop();
            producers.remove(0);
        }
        while (!consumers.isEmpty()) {
            consumers.get(0).stop();
            consumers.remove(0);
            
        }
    }
    
    private static void start(ArrayList<Producer> producers, ArrayList<Consumer> consumers, Buffer buffer, int nProd, int nCons, int timeoutP, int timeoutC) {
        Producer producer;
        Consumer consumer;
        for (int i = 0; i < nProd; i++) {
            producer = new Producer(buffer, timeoutP);
            producers.add(producer);
            producer.start();
            
        }
        for (int i = 0; i < nCons; i++) {
            consumer = new Consumer(buffer, timeoutC);
            consumers.add(consumer);
            consumer.start();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        GUIFrame frame = ProducerConsumer.get_frame();
        boolean panel_running = true;
        
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        
        while(panel_running){
            /*
            Get state has 3 values
                -1: Stop
                 0: Waiting to fill data
                 1: To calculus
            */
            
            if (frame.get_state() == 1){
                try{
                    Buffer buffer = new Buffer();
                    // Parameters for producer / consumers
                    int timeout_producer = frame.getEsperaProductor();
                    int timeout_consumer = frame.getEsperaConsumidor();
                    int nProducers = frame.getProductores();
                    int nConsumers = frame.getConsumidores();
                    int n = frame.get_n_value();
                    int m = frame.get_m_value();
                    
                    frame.set_enabled(false);
                    // Run threads
                    start(producers, consumers, buffer, nProducers, nConsumers, timeout_producer, timeout_consumer);
                    
                    //stop(producers, consumers);
                    

                    // Break loop
                    panel_running = !panel_running;
                } catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Error al introducir un número invalido");
                    frame.setEnabled(true);
                    frame.set_state(0);
                }
            }
            if (frame.get_state() == -1){
                // Stop and close
                frame.setVisible(false); //you can't see me!
                frame.dispose(); //Destroy the JFrame object
                panel_running = !panel_running;
            }
            Thread.sleep(1000);
        }
    }
    
}
