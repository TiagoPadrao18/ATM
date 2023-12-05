import java.io.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    List<Card> cardsAccounts = new LinkedList<>();

    Scanner scan = new Scanner(System.in);

    public void atmMenu() {
        try {
            boolean dontLeave = false;
            while (!dontLeave) {
                createBankAccounts();
                System.out.println("1 - Insert Card");
                System.out.println("2 - Quit");
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("PIN: ");
                        String pin = scan.next();
                        existAccount(pin);
                        if(existAccount(pin)!=null) {
                            bankMenu(existAccount(pin));
                        }
                        break;
                    case 2:
                        dontLeave = true;
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void bankMenu(Card card) {
        boolean dontLeave = false;
        while (!dontLeave) {
            System.out.printf("%s account",card.getName());
            System.out.printf("%d $\n\n",card.getBalance());
            System.out.println("1 - Deposit");
            System.out.println("2 - Withdraw");
            System.out.println("3 - Bank Transfer");
            System.out.println("4 - Leave");
           int option = scan.nextInt();
           switch (option){
               case 1-> depositCash(card);
               case 2-> withDraw(card);
               case 3-> bankTransfer(card);
               case 4-> dontLeave = true;
           }
        }

    }


    private void bankTransfer(Card card){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("moviments.txt"));

            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the card number of the account you want to transfer to:");
            String cardNumber = scan.next();

            int card2Index = -1;
            for (int i = 0; i < cardsAccounts.size(); i++) {
                if (cardsAccounts.get(i).getCardNumber().equals(cardNumber)) {
                    card2Index = i;
                    break;
                }
            }
            if (card2Index == -1) {
                System.out.println("Invalid card number. Transfer aborted.");
                return;
            }
            System.out.println("Insert the quantity:");
            int quantityOfCash = scan.nextInt();
            if (card.getBalance() < quantityOfCash) {
                System.out.println("Insufficient funds. Transfer aborted.");
                return;
            }
            cardsAccounts.get(card2Index).setBalance(cardsAccounts.get(card2Index).getBalance() + quantityOfCash);
            card.setBalance(card.getBalance() - quantityOfCash);

            writer.write("The user " + card.getName() + " transfer " + quantityOfCash + " to " + cardsAccounts.get(card2Index).getName());
            System.out.println("Transfer successful.");

            writer.close();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void depositCash(Card card) {
        System.out.println("How much you want to deposit?");
        int quantityOfCash = scan.nextInt();
        card.setBalance(card.getBalance() + quantityOfCash);
    }

    private void withDraw(Card card){
        System.out.println("How much you want to with draw?");
        int quantityOfCashToWithdraw = scan.nextInt();
        card.setBalance(card.getBalance()-quantityOfCashToWithdraw);
    }


    private Card existAccount(String cardNumber){
        for (Card account : cardsAccounts) {
            if (cardNumber.equals(account.getCardNumber())) {
                if (account.isCardBlocked()) {
                    System.out.println("Card Blocked");
                    return null;
                }
                return account;
            }
        }

        return null;
    }


    public void saveUsersToDataBase() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"));
            for (Card card : cardsAccounts) {
                writer.write(card.toString());
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void readDatBase() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] cardDetails = line.split(",");
                if (cardDetails.length == 4) {
                    String cardNumber = cardDetails[0];
                    String name = cardDetails[1];
                    int balance = Integer.parseInt(cardDetails[2]);
                    boolean cardBlocked = Boolean.parseBoolean(cardDetails[3]);

                    Card card = new Card(cardNumber, name, balance,cardBlocked);
                    cardsAccounts.add(card);
                }
            }

            reader.close();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }




    private void createBankAccounts() {
        cardsAccounts.add(new Card("1234567", "Tiago", 12000000,false));
        cardsAccounts.add(new Card("764321", "Leo", 11,false));
        cardsAccounts.add(new Card("1234321", "Ricardo", 20,true));


    }
}
