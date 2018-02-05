import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class FilePermision {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
BufferedReader br1 = new BufferedReader (new FileReader("D:\\file1.txt"));
BufferedReader br2 = new BufferedReader (new FileReader("D:\\file2.txt"));
boolean equal = true;
int len=0;
String line1 = br1.readLine();

String line2 = br2.readLine();

while(line1!=null || line2!=null)
{
if(line1==null || line2==null)
{
	System.out.println(len);
	equal = false;
break;	
}
else if(! line1.equalsIgnoreCase(line2))
{
	System.out.println("H1");
	equal = false;
	break;
}
line1 = br1.readLine();
line2 = br1.readLine();
len++;
}
if(equal == true)
{
System.out.println("Files are same");	
}
else
{
System.out.println("Files are diffrent");
}
br1.close();
br2.close();
		}

}
