public class InsufficientMoneyException extends Exception {
    public InsufficientMoneyException()
    {
        super("You don't have enough money");
    }
}
