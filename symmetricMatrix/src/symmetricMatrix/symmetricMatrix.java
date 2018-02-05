package symmetricMatrix;

import java.util.Arrays;
import java.util.Scanner;

public class symmetricMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in =new Scanner(System.in);
		int r1,r2,c1,c2;
		System.out.println("enter rows");
         r1 = in.nextInt();
         System.out.println("enter columns");
         c1=in.nextInt();
        boolean sym = true;
         int[][] matrix1 = new int[r1][c1];
         int[][] trans = new int[c1][r1];
         System.out.println("enter matrix");
         for(int i=0;i<r1;i++)
         {
        	 for(int j=0;j<c1;j++)
        	 {
        		 matrix1[i][j]=in.nextInt();
        		 
        	 }
        	 }
         for(int i=0;i<c1;i++)
         {
        	 for(int j=0;j<r1;j++)
        	 {
        		
        	trans[i][j] =matrix1[j][i];
        	 
        	 }
         }
         System.out.println("transpose is");
         for(int i=0;i<c1;i++)
         {
        	 for(int j=0;j<r1;j++)
        	 {
        System.out.print(trans[i][j]+"\t");
        	 }
        	  System.out.println();
         }
         for(int i=0;i<r1;i++)
         {
        	 for(int j=0;j<c1;j++)
        	 {
        		 if(matrix1[i][j] != trans[i][j])
        		 {
        			 sym = false;
        			 break;
        			 
        		 }
        	 }
         }
         if(sym)
         {
        	 System.out.println("matrix is sym");
        	 
         }
         String str = "keep";
     	char[] s1array = str.toLowerCase().toCharArray();
     	Arrays.sort(s1array);
     	 	System.out.println(s1array);
        	 }
        	
} 
        	 
	


