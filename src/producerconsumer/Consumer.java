
package producerconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer extends Thread {
    Buffer buffer;
    int timeout;
    
    Consumer(Buffer buffer, int timeout) {
        this.buffer = buffer;
        this.timeout = timeout;
    }
    
    @Override
    public void run() {
        System.out.println("Running Consumer...");
        String product;
        
        for(int i=0 ; i<5 ; i++) {
            product = this.buffer.consume();
            //System.out.println("Consumer consumed: " + product);
            Buffer.print("Consumer consumed: " + product);
            
            try {
                Thread.sleep(this.timeout);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
