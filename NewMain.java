/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package williamWasher;

import java.text.NumberFormat;
import java.util.Scanner;

/**
 *
 * @author Washer
 */
public class NewMain {

   public NewMain()
   {
       
   }
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("Enter number");
        int[] digits = {0,1,2,3,4,5,6,7,8,9};
       
        long num = s.nextLong();
         
        Double a = Math.pow(10,2);
        Double b = Math.pow(10,3);
        Double c = Math.pow(10,4);
        Double d = Math.pow(10,5);
        Double e = Math.pow(10,6);
        Double f = Math.pow(10,7);
        Double g = Math.pow(10,8);
  
  long[] h = {a.longValue(),b.longValue(),c.longValue(),d.longValue(),e.longValue(),f.longValue(),g.longValue()};
  for(int i =0; i < h.length-1; i++)// this loop only gets the digit in the highest place holder more loops to keep breaking the number down individually.
  {   int counter = 0;
      long[] sepDigits = new long[counter + 1];//this array will hold the digits of the number seperately using a counter as the algorithm continues
     
      if(num < h[0]){// if the number is two digits and is divisible by 11 then it is palindrom
       for(int y=0; y < digits.length; y++){
         if(num/11 == digits[y]){
             System.out.println(num+"/11 = " + num/11);
             break;
          }
        }
       System.out.println("Found a palindrome = " + num);
       break;// found the two digit palindrome break the loop.
      } 
      
      if(num > h[i] & num< h[i + 1]){
          System.out.println(doFormat(num) + " is greater than " + doFormat(h[i]) + " but less than " + doFormat(h[i+1]));
          long findFirst = num/h[i];
          sepDigits[counter] = findFirst;
          System.out.println("findFirst " + findFirst + " sepDigits = " + sepDigits[counter]);
          counter = counter++;
          long findSecond = num - (findFirst * h[i]);
          System.out.println("findSecond " + findSecond);
          
         if(findSecond < h[i]){
             long findThird = h[i]/findSecond;
             for(int w = 0; w<digits.length; w++){
              if((10-findThird) == digits[w]){
                  sepDigits[counter] = digits[w];
                  System.out.println(sepDigits[counter]);
              }
             }
         }
               
           
      }
      
  }
}
   private static String doFormat(double num)
   {
       return NumberFormat.getNumberInstance().format(num);
       
   }
    
}
