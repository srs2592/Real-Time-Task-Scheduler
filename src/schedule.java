
import java.awt.*;
import java.io.*;
import java.util.*;




public class schedule {
	
	 static Scanner x;
	 static String[][] inputData = new String[6][6];
	 static int [][] inputInt = new int [6][6];
	 static String[][] opText = new String [50][5];
	
/////////////Reading & Displaying the Input Text File and Saving it into an Array////////////
	public static void readFile(){
		int i=0;
		while(x.hasNext()){
			
			for(int m=0; m<6; m++){
			String a = x.next();
			inputData[i][m]=a;
			if(m>0)
			inputInt[i][m]=Integer.parseInt(inputData[i][m]);
			System.out.printf("%s\t", inputData[i][m]);
			}
			System.out.println("");
			i++;
		}
		
		x.close();
		
	}
	/*
/////////////////GCD Method////////////////////////////
	public static int GCD(int a, int b) {
	    if (b == 0) return a;
	    else return (GCD (b, a % b));
	}
/////////////////LCM Method////////////////////////////
	public static int LCM (int a, int b){
		return (a * b / GCD(a,b));		
	}
	*/
	
///////////////////////////////////Main Method//////////////////////////////////////////////
	public static void main(String[] argv) {
		
		 File f1 = null;
		 String f2 = null;
		 String f3 = "default";
		// int gcd, lcm;
		 
		  
	    if (argv.length > 0) {
	      try {
	        
		      f1 = new File(argv[0]);
		      x = new Scanner(f1);
		  	  
		      System.out.println("Reading sample.txt complete");
	      
		  	  f2 = argv[1];
		  	  	if (argv.length==3){
		  	  		f3 = argv[2];
		  	  		System.out.println("Scheduling Method is "+f2+" "+f3);
		  	  	}
		  	  	else if(argv.length==2){
		  	  		System.out.println("Scheduling Method is "+f2);
		  	  	}
		  	  
		  	  System.out.println("Displaying sample.txt complete");
		  	  readFile();
	  	/*  
	  	  gcd = GCD(inputInt[5][1],GCD(inputInt[4][1],GCD(inputInt[3][1],GCD(inputInt[1][1],inputInt[2][1]))));
	  	  System.out.println("\nGCD of Periods/Deadlines is "+ gcd);
	  	  
	  	  lcm = LCM(inputInt[5][1],LCM(inputInt[4][1],LCM(inputInt[3][1],LCM(inputInt[1][1],inputInt[2][1]))));
	  	  System.out.println("\nLCM of Periods/Deadlines is "+ lcm);
	  	  */
		  	  if(f3.equals("EE")){
			  	  if (f2.equals("EDF")){
			  		  edfEE test = new edfEE(inputData);
			  		  opText = test.getOutput();
			  	  }
			  	  else if (f2.equals("RM")){
			  		  rmEE test = new rmEE(inputData);
			  		  opText = test.getOutput();
			  	  }
			  	  
			  	writeFile f = new writeFile(opText,(f2+f3));
			  	
		  	  }
		  	  else if(f3.equals("default")){
		  		if (f2.equals("EDF")){
			  		  edfFinal test = new edfFinal(inputData);
			  		  opText = test.getOutput();
			  	  }
			  	  else if (f2.equals("RM")){
			  		  rmFinal test = new rmFinal(inputData);
			  		  opText = test.getOutput();
			  	  } 
		  		
		  		writeFile f = new writeFile(opText,f2);
		  		
		  	  }
		  	  
	  	  
	  	  
	      }//try end
	      catch (Exception e) {
	    	System.err.println(e);
	        System.exit(1);
	      }//catch end
	    }// if argv end
	    else {
	      System.err.println("usage: java schedule <textfile>");
	      System.exit(1);
	    }
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////
}
