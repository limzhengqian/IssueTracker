/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BugsLife;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.json.simple.JSONObject;

/**
 *
 * @author user
 */
public class UpdateJSON extends javax.swing.JFrame {

    /**
     * Creates new form UpdateJSON
     */
    public UpdateJSON() throws InterruptedException {
        //initialise components of the GUI
        initComponents();
        //set the jframe to apprear at the middle of the screen
        setLocationRelativeTo(null);
        //call method start thread
        startThread();
        //let the frame visible
        setVisible(true);
        //hide the frame when the x button is pressed
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

    }

    //method to start update json file
   private void startThread() throws InterruptedException {
        SwingWorker<Void, Integer> swingWorker = new SwingWorker<Void, Integer>() {
            @Override
            //carry out work in the background
            protected Void doInBackground() throws Exception {
                //since we dont know how long will the progress take, we let the bar become looping 
                jProgressBar1.setIndeterminate(true);
                try {
                    // TODO add your handling code here:
                    //create object of projectsDAO class
                    ProjectsDAO project = new ProjectsDAO();
                    //initialise arraylist of project 
                    ArrayList<Project> projectList = new ArrayList();
                    //get all project from database into the arrayList
                    projectList = project.getAllProject();
                    //create object of UsersDAO class
                    UsersDAO user = new UsersDAO();
                    //initialise arraylist of User
                    ArrayList<User> userList = new ArrayList();
                    //get all user from database into the arraylist
                    userList = user.getAllUsers();
                    //loop through all the project
                    for (int i = 0; i < projectList.size(); i++) {
                        //create  object of IssuesDAO class
                        IssuesDAO issue = new IssuesDAO();
                        //initialise arraylist of Issue
                        ArrayList<Issue> issueList = new ArrayList();
                        //get all the issue of the current project from database
                        issueList = issue.getAllIssue((int) projectList.get(i).getId());
                        //loop the issuelist
                        for (int j = 0; j < issueList.size(); j++) {
                            //create  object of CommentsDAO class
                            CommentsDAO comment = new CommentsDAO();
                            //initialise arraylist of CommentWithoutImage 
                            ArrayList<CommentWithoutImage> commentList = new ArrayList();
                             //get all the comment of the current issue from database
                            commentList = comment.getAllCommentsNoIMG(issueList.get(j).getId());
                            //loop the comment list
                            for (int k = 0; k < commentList.size(); k++) {
                                //create  object of ReactsDAO class
                                ReactsDAO react = new ReactsDAO();
                                //initialise arraylist of React
                                ArrayList<React> reactList = new ArrayList();
                                //get all the react of the current comment from database
                                reactList = react.getAllReact((int) commentList.get(k).getComment_id());
                                //loop the reactlist
                                for(int l=0;l<reactList.size();l++){
                                    //initialise all the count as 0
                                    reactList.get(l).setCount(0);
                                }
                                //set the react variable of comment list with the react arraylist
                                commentList.get(k).setReact(reactList);
                            }
                            //set the comment variable of issue list with the comment arraylist
                            issueList.get(j).setCnoimg(commentList);
                        }
                        //set the issue variable of project list with the issue arraylist
                        projectList.get(i).setIssues(issueList);
                    }
                    //construct a Gson instance
                    Gson gson = new GsonBuilder().create();
                    //convert projectLists arrayList into string
                    String listString = gson.toJson(projectList, new TypeToken<ArrayList<Project>>() {
                    }.getType());
                    //pass the string into jsonArray
                    org.json.JSONArray jsonArray = new org.json.JSONArray(listString);
                    //convert userlist arraylits into string
                    String listString2 = gson.toJson(userList, new TypeToken<ArrayList<User>>() {
                    }.getType());
                    //convert the string into jsonArray
                    org.json.JSONArray jsonArray2 = new org.json.JSONArray(listString2);
                    //create a jsonobject
                    JSONObject main = new JSONObject();
                    //put the 2 json array into the jsonobject
                    main.put("projects", jsonArray);
                    main.put("users", jsonArray2);
                    //write the json file with format
                    Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
                    JsonParser jp = new JsonParser();
                    JsonElement je = jp.parse(main.toJSONString());
                    //convert to string
                    String prettyJsonString = gson1.toJson(je);
                    FileWriter file;
                    try {
                        //write into the file location
                        file = new FileWriter("newdata.json");
                        file.write(prettyJsonString);
                        file.flush();
                    } catch (IOException ex) {
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ProjectDashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ProjectDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

            @Override
            protected void done() {
                //after done, stop the progress bar looping
                jProgressBar1.setIndeterminate(false);
                //show message done
                JOptionPane.showMessageDialog(null, "Done");
                //close this frame
                UpdateJSON.this.dispose();
            }
            
         };
        swingWorker.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Progress Bar");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 12)); // NOI18N
        jLabel1.setText("Updating JSON File, Please Be Patient.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateJSON.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateJSON.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateJSON.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateJSON.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UpdateJSON().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UpdateJSON.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
