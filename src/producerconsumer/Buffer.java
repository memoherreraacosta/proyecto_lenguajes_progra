
package producerconsumer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buffer {
    
    public ArrayList<String> buffer;
    
    Buffer() {
        this.buffer = new ArrayList<>();
    }
    
    synchronized String consume() {
        String product;
        
        if(this.buffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        product = this.buffer.remove(this.buffer.size() - 1);
        notify();
        
        return product;
    }
    
    synchronized void produce(String product) {
        if(this.buffer.size() >= 100) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.buffer.add(product);
        
        notify();
    }
    
    static int count = 1;
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
}
