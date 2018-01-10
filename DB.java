/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class DB {
    
   private Connection c;
   private PreparedStatement ps;
   private PreparedStatement ps2;
   private Statement s;
   private ResultSet rs;
   private volatile String name;   

   
   private boolean dbConnection() 
   {
       /*********************************************************************************************************************************************
       *  WARNING DATABASE WILL NOT BE ABLE TO BE ACCESSED UNTIL THE BELOW QUERY IS EXECUTED BY A ROOT USER OR EXISTIN USER WITH PROPER GRANTS  
       *
       *  create database BankInfo;
       *  create user 'admin'@'localhost' Identified By 'admin';
       *  use BankInfo;
       *  Grant Select,Insert,Update,Create,References on BankInfo.*
       *  To 'admin'@'localhost';
       *  
       *///*******************************************************************************************************************************************
        String userName = "admin";
        String passWord = "admin";
        String url = "jdbc:mysql://localhost:3306";
        
        try
        {
            
          Class.forName("com.mysql.jdbc.Driver");
          c = DriverManager.getConnection(url,userName,passWord);
          
          s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
          s.execute("use BankInfo;");
          s.execute("Create table if not exists  Users(id int not null auto_increment"// create User Table to set up basic information
                                           + ", userName varchar(20)"
                                           + ", passWord varchar(20)"
                                           + ", fName varchar(20)"
                                           + ", lName varchar(20)"
                                           + ", dob varchar(8)"
                                           + ", street varchar(30)"
                                           + ", city varchar(30)"
                                           + ", state varchar(20)"
                                           + ", zip int(8)"
                                           + ", checkingBalance Double"
                                           + ", checkingNumber int(6)"
                                           + ", savingsBalance Double"
                                           + ", savingsNumber int(6)"
                                           + ", creditLine Double"
                                           + ", primary key (id) );");
          
            rs = s.executeQuery("select id from Users where( id = 1)");
            if(rs.first())// make sure the first row gets started or insertion and updating won't work with auto increment enabled
            {
                rs = null;
            }
            else
            {
                s.execute("insert into Users(id, userName, passWord) Values(1,'db', 'admin');");

            }
            s.execute("create table if not exists TransactionsDatesForChecking(id int(10)not null, " // creates a table for transaction on checking accounts.
                                           + " transDate Timestamp not null, "
                                           + " accountNumber int(6) not null, "
                                           + " amountOfTransaction Double not null,"
                                           + " Foreign Key(id) References Users(id));");
            String put = " Insert Into transactionsDatesForChecking"
                                           + "(id,transDate,accountNumber,amountOfTransaction) values(?,?,?,?);";
            ps = c.prepareStatement(put); 
           
           
 
           
            
                                         
            s.execute("create table if not exists TransactionsDatesForSavings(id int(10) not null, " // creates a table for transaction on checking accounts.
                                           + " transDate Timestamp not null, "
                                           + " accountNumber int(6) not null, "
                                           + " amountOfTransaction Double not null,"
                                           + " Foreign Key(id) References Users(id));");
                                          
                String prepareSQLForSavings = "Insert Into transactionsDatesForSavings(id,transDate,accountNumber,amountOfTransaction) values(?,?,?,?);";
                ps2 = c.prepareStatement(prepareSQLForSavings); 
        }
        catch(SQLException e)
        {
          System.out.println(e.getMessage() + " Error Code " + e.getErrorCode() + " SQL State" + e.getSQLState());
          return false;
        } catch (ClassNotFoundException ex) { 
          return false;
       }
     return true;  
     }
   
   private boolean testName(String name)
   {
       if(name != null)
          return true;
       else
          return false;  
   }
    
     public boolean createNewUser(Map<String,String[]> map) 
    { 
       try 
       {  
          if(dbConnection())
           {
              
              rs = s.executeQuery("select * from Users;");
              String use = "";        
              String pass = "";
              String parameterValuesCombined = "";
               for(String key : map.keySet())
               {
                 for(String val : map.get(key))
                 { 
                   if(key.equals("useName"))
                   {
                       if(val.isEmpty())
                          return false;
                               
                       use = val; 
                   }
                   else if(key.equals("passName"))
                   {
                       if(val.isEmpty())
                          return false;
                              
                       pass = val;
                       parameterValuesCombined = use + pass;
                   }
                  }
                }
               
                while(rs.next())
                {  
                    String userName = rs.getString(2);
                    String passWord = rs.getString(3);
                    String dbValuesCombined = userName + passWord;
                    System.out.println("dbValues " + dbValuesCombined + " ------ paramVAlue " +  parameterValuesCombined);
                    System.out.println(use + "  " + pass);
                  
                    if(dbValuesCombined.equals(parameterValuesCombined))
                    {
                        return false;
                    }
                    else if(!dbValuesCombined.equals(parameterValuesCombined) & rs.isLast()) 
                    {
                       rs.moveToInsertRow();
                       rs.updateString(2,use);
                       rs.updateString(3,pass);
                       rs.insertRow();
                       name = use;
                       return true;
                   }     
                   
                }// end while loop
           System.out.println("Should be out of while" );  
            }// end if(dbConnection) statement;
            else//this else is if dbConnection() method returns false
            {
              System.out.println("Went wromg here because dbconnection failed");
              return false;
            }
        }//end try
        catch (SQLException ex)           
        {
           System.out.println(ex.getMessage());
          return false ;     
        } 
        finally
        {
           try {
                s.close();
                c.close();
              
           } 
           catch (SQLException ex) 
           {
               System.out.println(ex.getMessage() +"HELLLLLLLLLLLLLLOOOOOOOOOOOOOOOO");
             return false;
           }
        }
      System.out.println("Went wromg here because no conditions were met failed");
    return false;
    }
   //checks if the username and password already exists in database, and rejects it if it does. 
  public boolean checkUserAndPassWord(Map<String,String[]>  map)
   {  
       String pass="";
       String use = "";
        String parameterValuesCombined = "";
       try 
       {  
           if(dbConnection())
           {
             rs = s.executeQuery("select * from Users order by id asc;");
               for(String key : map.keySet())
               {
                 for(String val : map.get(key))
                 {
                   if(key.equals("u"))
                   {
                     if(val.isEmpty())//make sure cant submit empty form fields and get into restricted pages
                        return false;
                                 
                          use = val;
                   }
                   else if(key.equals("p"))
                   {
                      if(val.isEmpty())
                        return false;
                            
                         pass = val;
                         parameterValuesCombined = use + pass;
                         break;
                    }
                 }
              }
              while(rs.next())
              {  
                String userName = rs.getString(2);
                String passWord = rs.getString(3);
                String dbValuesCombined = userName + passWord;
                System.out.println(dbValuesCombined);
                if(dbValuesCombined.equals(parameterValuesCombined))
                {
                    
                  name = use;
                  return true;
                }
              }
            } 
            else //this else will execute if dbConnection() method returns false;
            {
               return false;
            }
         }
         catch (SQLException ex){
         System.out.println(ex.getMessage());
         ex.printStackTrace();
         return false;      
         } 
         finally
        {
           try{
               c.close();
               s.close();
           }catch(SQLException e){
               return false;
           }
       }
     
      
     return false;      
   }
   // *******************get user info for the session****************************
   public String getUserName()
   {
       return getName(name);
   }
   private String getName(String name)
   {   
       String sessionName = name;
       name = null;
       return sessionName;
   }
   //**************************add in bank account creation and information*******************************
   public boolean makeBankAccount(Map<String,String[]> m, String sessionVariable)
   {
       
       
       try
       {
         if(dbConnection())
         {
            name = sessionVariable;
           
            rs = s.executeQuery("select * from Users;");
            ResultSetMetaData r = rs.getMetaData();
           
            while(rs.next())
            {
               String use = rs.getString("userName");
               System.out.println(use + "----------" + name);
               if(use.equals(name))
               { 
                 Random random = new Random();
                 Integer checking = random.nextInt(100000);
                 Integer savings = random.nextInt(100000);
                 for(String key : m.keySet())
                 {  
                   for(String value : m.get(key))
                   {   
                      if(r.getColumnName(4).equals("fName") & key.equals("fName"))
                       rs.updateString(4, value);
                      else if(r.getColumnName(5).equals("lName") & key.equals("lName"))
                       rs.updateString(5, value);
                      else if(r.getColumnName(6).equals("dob") & key.equals("dob"))
                       rs.updateString(6, value);
                      else if(r.getColumnName(7).equals("street") & key.equals("street"))
                       rs.updateString(7, value);
                      else if(r.getColumnName(8).equals("city") & key.equals("city"))
                       rs.updateString(8, value);
                      else if(r.getColumnName(9).equals("state") & key.equals("state"))
                       rs.updateString(9, value);
                      else if(r.getColumnName(10).equals("zip") & key.equals("zip"))
                       rs.updateString(10, value);
                      else if(r.getColumnName(11).equals("checkingBalance") & value.equals("Checking Account"))
                      {
                       rs.updateDouble(11, 0.00);
                       rs.updateInt("checkingNumber", checking);
                       Timestamp st = new Timestamp(System.currentTimeMillis());
                       ps.setInt(1, rs.getInt(1));
                       ps.setTimestamp(2, st);
                       ps.setInt(3, checking);
                       ps.setDouble(4, 0.0);
                       ps.executeUpdate();
                      }
                      else if(r.getColumnName(13).equals("savingsBalance") & value.equals("Savings Account"))
                      {  
                        rs.updateDouble(13, 0.00);
                        rs.updateInt("savingsNumber", savings);
                        Timestamp st = new Timestamp(System.currentTimeMillis());
                        ps2.setInt(1, rs.getInt(1));
                        ps2.setTimestamp(2, st);
                        ps2.setInt(3, savings);
                        ps2.setDouble(4, 0.0);
                        ps2.executeUpdate();
                      }
                    }
                  }
                  rs.updateRow();
                  return true;
               }
             }  
          }// end dbConnection method()
         else//this else is if dbConnection() method returns false
         {
             System.out.println("Connection method returned false in makeBankAccount method");
             return false;
         }
        }
       catch(SQLException e)
       {
          System.out.println(e.getMessage() + "  is it here");
          
          return false;
       }
       finally
       {
           try{
               c.close();
               s.close();
           }
           catch(SQLException se)
           {
               System.out.println(se.getMessage() + "In the finally block of makeBankAccount() method");
               return false;
           }
       }
           
         System.out.println("It didnt work");      
       return false;
   }
   //*********************************************************************************************
   public Integer getCheckingAccountNumber(String accountType, String userNameFromSession)
   {
       Integer userCheckingAccountNumber = 0;
       try{
           if(userNameFromSession == null)
           { 
               name = "Your session is over.";
               return -2;
           }
           if(dbConnection())
           {
               rs = s.executeQuery("Select userName ,checkingNumber from Users;");
               
               
               while(rs.next())
               {
                   if(accountType.equals("c") & userNameFromSession.equals(rs.getString("userName")))
                   {
                      userCheckingAccountNumber  = rs.getInt("checkingNumber");
                       return  userCheckingAccountNumber ;
                   }
               }
           }
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
       
       return -1;
   }
   public Integer getSavingsAccountNumber(String accountType, String userNameFromSession)
   {
        Integer userSavingsAccountNumber = 0;
       try{
           if(userNameFromSession == null)
           { 
               name = "Your session is over.";
               return -2;
           }
           if(dbConnection())
           {
               rs = s.executeQuery("Select userName ,savingsNumber from Users;");
               while(rs.next())
               {
                   if(accountType.equals("s") & userNameFromSession.equals(rs.getString("userName")))
                   {
                      userSavingsAccountNumber  = rs.getInt("savingsNumber");
                       return  userSavingsAccountNumber ;
                   }
               }
           }
          }catch(SQLException e){
           System.out.println(e.getMessage());
          }
       
    
       return -1;
   }
   
   public boolean debitOrCredit(Map<String,String[]> m,String sessionName)
   {
       this.name = sessionName;
      if(name == null)
          return false;
   
      
     int accountNum = 0;
     int idNum = 0;
     double amount = 0.0;
     double updatedBalance = 0.0;
     String action = "";
     String accountType ="";
     
     
      for(String key : m.keySet())
         for(String value : m.get(key))
         {
              if(key.equals("accountSelected"))
              {    accountNum = Integer.parseInt(value);
              System.out.println(accountNum);
              }
              else if(key.equals("amount"))
              {
                   amount = Double.parseDouble(value);
                   System.out.println(amount);
              }
              else if(key.equals("typeOfTransaction"))
              {
                  action = value;
                  System.out.println(action);
              }
              else if(key.equals("accountType"))
              {
                  accountType = value;
                  System.out.println(accountType);
              }
            } 
             
       try
       {
         if(dbConnection())
         {
           if(accountType.equals("Checking"))
           {
              rs = s.executeQuery("Select * from Users;");
               while(rs.next())
               {
                 if(rs.getString("userName").equals(name) & rs.getInt("checkingNumber") == accountNum)
                 {  
                   if(action.equals("Credit"))
                   {    
                     idNum = rs.getInt(1);
                     updatedBalance = (rs.getDouble("checkingBalance") + amount);
                     rs.updateDouble("checkingBalance", updatedBalance);
                     rs.updateRow();
                     rs.close();
                     break;
                    }
                    else if(action.equals("Debit"))
                    {
                      idNum = rs.getInt(1);
                      updatedBalance = (rs.getDouble("checkingBalance") - amount);
                      rs.updateDouble("checkingBalance", updatedBalance);
                      rs.updateRow(); 
                      rs.close();
                      break;
                    }
                  }
               }
            }
            else if(accountType.equals("Savings"))
            {
              rs = s.executeQuery("Select * from Users;");
               while(rs.next())
               {
                 if(rs.getString("userName").equals(name) & rs.getInt("savingsNumber") == accountNum)
                 {  
                   if(action.equals("Credit"))
                   {
                     idNum = rs.getInt(1); 
                     updatedBalance = (rs.getDouble("savingsBalance") + amount);
                     rs.updateDouble("savingsBalance", updatedBalance);
                     rs.updateRow();
                     updatedBalance = rs.getDouble("savingsBalance");
                     System.out.println("Savings updated balance " + updatedBalance + "*****************************");
                     rs.close();
                     break;
                   }
                   else if(action.equals("Debit"))
                   {
                     idNum = rs.getInt(1); 
                     updatedBalance = (rs.getDouble("savingsBalance") - amount);
                     rs.updateDouble("savingsBalance", updatedBalance);
                     rs.updateRow();
                     updatedBalance = rs.getDouble("savingsBalance");
                     rs.close();
                     break;
                   }
                  }
                }//end while
                
              }
              else
              {
                System.out.println("It is not account type");
              }
          }//end dbConnection test
         else //if dbConnection return false;
          {
           System.out.println("Probem with connection method");
           return false;
          }
       setDates(idNum,accountNum,updatedBalance,accountType);
       idNum=0;
       return true;
       }//end try
       catch(SQLException e)
       {
          System.out.println(e.getMessage());
       }catch(Exception ex){
           System.out.println(ex.getMessage());
       }
      return false;
   }
   
   private void setDates(int id, int accountNum, double updatedBalance, String accountType)
   { System.out.print("did we even get here 1");
       if(accountType.equals("Checking")){
         try{
            Timestamp st = new Timestamp(System.currentTimeMillis()); 
               ps.setInt(1, id);
               ps.setTimestamp(2, st);
               ps.setInt(3, accountNum);
               ps.setDouble(4, updatedBalance);
               ps.executeUpdate();
                System.out.print("did we even get here2");
           }catch(SQLException e)
           {
               System.out.println(e.getMessage());
           }
       }
       else if(accountType.equals("Savings")){
         try{
            Timestamp st = new Timestamp(System.currentTimeMillis());
               ps2.setInt(1, id);
               ps2.setTimestamp(2, st);
               ps2.setInt(3,accountNum );
               ps2.setDouble(4, updatedBalance);
               ps2.executeUpdate(); 
               System.out.print("did we even get here3");
           }catch(SQLException e)
           {
               System.out.println(e.getMessage());
           }
       }
   }
   public ArrayList<Object[]> getDates(Map<String,String[]> m , String sessionName)
   {  
       ArrayList<Object[]> rows = new ArrayList<>();
       int id = 0;
       this.name = sessionName;
//       if(name == null)
//       {
//           rows = null;
//           return rows;
//       }
       if(dbConnection())
       {
        try{
         rs = s.executeQuery("Select * from Users;");
         while(rs.next())
         {
            if(name.equals(rs.getString("userName")))
            {
              id = rs.getInt("id");
              break;
            }
         }
        }catch(SQLException e){
         System.out.println(e.getMessage());
        }
       }  
      String accountType = "";
      long begin = 0;
      long end = 0;
      Object[] row = {null, null, null};
      
       for(String key : m.keySet())
         for(String value : m.get(key))
         {
              if(key.equals("begin"))
              {  
                  begin = transformDates(value);
                  System.out.println(begin);
              }
              else if(key.equals("end"))
              {
                   end = transformDates(value);
                   System.out.println(end);
              }
               if(key.equals("accountType"))
              {
                   accountType = value;
                   System.out.println(accountType);
              }
         } 
       try{
        if(dbConnection()){             
         if(accountType.equals("Checking")){
             
            rs = s.executeQuery("select * from transactionsDatesForChecking");
            while(rs.next()){  
             Long l = rs.getTimestamp("transDate").getTime();
              System.out.println("In the Checking checks" + l);
             if(id == rs.getInt("id"))
             { 
               System.out.println("The id of the User--" + id + " the database ids " + rs.getInt("id"));
               row = new Object[3];
               row[0] = rs.getTimestamp("transDate");
               row[1] = rs.getInt("accountNumber");
               row[2] = rs.getDouble("amountOfTransaction");
               rows.add(row);
               } 
            }
         }
         else if(accountType.contains("Savings"))
         {
           rs = s.executeQuery("select * from transactionsDatesForSavings");
           while(rs.next())
           { 
              Long l = rs.getTimestamp("transDate").getTime();
             if(id == rs.getInt("id"))// & l > begin & l < end)
             {  
               row = new Object[3];
               row[0] = rs.getTimestamp("transDate");
               row[1] = rs.getInt("accountNumber");
               row[2] = rs.getDouble("amountOfTransaction");
               rows.add(row);
               } 
            }   
          }   
         }
        else
            System.out.println("DBCONNECT ");
       }
       catch(SQLException s)
       {
           System.out.println(s.getMessage());
       }
       System.out.println(rows.size() + "Hi");
       return rows;
  }
   
   private long transformDates(String dateAsString){
   long milliDate=0;
       try{
       DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
       Date d = df.parse(dateAsString);
       milliDate = d.getTime();
       }catch(ParseException pe){
       pe.printStackTrace();
       }
     return milliDate;   
   }
}