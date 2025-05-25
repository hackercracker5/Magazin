        import java.io.*;
        import java.sql.ClientInfoStatus;
        import java.time.LocalDate;
        import java.time.LocalDateTime;
        import java.time.format.DateTimeFormatter;
        import java.time.temporal.ChronoUnit;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;
        import java.util.Scanner;

        class Stock
        {
            private String id="";
            private String name="";
            private double localPrice;
            private boolean  isFood;
            private LocalDate expirationDate;
            private int quantitiy;
            private double supplierPrice=localPrice;
            public double getSupplierPrice() { return supplierPrice; }

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
                setPrice(price);
                setIsFood(isFood);
                setExpirationDate(expirationDate);
                setQuantitiy(quantitiy);
            }
        }


        class Employee{
            private String name;
            private String id;
            private double salary;
            private int receipts=0;
            private double incomeEarned = 0;

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


            private static class IncomeRecord {

                String empId;
                double income;
                IncomeRecord(String id, double inc) { empId = id; income = inc; }
            }


            private static final List<IncomeRecord> ledger = new ArrayList<>();
            static {
                try (BufferedReader br = new BufferedReader(new FileReader("global_receipts_summary.txt"))) {
                    String line;
                    String currentId = null;
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("Cashier ID:")) {
                            currentId = line.substring(line.indexOf(':') + 1).trim();
                        } else if (line.startsWith("Employee income (markup):")) {
                            double inc = Double.parseDouble(line.substring(line.indexOf(':') + 1).trim());
                            if (currentId != null) updateLedger(currentId, inc);
                        }
                    }
                } catch (IOException | NumberFormatException ignored) { }
            }
            private static void updateLedger(String id, double add) {
                for (IncomeRecord rec : ledger) {
                    if (rec.empId.equals(id)) { rec.income += add; return; }
                }
                ledger.add(new IncomeRecord(id, add));
            }

            private double sessionIncome = 0;
            public double getCumulativeIncome() {
                for (IncomeRecord rec : ledger)
                    if (rec.empId.equals(id)) return rec.income;
                return 0;
            }

            public int getReceipts()
            {
                return receipts;
            }

            public void increaseReceipts() {
                ++this.receipts;
            }


            public void addIncome(double inc)
            {
                incomeEarned += inc;
                updateLedger(id, inc);
            }
            public double getIncome() { return incomeEarned; }

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
        class User
        {
            private double money;

            public double getMoney() {
                return money;
            }
            public void setMoney(double money)
            {
                this.money = money;
            }
            public User(double money)
            {
                setMoney(money);
            }
        }


        class Store
        {
            private int foodPercentage;
            private int noFoodPercentage;
            private int maxDiscount;

            public int getFoodPercentage() {
                return foodPercentage;
            }
            public void setFoodPercentage(int foodPercentage)
            {
                this.foodPercentage = foodPercentage;
            }


            public int getNoFoodPercentage()
            {
                return noFoodPercentage;
            }
            public void setNoFoodPercentage(int noFoodPercentage) {
                this.noFoodPercentage = noFoodPercentage;
            }


            public int getMaxDiscount()
            {
                return maxDiscount;
            }
            public void setMaxDiscount(int maxDiscount)
            {
                this.maxDiscount = maxDiscount;
            }


            public User user;
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
            private boolean IsValidQuantity(Stock stock)
            {
                if (stock.getQuantitiy() > 0)
                    return true;
                else
                {
                    stock.setQuantitiy(0);
                    return false;
                }
            }
            private boolean IsValidDate(Stock stock)
            {
                if (stock.getExpirationDate().isBefore(LocalDate.now())) {
                    return false;

                }
                return true;
            }
            private boolean HasMoney(double price)
            {
                if (user.getMoney()>=price)
                {

                    return true;
                }
                else{

                return false;
                }
            }
            public void Buy(Stock stock) throws InsufficientStockException,InsufficientMoneyException
            {

                if (IsValidQuantity(stock)&&IsValidDate(stock)&&HasMoney(stock.getLocalPrice()))
                {
                    stock.setQuantitiy(stock.getQuantitiy() - 1);
                    user.setMoney(user.getMoney()- stock.getLocalPrice());
                    /*System.out.println(user.getMoney());
                    System.out.println(HasMoney(stock.getLocalPrice()));*/
                }
                else {
                    if (!IsValidQuantity(stock))
                        throw new InsufficientStockException(stock.getId(), stock.getName());
                    else if (!HasMoney(stock.getLocalPrice())) {
                        throw new InsufficientMoneyException();
                    }
                }
            }
            public void Discount(Stock stock)
            {
                if (ChronoUnit.DAYS.between(LocalDate.now(),stock.getExpirationDate()) <=45)
                {
                    if (ChronoUnit.DAYS.between(LocalDate.now(),stock.getExpirationDate())<=12)
                    {
                        stock.setPrice((100 - maxDiscount) * stock.getLocalPrice() / 100);
                    }
                    else
                    {
                        stock.setPrice((100 - maxDiscount/2) * stock.getLocalPrice() / 100);
                    }
                }
            }
            public double calculateTaxIncomeFromReceipts() {
                double income = 0;
                for (Receipt r : Receipt.getGlobalReceipts()) {
                    for (Stock s : r.getPurchasedItems()) {
                        double percent = s.getIsFood() ? foodPercentage : noFoodPercentage;
                        double supplier = s.getSupplierPrice();
                        income += s.getLocalPrice() - supplier;
                    }
                }
                return income;
            }
            public Store(int FoodPercentage,int NoFoodPercentage,int maxDiscount,List<Checkout> checkouts,List<Employee> employees,List<Stock> stocks,User user)
            {

                setFoodPercentage(FoodPercentage);
                setNoFoodPercentage(NoFoodPercentage);
                setMaxDiscount(maxDiscount);
                this.checkouts = checkouts;
                this.employees = employees;
                this.stocks = stocks;
                this.user = user;
                for (Stock s : stocks)
                {
                    Discount(s);
                }
                PublicPrice();
            }
        }






        public class Main {
            public static void main(String[] args) {
                    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
                // to see how IntelliJ IDEA suggests fixing it.
                Stock stock=new Stock("r2t4t4","Kola",50,true,LocalDate.of(2025, 6, 5   ),50);

                System.out.println("Hello and welcome!");
                List<Checkout> checkouts=new ArrayList<Checkout>();
                List<Employee> employees= new ArrayList<Employee>();
                employees.add(new Employee("E1", "Ivan", 1200));
                employees.add(new Employee("E2", "Maria", 1250));
                employees.add(new Employee("E3", "Petar", 1100));
                employees.add(new Employee("E4", "Elena", 1150));
                employees.add(new Employee("E5", "Georgi", 1300));
                List<Stock> stocks=new ArrayList<Stock>();
                stocks.add(stock);
                stocks.add(new Stock("r2t4t5","Voda",50,true,LocalDate.of(2026, 6, 5   ),3));
                User user=new User(550);
                Store store=new Store(15,35,50,checkouts,employees,stocks,user);

                Random rnd = new Random();
                for (int i = 1; i <= 4; i++) {
                    checkouts.add(new Checkout(
                            employees.get(rnd.nextInt(employees.size())), "C" + i));
                }

                Scanner sc = new Scanner(System.in);
                System.out.println("Choose checkout (1-4) or 0 to exit:");
                int ch = sc.nextInt();
                if (ch == 0) return;
                Checkout active = checkouts.get(ch - 1);

                Receipt receipt = new Receipt(active.getAssignedEmployee());

                while (true) {
                    System.out.println("Available items (0 to finish):");
                    for (int i = 0; i < stocks.size(); i++) {
                        Stock s = stocks.get(i);
                        System.out.printf("%d) %s | Price %.2f | Qty %d%n",
                                i + 1, s.getName(), s.getLocalPrice(), s.getQuantitiy());
                    }
                    int choice = sc.nextInt();
                    if (choice == 0) break;
                    if (choice < 1 || choice > stocks.size()) continue;

                    Stock sel = stocks.get(choice - 1);
                    try {
                        store.Buy(sel);
                        receipt.addItem(sel);                      // log purchase
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                receipt.saveToFile();
                receipt.addToGlobalReceipts();
                try { receipt.serialize(); } catch (Exception ignored) {}


                System.out.println("Total paid: " + receipt.getTotalAmount());          // ADDED THIS
                System.out.println("Remaining balance: " + user.getMoney());            // ADDED THIS


            }}
