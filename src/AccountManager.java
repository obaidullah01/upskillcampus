import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class AccountManager {
    private Map<String, User> users;
    private String lastGeneratedAccountNumber;

    public AccountManager() {
        users = new HashMap<>();
    }

    public String registerUser(String name, String address, String contactInfo, double initialDeposit, String password) {
        // Generate a unique account number
        String accountNumber = generateAccountNumber();

        User newUser = new User(accountNumber, name, address, contactInfo, initialDeposit, password);
        users.put(newUser.getAccountNumber(), newUser);
        lastGeneratedAccountNumber = newUser.getAccountNumber(); // Set the last generated account number

        return accountNumber; // Return the account number as a String
    }
    private String generateAccountNumber() {
        //To generate a unique 15-digit account number starting with "90"
        Random random = new Random();
        String accountNumber;
        do {
            // Generate a random 13-digit number (within the range of 1000000000000 to 9999999999999)
            long randomNumber = 1000000000000L + random.nextLong() % (9999999999999L - 1000000000000L + 1);
            accountNumber = "90" + Math.abs(randomNumber); // Append "90" to the beginning of the random digits
        } while (users.containsKey(accountNumber)); // Check if the generated account number is already in use


        return accountNumber;
    }




    public void updateAccountInfo(User user, String name, String address, String contactInfo) {
        user.setName(name);
        user.setAddress(address);
        user.setContactInfo(contactInfo);
    }

    public User getUserByAccountNumber(String accountNumber) {
        return users.get(accountNumber);
    }

    public String getLastGeneratedAccountNumber() {
        return lastGeneratedAccountNumber;
    }
}



