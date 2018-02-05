import java.util.Scanner;


public class FrequencyDigit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		int n = reader.nextInt(); 
		FrequencyDigit obj=new FrequencyDigit();
	    obj.CountDigit(n);
	    
	    
	
	}

public void CountDigit(int n)
{
	int num=n;
	int freq[]=new int[10];
	while(num!=0)
	{
		int rem=num%10;
		freq[rem]++;
		num =num/10;
		
	}
	for(int i=0;i<freq.length;i++)
	{
		if(freq[i]!=0)
		{
			System.out.println(""+i+" occurs "+freq[i]+" times in digit ");
			
		}
		
	}
}
}
