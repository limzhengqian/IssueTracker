package BugsLife;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class EditIssue extends javax.swing.JDialog {

    private int projectIndex;
    private Issue editIssue;
    private IssuesDAO issueDAO;
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();

    /**
     * Creates new form EditIssue
     */
    public EditIssue(int projectIndex, Issue editIssue) throws SQLException {
        initComponents();
        this.projectIndex = projectIndex;
        this.editIssue = editIssue;
        this.issueDAO = new IssuesDAO();
        //set image for the undo and redo button
        scaleImage();
        
        //scale image for logo
        scaleImageLogo();

        //set undo and redo button to not enabled
        setButton();

        //set information of issue
        setInformation();

        //adds key listener to jtextArea1 to detect keyboard entries
        addKeyListener();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //starts the SwingWorker thread
        startThread();
    }

    private void setInformation() {
        titleText.setText(editIssue.getTitle());
        priorityText.setText(String.valueOf(editIssue.getPriority()));
        assignedToText.setText(editIssue.getAssignee());
        tagText.setText(editIssue.getTag().toString().substring(1, editIssue.getTag().toString().length() - 1));
        descriptionText.setText(editIssue.getDescriptionText());
        editedByText.setText(Login.getUsername());
        editedByText.setEditable(false);
    }

    //scale the image of the undo and redo icon
    private void scaleImage() {
        ImageIcon icon = new ImageIcon("undo.png");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(Undo.getWidth() - 5, Undo.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        Undo.setIcon(icon);

        icon = new ImageIcon("redo.png");
        img = icon.getImage();
        newImg = img.getScaledInstance(Redo.getWidth() - 5, Redo.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        Redo.setIcon(icon);
    }
    
    //Scales the image of the logo
    private void scaleImageLogo() {
        ImageIcon imageIcon = new ImageIcon("virus.png");
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(jLabel8.getWidth() - 5, jLabel8.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        jLabel8.setIcon(imageIcon);

    }

    //set the undo and redo button to not enabled
    //add the description text to the undoStack to set as initial and set default size as 1
    private void setButton() {
        Redo.setEnabled(false);
        Undo.setEnabled(false);
        undoStack.add(editIssue.getDescriptionText());
    }

    //start the SwingWorker thread
    private void startThread() {
        //initialize new SwingWorker
        SwingWorker sw = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                //constantly runs the SwingWorker thread in background as long is jTextArea is showing
                while (descriptionText.isShowing()) {
                    try {
                        //pause for 1000 milliseconds
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CreateComment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    update();
                }
                return null;
            }
        ;
        };
        sw.execute();
    }

    //Updates after each interation of SwingWorker thread
    private void update() {
        String text = descriptionText.getText();

        //if the top of undoStack is not equal to text during the update
        if (!undoStack.peek().equals(descriptionText.getText())) {
            //add the text into the stack
            undoStack.add(text);
        }

        //if size > 1 (since size = 1 is default)
        if (undoStack.size() > 1) {
            //set enabled to true
            Undo.setEnabled(true);
        }
    }

    private void addKeyListener() {
        descriptionText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //clears the redo stack if keyboard is typed
                redoStack.clear();
                Redo.setEnabled(false);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //no implementation
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //no implementation
            }
        });
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
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        titleText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        priorityText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tagText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        assignedToText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        required1 = new javax.swing.JLabel();
        required2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Undo = new javax.swing.JButton();
        description = new javax.swing.JLabel();
        editedByText = new javax.swing.JTextField();
        userList = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        Redo = new javax.swing.JButton();
        required4 = new javax.swing.JLabel();
        Submit = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        New = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setToolTipText("");
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        descriptionText.setColumns(20);
        descriptionText.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        descriptionText.setRows(5);
        descriptionText.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        descriptionText.setEditable(true);
        jScrollPane2.setViewportView(descriptionText);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel1.setText("Title:");

        titleText.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        titleText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleTextActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel2.setText("Priority:");

        priorityText.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        priorityText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priorityTextActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel3.setText("Tag:");

        tagText.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tagText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagTextActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel4.setText("Assigned to:");

        assignedToText.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        assignedToText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assignedToTextActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel5.setText("Edited by:");

        required1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        required1.setForeground(new java.awt.Color(255, 51, 51));
        required1.setText("*Required");

        required2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        required2.setForeground(new java.awt.Color(255, 51, 51));
        required2.setText("*Required");

        jLabel6.setText("* 'Tag' and 'Assigned to' can be left empty");

        Undo.setBackground(new java.awt.Color(204, 204, 204));
        Undo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Undo.setFocusable(false);
        Undo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UndoMouseClicked(evt);
            }
        });
        Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoActionPerformed(evt);
            }
        });

        description.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 28)); // NOI18N
        description.setText("Issue description");

        editedByText.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        editedByText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editedByTextActionPerformed(evt);
            }
        });

        userList.setBackground(new java.awt.Color(0, 153, 255));
        userList.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        userList.setForeground(new java.awt.Color(255, 255, 255));
        userList.setText("List of users");
        userList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        userList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userListActionPerformed(evt);
            }
        });

        jLabel7.setText("* Separate tags with a comma ','");

        Redo.setBackground(new java.awt.Color(204, 204, 204));
        Redo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Redo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RedoMouseClicked(evt);
            }
        });

        required4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        required4.setForeground(new java.awt.Color(255, 51, 51));
        required4.setText("*Required");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(description)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(required4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Redo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(26, 26, 26)
                                .addComponent(priorityText))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addGap(55, 55, 55)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(titleText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tagText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(required1)
                            .addComponent(required2))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(userList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(assignedToText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editedByText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(titleText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(required1))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(editedByText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(assignedToText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tagText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(priorityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(required2)
                            .addComponent(userList))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(description)
                            .addComponent(required4))
                        .addContainerGap())
                    .addComponent(Undo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Redo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        Submit.setBackground(new java.awt.Color(0, 153, 255));
        Submit.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        Submit.setForeground(new java.awt.Color(255, 255, 255));
        Submit.setText("Submit");
        Submit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitActionPerformed(evt);
            }
        });

        cancel.setBackground(new java.awt.Color(0, 153, 255));
        cancel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        cancel.setForeground(new java.awt.Color(255, 255, 255));
        cancel.setText("Cancel");
        cancel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cancel.setMaximumSize(new java.awt.Dimension(59, 27));
        cancel.setMinimumSize(new java.awt.Dimension(59, 27));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));

        New.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 30)); // NOI18N
        New.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        New.setText("Edit Issue");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(New, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(New, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Submit, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Submit)
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Submit, cancel});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 808, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void titleTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleTextActionPerformed

    private void priorityTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priorityTextActionPerformed
        // TODO add your handling code here:
        // check if priority is entered correctly
    }//GEN-LAST:event_priorityTextActionPerformed

    private void tagTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tagTextActionPerformed

    private void assignedToTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assignedToTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assignedToTextActionPerformed

    private void RedoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RedoMouseClicked
        //if redoStack is not empty
        if (!redoStack.isEmpty()) {
            //add top of redoStack to undoStack and set text as top of redoStack
            undoStack.add(redoStack.peek());
            Undo.setEnabled(true);
            descriptionText.setText(redoStack.pop());
        }
        //if redoStack empty set enabled as false
        if (redoStack.isEmpty()) {
            Redo.setEnabled(false);
        }
    }//GEN-LAST:event_RedoMouseClicked

    private void UndoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UndoMouseClicked
        //if undoStack size > 1 (since size = 1 is default)
        if (undoStack.size() > 1) {
            //if top of undoStack is not equal to text
            if (!undoStack.peek().equals(descriptionText.getText())) {
                //adds the text into the undoStack
                undoStack.add(descriptionText.getText());
            }
            //pops the undoStack and add into redoStack
            redoStack.add(undoStack.pop());

            //set the Redo button as enabled
            Redo.setEnabled(true);

            //sets the text to top of undoStack
            descriptionText.setText(undoStack.peek());
        }

        //if undoStack size = 1, set enabled as false
        if (undoStack.size() == 1) {
            Undo.setEnabled(false);
        } else {
            Undo.setEnabled(true);
        }
    }//GEN-LAST:event_UndoMouseClicked

    private void UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UndoActionPerformed

    private void editedByTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editedByTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editedByTextActionPerformed

    private void userListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userListActionPerformed
        try {
            //get all usernames of users available
            UsersDAO userDAO = new UsersDAO();
            String[] usernames = new String[userDAO.getAllUsers().size()];
            ArrayList<User> arrListUser = userDAO.getAllUsers();
            for (int i = 0; i < arrListUser.size(); i++) {
                usernames[i] = arrListUser.get(i).getUsername();
            }
            
            //create new jframe and add jList of usernames to the jframe
            JFrame jframe = new JFrame();
            JList users = new JList(usernames);
            users.setFont(new Font("Arial", Font.BOLD, 15));
            jframe.add(users);
            jframe.setSize(250, 250);
            jframe.setLocationRelativeTo(null);
            jframe.setResizable(false);
            jframe.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(CreateIssue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_userListActionPerformed

    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        //description, title and priority must be non-empty
        if (descriptionText.getText().replaceAll("\\s", "").equals("") && titleText.getText().equals("") && priorityText.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Warning! Title, priority and description text must be non-empty to be posted", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // get the issue details to be added into database
            try {
                Date date = new Date();
                long currentTimeStamp = date.getTime() / 1000L;
                int priority = Integer.parseInt(priorityText.getText());
                ArrayList<String> tag = new ArrayList<>();
                tag.add(tagText.getText());
                String user = Login.getUsername();
                
                // create the edited issue with all the details
                Issue newIssue = new Issue(0, priority, titleText.getText(), descriptionText.getText(), user,
                        assignedToText.getText(), editIssue.getStatus(), currentTimeStamp, tag);
                
                // update the issue into database
                issueDAO.editIssue(newIssue, editIssue);
                JOptionPane.showMessageDialog(null, "Your issue has been edited");
                dispose();
                
                // determine the changes made and add it into changeLog
                // check for title
                if (!newIssue.getTitle().equals(editIssue.getTitle())) {
                    String old = editIssue.getTitle();
                    String edited = newIssue.getTitle();
                    issueDAO.addIssueLog(projectIndex + 1, editIssue.getId(),
                            user + " edited the title of issue from '" + old + "' to '" + edited + "'", (int) currentTimeStamp);
                }
                // check for priority
                if (newIssue.getPriority() != editIssue.getPriority()) {
                    int old = editIssue.getPriority();
                    int edited = newIssue.getPriority();
                    issueDAO.addIssueLog(projectIndex + 1, editIssue.getId(),
                            user + " edited the priority of issue from '" + old + "' to '" + edited + "'", (int) currentTimeStamp);
                }
                // check for assignee
                if (!newIssue.getAssignee().equals(editIssue.getAssignee())) {
                    String old = editIssue.getAssignee();
                    String edited = newIssue.getAssignee();
                    issueDAO.addIssueLog(projectIndex + 1, editIssue.getId(),
                            user + " edited the assignee of issue from '" + old + "' to '" + edited + "'", (int) currentTimeStamp);
                }
                // check for description
                if (!newIssue.getDescriptionText().equals(editIssue.getDescriptionText())) {
                    String old = editIssue.getAssignee();
                    String edited = newIssue.getAssignee();
                    issueDAO.addIssueLog(projectIndex + 1, editIssue.getId(),
                            user + " edited the issue description.", (int) currentTimeStamp);
                }
                // check for tag
                if (!newIssue.getTag().toString().equals(editIssue.getTag().toString())) {
                    String old = editIssue.getTag().toString().substring(1, editIssue.getTag().toString().length() - 1);
                    String edited = newIssue.getTag().toString().substring(1, newIssue.getTag().toString().length() - 1);
                    issueDAO.addIssueLog(projectIndex + 1, editIssue.getId(),
                            user + " edited the tag of issue from '" + old + "' to '" + edited + "'", (int) currentTimeStamp);
                }
            } catch (SQLException ex) {
                Logger.getLogger(EditIssue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SubmitActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

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
            java.util.logging.Logger.getLogger(EditIssue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditIssue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditIssue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditIssue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                EditIssue dialog = new EditIssue(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel New;
    private javax.swing.JButton Redo;
    private javax.swing.JButton Submit;
    private javax.swing.JButton Undo;
    private javax.swing.JTextField assignedToText;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel description;
    private javax.swing.JTextArea descriptionText;
    private javax.swing.JTextField editedByText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField priorityText;
    private javax.swing.JLabel required1;
    private javax.swing.JLabel required2;
    private javax.swing.JLabel required4;
    private javax.swing.JTextField tagText;
    private javax.swing.JTextField titleText;
    private javax.swing.JButton userList;
    // End of variables declaration//GEN-END:variables
}
