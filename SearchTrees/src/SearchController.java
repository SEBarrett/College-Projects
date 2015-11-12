import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;

public class SearchController {
	
	public static int startNode; 
	public static Hashtable<Integer, ArrayList<Integer>> adjacencyList;
	public static Hashtable<Integer, Boolean> discoverableNodes;
	
	public void run(){
		ArrayList<Integer> uniqueNodes = new ArrayList<Integer>();
		discoverableNodes = new Hashtable<Integer, Boolean>();
	    adjacencyList = new Hashtable<Integer, ArrayList<Integer>>();
		ArrayList<Integer> nodeList = new ArrayList<Integer>();
		ArrayList<Integer> values;
		BufferedReader read = null;
		String thisLine = null;
		String key;
		String value;
		int one;
		int two;
		startNode = 0;
		
		try{
			read = new BufferedReader(new FileReader("edges.txt"));
			//read = new BufferedReader(new FileReader("test.txt"));
			while((thisLine = read.readLine()) != null){
				String[] part = thisLine.split(" ");
				key = part[0];
				value = part[1];
				one = Integer.parseInt(key);
				two = Integer.parseInt(value);
				
				nodeList.add(one);
				nodeList.add(two);
				
				if(startNode == 0){
					startNode = one;
				}
				
				if(uniqueNodes.contains(one) == false)
					uniqueNodes.add(one);
			
				if(uniqueNodes.contains(two) == false)
					uniqueNodes.add(two); 
				
				if(adjacencyList.containsKey(one)){
					adjacencyList.get(one).add(two);
				}
				else{
					values  = new ArrayList<Integer>();
					values.add(two);
					adjacencyList.put(one, values);		
				}
				
				if(adjacencyList.containsKey(two)){
					adjacencyList.get(two).add(one);
				}
				else{
					values  = new ArrayList<Integer>();
					values.add(one);
					adjacencyList.put(two, values);		
				}
				
				
				discoverableNodes.put(one, false);
				discoverableNodes.put(two, false);
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
	
		}
		//printAdjacencyList(adjacencyList);
		//System.out.println("Start Node: " + startNode);
		
	}
	
	public static void printAdjacencyList(Hashtable<Integer, ArrayList<Integer>> list){
		System.out.println("Graph");
		for(int v: list.keySet()){
			System.out.println("[" + v + "] ");
			ArrayList<Integer> adjVerticies = list.get(v);
			if(adjVerticies != null){
				for(int u : adjVerticies){
					System.out.print(u + " ");
				}
			}
			System.out.println();
		}
	
	}
	
	public int findStart(){
		return startNode;
	}
	
	public Hashtable<Integer, ArrayList<Integer>> findAdjacencyList(){
		return adjacencyList;
	}
	public  Hashtable<Integer, Boolean> findDiscoverableNodes(){
		return discoverableNodes;
	}
	
	
}
