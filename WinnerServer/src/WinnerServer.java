import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WinnerServer {

    public static void main(String[] args) {
        try {
            Server server = new Server();//Καλούμε τον constructor της κλάσης
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1098);//Φτιάχνουμε ενα registry στο port 1098
            Naming.rebind("//localhost/WinnerDirectory", server);//Δημιουργούμε ενα στιγμιότυπο του απομακρυσμένου αντικειμένου server
            System.out.println("Server up and running");//Εκτυππώνουμε ένα μύνημα στην οθόνή οτι ο server τρέχει κανονικά
        } catch (IOException ex) {//Οι απαραίτητες εξαιρέσεις
            Logger.getLogger(WinnerServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WinnerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
