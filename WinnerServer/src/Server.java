
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//Δημιουργούμε την κλάση οπου θα υλοποιήσουμε τις λειτουργίες της διεπαφής του εξυπηρετητή
public class Server extends UnicastRemoteObject implements WinnerOpperations {//Κληρονομεί επισής απο την κλάση UnicastRemoteObject
    
    private ArrayList<String> winners = new ArrayList<String>();//Μια λίστα με τα ονόματα των νικητών
    
    public Server()throws IOException, FileNotFoundException, ClassNotFoundException { //Με το που ξεκινάει ο Server και καλείτε ο constructor κάνουμε load απο ένα αρχείο την λίστα
        try {
            load();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<String> winners() throws RemoteException {//Μέθοδος που επιστρέφει την λίστα
        return winners;
    }
        public void save() throws FileNotFoundException, IOException {//Μέθοδος για να γράφουμε την λίστα σε ένα αρχείο αντικειμένων 
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Winners"));//Δηλώνουμε μια ροή για να γράψουμε objects σε ενα αρχείο με όνομα Winners
        out.writeObject(winners);//Γράφουμε μέσα την δομή αποθήκευσης ArrayList<String>
        out.close();//κλείνουμε την ροή
    }

    public void load() throws FileNotFoundException, IOException, ClassNotFoundException {//Μέθοδος για να διαβάζουμε την λίστα απο ένα αρχείο αντικειμένων
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Winners"));//Δηλώνουμε μια ροή για να διαβάσουμε objects απο ενα αρχείο με όνομα Winners
        winners = (ArrayList<String>) in.readObject();//Αποθηκέυουμε στην λίστα με τους νικητές το αντικείμενο ιδιου τύπου που διαβάσαμε απο το αρχείο
        in.close();//κλείνουμε την ροή
    }

    @Override
    public void addWinner(String username) throws RemoteException {//Προσθέτουμε εναν καινούριο νικητή στην λίστα και κάνουμε save
        winners.add(username);
        try {
            save();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
