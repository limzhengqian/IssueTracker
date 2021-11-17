/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BugsLife;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author user
 */
public class WeeklyReportDashBoard extends javax.swing.JFrame {

    /**
     * Creates new form WeeklyReportDashBoard
     */
    private int year;
    private int projectIndex;
    DefaultMutableTreeNode top;
    private String month;
    private String week;
    private boolean yearselected = false;
    private IssuesDAO iss;
    private ArrayList<Issue>isslist;
    public WeeklyReportDashBoard() {

    }

    WeeklyReportDashBoard(int projectIndex) throws SQLException {
        //get projectindedx from previous class
        this.projectIndex = projectIndex;
        //initialise IssuesDAO
        iss=new IssuesDAO();
        ////get all issue of certain project ID from database and pass into Java ArrayList
        isslist=iss.getAllIssue(this.projectIndex+1);
        //initialise components of the GUI
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    //method to create node for jtree for non-leap year
    private void createNodesMonthsNoLeap(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode m1 = new DefaultMutableTreeNode("January");
        //call createNodesWeek5 method to create child node of 5 weeks
        createNodesWeeks5(m1);
        //call createNodesWeek4 method to create child node of 4 weeks for february
        DefaultMutableTreeNode m2 = new DefaultMutableTreeNode("February");
        createNodesWeeks4(m2);
        DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("March");
        createNodesWeeks5(m3);
        DefaultMutableTreeNode m4 = new DefaultMutableTreeNode("April");
        createNodesWeeks5(m4);
        DefaultMutableTreeNode m5 = new DefaultMutableTreeNode("May");
        createNodesWeeks5(m5);
        DefaultMutableTreeNode m6 = new DefaultMutableTreeNode("June");
        createNodesWeeks5(m6);
        DefaultMutableTreeNode m7 = new DefaultMutableTreeNode("July");
        createNodesWeeks5(m7);
        DefaultMutableTreeNode m8 = new DefaultMutableTreeNode("August");
        createNodesWeeks5(m8);
        DefaultMutableTreeNode m9 = new DefaultMutableTreeNode("September");
        createNodesWeeks5(m9);
        DefaultMutableTreeNode m10 = new DefaultMutableTreeNode("October");
        createNodesWeeks5(m10);
        DefaultMutableTreeNode m11 = new DefaultMutableTreeNode("November");
        createNodesWeeks5(m11);
        DefaultMutableTreeNode m12 = new DefaultMutableTreeNode("December");
        createNodesWeeks5(m12);
        //add all nodes to top nodes
        top.add(m1);
        top.add(m2);
        top.add(m3);
        top.add(m4);
        top.add(m5);
        top.add(m6);
        top.add(m7);
        top.add(m8);
        top.add(m9);
        top.add(m10);
        top.add(m11);
        top.add(m12);
    }
    //same method but for leap year
    private void createNodesMonthsLeap(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode m1 = new DefaultMutableTreeNode("January");
        createNodesWeeks5(m1);
        //leap year have 5 weeks for february
        DefaultMutableTreeNode m2 = new DefaultMutableTreeNode("February");
        createNodesWeeks5(m2);
        DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("March");
        createNodesWeeks5(m3);
        DefaultMutableTreeNode m4 = new DefaultMutableTreeNode("April");
        createNodesWeeks5(m4);
        DefaultMutableTreeNode m5 = new DefaultMutableTreeNode("May");
        createNodesWeeks5(m5);
        DefaultMutableTreeNode m6 = new DefaultMutableTreeNode("June");
        createNodesWeeks5(m6);
        DefaultMutableTreeNode m7 = new DefaultMutableTreeNode("July");
        createNodesWeeks5(m7);
        DefaultMutableTreeNode m8 = new DefaultMutableTreeNode("August");
        createNodesWeeks5(m8);
        DefaultMutableTreeNode m9 = new DefaultMutableTreeNode("September");
        createNodesWeeks5(m9);
        DefaultMutableTreeNode m10 = new DefaultMutableTreeNode("October");
        createNodesWeeks5(m10);
        DefaultMutableTreeNode m11 = new DefaultMutableTreeNode("November");
        createNodesWeeks5(m11);
        DefaultMutableTreeNode m12 = new DefaultMutableTreeNode("December");
        createNodesWeeks5(m12);
        top.add(m1);
        top.add(m2);
        top.add(m3);
        top.add(m4);
        top.add(m5);
        top.add(m6);
        top.add(m7);
        top.add(m8);
        top.add(m9);
        top.add(m10);
        top.add(m11);
        top.add(m12);
    }
    //method to create 6 child nodes for each month
    private void createNodesWeeks5(DefaultMutableTreeNode node) {
        DefaultMutableTreeNode week1 = new DefaultMutableTreeNode("First Week");
        DefaultMutableTreeNode week2 = new DefaultMutableTreeNode("Second Week");
        DefaultMutableTreeNode week3 = new DefaultMutableTreeNode("Third Week");
        DefaultMutableTreeNode week4 = new DefaultMutableTreeNode("Forth Week");
        DefaultMutableTreeNode week5 = new DefaultMutableTreeNode("Fifth Week");
        DefaultMutableTreeNode month1 = new DefaultMutableTreeNode("Monthly Report");
        node.add(week1);
        node.add(week2);
        node.add(week3);
        node.add(week4);
        node.add(week5);
        node.add(month1);
    }
    //method to create 5 child nodes for february
    private void createNodesWeeks4(DefaultMutableTreeNode node) {
        DefaultMutableTreeNode week1 = new DefaultMutableTreeNode("First Week");
        DefaultMutableTreeNode week2 = new DefaultMutableTreeNode("Second Week");
        DefaultMutableTreeNode week3 = new DefaultMutableTreeNode("Third Week");
        DefaultMutableTreeNode week4 = new DefaultMutableTreeNode("Forth Week");
        DefaultMutableTreeNode month1 = new DefaultMutableTreeNode("Monthly Report");
        node.add(week1);
        node.add(week2);
        node.add(week3);
        node.add(week4);
        node.add(month1);
    }
    //method to determine leap year
    private boolean leap(int year) {
        boolean leap = false;

        if (year % 4 == 0) {

            // if the year is century
            if (year % 100 == 0) {

                // if year is divided by 400
                // then it is a leap year
                if (year % 400 == 0) {
                    leap = true;
                } else {
                    leap = false;
                }
            } // if the year is not century
            else {
                leap = true;
            }
        } else {
            leap = false;
        }
        return leap;
    }

//    private void generateReport(DefaultMutableTreeNode week1, DefaultMutableTreeNode week2, DefaultMutableTreeNode week3, DefaultMutableTreeNode week4, DefaultMutableTreeNode week5) {
//        for (int i = 0; i < Main.projects.get(projectIndex).getIssues().size(); i++) {
//            long timestamp = Main.projects.get(projectIndex).getIssues().get(i).getTimestamp();
//            Date issueDate = new java.util.Date(timestamp * 1000L);
//            LocalDate date = issueDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            WeekFields weekFields = WeekFields.of(Locale.getDefault());
//            int weekNum = date.get(weekFields.weekOfWeekBasedYear());
//            if (weekNum >= 1 && weekNum <= 5) {
//
//            }
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DefaultMutableTreeNode top =
        new DefaultMutableTreeNode("Project");
        jTree1 = new javax.swing.JTree(top);
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ArrayList<Integer>checkdupyears=new ArrayList<>();
        for(int i=0;i<isslist.size();i++){
            long timestamp=isslist.get(i).getTimestamp();
            Date date1 = new Date(timestamp * 1000L);
            LocalDate date = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(!checkdupyears.contains(date.getYear())){

                DefaultMutableTreeNode tops=new DefaultMutableTreeNode(date.getYear());
                if(leap(date.getYear())){
                    createNodesMonthsLeap(tops);
                    top.add(tops);
                }
                else{
                    createNodesMonthsNoLeap(tops);
                    top.add(tops);

                }
                checkdupyears.add(date.getYear());
            }
        }
        jTree1.setDragEnabled(true);
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("List of Reports in Weeks/Months");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //when jtree is clicked
    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        // TODO add your handling code here:
        //get location of the click
        TreePath tp = jTree1.getPathForLocation(evt.getX(), evt.getY());
        //if click once
        if (evt.getClickCount() == 1) {
            //get the lastest component of the path as the week
            this.week = tp.getLastPathComponent().toString();
            //get the previous item from the week
            this.month=tp.getParentPath().getLastPathComponent().toString();
            //get the beginning path as the year
            this.year=Integer.parseInt(tp.getPathComponent(WIDTH).toString());
        }
        //if week is 1st week
        if (week.equalsIgnoreCase("First Week")) {
            try {
                //create weekly report for 1st week
                new WeeklyReport(year, month, 1, projectIndex);
            } catch (SQLException ex) {
                Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //if week is second week
        else if (week.equalsIgnoreCase("Second Week"))
            //create report for second week
            try {
                new WeeklyReport(year, month, 2, projectIndex);
        } catch (SQLException ex) {
            Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }//if week is 3rd week//if week is 3rd week//if week is 3rd week//if week is 3rd week
        else if (week.equalsIgnoreCase("Third Week"))
            try {
                //create report for 3rd week
                new WeeklyReport(year, month, 3, projectIndex);
        } catch (SQLException ex) {
            Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }//if week is 4th weeek//if week is 4th weeek//if week is 4th weeek//if week is 4th weeek
        else if (week.equalsIgnoreCase("Forth Week"))
            try {
                //create report for 4th week
                new WeeklyReport(year, month, 4, projectIndex);
        } catch (SQLException ex) {
            Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }//if week is 5th week//if week is 5th week//if week is 5th week//if week is 5th week
        else if (week.equalsIgnoreCase("Fifth Week"))
            try {
                //create report for 5th week
                new WeeklyReport(year, month, 5, projectIndex);
        } catch (SQLException ex) {
            Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }//if choose monthly report//if choose monthly report//if choose monthly report//if choose monthly report
        else if(week.equalsIgnoreCase("Monthly Report"))
            try {
                //create monthly report
                new MonthlyReport(year, month, projectIndex);
        } catch (SQLException ex) {
            Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTree1MouseClicked

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
            java.util.logging.Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WeeklyReportDashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WeeklyReportDashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

}
