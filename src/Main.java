import ExpenseManger.ExpenseFunctions;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Expense Tracker
        Scanner in=new Scanner(System.in);
        String cont="YES";
        while (cont.equalsIgnoreCase("yes")){
            System.out.print("1. Add Expense\n2. Update Expense\n3. Delete Expense\n4. View Expenses \n5. Summary of Expenses \n6. View a specific expense\nPick Your Choice From above- ");
            int n = in.nextInt();
            in.nextLine();
            ExpenseFunctions a = new ExpenseFunctions();
            switch (n) {
                case 1:
                    System.out.print("Add Expense- \nDate(dd/mm/yyyy):");
                    String date = in.nextLine();
                    System.out.print("Add amount-");
                    double amount = in.nextDouble();
                    in.nextLine();
                    System.out.print("Add Description-");
                    String description = in.nextLine();
                    System.out.print("Enter expense Category(food, beverage, stationary...)-");
                    String category=in.nextLine();
                    a.AddExpense(date, amount, description, category);
                    break;
                case 2:
                    System.out.println("Update Expense-");
                    System.out.print("Enter Payment Date(dd/mm/yyyy) which need to be updated:");
                    String SDate = in.nextLine();
                    System.out.print("Enter amount which need to be updated:");
                    double oldAmount=in.nextDouble();
                    System.out.print("Update amount:");
                    double newAmount = in.nextDouble();
                    in.nextLine();
                    System.out.print("Select Category:");//Two level check to update value
                    String Category = in.nextLine();
                    a.UpdateExpense(SDate, oldAmount, newAmount, Category);
                    break;
                case 3:
                    System.out.print("Delete Expense- \nSelect Date(dd/mm/yyyy):");
                    String Date = in.nextLine();
                    System.out.print("Select amount:");
                    double Amount = in.nextDouble();
                    in.nextLine();
                    System.out.print("Select Category:");//Two level check to update value
                    category = in.nextLine();
                    a.DeleteExpense(Date, Amount,category);
                    break;
                case 4:
                    a.View();
                    break;
                case 5:
                    System.out.print("Select Month(January,February,March,April...):");
                    String Month = in.nextLine();
                    a.MonthlySummary(Month);
                    break;
                case 6:
                    System.out.print("Select date:");
                    date=in.nextLine();
                    System.out.print("Select amount:");
                    double amt=in.nextDouble();
                    in.nextLine();
                    System.out.print("Select Category:");
                    category=in.nextLine();
                    a.Select(date,amt,category);
                    break;
                default:
                    System.out.println("Please Enter a valid choice from the above");
            }
            System.out.print("Do you Want to continue(YES/NO):");
            cont=in.nextLine();
        }
    }
}