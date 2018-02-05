import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
//import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class Unzip {

	
	public static void main(String[] args) {
        String zipFilePath = "D:\\Docs.zip";
            String destDir = "D:\\Docs";
            unzip(zipFilePath, destDir);
}

	private static void unzip(String zipFilePath, String destDir) {
		// TODO Auto-generated method stub
		 FileInputStream fis;
	        //buffer for read and write data to file
	        byte[] buffer = new byte[512];
	        try {
	            fis = new FileInputStream(zipFilePath);
	            System.out.println(fis);
	            ZipInputStream zis = new ZipInputStream(fis);
	            System.out.println(zis);
	            ZipEntry ze = zis.getNextEntry();
	            System.out.println("zipentry is "+ze);
	            while(ze != null){
	                String fileName = ze.getName();
	                System.out.println(fileName);
	                File newFile = new File(destDir + File.separator + fileName);
	                System.out.println("Unzipping to "+newFile.getAbsolutePath());
	                //create directories for sub directories in zip
	                new File(newFile.getParent()).mkdirs();
	                FileOutputStream fos = new FileOutputStream(newFile);
	                System.out.println(fos);
	                int len;
	                for(int i=1 ;i<=zis.read(buffer);++i)
	                {
	                    fos.write(i);
	                }
	              
	                fos.close();
	                //close this ZipEntry
	                zis.closeEntry();
	                ze = zis.getNextEntry();
	            }
	            //close last ZipEntry
	            zis.closeEntry();
	            zis.close();
	            fis.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	         
	    }
		
		
	}
