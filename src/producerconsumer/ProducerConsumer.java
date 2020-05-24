
package producerconsumer;

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
    
    public static void main(String[] args) throws InterruptedException {
        
        GUIFrame frame = ProducerConsumer.get_frame();
        boolean panel_running = true;

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
                    // Run producers
                    for (int i = 0; i < nProducers; i++)
                        (new Producer(buffer,timeout_producer)).start();

                    // Run consumers
                    for (int i = 0; i < nConsumers; i++)
                        (new Consumer(buffer,timeout_consumer)).start();

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
