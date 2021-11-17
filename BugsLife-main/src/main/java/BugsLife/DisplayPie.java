/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BugsLife;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author user
 */
public class DisplayPie extends javax.swing.JFrame {

    private String[][] arrTag;
    private int issueResolved, issueUnresolved, issueinProgress;
    private int issue1,issue2,issue3,issue4,issue5,issue6,issue7;
    /**
     * Creates new form DisplayPieForTag
     */
    public DisplayPie() {
        initComponents();
    }

    DisplayPie(String[][] arrTag) {
        initComponents();
        this.arrTag = arrTag;
        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset, "Statistic of tags");
        ChartPanel chartPanel1 = new ChartPanel(chart);
        chartPanel1.setDomainZoomable(true);
        setContentPane(chartPanel1);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayPie(int sueinProgress, int sueResolved, int sueUnresolved) {
        this.issueinProgress = sueinProgress;
        this.issueResolved = sueResolved;
        this.issueUnresolved = sueUnresolved;
        PieDataset dataset = createDatasetStatus();
        JFreeChart chart = createChart(dataset, "Statistic of Status");
        ChartPanel chartPanel1 = new ChartPanel(chart);
        chartPanel1.setDomainZoomable(true);
        setContentPane(chartPanel1);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayPie(int sue1, int sue2, int sue3, int sue4, int sue5, int sue6, int sue7) {
        this.issue1=sue1;
        this.issue2=sue2;
        this.issue3=sue3;
        this.issue4=sue4;
        this.issue5=sue5;
        this.issue6=sue6;
        this.issue7=sue7;
        PieDataset dataset = createDatasetIssue();
        JFreeChart chart = createChart(dataset, "Statistic of Issue");
        ChartPanel chartPanel1 = new ChartPanel(chart);
        chartPanel1.setDomainZoomable(true);
        setContentPane(chartPanel1);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    DisplayPie(int sue1, int sue2, int sue3, int sue4, int sue5) {
        this.issue1=sue1;
        this.issue2=sue2;
        this.issue3=sue3;
        this.issue4=sue4;
        this.issue5=sue5;
        PieDataset dataset = createDatasetIssueMonth();
        JFreeChart chart = createChart(dataset, "Statistic of Issue");
        ChartPanel chartPanel1 = new ChartPanel(chart);
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
            java.util.logging.Logger.getLogger(DisplayPie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayPie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayPie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayPie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new DisplayPie().setVisible(true);
            }
        });
    }

    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        for (int i = 0; i < arrTag.length; i++) {
            result.setValue(arrTag[i][0], Integer.parseInt(arrTag[i][1]));
        }
        return result;
    }

    private JFreeChart createChart(PieDataset dataset, String statistic_of_tags) {
        JFreeChart chart = ChartFactory.createPieChart3D(statistic_of_tags, dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(0);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }

    private PieDataset createDatasetStatus() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("In Progress", issueinProgress);
        result.setValue("Resolved", issueResolved);
        result.setValue("Unresolved", issueUnresolved);
        return result;
    }

    private PieDataset createDatasetIssue() {
         DefaultPieDataset result = new DefaultPieDataset();
         result.setValue("Monday", issue1);
         result.setValue("Tuesday", issue2);
         result.setValue("Wednesday", issue3);
         result.setValue("Thursday", issue4);
         result.setValue("Friday", issue5);
         result.setValue("Saturday", issue6);
         result.setValue("Sunday", issue7);
         return result;
    }

    private PieDataset createDatasetIssueMonth() {
       DefaultPieDataset result = new DefaultPieDataset();
         result.setValue("Week 1", issue1);
         result.setValue("Week 2", issue2);
         result.setValue("Week 3", issue3);
         result.setValue("Week 4", issue4);
         result.setValue("Week 5", issue5);
        
         return result;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}