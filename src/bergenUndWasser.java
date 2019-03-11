import java.util.Scanner;

/*this program was fully coded by Pierre-Louis Pecheux for GSP*/

public class bergenUndWasser {

	public static void main(String[] args) {
		System.out.println("Welcome to the Mountain generator!");
		System.out.println("The Algorithm is efficient for a choice of big landscaps because the complexity is O(n)");
		System.out.println("Indeed, the Algorithm considers the level of the landscape only once in order to calaculate the area occupied by the water");
		System.out.println();
		//int [] pics = {1,2,4,4,4,3,1,4,4,5,1,4,2,6,4,7};//this is our array of int
		int[] pics = rdmPics();		
		buildScreen(pics,maxArray(pics));	
		maxLoc(pics);
		System.out.println("");
		System.out.print("the area occuped by the water is: ");
		System.out.println(area(pics,0,nextIndiceTrue(maxLoc(pics),0)));

	}
	
	public static int area (int[] tab, int beg, int end){//this recursiv method is calculating the area of the water in the landscape.O(n)
		if (beg >= tab.length-1)return 0;
		int somme = 0;
		for (int i=beg+1;i<end;i++){// n times 
			if (tab[beg]<tab[end]){
				somme+= tab[beg]-tab[i];
			}
			else{ 
				somme+= tab[end]-tab[i];
			}
		}	
		return somme+area(tab,end,nextIndiceTrue(maxLoc(tab),end));//repeat maximal 9 times
	}
	
	public static int nextIndiceTrue (boolean[] tab,int current){// this method is searching for the important levels that are retaining water.O(n)
		if (tab==null)return-1;
		if (current >= tab.length-1)return current;
		int i= current+1;
		while (tab[i]!=true && i!=tab.length)i++;// n times O(1)
		return i;
	}
	
	public static void buildScreen(int[] tab,int max){//this method generate the landscape O(n)
		char[] line = new char[tab.length*2];
		if (max!=0){			
			for (int i= 0; i<(tab.length*2);i++){ //Initialization of a Line O(n)
				if(i%2==0){							//define what to show in a colon, the number or the '_'
				line[i]= 
					(tab[i/2]==max)?'_':
					(tab[i/2]==max+1)?intToChar(max+1):' ';
				}
				else{							//from here , define when to show the '|'
					if(i<(tab.length*2)-1){
						line[i]=
								(((tab[(i-1)/2])>max && (tab[(i+1)/2])<=max)||(tab[(i-1)/2])<=max && (tab[(i+1)/2])>max)?
								'|':' ';
					}
					else if ((tab[tab.length-1])> max )line[i]='|';// define the right edge of the mountain
					else line [i]=' ';
				}
			}
			for(char b: line ){// O(n)
				System.out.print(b);
			}
			System.out.println();
			buildScreen(tab, max-1); //call for the next line (maximal 9 times)
			}
		
		else groundScreen(tab); //condition of the end of the recurcivity
	}
	
	public static void groundScreen(int[] tab){//generation of the indices under the levels.O(n)
		for (int t= 0; t<(tab.length*2-1);t++){ // this is the generation of the level where you can see the 1 level or some bars
			System.out.print((tab[t/2]!=1 || t%2!=0)?'_':'1');//O(n)
		}
		System.out.println('|');// this is the generation of the last edge of the mountain on the left
		int n = 0;
		for (int t= 0; t<(tab.length);t++){//this is the generation of the indice under the level
				System.out.print(  ((n>=0)&&(n<10))? " "+n:(n%100<10)? "0"+n%100:n%100);//in case of indices greater than 100, it will just indicate the two first numbers.
				//the goal is that the indices are following the levels, and as you can see, it finishes at the same place
				n++;
		}
	}
	
	public static int[] rdmPics(){//this method is generating an array of random numbers between 1 and 9 of the length you want 
		int n=-1;
		int[]result;
		
		System.out.println("Enter the number of pics (levels in the landscape) you want to generate randomly between 0 and 100:");
		System.out.println("if you chose more than 100, the result of the area of water will still be ok");
		System.out.println("if you chose more than 100, the  indice>100 are showing only the two first digits");
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		sc.close();
		if(n>0){ //here I want to verify that the people enter a good value
			result = new int[n];
			for(int r=0;r<n;r++) result[r] = (int)(9*Math.random()+1);
			return result;
		}
		else{
			System.out.println("sorry but have entered something wrong");
			result = new int[1];//here is a default generation in case of a wrong value
			result[0]=1;
			return result;
		}
		
	}

	public static boolean[] maxLoc(int [] tab){//This function is searching for the place of the pics that are important to calculate the area of the water.
		if (tab==null)return null;
	
		boolean[] result = new boolean[tab.length];
		//initialization of the array of boolean:
		for(int i=0;i<tab.length;i++){
			result[i]=false;}
		int max= maxArray(tab);
		//initialization of the borders 
		int sup= tab.length;
		int inf = 0;
		//searching for the max from the left
		while (max>0 || sup!=0){
			for(int i=0;i<sup;i++){
				//System.out.println("for" + i);
				if(tab[i]==max){
					result[i]=true;
					sup = i;
					//System.out.println(" if de " + i +" i " + result[i]);
					break;
				}
			}
			max--;
		}
		// for the right side
		max= maxArray(tab);
		while (max>0 || inf!=tab.length-1){
			for(int i=tab.length-1;i>inf;i--){
				//System.out.println("for" + i);
				if(tab[i]==max){
					result[i]=true;
					inf = i;
					//System.out.println(" if de " + i +" i " + result[i]);
					break;
				}
			}
			max--;
		}
		return result;
	}
		
	public static int maxArray(int[] tab){//this method is searching for the highest number in an array O(n)(I need it to determinate the highest point of the landscape).
		int max = tab[0];
		for(int i : tab){// n times maximal
			if (max<i)max=i;
		}
		return max;
	}

	public static char intToChar(int i){
        String s = ""+i;
        return s.charAt(0);
    }


}
