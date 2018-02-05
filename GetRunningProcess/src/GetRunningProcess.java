import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GetRunningProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		    String line;
		    Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
		   // Process p = Runtime.getRuntime().exec("ps -ef");
		    BufferedReader input =
		            new BufferedReader(new InputStreamReader(p.getInputStream()));
		    while ((line = input.readLine()) != null) {
		        System.out.println(line); 
		    }
		    input.close();
		} catch (Exception err) {
		    err.printStackTrace();
		}
	}

}
