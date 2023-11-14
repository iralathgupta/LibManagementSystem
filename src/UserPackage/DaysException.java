package UserPackage;

public class DaysException extends Exception {

    @Override
    public String toString() {
        return "Date of return is prior to date of borrowing.";
    }
}