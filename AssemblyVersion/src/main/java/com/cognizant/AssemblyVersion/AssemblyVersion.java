package com.cognizant.AssemblyVersion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AssemblyVersion {

	public static void main(String[] args) throws IOException 
        {
        File file =new File("D:\\T_PAYMENT_SER\\CDTService\\CDT.PaymentServices.WCF\\Properties\\AssemblyInfo.cs");
        Scanner in = null;
        String input="";
        String REGEX=".*\\\"(.*)\\\".*";
    Pattern pattern;
    Matcher matcher;

        try {
            in = new Scanner(file);
            while(in.hasNext())
            {
            	String line=in.nextLine();
                if(line.contains("AssemblyVersion"))
                {
                      input=line;
                }
            }
                 
                pattern = Pattern.compile(REGEX);
                matcher = pattern.matcher(input);
                if(matcher.find()==true)
                {
                     
                     System.out.println(matcher.group(1));
                     
                }
             
            
        } catch (FileNotFoundException e) {

		 
		
            e.printStackTrace();
        }

		
        }
}
	

