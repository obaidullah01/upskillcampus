public class AuthenticationManager {
    public boolean authenticate(User user, String password) {
        //To compare the entered password with the user's actual password
        return user.getPassword().equals(password);
    }
}
