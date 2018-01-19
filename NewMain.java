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
        long[] numToIndividualDigits = breakApart(num,nm.counter,nm);
        for(long l : numToIndividualDigits)
            System.out.println(l + " <-- if the number left of the arrow is zero then the input was a non palindrome two digit number");
        
}
   private static String doFormat(double num)
   {
       return NumberFormat.getNumberInstance().format(num);
       
   }
   public static long[] breakApart(long num,int counter,NewMain nm)
   {   
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
          //System.out.println(doFormat(num) + " is greater than " + doFormat(powersOfTen[j]) + " but less than " + doFormat(powersOfTen[j+1]));
          long findFirst = num/powersOfTen[j];/*this simple division gets a whole number by leaving the remainder e.g. 1234/1000 = 1.234 , 
                                              //but as a long data type the decimal is dropped and then the number can be broken up into individual digits.*/
          System.out.println("findfirst = " + findFirst + "is the counter 0 at first " + nm.counter);
          long findNext = num - (findFirst * powersOfTen[j]);
          nm.separatedDigits[nm.counter] = findFirst;
          long[] temp = new long[nm.counter + 1];
          temp[nm.counter] = findFirst;
          //System.out.println(nm.counter + 1 + " counter + 1 " + temp.length + " should be 2");
          nm.separatedDigits = new long[nm.counter + 1];
          nm.separatedDigits[nm.counter] = temp[nm.counter];
          
         
          
          
         System.out.println( "the length of the array " + nm.separatedDigits.length);
          System.out.println("num = " + num);
          System.out.println("findNext = " + findNext);
          if(findNext > 10){
              
              breakApart(findNext,nm.counter++,nm);
          }
          else if(findNext < 10){
              System.out.println(findNext + " find next");
              breakApart(findNext,nm.counter++,nm);
              long[] newSeparatedDigits = new long[nm.counter + 1];
              for(int x = 0; x < nm.separatedDigits.length;x++)
              {
                  newSeparatedDigits[x] = nm.separatedDigits[x];
                  System.out.println(nm.separatedDigits[x] + " last call ");
              }
              newSeparatedDigits[nm.separatedDigits.length - 1] = findNext;
              nm.separatedDigits = new long[newSeparatedDigits.length];
              nm.separatedDigits = newSeparatedDigits;
              break;
          }
      }
      
  }//end main foor loop
       return nm.separatedDigits;
   }
   
   private long[] adjustArray(long[] l, int count){
    
       
       return l;
   }
}
