
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;


public class Bingo extends javax.swing.JFrame {

    private boolean win = false;//Μεταβλητή που θα γίνει true αν ο χρήστης κάνει bingo
    private Timer timer = new Timer();//Μεταβλτή για να ελέγχουμε τον χρόνο που θα αλλάζει ο τυχερός αριθμός
    private String username;//το όνομα του χρήστη
    private String[][] board = new String[6][5];//ένας πίνακας για το δελτίο
    private ArrayList<Integer> numbers = new ArrayList();//Μια λίστα με όλους τους τυχερούς αριθμούς μέχρι εκείνη την στιγμή
    private JTable table;//Ένα jtable για να εκτυπώσουμε το δελτίο με γραφικά
    private int rowz;//Μεταβλητές για τις γραμμές και τις στήλες του δελτίου
    private int col;


    public Bingo(String username) {//Concstructor που δέχεται ως όρισμα το όνομα του χρήστη
        initComponents();
        this.username = username;
        start();
    }

    public void start() {
        String name = "//localhost/BingoDirectory";//Η διεύθυνση που βρίσκεται η αναφορά του αντικειμένου

        try {
            BingoOpperations look_op = (BingoOpperations) Naming.lookup(name);//Δημιουργούμε αντικείμενο της διεπαφής, το οποίο θα χρήσιμοποιήσουμε για να αναφερθουμε προς το αποκρυσμένο αντικείμενο
            board = look_op.bingoTicket();//Καλούμε την bingoTicket() οπου θα μας επιστρέψει εναν πίνακα 2d String( το δελτίο)
            String columns[] = {"B", "I", "N", "G", "O"};
            table = new JTable(board, columns);//Φτιάχνουμε το table 
            table.setRowHeight(50);
            /*JTextField textBox = new JTextField();//Προσπάθεια για να μπορούμε να δημαδέυουμε το δελτίο (Δεν λειτουργεί)
            TableColumn col0 = table.getColumnModel().getColumn(0);
            TableColumn col1 = table.getColumnModel().getColumn(1);
            TableColumn col2 = table.getColumnModel().getColumn(2);
            TableColumn col3 = table.getColumnModel().getColumn(3);
            TableColumn col4 = table.getColumnModel().getColumn(4);
            col0.setCellEditor(new DefaultCellEditor(textBox));
            col1.setCellEditor(new DefaultCellEditor(textBox));
            col2.setCellEditor(new DefaultCellEditor(textBox));
            col3.setCellEditor(new DefaultCellEditor(textBox));
            col4.setCellEditor(new DefaultCellEditor(textBox));*/
            table.setCellSelectionEnabled(true);
            scrollPane1.add(table);
            table.setDefaultRenderer(Object.class, new CustomModel());
            table.addMouseListener(new CustomListener());
            /*MouseListener l = new MouseListener() {//Προσπάθεια για να μπορούμε να δημαδέυουμε το δελτίο (Δεν λειτουργεί)
                @Override
                public void mouseClicked(MouseEvent e) {
                    textBox.setBackground(Color.red);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                    //table.getColumnModel().getColumn(1).setCellRenderer(new StatusColumnCellRenderer());
                    //textBox.setBackground(Color.red);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //textBox.setBackground(Color.red);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //textBox.setBackground(Color.red);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //textBox.setBackground(Color.red);
                }
            };
            table.addMouseListener(l);*/

            TimerTask action = new TimerTask() {//Φτιάχνουμε ενα αντικείμενο της κλάσης TimerTask και κάνουμε override τον contructor
                @Override
                public void run() {
                    try {
                        int x = look_op.lotteryNumber();//Με την κλήση της lotteryNumber() παίρνουμε τον τυχερό αριθμό
                        numbers.add(x);//τον προσθέτουμε στην λίστα
                        jLabel2.setText(String.valueOf(x));//Τον βάζουμε στο label(Να φαίνεται στον χρήστη)
                    } catch (RemoteException ex) {//Οι απαραίτητες εξαιρέσεις
                        Logger.getLogger(Bingo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ; 
                }
            };
            timer.scheduleAtFixedRate(action, 20000, 5000);//Αυτή η συνάρτηση θα ξεκινήσει μετα απο 20000 milsecond και θα εκτελείτε καθε 5000 milsecond(για να αλλάζει ο τυχαίος αριθμός
            //και θα εκτελείτε μεχρί να κληθεί η timer.cancel()
        } catch (NotBoundException ex) {//Οι απαραίτητες εξαιρέσεις
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new java.awt.ScrollPane();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Harrington", 1, 24)); // NOI18N
        jButton1.setText("BINGO");
        jButton1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(51, 102, 255)));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Harrington", 1, 70)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(51, 102, 255)));

        jLabel3.setFont(new java.awt.Font("Harrington", 1, 24)); // NOI18N
        jLabel3.setText("LOTTERY NUMBER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(338, 338, 338))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 980, 580));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Pac-Man\\Desktop\\icsd14092_Lab01\\Client\\pics\\Bingo.jpg")); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 800));

        setSize(new java.awt.Dimension(1216, 839));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
//Αν ο χρήστης πατήσει το κουμπί bingo
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String name = "//localhost/BingoDirectory";//Η διεύθυνση που βρίσκεται η αναφορά του αντικειμένου
        try {
            BingoOpperations look_op = (BingoOpperations) Naming.lookup(name);//Δημιουργούμε αντικείμενο της διεπαφής, το οποίο θα χρήσιμοποιήσουμε για να αναφερθουμε προς το αποκρυσμένο αντικείμενο
            win = look_op.bingoCheck(board, numbers);//καλούμε την συνάρτηση bingoCheck και δίνουμε ως όρισμα το δελτίο και την λίστα με τους μέχρι στιγμής τυχερούς αριθμούς
            if (win == true) {//Αν έχει κάνει bingo
                timer.cancel();//Ενημερώνουμε τον πελάτη να σταματήσει την παραγωγή τυχαίων αριθμών
                look_op.addWinner(username);//Προσθέτουμε τον χρήστη στην λίστα με τους νικητές
                JOptionPane.showMessageDialog(this, "YO YO YO you win!");//Εμφανίζουμε αντίστοιχο μήνυμα
                String[] options = new String[]{"Yes", "No"};
                int response = JOptionPane.showOptionDialog(null, "Do you want to Continue ?", "Congratulations", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                System.out.println(response);//Εμφανίζουμε ενα παράθυρο στον χρήστη με 2 επιλογές (1 να συνεχίσει ή να φύγει)
                if (response == 1) {//Αν πατήσει να φύγει 
                    System.exit(0);//τερματίζουμε την εφαρμογή
                } else {
                    Bingo b = new Bingo(username);//αλλιώς εμφανίζουμε καινούριο δελτίο
                    b.setVisible(true);
                    this.setVisible(false);
                }

            } else {
                JOptionPane.showMessageDialog(null, "You didnt Bingo yet!Continue Playing!");//Αν δεν έχει κάνει bingo τότε συνεχίζει κανονικά να παίζει (συνεχίζουν κα κληρόνονται κανονικα και οι τυχεροί αριθμοί)
            }
        } catch (NotBoundException ex) {//Οι απαραίτητες εξαιρέσεις
            Logger.getLogger(Bingo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Bingo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Bingo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

//Χρήσιμοποιήσαμε 2 κλάσεις οπού κάναμε και override για να σημαδεψουμε το δελτίο
    public class CustomListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent arg0) {//Αν ο χρήστης κλικάρει σε κάποιο κελί τότε εντοπίζουμε το κελί (θα είναι το board[rowz][col]
            super.mouseClicked(arg0);
            // Select the current cell
            rowz = table.getSelectedRow();
            col = table.getSelectedColumn();

            // Repaints JTable
            table.repaint();
        }
    }

    public class CustomModel extends DefaultTableCellRenderer {


        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Color c = Color.WHITE;
            if (isSelected && row == rowz & column == col) {
                c = Color.GREEN;//Και κάνουμε το  κελί πράσινο
            }
            label.setBackground(c);
            return label; 
        }
    }//Δυστηχώς ενώ αλλάζει χρώμα το κελί, δεν μένει μόνιμα 


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private java.awt.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
}
