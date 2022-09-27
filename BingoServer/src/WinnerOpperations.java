
import java.rmi.*;
import java.util.ArrayList;

public interface WinnerOpperations extends Remote {//Δημιουργούμε τήν κατάλληλη διεπαφή για τον πελάτη(BingoServer) κληρονομόντας απο την κλάση Remote και καλόντας τις αντίστειχες εξαιρέσεις 
//O BingoServer σε αυτήν την περίπτωση παίζει τον ρόλο ενός client και o WinnerServer του server
    public ArrayList<String> winners() throws RemoteException;//Μέθοδος που επιστρέφει μια λίστα με τα ονόματα των νικητών
    
    public void addWinner(String username) throws RemoteException;//Μέθοδος που δέχεται το όνομα ενος νικητή και το προσθέτει στην λίστα
}
