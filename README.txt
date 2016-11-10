*** My solution ***
In general, the problem is solved using the graph traversal analysis, 
  in particular, the breath first search (BFS) to find the shortest path between any given pair of nodes.
  
Each payment transactions in batch_payment is used to build a undirected relation between two users, and we can build a huge user network using the entire payment history.
Each user id is a vertex in the graph, and the undirected link between users are stored in the adjacency list.
I chosen adjacency list since the time complexity for BFS is about O(V+E), where V is the number of vertex and E is the number of Edge.
Another graph representation is adjacency matrix, which may yield time complexity as O(V^2) for BFS, much slower for this large graph.
  
*** Additional Feature to consider ***  
One additional feature to add, which is also optional, is that the stream_payment data can also be considered as part of batch_payment.
So we can consider the user network is growing as new payment transactions coming in. This feature is also implemented.




