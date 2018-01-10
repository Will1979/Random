
package williamWasher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Prime implements Runnable{
   Integer num;
   BlockingQueue<Integer> block;
    public Prime(BlockingQueue<Integer> b){
      this.block = b; 
    }
    
    private  void findPrime(Integer integer){
        
        int i =0;
        boolean test = false;
        List<Integer> list = new ArrayList<>((integer - 2));
        
        for(i = 2; i < integer; i++){
          if(integer % i == 0){
             test = false;
            
             System.out.println(integer + " is " + test);
             break;
          }
          else if(integer % i != 0){
             list.add(i);
             System.out.println(i);
          }
        }
        if(list.size() == (integer-2)){
           test = true;
           System.out.println(integer + " is " + test);
        }
        
    }

    @Override
    public void run() {
       while(true){
        try {
           num = block.take();
           findPrime(num);
        } catch (InterruptedException ex) {
          System.out.println(ex.getMessage() + "Program will end in three seconds");
            try {
                Thread.sleep(3000);
                System.exit(0);
            } catch (InterruptedException ex1) {
                Logger.getLogger(Prime.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
      }
    }
    
}
