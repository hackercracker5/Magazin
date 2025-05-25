import java.sql.ClientInfoStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

class Stock
{
    private String id="";
    private String name="";
    private double localPrice;
    private boolean  isFood;
    private LocalDate expirationDate;
    private int quantitiy;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public boolean getIsFood() {
            return isFood;
    }
    public void setIsFood(boolean isFood) {
        this.isFood = isFood;
    }


    public double getLocalPrice() {
        return localPrice;
    }
    public void setPrice(double price)
    {
        this.localPrice = price;
    }


    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }


    public int getQuantitiy() {
        return quantitiy;
    }
    public void setQuantitiy(int quantitiy)
    {
        this.quantitiy = quantitiy;
    }


    public Stock(String id, String name, double price, boolean isFood, LocalDate expirationDate, int quantitiy)
    {
        setId(id);
        setName(name);
        setIsFood(isFood);
        setExpirationDate(expirationDate);
        setQuantitiy(quantitiy);
    }
}


class Employee{
    private String name;
    private String id;
    private double salary;


    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
     this.name = name;
    }


    public String getId()
    {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public void setSalary(double salary) {
        this.salary = salary;
    }
    public double getSalary() {
        return salary;
    }


    public Employee(String id, String name, double salary)
    {
        setId(id);
        setName(name);
        setSalary(salary);
    }
}

class Checkout
{
    private Employee assignedEmployee;
    private String id;


    public void setId(String id) {
        this.id = id;
    }
    public String getId()
    {
        return id;
    }

    public Employee getAssignedEmployee()
    {
        return assignedEmployee;
    }

    public void setAssignedEmployee(Employee assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public Checkout(Employee assignedEmployee,String id)
    {
        setAssignedEmployee(assignedEmployee);
        setId(id);
    }
}

class Store
{
    private int foodPercentage;
    private int noFoodPercentage;
    public List<Checkout> checkouts;
    public List<Employee> employees;
    public List<Stock> stocks;
    private void PublicPrice()
    {
        for(Stock s : stocks) {
            if (s.getIsFood() == true)
                s.setPrice(s.getLocalPrice() + s.getLocalPrice() * foodPercentage / 100);
            else
                s.setPrice(s.getLocalPrice()+s.getLocalPrice() * noFoodPercentage / 100);
        }
    }
    private static boolean IsValidQuantity(Stock stock)
    {
        if (stock.getQuantitiy() > 0)
            return true;
        else
        {
            stock.setQuantitiy(0);
            return false;
        }
    }
    private static boolean IsValidDate(Stock stock)
    {
        if (stock.getExpirationDate().isBefore(LocalDate.now())) {
            return false;

        }
        return true;
    }
    public static void Buy(Stock stock) throws InsufficientStockException
    {
        if (IsValidQuantity(stock)&&IsValidDate(stock))
        {
            stock.setQuantitiy(stock.getQuantitiy() - 1);
        }
        else
            if (!IsValidQuantity(stock))
                throw new InsufficientStockException(stock.getId(), stock.getName());

    }
}



public class Main {
    public static void main(String[] args) {
            //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Stock stock=new Stock("r2t4t4","Kola",5.65,true,LocalDate.of(2026, 1, 8),5);
        System.out.println("Hello and welcome!");
        Store store = new Store();
        try {
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
            Store.Buy(stock);
        }
        catch (InsufficientStockException e)
        {
            System.out.println(e.getMessage());
        }


    }
}