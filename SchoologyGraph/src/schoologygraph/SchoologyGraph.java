/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoologygraph;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.*;

/**
* The SchoologyGraph object represents a graph of the students and classes in Schoology from a scraped data set. 
* It stores a JSONObject of nodes, a JSONObject representing a mapping between students and student ID's(and vice versa), a teacher list as well as a visited/previous list for graph traversals.
* @author  Suhas Hariharan
* @version 1.0
* @since   2020-09-21
*/
public class SchoologyGraph {

   
    private JSONObject nodes;
    private JSONObject student_lookup;
    private ArrayList<String> teacher_list;
    private ArrayList<String> visited;
    private Map<String,Path> previous;
     /**
     * @param graph_filename The filename of the graph text file, contains a JSON representation of the Schoology Graph.
     * @param student_filename The filename of the student list, contains a JSON representation of a mapping between student ID's and names.
     * @param teacher_filename The filename of the teacher list, contains a list of teacher ID's.
     * @throws org.json.JSONException
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public SchoologyGraph(String graph_filename, String student_filename, String teacher_filename) throws FileNotFoundException, IOException, JSONException {
        readGraph(graph_filename);
        readStudentList(student_filename);
        readTeacherList(teacher_filename);
    }
    /**
     * @param filename The filename of the graph.
     * @throws org.json.JSONException
     * @throws java.io.IOException
     * Effect, reads in the JSON content and stores it as nodes.
     */
    public void readGraph(String filename) throws FileNotFoundException, IOException, JSONException  {
       String content = new String(Files.readAllBytes(Paths.get(filename)));
       JSONObject obj = new JSONObject(content);
       this.nodes=obj;
    }
    /**
     * @param filename The filename of the student list.
     * @throws org.json.JSONException
     * @throws java.io.IOException
     * Effect, reads in the JSON content and stores it as student_lookup.
     */
    public void readStudentList(String filename) throws FileNotFoundException, IOException, JSONException {
       String content = new String(Files.readAllBytes(Paths.get(filename)));
       JSONObject obj = new JSONObject(content);
       this.student_lookup=obj;
    }
    /**
     * @param filename The filename of the teacher list.
     * @throws org.json.JSONException
     * @throws java.io.IOException
     * Effect, reads in the list and stores it as a list teach_list.
     */
    public void readTeacherList(String filename) throws FileNotFoundException, IOException, JSONException {
       String content = new String(Files.readAllBytes(Paths.get(filename)));
       JSONArray obj = new JSONArray(content);
       ArrayList<String> teach_list = new ArrayList<String>();
       for (int i=0; i<obj.length(); i++) {
           teach_list.add(obj.getString(i));
       }
       this.teacher_list=teach_list;
    }
    
    /**
     * @param student_id The Schoology student ID of the student in question
     * @return student_name The student's real name.
     * @throws org.json.JSONException
     */
    public String getStudentNameFromId(String student_id) throws JSONException {
        return (String) student_lookup.get(student_id);
    }
    /**
     * @param student_name The student's real name.
     * @throws org.json.JSONException
     * @return student_id The Schoology student ID of the student in question
     */
    public String getIdFromStudentName(String student_name) throws JSONException {
        return (String) student_lookup.get(student_name);
    }
    /**
     * @param student1 The student representing the end of the original graph traversal.
     * @param student2 The next student in the graph traversal.
     * @return final_path An array list of Path objects representing the shortest path from the initial student2 to student1.
     * @throws org.json.JSONException
     * Recursively reconstructs the shortest path from the previous list, terminates upon student1 equalling student2.
     */
    public ArrayList<Path> reconstructPath(String student1, String student2, ArrayList<Path> final_path) throws JSONException {
        if (student1 == student2) {
            return final_path;
        }
        if (!previous.containsKey(student2)) {
            return final_path;
        }
        Path current_path = previous.get(student2);
        final_path.add(0,current_path);
        return reconstructPath(student1, current_path.getStartingNode(), final_path);
    }
    /**
     * @param student1 The first student.
     * @param student2 The second student, representing the final node in the traversal.
     * @param include_teachers A boolean determining whether to include teachers or not in the graph traversal.
     * @return final_path A list of Path objects representing the shortest path in order. 
     * @throws org.json.JSONException
     * Begins the BFS and returns the reconstructed path at the end.
     */
    public ArrayList<Path> findShortestPath(String student1, String student2, boolean include_teachers) throws JSONException {
        visited = new ArrayList<String>();
        previous = new HashMap<String,Path>();
        Queue<String> queue = new ArrayDeque();
        queue.add(student1);
        bfs(student2, queue, include_teachers);
        String new_student = student2;
        ArrayList<Path> final_path = new ArrayList<Path>();
        return reconstructPath(student1, student2, final_path);
    }
     /** 
     * @param student2 The current student, starting node at this point of the search.
     * @param student_queue A queue of remaining students in the current level of the traversal.
     * @param include_teachers A boolean representing whether or not to include teachers in the traversal.
     * A breadth-first-search, beginning at the current student(given in student_queue) and expanding until it reaches student2, storing the previous student at each point to allow for path reconstruction.
     */
    private void bfs(String student2, Queue<String> student_queue, boolean include_teachers) throws JSONException {
        if (student_queue.isEmpty()) {
            return;
        }
        String starting_student=student_queue.poll();
        if (starting_student==student2) {
            return;
        }
        
        JSONObject student_obj = nodes.getJSONObject(starting_student);
        if (student_obj == null) {
            return;
        }
        JSONArray edge_list = student_obj.names();
        for (int i=0; i<edge_list.length(); i++) {
            if (include_teachers==false && teacher_list.contains(edge_list.getString(i))) {
            }
            else if (!visited.contains(edge_list.getString(i))) {         
                JSONArray edge_type = student_obj.getJSONArray(edge_list.getString(i));
                String temp_id = edge_list.getString(i);
                visited.add(temp_id);
                String edge_name = (String) edge_type.get(0);
                previous.put(temp_id, new Path(starting_student, edge_name));
                student_queue.add(temp_id);  
            }
        }
        bfs(student2, student_queue, include_teachers);     
    }
    
}
