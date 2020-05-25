
package producerconsumer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

public class Buffer {
    
    
    public ArrayList<String> buffer;
    
    //Added variables
    public int n;
    public JProgressBar bar;
    
    Buffer(int n, JProgressBar bar) {
        this.buffer = new ArrayList<>();
        this.n = n;
        this.bar = bar;
        this.bar.setMinimum(0);
        this.bar.setMaximum(n);
    }
    
    /*Old buffer
    Buffer(){
        this.buffer = new ArrayList<>();
    }
    */
    
    synchronized String consume() {
        String product;
        
        if(this.buffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.bar.setValue(this.buffer.size());
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
        this.bar.setValue(this.buffer.size());
        
        notify();
    }
    
    static int count = 1;
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
    //Added this method
    public int getSize(){
        return this.buffer.size();
    }
    
}
