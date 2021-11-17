package BugsLife;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.table.*;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class IssueDashboard extends javax.swing.JFrame {
    private String reportChoice;
    private int projectIndex;
    private DefaultTableModel model;
    private IssuesDAO issueDAO;
    private CommentsDAO commentDAO;
    private ArrayList<Issue> issueArrList;
    private ArrayList<Comment> commentArrList;
    private ArrayList<History> changeLog;
    
    public IssueDashboard(int projectIndex) throws SQLException {
        this.projectIndex = projectIndex;
        this.issueDAO = new IssuesDAO();
        this.commentDAO = new CommentsDAO();
        this.issueArrList = issueDAO.getAllIssue(this.projectIndex + 1);
        this.changeLog = issueDAO.getIssueLog(this.projectIndex + 1);
        initComponents();
        //scale image for logo
        scaleImage();
        addRowToJTable();
        TableDesign();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //this.setExtendedState(this.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
    }
    
     //Scales the image of the logo
    private void scaleImage() {
        ImageIcon imageIcon = new ImageIcon("virus.png");
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(jLabel3.getWidth() - 5, jLabel3.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        jLabel3.setIcon(imageIcon);

    }

    private void TableDesign() {
        JTableHeader header = jTable1.getTableHeader();
        header.setBackground(Color.lightGray);
        header.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 14));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 8; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void addRowToJTable() {
        model = (DefaultTableModel) jTable1.getModel();
        Object rowData[] = new Object[8];
        // add row from data in json file to table
        for (int i = 0; i < issueArrList.size(); i++) {
            rowData[0] = issueArrList.get(i).getId();
            rowData[1] = issueArrList.get(i).getTitle();
            rowData[2] = issueArrList.get(i).getStatus();
            rowData[3] = issueArrList.get(i).getTag();
            rowData[4] = issueArrList.get(i).getPriority();
            long timestamp = issueArrList.get(i).getTimestamp();
            Date date = new Date(timestamp*1000L);
            LocalDate ld=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();   
            rowData[5] = ld;
            rowData[6] = issueArrList.get(i).getAssignee();
            rowData[7] = issueArrList.get(i).getCreatedBy();
            model.addRow(rowData);
        }
        // sort table based on priority
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        jTable1.setRowSorter(sorter);
        TableColumnModel m = jTable1.getColumnModel();
        m.getColumn(5).setCellRenderer(FormatRenderer.getDateTimeRenderer());
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexForPriority = 4;
        sortKeys.add(new RowSorter.SortKey(columnIndexForPriority, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    // filter by status
    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        jTable1.setRowSorter(tr);
        if (query != "Filter status") {
            tr.setRowFilter(RowFilter.regexFilter(query));
        }
    }

    // filter for search feature 
    private void filterSearch() throws SQLException, IOException {
        ArrayList<Issue> filteredArrList = new ArrayList<>();
        for (int i = 0; i < issueArrList.size(); i++) {
            String title = issueArrList.get(i).getTitle();
            
            //filter search with title
            if (FuzzySearch.tokenSetPartialRatio(title, TextFieldSearch.getText()) > 70){
                filteredArrList.add(issueArrList.get(i));
                continue;
            }

            //filter search with issue description
            String description = issueArrList.get(i).getDescriptionText();
            if (FuzzySearch.tokenSetPartialRatio(description, TextFieldSearch.getText()) > 70){
                filteredArrList.add(issueArrList.get(i));
                continue;
            }
            
            //filter search with comments
            this.commentArrList = commentDAO.getAllComments(issueArrList.get(i).getId());
            for (int j = 0; j < commentArrList.size(); j++){
                if (FuzzySearch.tokenSetPartialRatio(commentArrList.get(j).getText(), TextFieldSearch.getText()) > 70){
                    filteredArrList.add(issueArrList.get(i));
                    break;
                }
            }
            continue;
        }
        
        model.setRowCount(0);
        Object[] rowData = new Object[8];
         for (int i = 0; i < filteredArrList.size(); i++) {
            rowData[0] = filteredArrList.get(i).getId();
            rowData[1] = filteredArrList.get(i).getTitle();
            rowData[2] = filteredArrList.get(i).getStatus();
            rowData[3] = filteredArrList.get(i).getTag();
            rowData[4] = filteredArrList.get(i).getPriority();
                long timestamp = issueArrList.get(i).getTimestamp();
                Date date = new Date(timestamp*1000L);
                LocalDate ld=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();   
            rowData[5] = ld;
            rowData[6] = filteredArrList.get(i).getAssignee();
            rowData[7] = filteredArrList.get(i).getCreatedBy();
            model.addRow(rowData);
        }
         
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        jTable1.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexForPriority = 4;
        sortKeys.add(new RowSorter.SortKey(columnIndexForPriority, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
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
        jPanel2 = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        changeLogButton = new javax.swing.JButton();
        MenuStatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BackButton = new javax.swing.JToggleButton();
        RefreshButton = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TextFieldSearch = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        CancelSearch = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        newButton.setBackground(new java.awt.Color(0, 153, 204));
        newButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        newButton.setForeground(new java.awt.Color(255, 255, 255));
        newButton.setText("New issue");
        newButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        newButton.setFocusable(false);
        newButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newButtonMouseClicked(evt);
            }
        });
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        changeLogButton.setBackground(new java.awt.Color(0, 153, 204));
        changeLogButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 15)); // NOI18N
        changeLogButton.setForeground(new java.awt.Color(255, 255, 255));
        changeLogButton.setText("Change Log");
        changeLogButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        changeLogButton.setFocusable(false);
        changeLogButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeLogButtonMouseClicked(evt);
            }
        });
        changeLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeLogButtonActionPerformed(evt);
            }
        });

        MenuStatus.setBackground(new java.awt.Color(0, 153, 255));
        MenuStatus.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        MenuStatus.setForeground(new java.awt.Color(255, 255, 255));
        MenuStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Filter status", "Resolved", "Closed", "Open", "In Progress" }));
        MenuStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MenuStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MenuStatusItemStateChanged(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(51, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Title", "Status", "Tag", "Priority", "Time", "Assignee", "CreatedBy"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setRequestFocusEnabled(false);
        jTable1.setSelectionBackground(new java.awt.Color(204, 204, 255));
        jTable1.setShowGrid(false);
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
        }

        BackButton.setBackground(new java.awt.Color(0, 153, 255));
        BackButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        BackButton.setForeground(new java.awt.Color(255, 255, 255));
        BackButton.setText("Back");
        BackButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BackButton.setFocusable(false);
        BackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackButtonMouseClicked(evt);
            }
        });
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        RefreshButton.setBackground(new java.awt.Color(0, 153, 204));
        RefreshButton.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        RefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        RefreshButton.setText("Refresh");
        RefreshButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        RefreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RefreshButtonMouseClicked(evt);
            }
        });
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(51, 153, 255));
        jComboBox1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daily Report", "Weekly Report" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Generate Report");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 32)); // NOI18N
        jLabel1.setText("ISSUE BOARD");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("This search feature can look into the title, description text and the comments.");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Search :");

        TextFieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldSearchActionPerformed(evt);
            }
        });

        Search.setBackground(new java.awt.Color(255, 255, 255));
        Search.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Search.setText("Search");
        Search.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Search.setFocusable(false);
        Search.setPreferredSize(new java.awt.Dimension(91, 22));
        Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchMouseClicked(evt);
            }
        });
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        CancelSearch.setBackground(new java.awt.Color(255, 255, 255));
        CancelSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CancelSearch.setText("Cancel");
        CancelSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        CancelSearch.setFocusable(false);
        CancelSearch.setPreferredSize(new java.awt.Dimension(91, 22));
        CancelSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CancelSearchMouseClicked(evt);
            }
        });
        CancelSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addComponent(TextFieldSearch)
                                .addComponent(jLabel2)
                                .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CancelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 16)); // NOI18N
        jLabel5.setText("Issues");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(MenuStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(changeLogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 807, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(changeLogButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RefreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(newButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(MenuStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackButton))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {MenuStatus, RefreshButton, changeLogButton, jLabel5, newButton});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldSearchActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newButtonActionPerformed

    private void changeLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeLogButtonActionPerformed
        // TODO add your handling code here:         
    }//GEN-LAST:event_changeLogButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        // go to the selected issue page
        int column = 0;
        int row = jTable1.getSelectedRow();
        String value = jTable1.getValueAt(row, column).toString();
        int issueId = Integer.parseInt(value);        
        dispose();
        try {
            IssuePage issuePage = new IssuePage(projectIndex, issueId);
        } catch (SQLException ex) {
            Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void changeLogButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeLogButtonMouseClicked
        // TODO add your handling code here:
        // go to change log. if change log is empty, show message dialog
        if (changeLog.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no edit history");
        }else{
            dispose();
            new ChangeLog(projectIndex, changeLog);
        }
    }//GEN-LAST:event_changeLogButtonMouseClicked

    private void newButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newButtonMouseClicked
        try {
            // create new issue
            final JDialog CreateIssue = new CreateIssue(this, false, projectIndex);
        } catch (SQLException ex) {
            Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_newButtonMouseClicked

    private void MenuStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MenuStatusItemStateChanged
        // TODO add your handling code here:
        // get the selected status and call the filter method
        String query = MenuStatus.getSelectedItem().toString();
        filter(query);
    }//GEN-LAST:event_MenuStatusItemStateChanged

    private void BackButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackButtonMouseClicked
        // TODO add your handling code here:
        // go back to project dashboard
        dispose();
        try {
            ProjectDashboard projectDashboard = new ProjectDashboard();
        } catch (SQLException ex) {
            Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BackButtonMouseClicked

    private void RefreshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefreshButtonMouseClicked
        // TODO add your handling code here:
        // refresh issue dashboard
        this.dispose();
        try {
            new IssueDashboard(projectIndex);
        } catch (SQLException ex) {
            Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_RefreshButtonMouseClicked

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed

    private void SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchMouseClicked
        try {
            filterSearch();
        } catch (SQLException ex) {
            Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SearchMouseClicked

    private void CancelSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelSearchMouseClicked
        model.setRowCount(0);
        addRowToJTable();
        TextFieldSearch.setText("");
    }//GEN-LAST:event_CancelSearchMouseClicked

    private void CancelSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CancelSearchActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        reportChoice = jComboBox1.getSelectedItem().toString();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(reportChoice.equalsIgnoreCase("Daily Report")){
            try {
                new DailyReportDashBoard(projectIndex);
            } catch (SQLException ex) {
                Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(reportChoice.equalsIgnoreCase("Weekly Report")){
            try {
                new WeeklyReportDashBoard(projectIndex);
            } catch (SQLException ex) {
                Logger.getLogger(IssueDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(IssueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new IssueDashboard(projectIndex).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BackButton;
    private javax.swing.JButton CancelSearch;
    private javax.swing.JComboBox<String> MenuStatus;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JButton Search;
    private javax.swing.JTextField TextFieldSearch;
    private javax.swing.JButton changeLogButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton newButton;
    // End of variables declaration//GEN-END:variables

}
