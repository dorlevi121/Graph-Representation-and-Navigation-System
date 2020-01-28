# Graph representation and graph navigation system

The graph representation and graph navigation system is actually 2 OOP projects.  
**Graph Representation System** - The system allows the user to construct directed graphs and manipulate the graphs using graph algorithms. The whole system support UI.  
**Graph Navigation Game** - Game with maps from Ariel city in Israel (the maps represented as graphs), robots and fruits on them.  
The goal of this game is to eat as many fruits as possible in the given time (30 or 60 seconds).  

![](https://github.com/dorlevi121/ex4/blob/master/readmeIMG/1.jpg)

## Graph Representation System

### Features
- Create and add vertices;
- Create and add edges;
- Build a directed and weighted graph;
- Initialize a graph from file;
- Saves the graph to a file.;
- Saves the graph as a picture.;
- Check if the graph is connected;
- Check the length of the shortest path between 2 vertices;
- Computes a relatively short path which visit each node in the a list;

![](https://github.com/dorlevi121/ex4/blob/master/readmeIMG/notConnected.jpg)

### Classes

#### Node (-)
This class represents a vertex in a weighted directed graph.
The node class implementation the node_data interface.
Class variables:
- key (Vertex Number)
- location (Location of vertex n graph)
- weight
     
#### Edge (-)
This class represents a edge in a weighted directed graph.
The edge class implementation the edge_data interface.
* Node src (Source Vertex)
* Node dest (Destination Vertex)
* weight(Weight of edge)

#### DGraph (-)
This class represents a weighted directed graph.
The DGraph class implementation the graph interface.
* vertices (Collection of all the graph vertices)
* edges (Collection of all the graph edges)
* numOfEdges (Number of edges in the graph)

#### Graph_Algo (-)
This class represents a "regular" Graph Theory algorithms.
The Graph_Algo class implementation the graph_algorithms interface.
* g (DGraph type graph)
* init(String file_name) - Initialize a graph from file.
* save(String file_name) - Save the graph.
* isConnected() - Check if the graph is connected.
* shortestPathDist(int src, int dest) - Check the length of the shortest path between 2 vertices.
* shortestPath(int src, int dest) - Return the shortest path between 2 vertices.


