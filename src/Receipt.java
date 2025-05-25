import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int initCounter() {
        int last = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("global_receipts_summary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Receipt No:")) {
                    int colon = line.indexOf(':');
                    if (colon >= 0 && colon + 1 < line.length()) {
                        String num = line.substring(colon + 1).trim();
                        try { last = Integer.parseInt(num); } catch (NumberFormatException ignored) {}
                    }
                }
            }
        } catch (IOException ignored) { /* summary file absent first run */ }
        return last + 1;
    }

    private static int receiptCounter = initCounter();
    private static final List<Receipt> globalReceipts = new ArrayList<>();

    private final int receiptNumber;
    private final Employee employee;
    private final LocalDateTime dateTime;
    private final List<Stock> purchasedItems;
    private double totalAmount;

    public Receipt(Employee employee) {
        this.receiptNumber = receiptCounter++;
        this.employee = employee;
        this.dateTime = LocalDateTime.now();
        this.purchasedItems = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public void addItem(Stock item) {
        purchasedItems.add(item);
        totalAmount += item.getLocalPrice();
        double margin = item.getLocalPrice() - item.getSupplierPrice();
        employee.addIncome(margin);
    }

    public double getTotalAmount() { return totalAmount; }
    public int getReceiptNumber()  { return receiptNumber; }

    public void saveToFile() {
        String filename = "receipt_" + receiptNumber + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Receipt No: " + receiptNumber + " \n");
            writer.write("Date: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " \n");
            writer.write("Cashier: " + employee.getName() + " \n");
            writer.write("Cashier ID: " + employee.getId() + " \n");
            writer.write("Items: \n");
            for (Stock s : purchasedItems) {
                writer.write("- " + s.getName() + " | Price: " + s.getLocalPrice() + " | Qty: 1 \n");
            }
            writer.write("Total: " + totalAmount + " \n");
                    employee.increaseReceipts();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void addToGlobalReceipts() {
        globalReceipts.add(this);
        writeSummaryToGlobalFile();
    }

    private void writeSummaryToGlobalFile() {
        try (FileWriter w = new FileWriter("global_receipts_summary.txt", true)) {
            w.write("Receipt No: " + receiptNumber + " \n");
            w.write("Date: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " \n");
            w.write("Cashier: " + employee.getName() + " \n");
            w.write("Cashier ID: " + employee.getId() + " \n");
            w.write("Items: ");
            for (Stock s : purchasedItems) {
                w.write("- " + s.getName() + " | Price: " + s.getLocalPrice() + " | Qty: 1 \n");
            }
            w.write("Total: " + totalAmount + " \n");
                    w.write("Employee income (markup): " + employee.getCumulativeIncome() + " \n");
                            w.write("Employee salary: " + employee.getSalary() + " \n");
                                    w.write("-------------------------------------------------- \n");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static double calculateTotalIncome() {
        double sum = 0;
        for (Receipt r : globalReceipts) sum += r.getTotalAmount();
        return sum;
    }
    public static int getTotalReceiptCount() { return globalReceipts.size(); }

    public void serialize() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("receipt_" + receiptNumber + ".ser"));
        out.writeObject(this);
        out.close();
    }
    public static Receipt deserialize(String f) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        Receipt r = (Receipt) in.readObject();
        in.close(); return r;
    }

    public List<Stock> getPurchasedItems() { return purchasedItems; }
    public static List<Receipt> getGlobalReceipts() { return globalReceipts; }
}
