import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // start coding here
        int nInputs = scanner.nextInt();
        int fixed = 0;
        int reject = 0;
        int perfect = 0;

        for (int i = 0; i < nInputs; i++) {
            int input = scanner.nextInt();
            if (input == 1) {
                fixed++;
            } else if (input == -1) {
                reject++;
            } else if (input == 0) {
                perfect++;
            }
        } System.out.println(perfect + " " + fixed + " " + reject);
    }
}