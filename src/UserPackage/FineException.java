package UserPackage;
public class FineException extends Exception {

    private int fineAmount;

    public FineException(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    @Override
    public String toString() {
        return "Fine Amount: Rs. " + fineAmount;
    }
}
