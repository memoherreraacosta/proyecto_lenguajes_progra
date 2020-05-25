
package producerconsumer;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {
    Buffer buffer;
    int n;
    int m;
    int timeout;
    int num;
    
    boolean running = true;
    
    Producer(Buffer buffer, int timeout, int n, int m, int num) {
        this.buffer = buffer;
        this.timeout = timeout;
        this.n = n;
        this.m = m;
        this.num = num;
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
        String res = "("+op + " " + n1 + " " + n2 + ")";
        
        return res;
    }
    
    @Override
    public void run() {
        System.out.println("Running Producer...");
        String schemeOp;
        
        while(this.running) {
            schemeOp = this.buildOp();
            
            this.buffer.produce(schemeOp, this.num);
            
            try {
                Thread.sleep(this.timeout);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
