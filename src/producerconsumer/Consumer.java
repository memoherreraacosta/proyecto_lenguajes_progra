
package producerconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer extends Thread {
    Buffer buffer;
    int timeout;
    GUIFrame frame;
    int numConsumers;
    
    boolean running = true;
    
    Consumer(Buffer buffer, int timeout, GUIFrame frame, int numConsumers) {
        this.buffer = buffer;
        this.timeout = timeout;
        this.frame = frame;
        this.numConsumers = numConsumers;
    }
    
    private String parseSchemeOp(char op, int a, int b) {
        String res = "undefined";
            switch (op) {
                case '+' : res = Integer.toString(a+b); break;
                case '-' : res = Integer.toString(a-b); break;
                case '*' : res = Integer.toString(a*b); break;
                case '/' : 
                    if (b != 0) {
                        if (a % b == 0) res = Integer.toString(a/b);
                        else {
                            for (int i = 1; i < 10; i++){
                                if (a % i == 0 && b % i == 0){
                                    res = Integer.toString(a/i) + '/' + Integer.toString(b/i);
                                }
                            }
                        }
                    }
                    else res = "undefined";
                    break;
            }
        return res;
    }
    
    @Override
    public void run() {
        System.out.println("Running Consumer...");
        String schemeOp;
        char op;
        int   a;
        int   b;
        String res;
        
        //Testing
        //while(this.running) {
        for(int i = 0; i < this.numConsumers; i++){
            schemeOp = this.buffer.consume();
            this.frame.removeTablaPorHacer(this.buffer.getSize());
            op = schemeOp.charAt(1);
            a  = Character.getNumericValue(schemeOp.charAt(3));  
            b  = Character.getNumericValue(schemeOp.charAt(5));
            
            res = this.parseSchemeOp(op, a, b);
            String[] fila = {schemeOp.charAt(1)+"",schemeOp.charAt(3)+"",schemeOp.charAt(5)+"",res};
            this.frame.addTablaRealizado(fila);
            
            //System.out.println(schemeOp + " -> " + res + " | " + this.buffer.buffer.size());
            
            try {
                Thread.sleep(this.timeout);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
