import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class BFSearch {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		int startNode;
		//String type;
		Hashtable<Integer, ArrayList<Integer>> adjacencyList;
		Hashtable<Integer, Boolean> discoverableNodes;
		Hashtable<Integer, ArrayList<Integer>> tree = new Hashtable<Integer, ArrayList<Integer>>();
		SearchController controller = new SearchController();
		controller.run();
		startNode = controller.findStart();
		adjacencyList = controller.findAdjacencyList();
		discoverableNodes = controller.findDiscoverableNodes();
		//type = "Adjacency List";
		//printAdjacencyList(adjacencyList, type);
		//System.out.println("Discovered Nodes: " + discoverableNodes);
		//System.out.println("Start Node: " + startNode);

		System.out.println("Breadth First Search");
		tree = constructBFSTree(adjacencyList, startNode, discoverableNodes);
		//type = "Breadth First Search Tree";
		//printAdjacencyList(tree , type);
		searchTree(tree, adjacencyList, startNode, discoverableNodes);
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
		System.out.println("Total running time [min:sec:millisec] " + formatter.format(totalTime));
		
	}


	public static void printAdjacencyList(Hashtable<Integer, ArrayList<Integer>> list, String type){
		System.out.println(type + ":");
		for(int v: list.keySet()){
			System.out.print("[" + v + "] ");
			ArrayList<Integer> adjVerticies = list.get(v);
			if(adjVerticies != null){
				for(int u : adjVerticies){
					System.out.print(u + " ");
				}
			}
			System.out.println();
		}
	}
	public static Hashtable <Integer, ArrayList<Integer>> constructBFSTree (Hashtable <Integer, ArrayList<Integer>> adjacencyList, int startNode, Hashtable<Integer, Boolean> discoverableNodes) {
		int currentChild;
		int node;
		ArrayList<Integer> connectedElements = new ArrayList<Integer>();
		ArrayList<Integer> parentArray;
		Hashtable<Integer, Integer> parent = new Hashtable<Integer, Integer>();
		Queue <Integer> queue = new ArrayDeque<Integer>();
		Hashtable<Integer, ArrayList<Integer>> tree = new Hashtable<Integer, ArrayList<Integer>>();
		
		discoverableNodes.put(startNode, true);
		queue.add(startNode);
		
		while(!queue.isEmpty()){
			node = queue.remove();
			//System.out.println(adjacencyList.get(node));
			try{
				if(discoverableNodes.containsKey(node) == false){
					System.out.println("Can not find node in discovered");
					System.out.println(node);
					break;
				}//ENDIF
				if((adjacencyList.get(node) == null)){
					
				}//ENDIF
				else{
					connectedElements = adjacencyList.get(node);
					//System.out.println("Current Node: " + node + " Connected Nodes: " + connectedElements);
					for(int i = 0; i < connectedElements.size(); i ++){
						currentChild = connectedElements.get(i);
						//System.out.println("Looking at node: " + currentChild);
						//System.out.println("This node is discovered: " + discoverableNodes.get(currentChild));
						if(discoverableNodes.get(currentChild) == false){
							discoverableNodes.put(currentChild, true);
							queue.add(currentChild);
							parentArray = new ArrayList<Integer>();
							parentArray.add(node);
							tree.put(currentChild, parentArray);
						}
					}//ENDFOR
				}	
			}catch(Exception e){
				e.printStackTrace();
			}
		}//ENDWHILE
		//System.out.println("Reached end of tree construction");
		return tree;
		
	}//END constructTree

	
	public static void searchTree(Hashtable <Integer, ArrayList<Integer>> tree,Hashtable <Integer, ArrayList<Integer>> adjacencyList, int startNode, Hashtable<Integer, Boolean> discoverableNodes){
		Scanner keyboard = new Scanner(System.in);
		Queue <Integer> queue = new ArrayDeque<Integer>();
		ArrayList<Integer> connectedElements = new ArrayList<Integer>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		Set<Integer> keys = discoverableNodes.keySet();
		int node;
		int currentChild;
		boolean found = false;
		int searchNode = 0;
		int nodesTraversed = 1; 
		
		for(Integer key: keys){
			discoverableNodes.put(key, false);
	    }
		
		queue.add(startNode);
		
		while(searchNode == 0){
			System.out.print("Please enter the id (integer) to search for: ");
			if(keyboard.hasNextInt()){
				searchNode = keyboard.nextInt();
			}//ENDIF
			if(searchNode <= 0){
				searchNode = 0;
			}//ENDIF
		}//ENDWHILE
		
		while(!queue.isEmpty()){
			node = queue.remove();
			//System.out.println("Popping: " + node);
			//System.out.println("Discovered: " + discoverableNodes.get(node));
			try{
				if(discoverableNodes.containsKey(node) == false){
					System.out.print("Can not find node in discoverableNodes: ");
					System.out.println(node);
					break;
				}//ENDIF
				
				if(node == searchNode){
					found = true;
					path.add(node);
					break;
				}//ENDIF
				if(discoverableNodes.get(node) == false){
					discoverableNodes.put(node,  true);
					nodesTraversed ++;
					path.add(node);
					if((adjacencyList.get(node)) == null){
						//bad
					}else{
						connectedElements = adjacencyList.get(node);
						//System.out.println(connectedElements);
						for(int i = 0; i < connectedElements.size(); i++){
							currentChild = connectedElements.get(i);
							queue.add(currentChild);
						}//ENDFOR
					}//ENDELSE
				}//ENDIF
			}catch(Exception e){
				e.printStackTrace();
			}//ENDCATCH
		}//ENDWHILE
		if(found == true){
			System.out.println("Node was found.");
			System.out.println("Number of vertices traversed: " + nodesTraversed);
			System.out.println("Path: " + path);
		}//ENDIF
		else{
			System.out.println(searchNode + " node was not found.");
			System.out.println("Number of vertices traversed: " + nodesTraversed);
			System.out.println("Path: " + path);
		}
	}
}
