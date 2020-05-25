
package producerconsumer;

public class Consumer extends Thread {
    Buffer buffer;
    int timeout;
    int num;
    
    boolean running = true;
    
    Consumer(Buffer buffer, int timeout, int num) {
        this.buffer = buffer;
        this.timeout = timeout;
        this.num = num;
    }
  
    @Override
    public void run() {
        System.out.println("Running Consumer...");
        String schemeOp;
        char op;
        int   a;
        int   b;
        String res;
        while(this.running) {
            schemeOp = this.buffer.consume(this.num);
            try {
                Thread.sleep(this.timeout);
            } catch (InterruptedException ie) {
                //Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
