import java.util.InputMismatchException;
import java.util.Scanner;

public class EnhancedCalculator {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        CalculatorUI ui = new CalculatorUI(calculator);
        ui.start();
    }
}

class Calculator {
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public double squareRoot(double num) throws ArithmeticException {
        if (num < 0) {
            throw new ArithmeticException("Square root of negative numbers is not real.");
        }
        return Math.sqrt(num);
    }

    public double logarithm(double num) throws ArithmeticException {
        if (num <= 0) {
            throw new ArithmeticException("Logarithm is only defined for positive numbers.");
        }
        return Math.log(num);
    }

    public double factorial(double num) throws ArithmeticException {
        if (num < 0) {
            throw new ArithmeticException("Factorial is not defined for negative numbers.");
        }
        if (num % 1 != 0) {
            throw new ArithmeticException("Factorial is only defined for integers.");
        }
        
        int n = (int) num;
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public double modulo(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Modulo by zero is not allowed.");
        }
        return a % b;
    }

    public double sin(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    public double cos(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public double tan(double angle) {
        return Math.tan(Math.toRadians(angle));
    }
}

class CalculatorUI {
    private final Calculator calculator;
    private final Scanner scanner;
    
    public CalculatorUI(Calculator calculator) {
        this.calculator = calculator;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("╔══════════════════════════╗");
        System.out.println("║  ENHANCED CALCULATOR     ║");
        System.out.println("╚══════════════════════════╝");
        
        while (true) {
            displayMenu();
            try {
                int choice = scanner.nextInt();
                
                if (choice == 0) {
                    System.out.println("Exiting calculator. Goodbye!");
                    break;
                }
                
                processOperation(choice);
                
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (ArithmeticException e) {
                System.out.println("Math Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║       OPERATIONS         ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║ 1. Addition (+)          ║");
        System.out.println("║ 2. Subtraction (-)       ║");
        System.out.println("║ 3. Multiplication (*)    ║");
        System.out.println("║ 4. Division (/)          ║");
        System.out.println("║ 5. Power (x^y)           ║");
        System.out.println("║ 6. Square Root (√)       ║");
        System.out.println("║ 7. Logarithm (ln)        ║");
        System.out.println("║ 8. Factorial (n!)        ║");
        System.out.println("║ 9. Modulo (%)            ║");
        System.out.println("║ 10. Sine (sin)           ║");
        System.out.println("║ 11. Cosine (cos)         ║");
        System.out.println("║ 12. Tangent (tan)        ║");
        System.out.println("║ 0. Exit                  ║");
        System.out.println("╚══════════════════════════╝");
        System.out.print("Enter your choice: ");
    }
    
    private void processOperation(int choice) {
        switch (choice) {
            case 1:
                performBinaryOperation("Addition", calculator::add);
                break;
            case 2:
                performBinaryOperation("Subtraction", calculator::subtract);
                break;
            case 3:
                performBinaryOperation("Multiplication", calculator::multiply);
                break;
            case 4:
                performBinaryOperation("Division", calculator::divide);
                break;
            case 5:
                performBinaryOperation("Power", calculator::power);
                break;
            case 6:
                performUnaryOperation("Square Root", calculator::squareRoot);
                break;
            case 7:
                performUnaryOperation("Natural Logarithm", calculator::logarithm);
                break;
            case 8:
                performUnaryOperation("Factorial", calculator::factorial);
                break;
            case 9:
                performBinaryOperation("Modulo", calculator::modulo);
                break;
            case 10:
                performTrigOperation("Sine", calculator::sin);
                break;
            case 11:
                performTrigOperation("Cosine", calculator::cos);
                break;
            case 12:
                performTrigOperation("Tangent", calculator::tan);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private void performBinaryOperation(String operationName, BinaryOperation operation) {
        System.out.println("\n" + operationName);
        System.out.println("════════════════════");
        
        double a = getNumber("Enter first number: ");
        double b = getNumber("Enter second number: ");
        
        double result = operation.execute(a, b);
        System.out.printf("\nResult: %.6f%n", result);
    }
    
    private void performUnaryOperation(String operationName, UnaryOperation operation) {
        System.out.println("\n" + operationName);
        System.out.println("════════════════════");
        
        double num = getNumber("Enter number: ");
        
        double result = operation.execute(num);
        System.out.printf("\nResult: %.6f%n", result);
    }
    
    private void performTrigOperation(String operationName, TrigOperation operation) {
        System.out.println("\n" + operationName);
        System.out.println("════════════════════");
        
        double angle = getNumber("Enter angle in degrees: ");
        
        double result = operation.execute(angle);
        System.out.printf("\nResult: %.6f%n", result);
    }
    
    private double getNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    @FunctionalInterface
    private interface BinaryOperation {
        double execute(double a, double b);
    }
    
    @FunctionalInterface
    private interface UnaryOperation {
        double execute(double num);
    }
    
    @FunctionalInterface
    private interface TrigOperation {
        double execute(double angle);
    }
}