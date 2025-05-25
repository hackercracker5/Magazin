public class InsufficientStockException extends Exception {
    private String productId;
    private String productName;




    public InsufficientStockException(String productId, String productName) {
        super("Insufficient quantity for product " + productName+" with id "+productId );
        this.productId = productId;
        this.productName = productName;
    }
}
