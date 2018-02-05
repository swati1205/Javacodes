
public class ReplaceAll {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
String str =  "java jee";
int n=0;
String str1 = str.replaceAll("\\s", "");
char[] chararray = str1.toCharArray();
for(int i=0;i<chararray.length;i++)
{
	n=1;
for(int j=i+1;i<chararray.length;j++)
{
if(chararray[i]==chararray[j])
{
	n++;
}
}
System.out.println("the char " +chararray[i]+ "occurs "+ n + "times");
}
	}
}
