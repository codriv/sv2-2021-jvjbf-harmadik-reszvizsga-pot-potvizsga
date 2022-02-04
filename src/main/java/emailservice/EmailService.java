package emailservice;

import java.util.HashSet;
import java.util.Set;

public class EmailService {

    Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void registerUser(String emailAddress) {
        if (!isValid(emailAddress)) {
            throw new IllegalArgumentException("Email address is not valid: " + emailAddress);
        } else if (!users.add(new User(emailAddress))) {
            throw new IllegalArgumentException("Email address is already taken!");
        }
    }

    private boolean isValid(String email) {
        return email.matches("^[^@.][a-z.]+@[^\\.][a-z.]+\\.[a-z.]+[^@.]$");
    }

    public void sendEmail(String from, String to, String subject, String content) {
        User userFrom = users.stream().filter(user -> from.equals(user.getEmailAddress())).findFirst().get();
        User userTo = users.stream().filter(user -> to.equals(user.getEmailAddress())).findFirst().get();
        userFrom.sendEmail(subject, content, userTo);
    }

    public void sendSpam(String subject, String content) {
        users.stream().forEach(user -> user.getEmail(new Spam(user, subject, content)));
    }
}
