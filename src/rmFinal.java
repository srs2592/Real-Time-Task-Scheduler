import java.util.*;
import java.awt.List;

public class rmFinal {
	
	String [][] output= new String [50][5];
	String[][] dummyarray = new String[6][6];
	
	
	int [] execTime = new int [5];
	int [] deadlines = new int [5];
	
	double percentIdle=0;
	int totalRunTime=0;
	int totalIdleTime=0;
	double totalEnergy=0;
	
	
	int [] arrivalsW1= new int [7];
	int [] arrivalsW2= new int [7];
	int [] arrivalsW3= new int [7];
	int [] arrivalsW4= new int [7];
	int [] arrivalsW5= new int [7];;
	int [] arrivals= new int [30];
	
	int [] ArrangedOld = new int [5];
	int [][] Frequency = new int [5][2];
	int [] ArrangedNew = new int [5];
	int [] edfInput = new int [5];
	
	int nxtArrival = 0;
	int nxtArrival1 = 0;
	int time=1, r=0, count=0;
	int i1=0, i2=0, i3=0, i4=0, i5=0;
	
	PriorityQueue<Integer> ReadyQueue = new PriorityQueue<Integer>();
	PriorityQueue<Integer> WaitingQueue = new PriorityQueue<Integer>();
	PriorityQueue<Integer> PreemptionQueue = new PriorityQueue<Integer>();
	PriorityQueue<Integer> ArrivalQueue = new PriorityQueue<Integer>();
	
	public rmFinal(String[][] array){
		
		dummyarray = array;
		
		/////////////////////////////////////////////////////////
	
		for(int i=0;i<5;i++){
				deadlines[i] =Integer.parseInt(array[i+1][1]);
				edfInput[i] = deadlines[i];
				execTime[i] = Integer.parseInt(array[i+1][2]); 
		}
	
		for(int i=0;i<5;i++){
			System.out.println("Execution Time for task w"+(i+1)+" is "+execTime[i]+" sec");
		}		
		
		///////////////////
		generateArrivals();	
		System.out.println("RM Scheduling Starts");
		arrivals = combine(arrivalsW5,combine(arrivalsW4,combine(arrivalsW3,combine(arrivalsW1, arrivalsW2))));
        //System.out.println("concatenated array : " + Arrays.toString(arrivals));
		/////////////////////////////////////////////////////////
		Arrays.sort(deadlines);
		Arrays.sort(arrivals);
		
		int [] S=new int[5];
		for (int i=0;i<deadlines.length;i++){
			S[i]= getArrayIndex(edfInput,deadlines[i]);
		}
		
		for(int i=0;i<arrivals.length;i++)
			ArrivalQueue.add(arrivals[i]);
		
		while(ArrivalQueue.peek()==0){
			ArrivalQueue.poll();
		}
		
		
		for(int f=0;f<5;f++)
			ReadyQueue.add(deadlines[f]);
				
		
		///////////////////////////////////////////////////////////
		int i=0;
		while(time<=1000){
			
			if (time==ArrivalQueue.peek()){
				ArrivalQueue.poll();
				for(int iS=0;iS<S.length;iS++){
					
					if(i1<arrivalsW1.length && S[iS]==0){
						if(time == arrivalsW1[i1]){
							PreemptionQueue.add(edfInput[0]);
							if(WaitingQueue.contains(edfInput[0]))
								execTime[0]+=Integer.parseInt(dummyarray[1][2]);
							i1++;
						}
					}
					if(i2<arrivalsW2.length && S[iS]==1){
						if(time == arrivalsW2[i2]){
							PreemptionQueue.add(edfInput[1]);
							if(WaitingQueue.contains(edfInput[1]))
								execTime[1]+=Integer.parseInt(dummyarray[2][2]);
							i2++;
						}
					}
					if(i3<arrivalsW3.length && S[iS]==2){
						if(time == arrivalsW3[i3]){
							PreemptionQueue.add(edfInput[2]);
							if(WaitingQueue.contains(edfInput[2]))
								execTime[2]+=Integer.parseInt(dummyarray[3][2]);
							i3++;
						}
					}
					if(i4<arrivalsW4.length && S[iS]==3){
						if(time == arrivalsW4[i4]){
							PreemptionQueue.add(edfInput[3]);
							if(WaitingQueue.contains(edfInput[3]))
								execTime[3]+=Integer.parseInt(dummyarray[4][2]);
							i4++;
						}
					}
					if(i5<arrivalsW5.length && S[iS]==4){
						if(time == arrivalsW5[i5]){
							PreemptionQueue.add(edfInput[4]);
							if(WaitingQueue.contains(edfInput[4]))
								execTime[4]+=Integer.parseInt(dummyarray[5][2]);
							i5++;
						}
					}
					
					
				}
				
			}
			else if(time<ArrivalQueue.peek()){
				
				
				if(PreemptionQueue.isEmpty() && !ReadyQueue.isEmpty()){// 
					
					int dead = ReadyQueue.poll();
					int m = getArrayIndex(edfInput,dead);
					int RunTime = Math.min((ArrivalQueue.peek()-time), execTime[m]);
				
					if(execTime[m]>RunTime){
						if(!WaitingQueue.contains(edfInput[m]))
							WaitingQueue.add(edfInput[m]);
						execTime[m]=execTime[m]-RunTime;
					}
					else if(execTime[m]==RunTime){
						if(!WaitingQueue.contains(edfInput[m]))
							execTime[m]=Integer.parseInt((dummyarray[m+1][2]));
					}
					time=time+RunTime;
					PrintOutput((time-RunTime), dummyarray[m+1][0], RunTime, m);
					
				}
				
				
				else if(!PreemptionQueue.isEmpty()){
					
					int dead = PreemptionQueue.poll();
					int m = getArrayIndex(edfInput,dead);
					int RunTime = Math.min((ArrivalQueue.peek()-time), execTime[m]);
					
					if(execTime[m]!=RunTime){
						if(!WaitingQueue.contains(edfInput[m]))
							WaitingQueue.add(edfInput[m]);
						execTime[m]=execTime[m]-RunTime;
					}
					else if(execTime[m]==RunTime){
						if(!WaitingQueue.contains(edfInput[m]))
							execTime[m]=Integer.parseInt((dummyarray[m+1][2]));
						else{
							execTime[m]=Integer.parseInt((dummyarray[m+1][2]));
							WaitingQueue.remove(edfInput[m]);
						}
							
					}
					time=time+RunTime;
					PrintOutput((time-RunTime), dummyarray[m+1][0], RunTime, m);
					
				}
				
				
				else if(PreemptionQueue.isEmpty() && ReadyQueue.isEmpty() && !WaitingQueue.isEmpty()){
					
					int dead = WaitingQueue.poll();
					int m = getArrayIndex(edfInput,dead);
					
					int RunTime = Math.min((ArrivalQueue.peek()-time), execTime[m]);
					if(execTime[m]>RunTime){
						if(!WaitingQueue.contains(edfInput[m]))
						WaitingQueue.add(edfInput[m]);
						execTime[m]=execTime[m]-RunTime;
						
					}
					else if(execTime[m]==RunTime){
						if(!WaitingQueue.contains(edfInput[m]))
						execTime[m]=Integer.parseInt((dummyarray[m+1][2]));
										
					}
					time=time+RunTime;
					PrintOutput((time-RunTime), dummyarray[m+1][0], RunTime, m);
					
				}
				
				else if(PreemptionQueue.isEmpty() && ReadyQueue.isEmpty() && WaitingQueue.isEmpty()){
					
					int RunTime = (ArrivalQueue.peek()-time);
					
					time=time+RunTime;
					PrintOutput((time-RunTime), "IDL", RunTime, 6);
					
				}
				
				
			}
			
			
			if(i4>=5 && i2>=5)
				break;
			
		}
	
		
	}
	



	private int getTask(Integer peek) {
		PriorityQueue<Integer> A1 = new PriorityQueue<Integer>();
		for(int i=0;i<arrivalsW1.length;i++)
			A1.add(arrivalsW1[i]);
		PriorityQueue<Integer> A2 = new PriorityQueue<Integer>();
		for(int i=0;i<arrivalsW1.length;i++)
			A2.add(arrivalsW2[i]);
		PriorityQueue<Integer> A3 = new PriorityQueue<Integer>();
		for(int i=0;i<arrivalsW1.length;i++)
			A3.add(arrivalsW3[i]);
		PriorityQueue<Integer> A4 = new PriorityQueue<Integer>();
		for(int i=0;i<arrivalsW1.length;i++)
			A4.add(arrivalsW4[i]);
		PriorityQueue<Integer> A5 = new PriorityQueue<Integer>();
		for(int i=0;i<arrivalsW1.length;i++)
			A5.add(arrivalsW5[i]);
		
		
		if(A1.contains(peek))
		return 0;
		else if(A2.contains(peek))
			return 1;
		else if(A3.contains(peek))
			return 2;
		else if(A4.contains(peek))
			return 3;
		else
			return 4;
		
	}


	public void PrintOutput(int StartTime, String TaskName, int ExecutionTime, int m){
		
		if(ExecutionTime==0 || StartTime==1000){
					
		}
		else{
			double k;
			float energy=0;
							///////Updating Start Time of the Task////////////
							output[r][0] = Integer.toString(StartTime);
							
							///////Updating the Task Name////////////////////
							output[r][1] = TaskName;
							
							//////////////Updating the fequency/////////////
							if(TaskName.equals("IDL")){
								output[r][2] = "IDL";
								totalIdleTime+=ExecutionTime;
							}
							else {
								output[r][2] = "1188";
								totalRunTime+=ExecutionTime;
							}
							//////////////Updating the Execution Time/////////////
							output[r][3] = Integer.toString(ExecutionTime);
							
							//////////Updating the Energy consumed////////////////
							if(TaskName.equals("IDL"))
								k=0.084;
							else
								k =  ((double)Integer.parseInt(dummyarray[0][2])/(double)Integer.parseInt(dummyarray[0][1]));
								
							energy = (float) (k*ExecutionTime); //for e.g.; E = (625/1000) * (57) ;for task w4
							double roundOff = Math.round(energy * 100.0) / 100.0;
							totalEnergy+=roundOff;
							output[r][4] = Double.toString(roundOff) + "J";
							
							r++;
			
		}
		
	}
	

	public String[][] getOutput(){
		percentIdle = ((double)totalIdleTime/(double)1000)*100;
		System.out.println("Percentage Idle Time is = "+ percentIdle +" %");
		System.out.println("Total Execution Time of Tasks is = "+ (totalRunTime+1) +" sec");
		System.out.println("Total Energy Consumption is = "+ totalEnergy+" J");
				
		return output;
	}
	
	public int getArrayIndex(int[] arr,int value) {

        int k=0;
        for(int i=0;i<arr.length;i++){

            if(arr[i]==value){
                k=i;
                break;
            }
        }
    return k;
}
	
	public int compareArrivals(int m, int m1){
		int arr=0, arr1=0;
		if (m==0 && i1<arrivalsW1.length)
			arr = arrivalsW1[i1];
		else if (m==1 && i2<arrivalsW2.length)
			arr = arrivalsW2[i2];
		else if (m==2 && i3<arrivalsW3.length)
			arr = arrivalsW3[i3];
		else if (m==3 && i4<arrivalsW4.length)
			arr = arrivalsW4[i4];
		else if (m==4 && i5<arrivalsW5.length)
			arr = arrivalsW5[i5];
		
		if (m1==0 && i1<arrivalsW1.length)
			arr1 = arrivalsW1[i1];
		else if (m1==1 && i2<arrivalsW2.length)
			arr1 = arrivalsW2[i2];
		else if (m1==2 && i3<arrivalsW3.length)
			arr1 = arrivalsW3[i3];
		else if (m1==3 && i4<arrivalsW4.length)
			arr1 = arrivalsW4[i4];
		else if (m1==4 && i5<arrivalsW5.length)
			arr1 = arrivalsW5[i5];
			
		if(arr>arr1)
			return m1;
		else
			return m;
	}

	
	public int getArrival(int mc) {
		int arr=0;
		if (mc==0 && i1<arrivalsW1.length)
			arr = arrivalsW1[i1];
		else if (mc==1 && i2<arrivalsW2.length)
			arr = arrivalsW2[i2];
		else if (mc==2 && i3<arrivalsW3.length)
			arr = arrivalsW3[i3];
		else if (mc==3 && i4<arrivalsW4.length)
			arr = arrivalsW4[i4];
		else if (mc==4 && i5<arrivalsW5.length)
			arr = arrivalsW5[i5];
		System.out.println("Arrival="+arr);
		return arr;
	}
	
	public static int[] combine(int[] a, int[] b){
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
	
	public void generateArrivals(){
		int u=-1;
		do{
			u++;
			arrivalsW1[u]=(u+1)*edfInput[0];
		}while(arrivalsW1[u]<1000);
		
		u=-1;
		do{
			u++;
			arrivalsW2[u]=(u+1)*edfInput[1];
		}while(arrivalsW2[u]<1000);
		
		u=-1;
		do{
			u++;
			arrivalsW3[u]=(u+1)*edfInput[2];
		}while(arrivalsW3[u]<1000);
		
		u=-1;
		do{
			u++;
			arrivalsW4[u]=(u+1)*edfInput[3];
		}while(arrivalsW4[u]<1000);
		
		u=-1;
		do{
			u++;
			arrivalsW5[u]=(u+1)*edfInput[4];
		}while(arrivalsW5[u]<1000);
		
	}
	
}



