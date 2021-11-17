/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BugsLife;

import org.jfree.chart.ChartFactory;  
import org.jfree.chart.ChartPanel;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;  



/**
 *
 * @author user
 */
public class DisplayLine extends javax.swing.JFrame {

    /**
     * Creates new form DisplayLine
     */
    String [][]arrTag;
    private int issue1,issue2,issue3,issue4,issue5,issue6,issue7;
    private int issueResolved1,issueResolved2,issueResolved3,issueResolved4,issueResolved5,issueResolved6,issueResolved7,issueUnresolved1,issueUnresolved2,issueUnresolved3,issueUnresolved4,issueUnresolved5,issueUnresolved6,issueUnresolved7,issueinProgress1,issueinProgress2,issueinProgress3,issueinProgress4,issueinProgress5,issueinProgress6,issueinProgress7;
    private int issueResolved,issueUnresolved,issueinProgress;
    public DisplayLine() {
        initComponents();
    }
    public DisplayLine(String [][]arr){
        this.arrTag=arr;
        DefaultCategoryDataset dataset = createDataset(); 
        JFreeChart chart=ChartFactory.createLineChart("Tag", "Tag_Name", "Tag_Count", dataset,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayLine(int sueinProgress, int sueResolved, int sueUnresolved) {
        System.out.println("sohaicb");
        this.issueinProgress=sueinProgress;
        this.issueResolved=sueResolved;
        this.issueUnresolved=sueUnresolved;
        DefaultCategoryDataset dataset = createDatasetStatus(); 
        JFreeChart chart=ChartFactory.createLineChart("Status", "Status_Name", "Status_Count", dataset,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayLine(int sueResolved1, int sueResolved2, int sueResolved3, int sueResolved4, int sueResolved5, int sueResolved6, int sueResolved7, int sueUnresolved1, int sueUnresolved2, int sueUnresolved3, int sueUnresolved4, int sueUnresolved5, int sueUnresolved6, int sueUnresolved7, int sueinProgress1, int sueinProgress2, int sueinProgress3, int sueinProgress4, int sueinProgress5, int sueinProgress6, int sueinProgress7) {
        this.issueResolved1=sueResolved1;
       this.issueResolved2=sueResolved2;
       this.issueResolved3=sueResolved3;
       this.issueResolved4=sueResolved4;
       this.issueResolved5=sueResolved5;
       this.issueResolved6=sueResolved6;
       this.issueResolved7=sueResolved7;
       this.issueUnresolved1=sueUnresolved1;
       this.issueUnresolved2=sueUnresolved2;
       this.issueUnresolved3=sueUnresolved3;
       this.issueUnresolved4=sueUnresolved4;
       this.issueUnresolved5=sueUnresolved5;
       this.issueUnresolved6=sueUnresolved6;
       this.issueUnresolved7=sueUnresolved7;
       this.issueinProgress1=sueinProgress1;
       this.issueinProgress2=sueinProgress2;
       this.issueinProgress3=sueinProgress3;
       this.issueinProgress4=sueinProgress4;
       this.issueinProgress5=sueinProgress5;
       this.issueinProgress6=sueinProgress6;
       this.issueinProgress7=sueinProgress7;
        DefaultCategoryDataset dataset = createDatasetStatusNew(); 
        JFreeChart chart=ChartFactory.createLineChart("Status", "Day", "Status_Count", dataset,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayLine(int sue1, int sue2, int sue3, int sue4, int sue5, int sue6, int sue7) {
         this.issue1=sue1;
        this.issue2=sue2;
        this.issue3=sue3;
        this.issue4=sue4;
        this.issue5=sue5;
        this.issue6=sue6;
        this.issue7=sue7;
        DefaultCategoryDataset dataset = createDatasetIssue(); 
        JFreeChart chart=ChartFactory.createLineChart("Issue", "Day", "Issue_Count", dataset,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayLine(int sue1, int sue2, int sue3, int sue4, int sue5) {
         this.issue1=sue1;
        this.issue2=sue2;
        this.issue3=sue3;
        this.issue4=sue4;
        this.issue5=sue5;
        DefaultCategoryDataset dataset = createDatasetIssueMonthLine(); 
        JFreeChart chart=ChartFactory.createLineChart("Issue", "Week", "Issue_Count", dataset,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayLine(int sueResolved1, int sueResolved2, int sueResolved3, int sueResolved4, int sueResolved5, int sueUnresolved1, int sueUnresolved2, int sueUnresolved3, int sueUnresolved4, int sueUnresolved5, int sueinProgress1, int sueinProgress2, int sueinProgress3, int sueinProgress4, int sueinProgress5) {
         this.issueResolved1=sueResolved1;
       this.issueResolved2=sueResolved2;
       this.issueResolved3=sueResolved3;
       this.issueResolved4=sueResolved4;
       this.issueResolved5=sueResolved5;
       this.issueUnresolved1=sueUnresolved1;
       this.issueUnresolved2=sueUnresolved2;
       this.issueUnresolved3=sueUnresolved3;
       this.issueUnresolved4=sueUnresolved4;
       this.issueUnresolved5=sueUnresolved5;
        this.issueinProgress1=sueinProgress1;
       this.issueinProgress2=sueinProgress2;
       this.issueinProgress3=sueinProgress3;
       this.issueinProgress4=sueinProgress4;
       this.issueinProgress5=sueinProgress5;
        System.out.println("1: "+issueResolved1+" "+issueUnresolved1+" "+issueinProgress1);
        System.out.println("2: "+issueResolved2+" "+issueUnresolved2+" "+issueinProgress2);
        System.out.println("3: "+issueResolved3+" "+issueUnresolved3+" "+issueinProgress3);
        System.out.println("4: "+issueResolved4+" "+issueUnresolved4+" "+issueinProgress4);
        System.out.println("5: "+issueResolved5+" "+issueUnresolved5+" "+issueinProgress5);
          DefaultCategoryDataset dataset = createDatasetIssueMonth(); 
        JFreeChart chart=ChartFactory.createLineChart("Issue", "Week", "Issue_Count", dataset,PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMinimumSize(new java.awt.Dimension(100, 100));
        setSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(DisplayLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new DisplayLine().setVisible(true);
            }
        });
    }

    private DefaultCategoryDataset createDataset() {
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
         for(int i=0;i<arrTag.length;i++){
             dataset.addValue(Integer.parseInt(arrTag[i][1]),"Tag",arrTag[i][0]);
         }
         return dataset;
    }

    private DefaultCategoryDataset createDatasetStatus() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        dataset.addValue(issueinProgress,"Status","In Progress");
        dataset.addValue(issueResolved,"Status","Resolved");
        dataset.addValue(issueUnresolved,"Status","Unresolved");
        return dataset;
    }

    private DefaultCategoryDataset createDatasetStatusNew() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
        dataset.addValue(issueinProgress1,"In Progress","1");
        dataset.addValue(issueinProgress2,"In Progress","2");
        dataset.addValue(issueinProgress3,"In Progress","3");
        dataset.addValue(issueinProgress4,"In Progress","4");
        dataset.addValue(issueinProgress5,"In Progress","5");
        dataset.addValue(issueinProgress6,"In Progress","6");
        dataset.addValue(issueinProgress7,"In Progress","7");
        dataset.addValue(issueResolved1,"Close","1");
        dataset.addValue(issueResolved2,"Close","2");
        dataset.addValue(issueResolved3,"Close","3");
        dataset.addValue(issueResolved4,"Close","4");
        dataset.addValue(issueResolved5,"Close","5");
        dataset.addValue(issueResolved6,"Close","6");
        dataset.addValue(issueResolved7,"Close","7");
        dataset.addValue(issueUnresolved1,"Open","1");
        dataset.addValue(issueUnresolved2,"Open","2");
        dataset.addValue(issueUnresolved3,"Open","3");
        dataset.addValue(issueUnresolved4,"Open","4");
        dataset.addValue(issueUnresolved5,"Open","5");
        dataset.addValue(issueUnresolved6,"Open","6");
        dataset.addValue(issueUnresolved7,"Open","7");
        return dataset;
    }

    private DefaultCategoryDataset createDatasetIssue() {
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        dataset.addValue(issue1, "Issue", "Monday");
        dataset.addValue(issue2, "Issue", "Tuesday");
        dataset.addValue(issue3, "Issue", "Wednesday");
        dataset.addValue(issue4, "Issue", "Thursday");
        dataset.addValue(issue5, "Issue", "Friday");
        dataset.addValue(issue6, "Issue", "Saturday");
        dataset.addValue(issue7, "Issue", "Sunday");
        return dataset;
    }

    private DefaultCategoryDataset createDatasetIssueMonth() {
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        dataset.addValue(issueinProgress1,"In_Progress","Week 1");
        dataset.addValue(issueinProgress2,"In_Progress","Week 2");
        dataset.addValue(issueinProgress3,"In_Progress","Week 3");
        dataset.addValue(issueinProgress4,"In_Progress","Week 4");
        dataset.addValue(issueinProgress5,"In_Progress","Week 5");
        dataset.addValue(issueResolved1,"Resolved","Week 1");
        dataset.addValue(issueResolved2,"Resolved","Week 2");
        dataset.addValue(issueResolved3,"Resolved","Week 3");
        dataset.addValue(issueResolved4,"Resolved","Week 4");
        dataset.addValue(issueResolved5,"Resolved","Week 5");
        dataset.addValue(issueUnresolved1,"Unresolved","Week 1");
        dataset.addValue(issueUnresolved2,"Unresolved","Week 2");
        dataset.addValue(issueUnresolved3,"Unresolved","Week 3");
        dataset.addValue(issueUnresolved4,"Unresolved","Week 4");
        dataset.addValue(issueUnresolved5,"Unresolved","Week 5");
        return dataset;
    }

    private DefaultCategoryDataset createDatasetIssueMonthLine() {
         DefaultCategoryDataset dataset=new DefaultCategoryDataset();
         dataset.addValue(issue1, "Issue", "Week 1");
         dataset.addValue(issue2, "Issue", "Week 2");
         dataset.addValue(issue3, "Issue", "Week 3");
         dataset.addValue(issue4, "Issue", "Week 4");
         dataset.addValue(issue5, "Issue", "Week 5");
         return dataset;
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
