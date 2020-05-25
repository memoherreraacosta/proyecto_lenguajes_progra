
package producerconsumer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer extends Thread {
    Buffer buffer;
    int n;
    int m;
    int timeout;
    GUIFrame frame;
    int numProducers;
    
    boolean running = true;
    
    Producer(Buffer buffer, int timeout, int n, int m, GUIFrame frame, int numProducers) {
        this.buffer = buffer;
        this.timeout = timeout;
        this.n = n;
        this.m = m;
        this.frame = frame;
        this.numProducers = numProducers;
    }
    
    private String buildOp(){
   
        long time = System.currentTimeMillis();
        ThreadLocalRandom r = ThreadLocalRandom.current();
        String ops = "+-*/";
        String nums = "0123456789";
        int range_len = nums.length();
        
        char op = ops.charAt(r.nextInt(4));
        char n1 = nums.charAt(r.nextInt(this.n, this.m));
        char n2 = nums.charAt(r.nextInt(this.n, this.m));
        String[] tabla = {op+"",n1+"",n2+"",""};
        this.frame.addTabla(tabla);
        String res = "("+op + " " + n1 + " " + n2 + ")";
        
        return res;
    }
    
    @Override
    public void run() {
        System.out.println("Running Producer...");
        String schemeOp;
        //while(this.running) {
        for(int i = 0; i < this.numProducers; i++){
            schemeOp = this.buildOp();
            this.buffer.produce(schemeOp);
            //System.out.println("Producer produced: " + product);
            //Buffer.print("Producer produced: " + schemeOp);
            
            try {
                Thread.sleep(this.timeout);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
