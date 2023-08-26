import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private AccountManager accountManager;
    private TransactionManager transactionManager;
    private AccountStatement accountStatement;
    private AuthenticationManager authenticationManager;
    private User currentUser; // Initialize to null
    private List<String> transactionHistory;

    public UserInterface() {
        accountManager = new AccountManager();
        transactionManager = new TransactionManager();
        accountStatement = new AccountStatement();
        authenticationManager = new AuthenticationManager();
        transactionHistory = new ArrayList<>();
        currentUser = null; // Initialize to null
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Banking Information System!");

        while (true) {
            System.out.println("\nPlease select an Ooption:");
            System.out.println("1. Register new user");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = getValidInput(scanner, 1, 3);

            switch (choice) {
                case 1:
                    registerNewUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using the Banking Information System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getValidInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please enter a valid option.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void registerNewUser(Scanner scanner) {
        System.out.println("Please enter your Name:");
        String name = scanner.nextLine();

        System.out.println("Please enter your Address:");
        String address = scanner.nextLine();

        String contactInfo;
        do {
            System.out.println("Please enter your Contact Information (10 digits):");
            contactInfo = scanner.nextLine();
            if (!isValidContact(contactInfo)) {
                System.out.println("Invalid contact information. Please Try Again!!! ");
            }
        } while (!isValidContact(contactInfo)); // Repeat until contact detail is exactly 11 digits

        System.out.println("Please enter the initial Deposit Amount:");
        double initialDeposit = getValidDoubleInput(scanner);

        System.out.println("Please enter a Password:");
        String password = scanner.nextLine();

        String accountNumber = accountManager.registerUser(name, address, contactInfo, initialDeposit, password);

        if (accountNumber != null) {
            System.out.println("User registration successful. Your account number is: " + accountNumber);
        } else {
            System.out.println("User registration failed. Please try again later.");
        }
    }
    private boolean isValidContact(String contactInfo) {
        // Check if the contact detail is exactly 11 digits
        return contactInfo.matches("\\d{10}");
    }
    private void changePassword(Scanner scanner) {
        System.out.println("Please enter your current password:");
        String currentPassword = scanner.nextLine();

        if (currentUser.getPassword().equals(currentPassword)) {
            System.out.println("Please enter a new password:");
            String newPassword = scanner.nextLine();
            currentUser.setPassword(newPassword);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect current password. Password change failed.");
        }
    }


    private double getValidDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
    }

    private void loginUser(Scanner scanner) {
        System.out.println("Please enter your account number:");
        String accountNumber = scanner.nextLine();

        User user = accountManager.getUserByAccountNumber(accountNumber);

        if (user == null) {
            System.out.println("Account not found. Please create your account first to access our bank.");
        } else {
            System.out.println("Please enter your password:");
            String password = scanner.nextLine();

            if (authenticationManager.authenticate(user, password)) {
                currentUser = user;
                mainMenu(scanner);
            } else {
                System.out.println("Authentication failed. Please try again.");
            }
        }
    }



    private void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMain Menu - Welcome, " + currentUser.getName());
            System.out.println("Please select an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer Funds");
            System.out.println("4. View Account Information");
            System.out.println("5. View Account Statement");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");

            int choice = getValidInput(scanner, 1, 7);

            switch (choice) {
                case 1:
                    performDeposit(scanner);
                    break;
                case 2:
                    performWithdrawal(scanner);
                    break;
                case 3:
                    performFundTransfer(scanner);
                    break;
                case 4:
                    viewAccountInformation();
                    break;
                case 5:
                    viewAccountStatement();
                    break;

                case 6:
                    changePassword(scanner);
                    break;

                case 7:
                    System.out.println("Logout successful.");
                    currentUser = null;
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");


            }
        }
    }

    private void performDeposit(Scanner scanner) {
        System.out.println("Enter the deposit amount:");
        double amount = getValidDoubleInput(scanner);

        transactionManager.deposit(currentUser, amount);
        transactionHistory.add("Deposit: ₹" + amount);
        System.out.println("Deposit successful. Current balance: ₹" + currentUser.getAccountBalance());
    }

    private void performWithdrawal(Scanner scanner) {
        System.out.println("Enter the withdrawal amount:");
        double amount = getValidDoubleInput(scanner);

        if (transactionManager.withdraw(currentUser, amount)) {
            transactionHistory.add("Withdrawal: ₹" + amount);
            System.out.println("Withdrawal successful. Current balance: ₹" + currentUser.getAccountBalance());
        } else {
            System.out.println("Withdrawal failed. Insufficient funds.");
        }
    }

    private void performFundTransfer(Scanner scanner) {
        System.out.println("Enter the recipient's account number:");
        String recipientAccountNumber = scanner.nextLine();

        User recipient = accountManager.getUserByAccountNumber(recipientAccountNumber);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        System.out.println("Enter the transfer amount:");
        double amount = getValidDoubleInput(scanner);

        if (transactionManager.transferFunds(currentUser, recipient, amount)) {
            transactionHistory.add("Transfer to " + recipient.getName() + ": ₹" + amount);
            System.out.println("Transfer successful. Current balance: ₹" + currentUser.getAccountBalance());
        } else {
            System.out.println("Transfer failed. Insufficient funds.");
        }
    }

    private void viewAccountInformation() {
        System.out.println("Account Information for User: " + currentUser.getName());
        System.out.println("Account Number: " + currentUser.getAccountNumber());
        System.out.println("Address: " + currentUser.getAddress());
        System.out.println("Contact Info: " + currentUser.getContactInfo());
        System.out.println("Current Balance: ₹" + currentUser.getAccountBalance());
    }

    private void viewAccountStatement() {
        accountStatement.generateAccountStatement(currentUser, transactionHistory);
    }
}
