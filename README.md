# Graph Representation System and Graph Navigation Game

The graph representation and graph navigation system is actually 2 OOP projects.  
**Graph Representation System** - The system allows the user to construct directed graphs and manipulate the graphs using graph algorithms. The whole system support UI.  
**Graph Navigation Game** - Game with maps from Ariel city in Israel (the maps represented as graphs), robots and fruits on them.  
The goal of this game is to eat as many fruits as possible in the given time (30 or 60 seconds).  

### How to run the program
1. Download the project to your computer as a Zip.
2. Open Eclipse.
3. Click: File->Open Project from File System or Archive.
4. Click: Archive..
5. Choose the Zip file you have downloaded earlier (Step 1).
6. Run "MainGUI" as Java Application.
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

## Graph Navigation System
![](https://github.com/dorlevi121/ex4/blob/master/readmeIMG/2.jpg)  
The game has 2 modes:

* **Automatic**
* **Manual**  

The goal of the automatic mode is to eat as much fruit as possible - for this goal,   I used the algorithms from Graph Representation System (Dijkstra, shortest path,   etc).  The manual mode the user can play by himself with a mouse click on the robot he wants to move and another click on the vertex that the robot will move to (Provided the vertices are neighboring).  
Another option that I implemented its to export a KML file at the end of the game.
When the game is to finish the system ask the user if he wants to save a KML file.
In case and the user click yes. the system gives it to him a KML file and then the user can upload the KML file to  [Google Earth](https://earth.google.com) and see the original map with all him moves.
.  
This tuturiel will help you to upload the KML file to google earth:  
https://support.google.com/earth/answer/7365595?co=GENIE.Platform%3DDesktop&hl=en  

![](http://i.picasion.com/pic89/e359fd06a3f0840b5a0ceafd05e9a042.gif)  

Another thing that I implemented in the project is connecting to the database.
When the user starts a new game he can enter his ID anf connect to the server.
when the game is finish the score of the game and tie moves number saved in the database.  

![](http://i.picasion.com/pic89/442ec6e5581e1df88dd4e8ffccaa776d.gif)

The user has the following options:  
* Check its location relative to other users (for each step)
* How many times he played
* What is its maximum result
The user cannot move to the next level until he reaches the desired minimum score (as shown in the table below).
Another think that i implemented in the project is comecting to database  

![](http://i.picasion.com/pic89/373ae5a1a9ac482a463df7fce05a15d2.gif)  

![](http://i.picasion.com/pic89/9e218b88a1a0d5d1f687cdc0d13823fb.gif)  

|  Stage |Case   |Grade   |Moves   |
| ------------ | ------------ | ------------ | ------------ |
|  0 |0  |145   |290   |
| 1  | 1 |450   |580   |
|  3 |  2|720   |580   |
|   5|  3|570   |500   |
|   9|  4|510   |580   |
| 11|  5|1050 |580   |
| 13|  6|310   |580   |
| 16|  7|235   |290   |
| 19|  8|250   |580   |
| 20|  9|200   |290   |
| 23|11|1000  |1140   |
