package schoologygraph;
/**
* The Path class stores a path between two nodes, represented by the starting node and the name of the edge connection.
*
* @author  Suhas Hariharan
* @version 1.0
* @since   2020-09-21
*/

public class Path {
    private String startingNode;
    private String edgeName;
    /**
    * Constructor, takes in a starting node and an edge name.
    *  @param startingNode a string representing the starting node, would be in the form of a schoology student id
    *  @param edgeName a string representing the edge name, would be in the form of a class that students share in common.
    */
    public Path(String startingNode, String edgeName) {
        this.startingNode = startingNode;
        this.edgeName = edgeName;
    }
    /**
    * Constructor, takes in a starting node and an edge name.
    * @return startingNode the starting node for this path, a schoology student id.
    */
    public String getStartingNode() {
        return this.startingNode;
    }
    /**
    * Constructor, takes in a starting node and an edge name.
    * @return edgeName the edge name for this path, a schoology course name.
    */
    public String getEdgeName() {
        return this.edgeName;
    }
    public String toString() {
        return this.startingNode+":"+this.edgeName;
    }
  
}
