/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BugsLife;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author user
 */
public class DisplayBar extends javax.swing.JFrame {

    /**
     * Creates new form DisplayBar
     */
    private String [][] arrTag;
    private int issueResolved,issueUnresolved,issueinProgress;
    public DisplayBar() {
        initComponents();
    }

    DisplayBar(String[][] arrTag) {
        this.arrTag=arrTag;
        DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        for(int i=0;i<this.arrTag.length;i++){
            dcd.setValue(Integer.parseInt(arrTag[i][1]), "Tag_Count", arrTag[i][0]);
        }
        JFreeChart jc=ChartFactory.createBarChart("Count of Tag","Tag_Name","Tag_Count",dcd,PlotOrientation.HORIZONTAL,true,true,false);
        CategoryPlot plot=jc.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.CYAN);
       ChartPanel chartPanel1=new ChartPanel(jc);
        chartPanel1.setDomainZoomable(true);
        setContentPane(chartPanel1); 
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayBar(int sueinProgress, int sueResolved, int sueUnresolved) {
        this.issueinProgress=sueinProgress;
        this.issueResolved=sueResolved;
        this.issueUnresolved=sueUnresolved;
        DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        dcd.setValue(issueinProgress, "Status_Count", "In Progress");
        dcd.setValue(issueResolved, "Status_Count", "Resolved");
        dcd.setValue(issueUnresolved, "Status_Count", "Unresolved");
         JFreeChart jc=ChartFactory.createBarChart("Count of Status","Status_Name","Sstatus_Count",dcd,PlotOrientation.HORIZONTAL,true,true,false);
        CategoryPlot plot=jc.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.CYAN);
       ChartPanel chartPanel1=new ChartPanel(jc);
        chartPanel1.setDomainZoomable(true);
        setContentPane(chartPanel1); 
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayBar(int sue1, int sue2, int sue3, int sue4, int sue5, int sue6, int sue7) {
         DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        dcd.setValue(sue1, "Issue_Count", "Monday");
        dcd.setValue(sue2, "Issue_Count", "Tuesday");
        dcd.setValue(sue3, "Issue_Count", "Wednesday");
        dcd.setValue(sue4, "Issue_Count", "Thursday");
        dcd.setValue(sue5, "Issue_Count", "Friday");
        dcd.setValue(sue6, "Issue_Count", "Saturday");
        dcd.setValue(sue7, "Issue_Count", "Sunday");
         JFreeChart jc=ChartFactory.createBarChart("Count of Issue","Day","Issue_Count",dcd,PlotOrientation.HORIZONTAL,true,true,false);
        CategoryPlot plot=jc.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.CYAN);
       ChartPanel chartPanel1=new ChartPanel(jc);
        chartPanel1.setDomainZoomable(true);
        setContentPane(chartPanel1); 
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayBar(int sue1, int sue2, int sue3, int sue4, int sue5) {
         DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        dcd.setValue(sue1, "Issue_Count", "Week 1");
        dcd.setValue(sue2, "Issue_Count", "Week 2");
        dcd.setValue(sue3, "Issue_Count", "Week 3");
        dcd.setValue(sue4, "Issue_Count", "Week 4");
        dcd.setValue(sue5, "Issue_Count", "Week 5");
         JFreeChart jc=ChartFactory.createBarChart("Count of Issues","Week","Issue_Count",dcd,PlotOrientation.HORIZONTAL,true,true,false);
        CategoryPlot plot=jc.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.CYAN);
       ChartPanel chartPanel1=new ChartPanel(jc);
        chartPanel1.setDomainZoomable(true);
        setContentPane(chartPanel1); 
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            java.util.logging.Logger.getLogger(DisplayBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayBar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new DisplayBar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
