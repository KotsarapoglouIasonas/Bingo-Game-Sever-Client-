
import java.rmi.*;
import java.util.ArrayList;

public interface BingoOpperations extends Remote {//Δημιουργούμε τήν κατάλληλη διεπαφή για τον πελάτη(Client) κληρονομόντας απο την κλάση Remote και καλόντας τις αντίστειχες εξαιρέσεις

    public boolean login(String username, String password) throws RemoteException;//Μέθοδος που δέχεται ένα username και ένα password και αν αυθεντικοποιηθεί επιστρέφει ενα (true or false)

    public boolean register(String username, String password) throws RemoteException;//Μέθοδος που δέχεται ένα username και ένα password και αν γίνει η εγγραφή επιστρέφει ενα (true or false)

    public String[][] bingoTicket() throws RemoteException;//Μέθοδος που επιστρέφει εναν 2d String πίνακα (το δελτίο)

    public ArrayList<String> winners() throws RemoteException;//Μέθοδος που επιστρέφει μια λίστα με τα ονόματα των νικητών

    public int lotteryNumber() throws RemoteException;//Μέθοδος που επιστρέφει τον τυχερό αριθμό

    public boolean bingoCheck(String board[][],ArrayList<Integer> numbers) throws RemoteException;//Μέθοδος που δέχεται το δελτίο bingo και μια λίστα με τους τυχερους αριθμούς μέχρι
    //εκείνη την ώρα και ελέγχει αν ο παίκτης έχει κάνει bingo
    public void addWinner(String username) throws RemoteException;//Μέθοδος που δέχεται το όνομα ενος νικητή και το προσθέτει στην λίστα
}
