package UserPackage;
public class NoDayException extends Exception{
    @Override
    public String toString() {
        return "Date of return not entered. Try again.";
    }
}
