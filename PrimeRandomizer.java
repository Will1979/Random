package williamWasher;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PrimeRandomizer implements Runnable {

   BlockingQueue<Integer> bq = new ArrayBlockingQueue<>(1000);
    public static void main(String[] args) throws InterruptedException {
       PrimeRandomizer pr = new PrimeRandomizer();
       Thread getRandom = new Thread(pr);
       Thread checkPrime = new Thread(new Prime(pr.bq));
       getRandom.start();
       checkPrime.start();
    }
    
    @Override
    public void run(){
        while(true){
            try {
                bq.put(generateRandomNum());
            } catch (InterruptedException ex) {
                Logger.getLogger(PrimeRandomizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private Integer generateRandomNum(){
        Integer rand = 0;
        Random random = new Random();
        boolean find = true;
        while(find){
          rand = random.nextInt(1000);  
          if(rand > 2)//make sure we eliminate 1, the number itself and negative numbers
              break;
          else
           find = true;   
        }
    return rand;   
    }
   
}
