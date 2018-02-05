import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Hashmap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
HashMap<Integer, String> hmap = new HashMap<Integer, String>();

/*Enter value to hashmap */
hmap.put(1, "Swati");
hmap.put(12, "Chaitanya");
hmap.put(2, "Rahul");
hmap.put(7, "Singh");
hmap.put(49, "Ajeet");
hmap.put(3, "Anuj");

/*To iterate values from hashmap*/
Iterator itr = hmap.entrySet().iterator();
while(itr.hasNext())
{
Map.Entry pair = (Map.Entry)itr.next();
System.out.println(pair.getKey() + "=" +pair.getValue());

}

/*Get values based on keys*/
String str = hmap.get(2);
System.out.println(str);

/*ArrayList*/
ArrayList <String> arrList = new ArrayList <String>();
arrList.add("xyz");
arrList.add("abc");
Iterator itr1 = arrList.iterator();
while(itr1.hasNext())
{
System.out.println(itr1.next());	
}

	}

}
