import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class FreqOfDighHashMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		int n = reader.nextInt(); 
		HashMap <Integer, Integer> hmap = new HashMap<Integer, Integer>();
		
		while(n!=0)
		{
	    int num = n%10;
		 if(hmap.containsKey(num))
		 {
			 hmap.put(num,  hmap.get(num)+1); 
		 }
		 else 
		 {
			 hmap.put(num, 1);
			 
		 }
		 n = n/10;
		}
		 Iterator itr = hmap.entrySet().iterator();
		 while(itr.hasNext())
		 {
		 Map.Entry pair = (Map.Entry)itr.next();
		 System.out.println(pair.getKey() + "=" +pair.getValue());

		 }
	
	}

}
