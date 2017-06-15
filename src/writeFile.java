
import java.io.*;
import java.util.*;
import java.lang.*;


public class writeFile {
	
	private Formatter k;
	String newline = System.getProperty("line.separator");
	
	public writeFile(String[][] arrayOP, String z){
		try{
			k= new Formatter(z+"Output.txt");
		}
		catch(Exception e){
			System.err.println(e);
		}
		k.format("%s  Scheduling"+newline+ newline,z);
		k.format("Start@\tTskName\tFreqMHz\tExecTim\tEnergy"+ newline);
		
	for(int m=0;m<arrayOP.length;m++){
		for(int n=0;n<arrayOP[m].length;n++){
			if(arrayOP[m][n]!=null){
				k.format(" %s\t", arrayOP[m][n]);
			}
		}
		k.format(newline);
	}
		
	k.close();
	///////Displaying the Output on Command Window///////
	System.out.printf("\n%s\tScheduling",z);
	System.out.println("\nStart@\tTskName\tFreq\tExecTim\tEnergy");
	for(int n=0;n<arrayOP.length;n++){
		for(int m=0;m<arrayOP[n].length;m++)
		System.out.printf("%s\t", arrayOP[n][m]);
		System.out.println("");
	}
	
	}
	

}
