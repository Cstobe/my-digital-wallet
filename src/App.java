import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        if (args.length < 3) {
			System.out.println("Please provide at least the following three arguments:");
			System.out.println("  First argument is batch_payment.txt, used to build to graph of relations");
			System.out.println("  Second argument is stream_payment.txt, used to verify the relations in the graph");
			System.out.println("  Third argument is paymo_output, used to write out the verification results");
			System.out.println("If you want to grow the graph with new vertices and edges in stream_payment, please provide the fourth argument as <True>");
		}
		
        System.out.println("[Info]:: The file to build the graph is: " + args[0]);
        System.out.println("[Info]:: The file to process is: " + args[1]);
		System.out.println("[Info]:: The output files write to is: " + args[2]);
		
		boolean expand=false;
		if (args.length==4 && args[3]=="True") {
			System.out.println("[Info]:: grow the graph with new vertices and edges in stream_payment.");
			expand=true;
		}
        
		System.out.println("[Info]:: Reading the batch_payment file to build the graph...");
        Graph g = new Graph(args[0]);       // initialize graph using batch_payment
		// System.out.println(g);			// print graph
		
        System.out.println("[Info]:: The number of vertices in the graph: " + g.V());
        System.out.println("[Info]:: The number of edges in the graph: " + g.E());
        
		System.out.println("[Info]:: Reading the stream_payment file to process...");
        try{
			PrintWriter writer1 = new PrintWriter(args[2]+"/output1.txt");
			PrintWriter writer2 = new PrintWriter(args[2]+"/output2.txt");
			PrintWriter writer3 = new PrintWriter(args[2]+"/output3.txt");

	        BufferedReader br = new BufferedReader(new FileReader(args[1]));
	        String line = br.readLine();
	        while (line != null) {
	        	String[] tokens = line.split(", ");
	            if (tokens.length>3 && tokens[1].matches("[0-9]+") && tokens[2].matches("[0-9]+")) {
	            	int v1 = Integer.parseInt(tokens[1]);
	            	int v2 = Integer.parseInt(tokens[2]);
					
					if (g.findPathBFS(v1, v2, 1)){
						writer1.println("trusted");
					} else {
						writer1.println("unverified");
					}
					if (g.findPathBFS(v1, v2, 2)) {
						writer2.println("trusted");
					} else {
						writer2.println("unverified");
					}
					if (g.findPathBFS(v1, v2, 4)) {
						writer3.println("trusted");
					} else {
						writer3.println("unverified");
					}
					
					if (expand) { // grow the graph with new vertices and edges
						g.addVertex(v1);
						g.addVertex(v2);
						g.addEdge(v1, v2);
					}
	            } else 
					System.err.println("[Warning]:: in file "+args[1]+" unable to process line: "+line);
	            line = br.readLine();
	        }
			writer1.close();
			writer2.close();
			writer3.close();
	        br.close();
        }
	    catch (IOException e) {
		    e.printStackTrace();
	    }
    }
}
