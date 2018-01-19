package williamWasher;

import java.text.NumberFormat;
import java.util.Scanner;

/**
 *
 * @author Washer
 */
public class NewMain {
  public volatile int counter = 0;
  public volatile long[] separatedDigits = new long[counter + 1];
   public NewMain()
   {
       
   }
    public static void main(String[] args){
        NewMain nm = new NewMain();
        
        Scanner s = new Scanner(System.in);
        System.out.println("Enter number that is more than one digit to test if it is a palindrome number");
       
       
        long num = s.nextLong();
        nm.separatedDigits = breakApart(num,nm.counter,nm.separatedDigits,nm);
        for(long l : nm.separatedDigits)
            System.out.println(l + " <-- if the number left of the arrow is zero then the input was a non palindrome two digit number");
        
}
   private static String doFormat(double num)
   {
       return NumberFormat.getNumberInstance().format(num);
       
   }
   public static long[] breakApart(long num,int counter,long[] array,NewMain nm)
   {   counter = nm.counter;
        Double a = Math.pow(10,1);
        Double b = Math.pow(10,2);
        Double c = Math.pow(10,3);
        Double d = Math.pow(10,4);
        Double e = Math.pow(10,5);
        Double f = Math.pow(10,6);
        Double g = Math.pow(10,7);
        Double h = Math.pow(10,8);
        Double i = Math.pow(10,9);
        
   int[] digits = {0,1,2,3,4,5,6,7,8,9};
   long[] powersOfTen = {a.longValue(),b.longValue(),c.longValue(),d.longValue(),e.longValue(),f.longValue(),g.longValue(),h.longValue(),i.longValue()};
   
   for(int j =0; j < powersOfTen.length-1; j++){// this loop only gets the digit in the highest place holder more loops to keep breaking the number down individually.   int counter = 0;
      for(int y=0; y < digits.length; y++){
         if( nm.counter == 0 & num % 11 == 0 & num/11 == digits[y]){// if number is divisible by Eleven with no remainder then its a two digit palindrome
            System.out.println("Found a palindrome = " + num);      // counter has to be 0 to check that the first number entered by input is two digits
            nm.separatedDigits = new long[2];                          //otherwise a number like 1233 could be mistaken as a palidrome
            nm.separatedDigits[0]=num/11;
            nm.separatedDigits[1]=num/11;
            return nm.separatedDigits;
          }
         else if(nm.counter == 0 & num % 11 != 0 & num/11 == digits[y]){// a two digit number that is not a palindrome
             System.out.println(num + " is not a palindrome ");
             nm.separatedDigits[nm.counter] = 0;
             return nm.separatedDigits;
         }   
         
      }
        
       
       if(num > powersOfTen[j] & num< powersOfTen[j + 1]){
          long findFirst = num/powersOfTen[j];//this simple division gets a whole number by leaving the remainder e.g. 1234/1000 = 1.234 , 
           long findNext = num - (findFirst * powersOfTen[j]);  //but as a long data type the decimal is dropped and then the number can be broken up into individual digits.
             if(nm.counter == 0){
              nm.separatedDigits[counter] = findFirst;
             }
             else if(nm.counter > 0){
                 long[] temp = new long[nm.counter];
                    for(int z = 0; z < nm.separatedDigits.length; z++){
                        temp[z] = nm.separatedDigits[z];
                    }
                    nm.separatedDigits = new long[nm.counter + 1];
                    for(int n = 0; n < nm.separatedDigits.length; n++){
                        if(n == 0)
                         nm.separatedDigits[n] = temp[n];
                        else
                          nm.separatedDigits[n] = findFirst;
                    }        
                    for(int s = 0; s < nm.separatedDigits.length; s++){
                      System.out.println(" values in nm.separatedDigits " + nm.separatedDigits[s]);
                        if(s == nm.separatedDigits.length - 1)
                        System.out.println("-----------------------------------");
                    }
             }        
             else if(findNext < 10){
              System.out.println(findNext + " find next");
              long[] temp = new long[nm.counter];
                    for(int z = 0; z < nm.separatedDigits.length; z++){
                        temp[z] = nm.separatedDigits[z];
                    }
                    nm.separatedDigits = new long[nm.counter + 1];
                    for(int n = 0; n < nm.separatedDigits.length; n++){
                        if(n == 0)
                         nm.separatedDigits[n] = temp[n];
                        else
                          nm.separatedDigits[n] = findFirst;
                    }        
                    for(int s = 0; s < nm.separatedDigits.length; s++){
                      System.out.println(" values in nm.separatedDigits " + nm.separatedDigits[s]);
                        if(s == nm.separatedDigits.length - 1)
                        System.out.println("-----------------------------------");
                    }
             
              break;
              }
      breakApart(findNext,nm.counter++,nm.separatedDigits,nm);    
      }
      
  }//end main foor loop
       return nm.separatedDigits;
   }
   
 
}

  