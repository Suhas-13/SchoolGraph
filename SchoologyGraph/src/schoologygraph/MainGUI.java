
package schoologygraph;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.json.JSONException;

/**
 *
 * @author suhas
 * @version 1.0
 * @since   2020-09-21
 *  The Main GUI class for this program, handles the input and calling / creating SchoologyGraph objects. 
 */
public class MainGUI extends javax.swing.JFrame {
    private static SchoologyGraph graph;
    private static Graphics g;
    public MainGUI() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        student2_name = new javax.swing.JTextField();
        student1_name = new javax.swing.JTextField();
        generate_graph = new javax.swing.JButton();
        include_teachers = new javax.swing.JCheckBox();
        header_text = new javax.swing.JLabel();
        paragraph_text = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        student2_name.setText("Student #2");
        student2_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student2_nameActionPerformed(evt);
            }
        });

        student1_name.setText("Student #1");
        student1_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                student1_nameActionPerformed(evt);
            }
        });

        generate_graph.setText("Generate Graph");
        generate_graph.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generate_graphMouseClicked(evt);
            }
        });

        include_teachers.setText("Include Teachers");

        header_text.setText("Schoology Graph Program");

        paragraph_text.setText("<html>Welcome to the Schoology Graph Program by Suhas Hariharan.  It attempts to identify the shortest path between two given students, in the two fields below upon pressing the \"Generate Graph\" button.<br> To do this is treats students as nodes and any 2 students sharing a class as an edge. So if you are in the same math class as someone it'll represent that as an edge.<br> This data is scraped from Schoology, as such it is not perfect as about 10% of students have private profiles and as such are not part of this graph.  </html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(student1_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154)
                        .addComponent(include_teachers))
                    .addComponent(generate_graph))
                .addGap(144, 144, 144)
                .addComponent(student2_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(paragraph_text, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(header_text)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(header_text)
                .addGap(29, 29, 29)
                .addComponent(paragraph_text, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(include_teachers)
                    .addComponent(student1_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(student2_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(generate_graph)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void student2_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student2_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_student2_nameActionPerformed

    private void student1_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_student1_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_student1_nameActionPerformed

    private void generate_graphMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generate_graphMouseClicked

        DirectedGraphVisual frame = new DirectedGraphVisual();
        try {
            String student1=student1_name.getText();
            String student2=student2_name.getText();
            ArrayList<Path> paths = new ArrayList<Path>();
            paths = graph.findShortestPath(graph.getIdFromStudentName(student1),graph.getIdFromStudentName(student2),include_teachers.isSelected());
            if (paths.size() == 0) {
                JOptionPane optionPane = new JOptionPane("JSON Error, Path can't be determined or input error, please check spelling and capitalization. Please note that this program only has access to about 90% of students at SAS due to private Schoology profiles and as such is not 100% accurate", JOptionPane.ERROR_MESSAGE);   
                JDialog dialog = optionPane.createDialog("Failure");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                System.out.println("JSON Error, Path can't be determined or input error, please note that this program only has access to about 90% of students at SAS due to private Schoology profiles and as such is not 100% accurate");
            }
            else {
              int current_x_position = 125;
              frame.setVisible(true);
              frame.addNode(student1, current_x_position, 150);
              FontMetrics fontMetrics = frame.getGraphics().getFontMetrics();
              current_x_position+=fontMetrics.stringWidth(student1);            
              for (int i=1; i<paths.size(); i++) {
                current_x_position += ((fontMetrics.stringWidth(paths.get(i-1).getEdgeName()))+25);
                frame.addNode(graph.getStudentNameFromId(paths.get(i).getStartingNode()), current_x_position, 150);
                current_x_position+=(fontMetrics.stringWidth(paths.get(i).getStartingNode()));
                frame.addEdge(paths.get(i-1).getEdgeName(), i-1, i);
                frame.pack();
              }
              current_x_position += ((fontMetrics.stringWidth(paths.get(paths.size()-1).getEdgeName()))+65);
              frame.addNode(student2, current_x_position, 150);
              current_x_position+=(fontMetrics.stringWidth(student2));
              frame.addEdge(paths.get(paths.size()-1).getEdgeName(), paths.size()-1, paths.size());
              frame.setSize(current_x_position+150,300);
              frame.setVisible(true); 
            }
        }
        catch (JSONException error) {
            JOptionPane optionPane = new JOptionPane("JSON Error, Path can't be determined or input error, please check spelling and capitalization. Please note that this program only has access to about 90% of students at SAS due to private Schoology profiles and as such is not 100% accurate", JOptionPane.ERROR_MESSAGE);    
            JDialog dialog = optionPane.createDialog("Failure");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            System.out.println("JSON Error, Path can't be determined or input error, please note that this program only has access to about 90% of students at SAS due to private Schoology profiles and as such is not 100% accurate");
        } 
    }//GEN-LAST:event_generate_graphMouseClicked

    /**
     * Handles main driver and GUI code. 
     * @throws java.io.IOException
     */
    public static void main(String args[]) throws IOException, FileNotFoundException, JSONException {
   
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
                
            }
        });
        graph = new SchoologyGraph("schoology_graph.txt","student_ids.txt", "teacher_list.txt"); // reads in graph form the following text files  
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton generate_graph;
    private javax.swing.JLabel header_text;
    private javax.swing.JCheckBox include_teachers;
    private javax.swing.JLabel paragraph_text;
    private javax.swing.JTextField student1_name;
    private javax.swing.JTextField student2_name;
    // End of variables declaration//GEN-END:variables
}
