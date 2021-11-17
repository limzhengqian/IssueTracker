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
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class WeeklyReport extends javax.swing.JFrame {

    /**
     * Creates new form DisplayReport
     */
    private String issueChoice;
    private int maxTag;
    private String maxTagname;
    private String chartOption;
    private String statuschartOption;
    private int issueResolved, issueinProgress, issueUnresolved;
    private int issueResolved1, issueResolved2, issueResolved3, issueResolved4, issueResolved5, issueResolved6, issueResolved7, issueUnresolved1, issueUnresolved2, issueUnresolved3, issueUnresolved4, issueUnresolved5, issueUnresolved6, issueUnresolved7, issueinProgress1, issueinProgress2, issueinProgress3, issueinProgress4, issueinProgress5, issueinProgress6, issueinProgress7;
    private int maxscore = 0;
    private String maxname = "";
    private ArrayList<String> maxscorenamelist = new ArrayList();
    private String month;
    private String[][] arrTag;
    private int issue1, issue2, issue3, issue4, issue5, issue6, issue7;
    private int week;
    private IssuesDAO iss;
    private UsersDAO user;
    private ArrayList<User> usslist;
    private ArrayList<Issue> isslist;
    private ArrayList<Issue> issueofsameweek = new ArrayList();

    public WeeklyReport() {
        initComponents();
    }

    WeeklyReport(int year, String month, int week, int projectIndex) throws SQLException {
        //initialise userDAO
        this.user = new UsersDAO();
        //pass all user list from database into Java ArrayList
        usslist = user.getAllUsers();
        //get month from previous class
        this.month = month;
        //get week from previous class
        this.week = week;
        //initialise IssuesDAO
        iss = new IssuesDAO();
        //get all issue of certain project ID from database and pass into Java ArrayList
        isslist = iss.getAllIssue(projectIndex + 1);
        //set current week as 0
        int currentweek = 0;
        //loop through the issue list 
        for (int i = 0; i < isslist.size(); i++) {
            //get current issue's timestamp
            long issueTimeStamp = isslist.get(i).getTimestamp();
            //convert timestamp into Date
            Date date1 = new Date(issueTimeStamp * 1000L);
            //convert Date into LocalDate
            LocalDate date = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //current issue's year == year from previous class
            if (date.getYear() == year) {
                //if current issue's month same as month from previous class
                if (date.getMonth().toString().equalsIgnoreCase(month)) {
                    //if issue day is 1-7
                    if (date.getDayOfMonth() > 0 && date.getDayOfMonth() <= 7) {
                        //week1
                        currentweek = 1;
                    } //if issue day is 8 - 14
                    else if (date.getDayOfMonth() > 7 && date.getDayOfMonth() <= 14) {
                        //week2
                        currentweek = 2;
                    } //if issue day is 15 - 21
                    else if (date.getDayOfMonth() > 14 && date.getDayOfMonth() <= 21) {
                        //week3
                        currentweek = 3;
                    } //if issue day is 22 - 28
                    else if (date.getDayOfMonth() > 21 && date.getDayOfMonth() <= 28) {
                        //week4
                        currentweek = 4;
                    } else {
                        //else week5
                        currentweek = 5;
                    }
                    //if issue week same as week from previous class
                    if (week == currentweek) {
                        //add into the ArryList
                        issueofsameweek.add(isslist.get(i));
                    }
                }
            }
        }
        //call method generateReport() to determine value of all details needed to be displayed
        generatereport();
        //initialise components of the GUI
        initComponents();
        this.setLocationRelativeTo(null);
        //if no issue of this week, notify user
        if (issueofsameweek.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nothing happened this week", "Empty Week", JOptionPane.PLAIN_MESSAGE);
            this.dispose();
        } else {
            this.setVisible(true);
        }

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    //update detail for report display
    private void generatereport() {
        //get top scorer
        //**********************************************************************************************************************************************************
        //loop through the user ArrayList
        for (int i = 0; i < usslist.size(); i++) {
            //if current user point higher than max score
            if (usslist.get(i).getPoints() > maxscore) {
                ////clear the max score list
                maxscorenamelist.clear();
                //add the maxscore user into the max score list
                maxscorenamelist.add(usslist.get(i).getUsername());
                //update max score to the maxscore user's point
                maxscore = usslist.get(i).getPoints();
            } //if user's point is equal to max score, means multiple winners exist
            else if (usslist.get(i).getPoints() == maxscore) {
                //add the user into the maxscorelist
                maxscorenamelist.add(usslist.get(i).getUsername());
            }
        }
        //set the maxscorename into a string to be displayed
        for (int i = 0; i < maxscorenamelist.size(); i++) {
            maxname += maxscorenamelist.get(i) + " ";
        }
        //get Tag Count
        //**********************************************************************************************************************************************************
        //initialise a ArrayList to store tag
        ArrayList<String> list = new ArrayList();
        //loop through each issue's tag Arraylist
        for (int i = 0; i < issueofsameweek.size(); i++) {
            //if the tag of current issue is not in the list
            System.out.println("Tag size: " + issueofsameweek.get(i).getTag().size());
            for (int j = 0; j < issueofsameweek.get(i).getTag().size(); j++) {
                //if the tag of current issue is not in the list
                if (!list.contains(issueofsameweek.get(i).getTag().get(j))) {
                    if (issueofsameweek.get(i).getTag().get(j).equalsIgnoreCase("")) {
                        if (!list.contains("Untag")) {
                            list.add("Untag");
                        }
                    } else {
                        //add into the list
                        list.add(issueofsameweek.get(i).getTag().get(j));
                    }
                }
            }
        }

        //initialise a 2D array to differentiate count and tag name
        arrTag = new String[list.size()][2];
        //loop throught the 2D arary
        for (int i = 0; i < arrTag.length; i++) {
            //add the tag from ArrayList into the 2D array and and initialise tag count as 0
            arrTag[i][0] = list.get(i);
            arrTag[i][1] = "0";
        }

        //loop through list with issue of same day 
        for (int i = 0; i < issueofsameweek.size(); i++) {
            //loop through tag of current issue
            for (int j = 0; j < issueofsameweek.get(i).getTag().size(); j++) {
                //loop the 2D array
                for (int k = 0; k < arrTag.length; k++) {
                    //if the tag of 2D array is found in the current issue tag
                     if(issueofsameweek.get(i).getTag().get(j).equalsIgnoreCase("") && arrTag[k][0].equalsIgnoreCase("Untag")){
                        int count = Integer.parseInt(arrTag[k][1]);
                        count++;
                        arrTag[k][1] = Integer.toString(count);
                    }
                    //if the tag of 2D array is found in the current issue tag
                    else if (issueofsameweek.get(i).getTag().get(j).equalsIgnoreCase(arrTag[k][0])) {
                        //increase the count of tag insdie 2D array by one
                        int count = Integer.parseInt(arrTag[k][1]);
                        count++;
                        arrTag[k][1] = Integer.toString(count);
                    }
                }
            }
        }

        //determine the tag that appears the most 
        //**********************************************************************************************************************************************************
        //loop through the 2D array
        for (int i = 0; i < arrTag.length; i++) {
            //if count of current tag> max tag, replace it
            if (Integer.parseInt(arrTag[i][1]) > maxTag) {
                maxTagname = arrTag[i][0];
                maxTag = Integer.parseInt(arrTag[i][1]);
            } //if there exist a tag of same ammount, stack it
            else if (Integer.parseInt(arrTag[i][1]) == maxTag) {
                maxTagname = maxTagname + " " + arrTag[i][0];
            }
        }

        //determine the count of status 
        //**********************************************************************************************************************************************************
        //loop all the issue in the same month
        for (int i = 0; i < issueofsameweek.size(); i++) {
            if (issueofsameweek.get(i).getStatus().equalsIgnoreCase("In progress")) {
                issueinProgress++;
            } else if (issueofsameweek.get(i).getStatus().equalsIgnoreCase("Open")) {
                issueUnresolved++;
            } else if (issueofsameweek.get(i).getStatus().equalsIgnoreCase("Closed")) {
                issueResolved++;
            }
        }
        //determine the count of status for each dat
        //**********************************************************************************************************************************************************
        //loop all the issue in the same month
        for (int i = 0; i < issueofsameweek.size(); i++) {
            //get current issue timestamp
            long timestamp = issueofsameweek.get(i).getTimestamp();
            //convert timestamp to Date
            Date date = new Date(timestamp * 1000L);
            //convert Date to LocalDate
            LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //if issue status is in progress
            if (issueofsameweek.get(i).getStatus().equalsIgnoreCase("In Progress")) {
                //if day is monday
                if (ld.getDayOfWeek().toString().equalsIgnoreCase("monday")) {
                    //monday issue++ and inprogressmonday++
                    issue1++;
                    issueinProgress1++;
                } //else if is tuesday
                else if (ld.getDayOfWeek().toString().equalsIgnoreCase("tuesday")) {
                    //tuesday issue++ and inprogresstuesday++
                    issue2++;
                    issueinProgress2++;
                } //else if is wednesday
                else if (ld.getDayOfWeek().toString().equalsIgnoreCase("wednesday")) {
                    //wednesday issue++ and inprogresswednesday++
                    issue3++;
                    issueinProgress3++;
                } //else if is thursday
                else if (ld.getDayOfWeek().toString().equalsIgnoreCase("thursday")) {
                    //thrusday issue++ and inprogressthursday++
                    issue4++;
                    issueinProgress4++;
                } //else if is friday
                else if (ld.getDayOfWeek().toString().equalsIgnoreCase("Friday")) {
                    //friday issue++ and inprogressfriday++
                    issue5++;
                    issueinProgress5++;
                } //else if is saturday
                else if (ld.getDayOfWeek().toString().equalsIgnoreCase("saturday")) {
                    //saturday issue++ and inprogresssaturday++
                    issue6++;
                    issueinProgress6++;
                } //else if is sunday
                else if (ld.getDayOfWeek().toString().equalsIgnoreCase("Sunday")) {
                    //sunday issue++ and inprogresssunday++
                    issue7++;
                    issueinProgress7++;
                }
            } //repeat same thing for open and close
            else if (issueofsameweek.get(i).getStatus().equalsIgnoreCase("Open")) {
                if (ld.getDayOfWeek().toString().equalsIgnoreCase("monday")) {
                    issue1++;
                    issueUnresolved1++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("tuesday")) {
                    issue2++;
                    issueUnresolved2++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("wednesday")) {
                    issue3++;
                    issueUnresolved3++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("thursday")) {
                    issue4++;
                    issueUnresolved4++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("Friday")) {
                    issue5++;
                    issueUnresolved5++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("saturday")) {
                    issue6++;
                    issueUnresolved6++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("Sunday")) {
                    issue7++;
                    issueUnresolved7++;
                }
            } else if (issueofsameweek.get(i).getStatus().equalsIgnoreCase("Closed")) {
                if (ld.getDayOfWeek().toString().equalsIgnoreCase("monday")) {
                    issue1++;
                    issueResolved1++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("tuesday")) {
                    issue2++;
                    issueResolved2++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("wednesday")) {
                    issue3++;
                    issueResolved3++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("thursday")) {
                    issue4++;
                    issueResolved4++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("Friday")) {
                    issue5++;
                    issueResolved5++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("saturday")) {
                    issue6++;
                    issueResolved6++;
                } else if (ld.getDayOfWeek().toString().equalsIgnoreCase("Sunday")) {
                    issue7++;
                    issueResolved7++;
                }
            }
        }
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText(month+" Report Week "+week);

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 16)); // NOI18N
        jLabel2.setText("Star User of The Week: "+maxname);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel3.setText("Number of Issues This Week: "+issueofsameweek.size());

        jComboBox3.setBackground(new java.awt.Color(153, 204, 255));
        jComboBox3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bar Chart","Pie Chart","Line Graph" }));
        jComboBox3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jComboBox3.setFocusable(false);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 153, 255));
        jButton3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText(" Generate Graph");
        jButton3.setToolTipText("");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Semibold", 3, 14)); // NOI18N
        jLabel12.setText("Generate Issue Graph Here");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Issues In Progress: "+issueinProgress);

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("Issue Unresolved: "+issueUnresolved);

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setText("Issues Resolved: "+issueResolved);

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 3, 14)); // NOI18N
        jLabel8.setText("Generate Status Graph Here");

        jComboBox2.setBackground(new java.awt.Color(153, 204, 255));
        jComboBox2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bar Chart","Pie Chart","Line Graph" }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jComboBox2.setFocusable(false);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 153, 255));
        jButton2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Generate Graph");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semibold", 3, 14)); // NOI18N
        jLabel10.setText("Most Common Tag of the Week:");

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel9.setText("Tag Name: "+maxTagname);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel11.setText("Tag Numbers: "+maxTag);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 3, 14)); // NOI18N
        jLabel4.setText("Generate Tag Graph Here");

        jComboBox1.setBackground(new java.awt.Color(153, 204, 255));
        jComboBox1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bar Chart","Pie Chart","Line Graph" }));
        jComboBox1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jComboBox1.setFocusable(false);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Generate Graph");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel13.setText("Weekly Report");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(35, 35, 35)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //method to retrive choice of status chart from user
    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        statuschartOption = jComboBox2.getSelectedItem().toString();
    }//GEN-LAST:event_jComboBox2ActionPerformed
    //method to detect mouse click and generate status chart based on user choice
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //if bar chart is choosen, display data in bar chart
        if (statuschartOption.equalsIgnoreCase("Bar Chart")) {
            new DisplayBar(issueinProgress, issueResolved, issueUnresolved);
        }//if pie chart is choosen, display data in pie chart
        else if (statuschartOption.equalsIgnoreCase("Pie Chart")) {
            new DisplayPie(issueinProgress, issueResolved, issueUnresolved);
        }//if line graph is choosen, display data in line graph
        else if (statuschartOption.equalsIgnoreCase("Line Graph")) {
            new DisplayLine(issueResolved1, issueResolved2, issueResolved3, issueResolved4, issueResolved5, issueResolved6, issueResolved7, issueUnresolved1, issueUnresolved2, issueUnresolved3, issueUnresolved4, issueUnresolved5, issueUnresolved6, issueUnresolved7, issueinProgress1, issueinProgress2, issueinProgress3, issueinProgress4, issueinProgress5, issueinProgress6, issueinProgress7);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    //method to retrive choice of tag chart from user
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        chartOption = jComboBox1.getSelectedItem().toString();
    }//GEN-LAST:event_jComboBox1ActionPerformed
    //method to detect mouse click and generate tag chart based on user choice
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //if bar chart is choosen, display data in bar chart
        if (chartOption.equalsIgnoreCase("Bar Chart")) {
            new DisplayBar(arrTag);
        }//if pie chart is choosen, display data in pie chart
        else if (chartOption.equalsIgnoreCase("Pie Chart")) {
            new DisplayPie(arrTag);
        }//if line graph is choosen, display data in line graph
        else if (chartOption.equalsIgnoreCase("Line Graph")) {
            new DisplayLine(arrTag);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    //method to retrive choice of issue chart from user
    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
        issueChoice = jComboBox3.getSelectedItem().toString();
    }//GEN-LAST:event_jComboBox3ActionPerformed
    //method to detect mouse click and generate issue chart based on user choice
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //if bar chart is choosen, display data in bar chart
        if (issueChoice.equalsIgnoreCase("Bar Chart")) {
            new DisplayBar(issue1, issue2, issue3, issue4, issue5, issue6, issue7);
        } //if pie chart is choosen, display data in pie chart
        else if (issueChoice.equalsIgnoreCase("Pie Chart")) {
            new DisplayPie(issue1, issue2, issue3, issue4, issue5, issue6, issue7);
        }//if line graph is choosen, display data in line graph
        else if (issueChoice.equalsIgnoreCase("Line Graph")) {
            new DisplayLine(issue1, issue2, issue3, issue4, issue5, issue6, issue7);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(WeeklyReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WeeklyReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WeeklyReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WeeklyReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WeeklyReport().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration//GEN-END:variables

}
