package BugsLife;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class EditComment extends javax.swing.JFrame {

    private static String imageFileName = "";
    private int projectIndex, issueId;
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private Comment oldComment;
    private CommentsDAO commentDAO;
    private static boolean savedState = false;
    private static boolean emptyImage;

    public static void setSavedState(boolean savedState) {
        EditComment.savedState = savedState;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public static void setImageFileName(String imageFileName) {
        EditComment.imageFileName = imageFileName;
    }

    public static void setEmptyImage(boolean emptyImage) {
        EditComment.emptyImage = emptyImage;
    }

    public EditComment(Comment oldComment) throws SQLException {
        initComponents();
        this.oldComment = oldComment;
        this.commentDAO = new CommentsDAO();

        //set emptyImage = true if old comment has image
        //false if old comment has no image
        if (oldComment.getImg() == null) {
            emptyImage = true;
        } else {
            emptyImage = false;
        }

        //set image for the undo and redo button
        scaleImage();

        //set undo and redo button to not enabled
        setButton();

        //adds key listener to jtextArea1 to detect keyboard entries
        addKeyListener();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        //starts thread
        startThread();
    }

    //scale the image of undo and redo icon
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

    /**
     * Set the undo and redo button to not enabled Add empty string to undoStack
     * so that its default size is 1
     */
    private void setButton() {
        Redo.setEnabled(false);
        Undo.setEnabled(false);
        jTextArea1.setText(oldComment.getText());
        undoStack.add(oldComment.getText());
    }

    //start the SwingWorker thread
    private void startThread() {
        //initialize new SwingWorker
        SwingWorker sw = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                //constantly runs the SwingWorker thread in background as long is jTextArea is showing
                while (jTextArea1.isShowing()) {
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
        String text = jTextArea1.getText();

        //if the top of undoStack is not equal to text during the update
        if (!undoStack.peek().equals(jTextArea1.getText())) {
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
        jTextArea1.addKeyListener(new KeyListener() {
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        Undo = new javax.swing.JButton();
        Redo = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        Upload = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        jLabel1.setText("Edit your comment");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

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

        Redo.setBackground(new java.awt.Color(204, 204, 204));
        Redo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Redo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RedoMouseClicked(evt);
            }
        });

        Edit.setBackground(new java.awt.Color(0, 153, 255));
        Edit.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        Edit.setForeground(new java.awt.Color(255, 255, 255));
        Edit.setText("Confirm");
        Edit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EditMouseClicked(evt);
            }
        });
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        Upload.setBackground(new java.awt.Color(0, 153, 255));
        Upload.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        Upload.setForeground(new java.awt.Color(255, 255, 255));
        Upload.setText("Edit picture");
        Upload.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UploadMouseClicked(evt);
            }
        });

        Cancel.setBackground(new java.awt.Color(0, 153, 255));
        Cancel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        Cancel.setForeground(new java.awt.Color(255, 255, 255));
        Cancel.setText("Cancel");
        Cancel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Cancel.setMaximumSize(new java.awt.Dimension(59, 27));
        Cancel.setMinimumSize(new java.awt.Dimension(59, 27));
        Cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CancelMouseClicked(evt);
            }
        });
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Redo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Upload, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Redo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Upload)
                    .addComponent(Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Cancel, Edit, Upload});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        //set save state = false if user cancel to edit comment
        EditComment.savedState = false;

        //clear image file name
        EditComment.setImageFileName("");
        dispose();
    }//GEN-LAST:event_CancelActionPerformed

    private void EditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_EditMouseClicked

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        if (jTextArea1.getText().replaceAll("\\s", "").equals("")) {
            //show error emssage if comment text is empty
            JOptionPane.showMessageDialog(null, "Warning! Comment must be non-empty to be posted", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //get current timestamp at which comment is posted
            Date date = new Date();
            long currentTimeStamp = date.getTime() / 1000L;

            //initialize new comment
            //id = 0 because irrelevant 
            Comment newComment = new Comment(0, jTextArea1.getText(), currentTimeStamp, Login.getUsername());

            //show error emssage if comment text is not edited in any way
            if (jTextArea1.getText().equals(oldComment.getText())) {
                JOptionPane.showMessageDialog(null, "Warning! You need to edit the text to update it", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //if image is uploaded
                if (!imageFileName.equals("")) {
                    try {
                        InputStream in = new FileInputStream(imageFileName);
                        commentDAO.editComment(newComment, oldComment, (FileInputStream) in);
                        JOptionPane.showMessageDialog(null, "Your comment has been edited");
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(CreateComment.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(CreateComment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } //if image is not uploaded
                else {
                    try {
                        commentDAO.editComment(newComment, oldComment, emptyImage);
                        JOptionPane.showMessageDialog(null, "Your comment has been edited");
                    } catch (SQLException ex) {
                        Logger.getLogger(CreateComment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //set saved state = false and clear file name
                EditComment.savedState = false;
                EditComment.setImageFileName("");
                dispose();
            }
        }
    }//GEN-LAST:event_EditActionPerformed

    private void UploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UploadMouseClicked
        try {
            //create new instance of edit image
            new EditImage(null, false, oldComment, imageFileName, savedState);
        } catch (SQLException ex) {
            Logger.getLogger(EditComment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UploadMouseClicked

    private void UndoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UndoMouseClicked
        //if undoStack size > 1 (since size = 1 is default)
        if (undoStack.size() > 1) {
            //if top of undoStack is not equal to text
            if (!undoStack.peek().equals(jTextArea1.getText())) {
                //adds the text into the undoStack
                undoStack.add(jTextArea1.getText());
            }
            //pops the undoStack and add into redoStack
            redoStack.add(undoStack.pop());

            //set the Redo button as enabled
            Redo.setEnabled(true);

            //sets the text to top of undoStack
            jTextArea1.setText(undoStack.peek());
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

    private void RedoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RedoMouseClicked
        //if redoStack is not empty
        if (!redoStack.isEmpty()) {
            //add top of redoStack to undoStack and set text as top of redoStack
            undoStack.add(redoStack.peek());
            Undo.setEnabled(true);
            jTextArea1.setText(redoStack.pop());
        }
        //if redoStack empty set enabled as false
        if (redoStack.isEmpty()) {
            Redo.setEnabled(false);
        }
    }//GEN-LAST:event_RedoMouseClicked

    private void CancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelMouseClicked
        dispose();
    }//GEN-LAST:event_CancelMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //set saved state = false if user close the window
        EditComment.savedState = false;

        //clear image file name
        EditComment.setImageFileName("");
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(EditComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new EditComment().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel;
    private javax.swing.JButton Edit;
    private javax.swing.JButton Redo;
    private javax.swing.JButton Undo;
    private javax.swing.JButton Upload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
