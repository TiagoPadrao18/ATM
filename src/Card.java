public class Card {

    private final String cardNumber;
    private final String name;
    private int balance;

    private boolean cardBlocked;


    public Card(String cardNumber, String name, int balance, boolean cardBlocked) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.balance = balance;
        this.cardBlocked = cardBlocked;
    }

    @Override
    public String toString() {
        return "cardNumber='" + cardNumber + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance;
    }

    public int getBalance() {
        return balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }



    public String getName() {
        return name;
    }

    public boolean isCardBlocked() {
        return cardBlocked;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
