package emailservice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class User {

    private String emailAddress;
    private List<Email> incoming = new ArrayList<>();
    private List<Email> sent = new ArrayList<>();
    private boolean hasSpamFilter = false;

    public User(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<Email> getIncoming() {
        List<Email> incomingList = new ArrayList<>(incoming);
        incomingList.sort((o1, o2) -> String.valueOf(o2.isImportant()).compareTo(String.valueOf(o1.isImportant())));
        return incomingList;
    }

    public List<Email> getSent() {
        return new ArrayList<>(sent);
    }

    public boolean hasSpamFilter() {
        return hasSpamFilter;
    }

    public void spamFilterChange() {
        hasSpamFilter = !hasSpamFilter;
    }

    public void getEmail(Email email) {
        if (hasSpamFilter && email.getFrom().getEmailAddress().contains("spam")) {
            throw new IllegalStateException("Be careful, tis is a spam!");
        } else {
            incoming.add(email);
        }
    }

    public void sendEmail(String subject, String content, User to) {
        Email email = new Normal(this, to, subject, content);
        sent.add(email);
        to.getEmail(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return emailAddress.equals(user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }
}
