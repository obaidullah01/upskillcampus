public class User {
    private String accountNumber;
    private String name;
    private String address;
    private String contactInfo;
    private double accountBalance;
    private String password; // New field for storing password

    public User(String accountNumber, String name, String address, String contactInfo, double accountBalance, String password) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.contactInfo = contactInfo;
        this.accountBalance = accountBalance;
        this.password = password;
    }

    private String generateAccountNumber() {
        // Generate a unique account number
        return "ACC" + System.currentTimeMillis();
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Getters and setters
}
