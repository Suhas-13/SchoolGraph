/**
 *
 * @author suhas
 * @version 1.0
 * @since   2020-09-21
 *  The DirectedGraphVisual calculates the string length on each end and uses that to draw the graph, it stores nodes and edges to accomplish this as well.
 */
package schoologygraph;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DirectedGraphVisual extends JFrame {
    int width;
    int height;
    ArrayList<Node> nodes;
    ArrayList<edge> edges;

    public DirectedGraphVisual() { //Constructor
	nodes = new ArrayList<Node>();
	edges = new ArrayList<edge>();
	width = 30;
	height = 30;
    }


    class Node {
	int x, y;
	String node_name;
	public Node(String node_name, int nodeX, int nodeY) {
	    x = nodeX;
	    y = nodeY;
	    this.node_name = node_name;
	}
    }
    
    class edge {
	int first_node, second_node;
        String path_name;
	public edge(String path_name, int first_node, int second_node) { 
            this.path_name = path_name;
	    this.first_node = first_node;
            this.second_node = second_node;	    
	}
    }
    
    public void addNode(String node_name, int x, int y) { // adds node
	nodes.add(new Node(node_name,x,y));
    }
    public void addEdge(String path_name, int first_node, int second_node) { // adds edge
	edges.add(new edge(path_name, first_node, second_node));
    }
    
    public void paint(Graphics g) { // paints the nodes / edges on the frame.
	FontMetrics f = g.getFontMetrics();
	int nodeHeight = Math.max(height, f.getHeight());
	g.setColor(Color.black);
	for (edge e : edges) {
	    g.drawLine(nodes.get(e.first_node).x, nodes.get(e.first_node).y,
		     nodes.get(e.second_node).x, nodes.get(e.second_node).y);
            int edge_name_length = f.stringWidth(e.path_name);
            g.drawString(e.path_name, (nodes.get(e.first_node).x+f.stringWidth(nodes.get(e.first_node).node_name)+20),
			 nodes.get(e.first_node).y-15);
	}

	for (Node n : nodes) {
	    int nodeWidth = Math.max(width, f.stringWidth(n.node_name)+width/2);
	    g.setColor(Color.white);
	    g.fillOval(n.x, n.y-nodeHeight/2, 
		       nodeWidth, nodeHeight);
	    g.setColor(Color.black);
	    g.drawOval(n.x, n.y-nodeHeight/2, 
		       nodeWidth, nodeHeight);
	    g.drawString(n.node_name, n.x+7,
			 n.y+f.getHeight()/2);
	}
    }
}

