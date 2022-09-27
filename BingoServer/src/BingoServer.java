
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BingoServer {

    public static void main(String[] args) {
        try {
            Server server = new Server();//Καλούμε τον constructor της κλάσης
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);//Φτιάχνουμε ενα registry στο port 1099
            Naming.rebind("//localhost/BingoDirectory", server);//Δημιουργούμε ενα στιγμιότυπο του απομακρυσμένου αντικειμένου server
            System.out.println("Server up and running");//Εκτυππώνουμε ένα μύνημα στην οθόνή οτι ο server τρέχει κανονικά
        } catch (IOException ex) {//Οι απαραίτητες εξαιρέσεις
            Logger.getLogger(BingoServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BingoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
