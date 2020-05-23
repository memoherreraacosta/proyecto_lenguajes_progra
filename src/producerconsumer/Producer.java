
package producerconsumer;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer extends Thread {
    Buffer buffer;
    int timeout;
    Producer(Buffer buffer, int timeout) {
        this.buffer = buffer;
        this.timeout = timeout;
    }
    
    private String buildOp(){
        String ops = "+-*/";
        String nums = "0123456789";
        long time = System.currentTimeMillis();
        Random r = new Random(time);
        
        char op = ops.charAt(r.nextInt(4));
        char n1 = nums.charAt(r.nextInt(10));
        char n2 = nums.charAt(r.nextInt(10));
        String res = "("+op + " " + n1 + " " + n2 + ")";
        
        return res;
    }
    
    @Override
    public void run() {
        System.out.println("Running Producer...");
        String schemeOp;
        for(int i=0 ; i<5 ; i++) {
            schemeOp = this.buildOp();
            this.buffer.produce(schemeOp);
            //System.out.println("Producer produced: " + product);
            Buffer.print("Producer produced: " + schemeOp);
            
            try {
                Thread.sleep(this.timeout);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
