import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class saddlePoint {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        String output;
        String ip1 = in.nextLine().trim();
        output = reversingOfWords(ip1);
        System.out.println(String.valueOf(output));
		
	}

	private static String reversingOfWords(String input1) {
		// TODO Auto-generated method stub
		String[] words =  input1.split(" ");
     
		String rev = "";
		for(int i = words.length - 1; i >= 0 ; i--)
		{
		if(i!=0)
		{
			
			rev += words[i];
		}
		else
		   rev += words[i] + " ";
		}

		// rev = "question interview is This "

		// can also use StringBuilder:
		StringBuilder revb = new StringBuilder();
		for(int i = words.length - 1; i >= 0 ; i--)
		{
		  revb.append(words[i]);
		  revb.append(" ");
		}
		return revb.toString();
	}

	
 }

