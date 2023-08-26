public class TransactionManager {
    public void deposit(User user, double amount) {
        user.setAccountBalance(user.getAccountBalance() + amount);
    }

    public boolean withdraw(User user, double amount) {
        if (user.getAccountBalance() >= amount) {
            user.setAccountBalance(user.getAccountBalance() - amount);
        } else {
            System.out.println("Insufficient funds!");
        }
        return false;
    }

    public boolean transferFunds(User sender, User recipient, double amount) {
        if (sender.getAccountBalance() >= amount) {
            sender.setAccountBalance(sender.getAccountBalance() - amount);
            recipient.setAccountBalance(recipient.getAccountBalance() + amount);
        } else {
            System.out.println("Insufficient funds for transfer!");
        }
        return false;
    }
}
