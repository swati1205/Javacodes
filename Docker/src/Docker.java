import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class Docker {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		  String host="10.242.138.115";
		    String user="root";
		    String password="123root";
		    String str = null;
		    String command1="/root/.go/bin/analyze-local-images "+"-endpoint "+"http://10.242.138.116:6060 "+"-my-address "+"10.242.138.115 postgres";
		    try{
		    	
		    	java.util.Properties config = new java.util.Properties(); 
		    	config.put("StrictHostKeyChecking", "no");
		    	JSch jsch = new JSch();
		    	Session session=jsch.getSession(user, host, 22);
		    	session.setPassword(password);
		    	session.setConfig(config);
		    	session.connect();
		    	System.out.println("Connected");
		    	
		    	Channel channel=session.openChannel("exec");
		        ((ChannelExec)channel).setCommand(command1);
		        channel.setInputStream(null);
		        ((ChannelExec)channel).setErrStream(System.err);
		        
		        InputStream in=channel.getInputStream();
		        channel.connect();
		        byte[] tmp=new byte[1024];
		        while(true){
		          while(in.available()>0){
		            int i=in.read(tmp, 0, 1024);
		            if(i<0)break;
		           // System.out.print(new String(tmp, 0, i));
		            str =  new String(tmp, 0, i);
		            System.out.println(str);
		          }
		          
		          if(channel.isClosed()){
		            System.out.println("exit-status: "+channel.getExitStatus());
		            break;
		          }
		          try{Thread.sleep(1000);}catch(Exception ee){}
		        }
		        
		        channel.disconnect();
		        session.disconnect();
		        System.out.println("DONE");
		    }catch(Exception e){
		    	e.printStackTrace();
		    }

   } 

  
	

}
