import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ATM atm = new ATM();
        atm.atmMenu();
        atm.saveUsersToDataBase();
        atm.readDatBase();

    }
}