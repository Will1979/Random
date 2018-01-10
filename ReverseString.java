/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package williamWasher;

import java.util.Scanner;

/**
 *
 * @author Washer
 */
public class ReverseString {
    
    public static void main(String[] args)
    {
        String name = "William";
        
        int nameLength = name.length();
        char[] backwards = new char[nameLength];
        
        for(int i = 0; i < name.length() ; i++){
            backwards[nameLength-1] = name.charAt(i);
            nameLength--;
        }
        System.out.println(String.valueOf(backwards));
        
        Scanner s = new Scanner(System.in);
        boolean run =true;
        while(run)
        {
            boolean runToo = true;
            int num = 0;
             System.out.println("Enter only an Integer and also try a palindrome");
           while(runToo)
             if(s.hasNextInt())
             {
                   num = s.nextInt();
                  runToo =false;
             }
             else{
                  System.out.println("Please Enter only numbers no decimals or letters");
                  s.next();
                  runToo = true;
             }
                StringBuilder st = new StringBuilder(String.valueOf(num));
                if(Integer.toString(num).contains(st.reverse()))
                {
                     System.out.println(num +" is a palindrome number");
                      
                     run = false;
                }
                else
                {
                    System.out.println(num +" is not a palindrome");
                    run = true;
                }
                   
                
        }
       
        
    }
    
}
