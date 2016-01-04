/***
 * David Dossantos
 * Assignment 3 CS1B
 * Cellular Automation
 * 	
 */


import java.util.Scanner;

    public class Foothill
    {
       public static void main (String[] args)
       {
    	      	   
    	   int rule, k;
    	      String strUserIn;
    	      
    	      Scanner inputStream = new Scanner(System.in);
    	      Automaton aut;

    	      // get rule from user
    	      do
    	      {
    	         System.out.print("Enter Rule (0 - 255): ");
    	         // get the answer in the form of a string:
    	         strUserIn = inputStream.nextLine();
    	         // and convert it to a number so we can compute:
    	         rule = Integer.parseInt(strUserIn);

    	      } while (rule < 0 || rule > 255);

    	      // create automaton with this rule and single central dot
    	      aut = new Automaton(rule);

    	      // now show it
    	      System.out.println("   start");
    	      for (k = 0; k < 100; k++)
    	      {
    	         System.out.println( aut.toStringCurrentGen() );
    	         aut.propagateNewGeneration();
    	      }
    	      System.out.println("   end");
    	      inputStream.close();
    	   }
    	
       
      // }
    }
     
    class Automaton
    {
       // class constants
       public final static int MAX_DISPLAY_WIDTH = 121;
       private final static int RULES = 8;
       
       // private members
       private boolean rules[];  // allocate rules[8] in constructor!
       private String thisGen;   // same here
       private String extremeBit; // bit, "*" or " ", implied everywhere "outside"
       private int displayWidth;  // an odd number so it can be perfectly centered
       
       // public constructors, mutators, etc. (need to be written)
       
       public Automaton(int newRule)
       {
          rules = new boolean[RULES];
          setRule(newRule);
          extremeBit = " ";
          thisGen = "*";
          displayWidth = 79;
       }
       
       public void resetFirstGen()
       {
          thisGen = "*";
          extremeBit = " ";
       }
       
       public boolean setRule(int newRule)
       {      
          StringBuffer ruleInByteBuffString = new StringBuffer(Integer.toBinaryString(newRule));
         
          if ((newRule < 0) || (newRule > Math.pow(2, RULES) - 1))
             return false;
         
          ruleInByteBuffString = ruleInByteBuffString.reverse();
         
          while (ruleInByteBuffString.length() != RULES)
             ruleInByteBuffString.append("0");
         
          for (int i = 0; i < RULES; i++)
          {
             if (ruleInByteBuffString.charAt(i) == '0')
                rules[i] = false;
             else
                rules[i] = true;
          }
         
          return true;
       }
       public boolean setDisplayWidth(int width)
       {
          if ((width > MAX_DISPLAY_WIDTH) || (width < 1) || (width % 2 == 0))
             return false;
         
          displayWidth = width;
          return true;
       }
       
       public String toStringCurrentGen()
       {
          String finalString = thisGen;
           
          StringBuffer finalStringBuffer = new StringBuffer(finalString);
          int difference;
         
          if (finalString.length() == displayWidth)
             return finalString;
         
          if (finalString.length() >= displayWidth)
          {
             difference = finalString.length() - displayWidth;
             difference /= 2;
             
             finalString = finalString.substring(0,
                   finalString.length() - difference);
             finalString = finalString.substring(difference,
                   finalString.length());
             
             return finalString;
          }
         
          if (finalString.length() <= displayWidth)
          {
             difference = displayWidth - finalString.length();
             difference /= 2;
             
             for (int i = 0; i < difference; i++) {
                finalStringBuffer.append(extremeBit);
                finalStringBuffer.insert(0, extremeBit);
             }
             finalString = finalStringBuffer.toString();
             
             return finalString;
          }
         
          return finalString;
       }
       
       public void propagateNewGeneration()
       {
          StringBuffer thisGenStringBuffer = new StringBuffer(thisGen);
          StringBuffer nextGenStringBuffer = new StringBuffer("");
           
          if (extremeBit == "*")
          {
             thisGenStringBuffer.append("**");
             thisGenStringBuffer.insert(0, "**");
          }
          else
          {
             thisGenStringBuffer.append("  ");
             thisGenStringBuffer.insert(0, "  ");
          }
         
          thisGen = thisGenStringBuffer.toString();
         
          for (int i = 0; i < thisGen.length() - 2; i ++)
             if (rules[threeBitsToInteger(thisGen.charAt(i),
                   thisGen.charAt(i+1), thisGen.charAt(i+2))])
                nextGenStringBuffer.append("*");
             else
                nextGenStringBuffer.append(" ");
         
         
         
          if (rules[threeBitsToInteger(extremeBit.charAt(0),
                extremeBit.charAt(0), extremeBit.charAt(0))])
             extremeBit = "*";
          else
             extremeBit = " ";
         
          while (nextGenStringBuffer.charAt(0) == extremeBit.charAt(0))
             nextGenStringBuffer.deleteCharAt(0);
         
          thisGen = nextGenStringBuffer.toString();
         
          this.setDisplayWidth(thisGen.length());
         
       }
       
       private int threeBitsToInteger(char a, char b, char c)
       {
          int finalNumber = 0;
         
          if (c == '*')
             finalNumber += 1;
          if (b == '*')
             finalNumber += 2;
          if (a == '*')
             finalNumber += 4;
         
          return finalNumber;
       }
    }

