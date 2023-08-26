import java.util.List;

public class AccountStatement {
    public void generateAccountStatement(User user, List<String> transactionHistory) {
        System.out.println("Account Statement for User: " + user.getName());
        System.out.println("Account Number: " + user.getAccountNumber());
        System.out.println("Current Balance: Rs." + user.getAccountBalance());
        System.out.println("Transaction History:");

        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
