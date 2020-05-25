
package producerconsumer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

public class Buffer {
    
    
    public ArrayList<String> buffer;
    
    //Added variables
    public int n;
    public JProgressBar bar;
    public DefaultTableModel tablaHacer;
    public DefaultTableModel tablaRealizado;
    public JLabel realizadosLabel;
    public int realizados;
    
    Buffer(int n, JProgressBar bar, DefaultTableModel tablaHacer, DefaultTableModel tablaRealizado, JLabel label) {
        this.buffer = new ArrayList<>();
        this.n = n;
        this.bar = bar;
        this.bar.setMinimum(0);
        this.bar.setMaximum(n);
        this.tablaHacer = tablaHacer;
        this.tablaRealizado = tablaRealizado;
        this.realizadosLabel = label;
        this.realizados = 0;
    }
    
    /*Old buffer
    Buffer(){
        this.buffer = new ArrayList<>();
    }
    */
    
    synchronized String consume(int num) {
        String product = "";
        
        while(this.buffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.realizados++;
        this.realizadosLabel.setText("Num realizados : "+this.realizados+"");
        product = this.buffer.remove(this.buffer.size() - 1);
        String res = parseSchemeOp(product.charAt(1), Character.getNumericValue(product.charAt(3)), Character.getNumericValue(product.charAt(5)));
        String[] fila = {product.charAt(1)+"",product.charAt(3)+"",product.charAt(5)+"",res, num+""};
        this.tablaRealizado.addRow(fila);
        this.tablaHacer.removeRow(this.buffer.size());
        this.bar.setValue(this.buffer.size());
 
        notify();
        
        return product;
    }
    
    synchronized void produce(String product, int num) {
        while(this.buffer.size() >= this.n) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String[] tabla = {product.charAt(1)+"",product.charAt(3)+"",product.charAt(5)+"",num+""};
        this.tablaHacer.addRow(tabla);
        this.buffer.add(product);
        this.bar.setValue(this.buffer.size());
        
        notify();
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
    
    static int count = 1;
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
    //Added this method
    /*
    public int getSize(){
        return this.buffer.size();
    }
    */
    
}
