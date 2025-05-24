import java.sql.ClientInfoStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

class Stock
{
    private String id="";
    private String name;
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


    Stock(String id, String name, double price, boolean isFood, LocalDate expirationDate, int quantitiy)
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

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}