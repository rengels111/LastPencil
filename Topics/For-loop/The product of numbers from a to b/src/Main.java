import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        long a = scanner.nextLong();
        long b = scanner.nextLong();
        long mul = 1;

        for (long i = a; i < b; i++) {
            mul *= i;
        } System.out.println(mul);
    }
}