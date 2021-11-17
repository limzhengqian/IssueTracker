package BugsLife;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class IndividualComment extends javax.swing.JFrame {

    private ArrayList<React> react;
    private ReactsDAO reactDAO;
    private CommentsDAO commentDAO;
    private Comment comment;
    private String individualReaction;
    private BufferedImage img;
    private ArrayList<CommentLog> commentLog;
    
    /**
     * Creates new form IndiComment
     */
    public IndividualComment(Comment individualComment) throws MalformedURLException, SQLException, IOException {
        this.commentDAO = new CommentsDAO();
        comment = individualComment;
        this.reactDAO = new ReactsDAO();
        this.react = reactDAO.getAllReact((int) individualComment.getComment_id());
        this.individualReaction = reactDAO.getIndividualReact((int) comment.getComment_id(), Login.userName);
        this.commentLog = commentDAO.getCommentLog((int) individualComment.getComment_id());
        
        initComponents();
        
        //scale image for logo
        scaleImageLogo();
        
        //scale image for the 3 reaction icons
        scaleImage();

        //check whether commemt has image or not
        checkImage();
        
        //check the reation of user who is current logged in to the comment
        checkReact();

        Border blackline = BorderFactory.createLineBorder(Color.black);
        Border margin = new EmptyBorder(3, 5, 3, 5);
        jLabel1.setBorder(new CompoundBorder(blackline, margin));

        this.setLayout(new java.awt.BorderLayout());
        this.add(jPanel1, java.awt.BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    
    //Scales the image of the logo
    private void scaleImageLogo() {
        ImageIcon imageIcon = new ImageIcon("virus.png");
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(jLabel7.getWidth() - 5, jLabel7.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        jLabel7.setIcon(imageIcon);

    }
    
    //scale the image of the 3 reaction icons
    private void scaleImage() {
        ImageIcon icon = new ImageIcon("thumbsup.png");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(jLabel3.getWidth() - 5, jLabel3.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        jLabel3.setIcon(icon);

        icon = new ImageIcon("happy.png");
        img = icon.getImage();
        newImg = img.getScaledInstance(jLabel4.getWidth() - 5, jLabel4.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        jLabel4.setIcon(icon);
        
        icon = new ImageIcon("angry.png");
        img = icon.getImage();
        newImg = img.getScaledInstance(jLabel6.getWidth() - 5, jLabel6.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        jLabel6.setIcon(icon);
    }

    //check whether comment has image or not
    //set image if image is not null
    private void checkImage() {
        if (comment.getImg() != null) {
            ImageIcon icon = new ImageIcon(comment.getImg());
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(jLabel2.getWidth(), jLabel2.getHeight(), Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);
            //set the image as icon of jlabel
            jLabel2.setIcon(icon);
        }
    }

    //check reaction of user to the comment from database and update the buttons
    private void checkReact() throws SQLException {
        //get the reaction from database
        this.individualReaction = reactDAO.getIndividualReact((int) comment.getComment_id(), Login.userName);

        //if no reaction
        if (individualReaction.equals("")) {
            angry.setText("Angry");
            happy.setText("Happy");
            thumbsup.setText("Thumbs Up");
        } 
        
        //if user has reaction angry
        else if (individualReaction.equals("angry")) {
            angry.setText("Un-Angry");
            happy.setText("Happy");
            thumbsup.setText("Thumbs Up");
            happy.setEnabled(false);
            thumbsup.setEnabled(false);
        } 
        
        //if user has reacted happy
        else if (individualReaction.equals("happy")) {
            angry.setText("Angry");
            happy.setText("Un-Happy");
            thumbsup.setText("Thumbs Up");
            angry.setEnabled(false);
            thumbsup.setEnabled(false);
        } 
        
        //if user reacted a thumbs up
        else if (individualReaction.equals("thumbs up")) {
            angry.setText("Angry");
            happy.setText("Happy");
            thumbsup.setText("Un-Thumbs Up");
            angry.setEnabled(false);
            happy.setEnabled(false);
        }
    }

    //refreshes the jframe
    private void refresh() throws MalformedURLException, SQLException, IOException {
        this.dispose();
        new IndividualComment(comment);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        angry = new javax.swing.JButton();
        happy = new javax.swing.JButton();
        thumbsup = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        changelog = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText(comment.toString() + "\n\n" + "Reaction: "+ react.toString());
        jTextArea1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel1.setText("React");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        angry.setBackground(new java.awt.Color(255, 51, 51));
        angry.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        angry.setText("jButton1");
        angry.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        angry.setFocusable(false);
        angry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                angryMouseClicked(evt);
            }
        });

        happy.setBackground(new java.awt.Color(255, 255, 0));
        happy.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        happy.setText("jButton2");
        happy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        happy.setFocusable(false);
        happy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                happyMouseClicked(evt);
            }
        });

        thumbsup.setBackground(new java.awt.Color(0, 204, 51));
        thumbsup.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        thumbsup.setText("jButton3");
        thumbsup.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        thumbsup.setFocusable(false);
        thumbsup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thumbsupMouseClicked(evt);
            }
        });

        edit.setBackground(new java.awt.Color(0, 153, 255));
        edit.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        edit.setForeground(new java.awt.Color(255, 255, 255));
        edit.setText("Edit Comment");
        edit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        edit.setFocusable(false);
        edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
        });

        changelog.setBackground(new java.awt.Color(0, 153, 255));
        changelog.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        changelog.setForeground(new java.awt.Color(255, 255, 255));
        changelog.setText("Change Log");
        changelog.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        changelog.setFocusable(false);
        changelog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changelogActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        jLabel2.setText("                              No image to preview");

        delete.setBackground(new java.awt.Color(0, 153, 255));
        delete.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 13)); // NOI18N
        delete.setForeground(new java.awt.Color(255, 255, 255));
        delete.setText("Delete Comment");
        delete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        delete.setFocusable(false);
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
        });
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(changelog, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(thumbsup, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(happy, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(angry, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(129, 129, 129)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel5)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(changelog, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(happy))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(thumbsup, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(angry, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {happy, jLabel4});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {angry, jLabel5});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changelogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changelogActionPerformed
        if (commentLog.size() == 0){
            //show message if there is no edit history
            JOptionPane.showMessageDialog(null, "There is no edit history");
        }else{
            //create new instance of edit history if commentLog array list is not empty
            new EditHistory(null, false, commentLog);
        }
    }//GEN-LAST:event_changelogActionPerformed

    private void editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editMouseClicked
        if (!Login.userName.equals(comment.getUser())){
            //if login user is not the person who posted the comment
            //show error message when trying to edit comment
            JOptionPane.showMessageDialog(null, "Warning! You do not have access to edit other people's comments", "Error" , JOptionPane.ERROR_MESSAGE);
        }else{
            dispose();
            try {
                //create new instance of editComment
                new EditComment(comment);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_editMouseClicked

    private void thumbsupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thumbsupMouseClicked
        //if no reaction
        if (individualReaction.equals("")) {
            try {
                //add thumbs up reaction
                reactDAO.addReact("thumbs up", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted angry
        else if (individualReaction.equals("angry")) {
            try {
                //remove angry reaction and add thumbs up reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
                reactDAO.addReact("thumbs up", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted happy
        else if (individualReaction.equals("happy")) {
            try {
                //remove happy reaction and add thumbs up reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
                reactDAO.addReact("thumbs up", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted thumbs up
        else if (individualReaction.equals("thumbs up")) {
            try {
                //removes the thumbs up reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            //update individual reaction and buttons
            //then refresh the individualcomment jframe
            checkReact();
            refresh();
        } catch (MalformedURLException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_thumbsupMouseClicked

    private void happyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_happyMouseClicked
        //if no reaction
        if (individualReaction.equals("")) {
            try {
                //add happy reaction
                reactDAO.addReact("happy", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted angry
        else if (individualReaction.equals("angry")) {
            try {
                //remove the angry reaction and add happy reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
                reactDAO.addReact("happy", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted happy
        else if (individualReaction.equals("happy")) {
            try {
                //remove the happy reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted thumbs up
        else if (individualReaction.equals("thumbs up")) {
            try {
                //remove the thumbs up reaction and add happy reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
                reactDAO.addReact("happy", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            //update individual reaction and buttons
            //then refresh the individualcomment jframe
            checkReact();
            refresh();
        } catch (MalformedURLException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_happyMouseClicked

    private void angryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_angryMouseClicked
        //if no reaction
        if (individualReaction.equals("")) {
            try {
                //add angry reaciton the database
                reactDAO.addReact("angry", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted angry
        else if (individualReaction.equals("angry")) {
            try {
                //remove the angry reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted happy
        else if (individualReaction.equals("happy")) {
            //remove the happy reaction and add angry reaction
            try {
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
                reactDAO.addReact("angry", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //if already reacted thumbs up
        else if (individualReaction.equals("thumbs up")) {
            try {
                //remove the thumbs up and add angry reaction
                reactDAO.removeReact((int) comment.getComment_id(), Login.userName);
                reactDAO.addReact("angry", (int) comment.getComment_id(), Login.userName);
            } catch (SQLException ex) {
                Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            //update individual reaction and buttons
            //then refresh the individualcomment jframe
            checkReact();
            refresh();
        } catch (MalformedURLException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_angryMouseClicked

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        //give warning if user is not the one who posted the comment
        if (!Login.userName.equals(comment.getUser())){
            JOptionPane.showMessageDialog(null, "Warning! You do not have access to delete other people's comments", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //prompt user to confirm whether to delete comment or not
        else{
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the comment?", "Select an option", JOptionPane.YES_NO_CANCEL_OPTION);
            //0 - yes, 1 - no, 2 - cancel
            if (choice == 0){
                try {
                    commentDAO.deleteComment((int) comment.getComment_id());
                } catch (SQLException ex) {
                    Logger.getLogger(IndividualComment.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        }
    }//GEN-LAST:event_deleteActionPerformed

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
            java.util.logging.Logger.getLogger(IndividualComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IndividualComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IndividualComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IndividualComment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                //    new IndividualComment(0, 0, 0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton angry;
    private javax.swing.JButton changelog;
    private javax.swing.JButton delete;
    private javax.swing.JButton edit;
    private javax.swing.JButton happy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton thumbsup;
    // End of variables declaration//GEN-END:variables
}
