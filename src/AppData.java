
import UserPackage.*;
import BooksPackage.*;
public class AppData {
    private static AppData instance;
    private User currentUser;
    private String searchText;

    private AppData() {
        // Private constructor to prevent instantiation outside of this class
    }

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String text) {
        searchText = text;
    }
}