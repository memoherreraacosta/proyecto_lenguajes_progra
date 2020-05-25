package producerconsumer;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ProducerConsumer {
    
    boolean iniciado;
    
    public ProducerConsumer() throws InterruptedException{
        this.iniciado = false;
        runPanel();
    }

    private static GUIFrame get_frame() {
        GUIFrame frame = new GUIFrame();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        return frame;
    }

    private static void call_pane(String text){
        JOptionPane.showMessageDialog(
            null,
            text
        );
    }

    private static void stop(
                            ArrayList<Producer> producers,
                            ArrayList<Consumer> consumers)
    {
        while (!producers.isEmpty()) {
            producers.get(0).interrupt();
            producers.get(0).running = false;
            producers.remove(0);
        }
        while (!consumers.isEmpty()) {
            consumers.get(0).interrupt();
            consumers.get(0).running = false;
            consumers.remove(0);
        }
    }

    private static void start(
                            ArrayList<Producer> producers,
                            ArrayList<Consumer> consumers,
                            Buffer buffer,
                            int nProd,
                            int nCons,
                            int n,
                            int m,
                            int timeoutP,
                            int timeoutC)
    {
        Producer producer;
        Consumer consumer;
        
        for(int i = 0; i < nProd; i++){
            producer = new Producer(buffer, timeoutP, n, m);
            producers.add(producer);
            producer.start();
        }
        
        for(int i = 0; i < nCons; i++){
            consumer = new Consumer(buffer, timeoutC);
            consumers.add(consumer);
            consumer.start();
        }
        
    }

    public static void main(String[] args) throws InterruptedException{
        ProducerConsumer nuevo = new ProducerConsumer();
    }
    
    private void runPanel() throws InterruptedException{
        
        GUIFrame frame = ProducerConsumer.get_frame();
        boolean panel_running = true;
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        
        while (panel_running) {
            /*
            Get state has 3 values
                -1: Stop
                 0: Waiting to fill data
                 1: To calculus
             */
            if (frame.getState() == 1) {
                try {
                    DefaultTableModel tablaHacer = frame.getTablaHacer();
                    DefaultTableModel tablaRealizado = frame.getTablaRealizado();
                    Buffer buffer = new Buffer(frame.getTamanoBuffer(), frame.getProgressBar(), tablaHacer, tablaRealizado);
                   
                    //Old buffer
                    //Buffer buffer = new Buffer();
                    
                    // Parameters for producer / consumers
                    int timeout_producer = frame.getEsperaProductor();
                    int timeout_consumer = frame.getEsperaConsumidor();
                    int nProducers = frame.getProductores();
                    int nConsumers = frame.getConsumidores();
                    int n = frame.get_n_value();
                    int m = frame.get_m_value();
                    
                    if (n > m) {
                        call_pane("N debe de ser menor o igual que M");
                        frame.setState(0);
                    }
                    /*
                        The state can change while validating the input data
                    */
                    if (frame.getState() == 1 && !this.iniciado){
                        this.iniciado = true;
                            // Run threads
                        //Only for testing
                        //panel_running = false;
                        start(
                            producers,
                            consumers,
                            buffer,
                            nProducers,
                            nConsumers,
                            n,
                            m,
                            timeout_producer,
                            timeout_consumer
                        );
                        //Only for testing
                        //break;
                        if(frame.getState() != 1)
                            break;
                    }
                    
                } catch (NumberFormatException e) {
                    panel_running = true;
                    call_pane("Error al introducir un número invalido: " + e);
                    frame.setState(0);
                    
                } catch (Exception ex) {
                    call_pane("WTF  " + ex);
                    frame.setState(0);
                }
            }
            if (frame.getState() == -1) {
                // Stop and close
                call_pane("Stopping process, removing threads");
                stop(
                    producers,
                    consumers
                );
                //frame.setVisible(false);
                //frame.dispose();
                frame.setState(0);
                //panel_running = false;
                call_pane("Process finished");
                this.iniciado = false;
            }
            Thread.sleep(200);
        }
    }

}
