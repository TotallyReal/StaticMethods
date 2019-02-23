/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticmethods;

/**
 *
 * @author eofir
 */
public class NUM {
    
    public static double[] uniform(int n){
        if (n<1)
            return null;
        double d[] = new double[n];
        for (int i = 0; i < d.length; i++) {
            d[i] = 1.0/n;            
        }
        return d;
    }
    
}
