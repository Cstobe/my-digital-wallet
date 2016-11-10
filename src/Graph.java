import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Graph {
    private ArrayList<Integer> vertices;
    private Map<Integer, Set<Integer>> adjacency;
    private int totalEdges;
    
    /**  
     * Initializes a graph from the specified input file.
     * @param  inputfile contains the information to generate the graph
     */
	public Graph(String inputfile) {
    	try {
    		vertices = new ArrayList<Integer>();
    		totalEdges = 0;
    		adjacency = new HashMap<Integer, Set<Integer>>();
    		BufferedReader br = new BufferedReader(new FileReader( inputfile ));
            String line = br.readLine();
            while (line != null) {
            	String[] tokens = line.split(", ");
                if (tokens.length>3 && tokens[1].matches("[0-9]+") && tokens[2].matches("[0-9]+")) {
                	int v1 = Integer.parseInt(tokens[1]);
                	int v2 = Integer.parseInt(tokens[2]);
                	// add vertex in the graph
					addVertex(v1);
                	addVertex(v2);
					// add edge in the adjacency list representation of graph
					addEdge(v1, v2);
                } else 
					System.err.println("[Warning]:: in file "+inputfile+" unable to process line: "+line);
                line = br.readLine();
            }
            br.close();
        }
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    }

	/**
     * Add new vertex in the graph
     * @param vertex
	 * @return boolean if the vertex was added
     */
    public boolean addVertex(int v) {
    	if (vertices.contains(v))
			return false;
		else
			return vertices.add(v);
    }
    
	/**
     * Add new edge in the adjacency list representation
	 * @param pair of vertex
     */
    public void addEdge(int v1, int v2) {
    	Set<Integer> tmp;
		if (adjacency.containsKey(v1))
			tmp = adjacency.get(v1);
		else
			tmp = new HashSet<Integer>();
		tmp.add(v2);
		adjacency.put(v1, tmp);
		
		if (adjacency.containsKey(v2))
			tmp = adjacency.get(v2);
		else
			tmp = new HashSet<Integer>();
		tmp.add(v1);
		adjacency.put(v2, tmp);
		
		totalEdges++;
    }
	
    /**
     * Returns the number of vertices in this graph.
     * @return the number of vertices in this graph
     */
    public int V() {
        return vertices.size();
    }

    /**
     * Returns the number of edges in this graph.
     * @return the number of edges in this graph
     */
    public int E() {
        return totalEdges;
    }
    
    /**
     * Check if there is a path with length less than limit between start and end vertex using Breath First Search algorithm
     * @return boolean
     * @param start the start vertex
     * @param end the end vertex
     * @param limit the limit for the path
     */
    public boolean findPathBFS(int start, int end, int limit) {
	    // Initialize
    	HashMap<Integer, Integer> pathListA = new HashMap<Integer, Integer>();
    	int maxLengthListA = 0;
    	HashMap<Integer, Integer> pathListB = new HashMap<Integer, Integer>();
    	int maxLengthListB = 0;
	    pathListA.put(start,0);
	    pathListB.put(end,0);
	    ArrayDeque<Integer> queueA = new ArrayDeque<Integer>(); 
	    ArrayDeque<Integer> queueB = new ArrayDeque<Integer>(); 
	    queueA.add(start);
	    queueB.add(end);
	    while ( queueA.size()>0 && queueB.size()>0 ) {
	    	int v1 = queueA.poll();
	    	if (vertices.contains(v1)) {
		    	for (int w : adjacency.get(v1)) {
		    		if (pathListB.containsKey(w))
		        		// finish
		        		return true;
		    		else {
		        		// insert 
		    			int lengthA = pathListA.get(v1) + 1;
		    			if (lengthA > maxLengthListA)
		    				maxLengthListA = lengthA;
		        		if ( (lengthA + maxLengthListB) < limit ) {
		        			if (!pathListA.containsKey(w)) {
		        			    pathListA.put(w, lengthA);
		        			    queueA.add(w);
		        			}
							continue;
		        		} else
		        			return false;
		        	}
		        }
	    	} else 
	    		return false;
	    	
	    	int v2 = queueB.poll();
	    	if (vertices.contains(v2)) {
		    	for (int w : adjacency.get(v2)) {
		        	if ( pathListA.containsKey(w) )
		        		// finish
		        		return true;
		    		else {
		        		// insert 
		    			int lengthB = pathListB.get(v2) + 1;
		    			if (lengthB > maxLengthListB)
		    				maxLengthListB = lengthB;
		        		if ( (lengthB + maxLengthListA) <= limit ) {
		        			if (!pathListB.containsKey(w)) {
		        			    pathListB.put(w, lengthB);
		        			    queueB.add(w);
		        			}
							continue;
		        		} else {
		        			return false;
		        		}
		        	}
		        }
	    	} else {
	    		return false;
	    	}
	    }
	    return false;
    }
	
	/**
     * Returns a string representation of this graph.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V() + " vertices, " + E() + " edges " + System.getProperty("line.separator"));
        for (int i = 0; i < V(); i++) {
            s.append(vertices.get(i) + ": ");
            for (int w : adjacency.get(vertices.get(i))) {
                s.append(w + " ");
            }
            s.append(System.getProperty("line.separator"));
        }
        return s.toString();
    }
}