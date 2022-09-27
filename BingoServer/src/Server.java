
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//Δημιουργούμε την κλάση οπου θα υλοποιήσουμε τις λειτουργίες της διεπαφής του εξυπηρετητή
public class Server extends UnicastRemoteObject implements BingoOpperations {//Κληρονομεί επισής απο την κλάση UnicastRemoteObject

    private HashMap<String, String> users = new HashMap<String, String>();//Μια δομή αποθήκευσης hashmap (Για να εξασφαλίσουμε οτι οι χρήστες θα έχουν μοναδικά στοιχεία)
    //Στην προκειμένη περίπτωση έχουμε βάλει να έχουν μοναδικό username

    public Server() throws IOException, FileNotFoundException, ClassNotFoundException {//Με το που ξεκινάει ο Server και καλείτε ο constructor κάνουμε load απο ένα αρχείο το hashmap
        load();
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {//Μέθοδος που δέχεται ένα username και ένα password
        if (users.containsKey(username)) {//Αν το όνομα υπάρχει στο hashmap
            if (users.get(username).equals(password)) {//Ελέγχουμε αν ταιρίαζει και ο κωδικός για το συγκεκριμένο username
                return true;//αν ισχύει επιστρέφουμε true
            }
        }
        return false;//αν όχι επιστρέφουμε false
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {//Μέθοδος που δέχεται ένα username και ένα password
        if (users.containsKey(username) == false) {//Αν το username που δίνει ο χρήστης για να εγγραφεί δεν υπάρχει ήδη στο hashmap
            users.put(username, password);//Κάνουμε εγγραφή στο hashmap με τα στοιχεία που έδωσε
            try {
                save();//κάνουμε save
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } else {
            System.out.println("Username already exist!");//Αν υπάρχει ήδη άλλος χρήστης με το ίδιο username εμφανίζουμε μύνημα Λάθους
            return false;
        }
    }

    public void save() throws FileNotFoundException, IOException {//Μέθοδος για να γράφουμε το hashmap σε ένα αρχείο αντικειμένων 
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Users"));//Δηλώνουμε μια ροή για να γράψουμε objects σε ενα αρχείο με όνομα Users
        out.writeObject(users);//Γράφουμε μέσα την δομή αποθήκευσης hashmap
        out.close();//κλείνουμε την ροή
    }

    public void load() throws FileNotFoundException, IOException, ClassNotFoundException {//Επιστρέφει μια δομή αποθήκευσης HashMap
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Users"));//Δηλώνουμε μια ροή για να διαβάσουμε objects απο ενα αρχείο με όνομα Users
        users = (HashMap<String, String>) in.readObject();//Αποθηκέυουμε στο hashmap με τους users το αντικείμενο ιδιου τύπου που διαβάσαμε απο το αρχείο
        in.close();//κλείνουμε την ροή
    }

    @Override
    public ArrayList<String> winners() {
        String name = "//localhost/WinnerDirectory";//Η διεύθυνση που βρίσκεται η αναφορά του αντικειμένου
        ArrayList<String> winners = new ArrayList<String>();//δημιουργία λίστας για τους νικητές
        try {
            WinnerOpperations look_op = (WinnerOpperations) Naming.lookup(name);//Δημιουργούμε αντικείμενο της διεπαφής, το οποίο θα χρήσιμοποιήσουμε για να αναφερθουμε προς το αποκρυσμένο αντικείμενο
            winners = look_op.winners();//καλούμε την μέθοδο winners()
        } catch (NotBoundException ex) {//Οι απαραίτητες εξαιρέσεις
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return winners;
    }

    @Override
    public String[][] bingoTicket() throws RemoteException {//Μέθοδος που επιστρέφει εναν 2d String πίνακα (το δελτίο)
        Random rand = new Random();//Φτιαχνουμέ ενα αντικείμενο Random που θα χρησιμοποιήσουμε για την τυχαιότητα των αριθμών
        String[][] board = new String[6][5]; // Δηλώνουμε εναν Πίνακα τύπου String
        board[0][0] = "B";//Στην πρώτη γραμμή βάζουμε ένα - ένα τα γράμματα για το BINGO
        board[0][1] = "I";
        board[0][2] = "N";
        board[0][3] = "G";
        board[0][4] = "O";
        for (int i = 1; i < 6; i++) {//Γεμίζουμε τον πίνακα με τυχαίους αριθμους (σε μορφή String με την βοήθεια της toString)
            for (int j = 0; j < 5; j++) {
                if (j == 0) {
                    int x = 1 + rand.nextInt(15);//1η στήλη απο το 1 μεχρί το 15
                    board[i][j] = Integer.toString(x);
                } else if (j == 1) {//2η στήλη απο το 16 μεχρί το 30
                    int x = 16 + rand.nextInt(15);
                    board[i][j] = Integer.toString(x);
                } else if (j == 2) {//3η στήλη απο το 31 μεχρί το 45
                    int x = 31 + rand.nextInt(15);
                    board[i][j] = Integer.toString(x);
                } else if (j == 3) {//4η στήλη απο το 46 μεχρί το 60
                    int x = 46 + rand.nextInt(15);
                    board[i][j] = Integer.toString(x);
                } else {//5η στήλη απο το 61 μεχρί το 75
                    int x = 61 + rand.nextInt(15);
                    board[i][j] = Integer.toString(x);
                }
            }
        }
        board[3][2] = " ";//Στο κεντρικό κελί βάζουμε έναν κενό χαρακτήρα
        return board;//Επιστρέφουμε τον πίνακα
    }

    @Override
    public int lotteryNumber() throws RemoteException {//Μέθοδος που επιστρέφει τον τυχερό αριθμό
        Random rand = new Random();//Φτιαχνουμέ ενα αντικείμενο Random που θα χρησιμοποιήσουμε για την τυχαιότητα των αριθμών
        int x = rand.nextInt(75) + 1;//Παίρνουμε έναν αριθμό απο το 1 μεχρί το 75
        return x;//τον επιστρέφουμε
    }

    @Override
    public boolean bingoCheck(String board[][], ArrayList<Integer> numbers) throws RemoteException {//Μέθοδος που δέχεται το δελτίο bingo και μια λίστα με τους τυχερους αριθμούς μέχρι εκείνη την ώρα και ελέγχει αν ο παίκτης έχει κάνει bingo
        boolean win = false;//Μεταβλητή που θα γίνει true αν ο χρήστης έχει κάνει bingo
        if (numbers.contains(Integer.parseInt(board[1][0])) && numbers.contains(Integer.parseInt(board[5][0])) && numbers.contains(Integer.parseInt(board[1][4])) && numbers.contains(Integer.parseInt(board[5][4]))) {
            win = true;//Ελέγχουμε αν οι 4 γωνίες του πίνακα υπαρχούν στην λίστα με τους τυχερούς αριθμούς (κάνουμε το String σε int με την κλήση της  parseInt)
        }
        int ok1 = 0;//Δηλώνουμε 5 μεταβλητές που χρήσιμέυουν ως μετρητές 
        int ok2 = 0;
        int ok3 = 0;
        int ok4 = 0;
        int ok5 = 0;
        for (int i = 1; i < 6; i++) {//Ελέγχουμε μια μια όλες τις σείρες και αν κάποιος αριθμός απο τον πίνακα υπάρχει στην λίστα, τότε αυξάνουμε τον μετρητή κατα 1
            if (numbers.contains(Integer.parseInt(board[i][0]))) {
                ok1++;
            }
            if (numbers.contains(Integer.parseInt(board[i][1]))) {
                ok2++;
            }
            if (board[i][2].equals(" ") == false) {
                if (numbers.contains(Integer.parseInt(board[i][2]))) {
                    ok3++;
                }
            }
            if (numbers.contains(Integer.parseInt(board[i][3]))) {
                ok4++;
            }
            if (numbers.contains(Integer.parseInt(board[i][4]))) {
                ok5++;
            }
            if (ok1 == 5 || ok2 == 5 || ok3 == 4 || ok4 == 5 || ok5 == 5) {//Αν κάποιος απο τους μετρήτες είναι 5 (ή ο μεσαίος 4 λόγο του κενού που είναι μπαλαντέρ)
                win = true;//έχει κάνει bingo
                break;
            }
        }
        ok1 = 0;//Δηλώνουμε 5 μεταβλητές που χρήσιμέυουν ως μετρητές 
        ok2 = 0;
        ok3 = 0;
        ok4 = 0;
        ok5 = 0;
        for (int i = 0; i < 5; i++) {//Ελέγχουμε μια μια όλες τις στήλες και αν κάποιος αριθμός απο τον πίνακα υπάρχει στην λίστα, τότε αυξάνουμε τον μετρητή κατα 1
            if (numbers.contains(Integer.parseInt(board[1][i]))) {
                ok1++;
            }
            if (numbers.contains(Integer.parseInt(board[2][i]))) {
                ok2++;
            }
            if (board[3][i].equals(" ") == false) {
                if (numbers.contains(Integer.parseInt(board[3][i]))) {
                    ok3++;
                }
            }
            if (numbers.contains(Integer.parseInt(board[4][i]))) {
                ok4++;
            }
            if (numbers.contains(Integer.parseInt(board[5][i]))) {
                ok5++;
            }
            if (ok1 == 5 || ok2 == 5 || ok3 == 4 || ok4 == 5 || ok5 == 5) {//Αν κάποιος απο τους μετρήτες είναι 5 (ή ο μεσαίος 4 λόγο του κενού που είναι μπαλαντέρ)
                win = true;//έχει κάνει bingo
                break;
            }
        }//Τέλος ελέγχουμε και τις 2 διαγωνίους 
        if (numbers.contains(Integer.parseInt(board[1][0])) && numbers.contains(Integer.parseInt(board[2][1])) && numbers.contains(Integer.parseInt(board[4][3])) && numbers.contains(Integer.parseInt(board[5][4]))) {
            win = true;//έχει κάνει bingo
        }
        if (numbers.contains(Integer.parseInt(board[1][4])) && numbers.contains(Integer.parseInt(board[2][3])) && numbers.contains(Integer.parseInt(board[4][1])) && numbers.contains(Integer.parseInt(board[5][0]))) {
            win = true;//έχει κάνει bingo
        }

        return win;//Επιστρέφουμε την μεταβλητή
    }

    @Override
    public void addWinner(String username) throws RemoteException {//Μέθοδος που δέχεται το όνομα ενος νικητή και το προσθέτει στην λίστα
        String name = "//localhost/WinnerDirectory";//Η διεύθυνση που βρίσκεται η αναφορά του αντικειμένου
        try {
            WinnerOpperations look_op = (WinnerOpperations) Naming.lookup(name);//Δημιουργούμε αντικείμενο της διεπαφής, το οποίο θα χρήσιμοποιήσουμε για να αναφερθουμε προς το αποκρυσμένο αντικείμενο
            look_op.addWinner(username);//καλούμε την κατάλληλη μέθοδο
        } catch (NotBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
