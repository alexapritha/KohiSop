import java.util.Scanner;

public class QRIS implements IPayment {
    private double saldo;

    public QRIS(Scanner scanner) {
        this.saldo = inputSaldo(scanner);
    }

    private double inputSaldo(Scanner scanner) {
        while (true) {
            System.out.print("Masukkan saldo QRIS Anda (IDR): ");
            try {
                double saldo = Double.parseDouble(scanner.nextLine());

                if (saldo >= 0) {
                    return saldo;
                } else {
                    System.out.println("Saldo tidak valid. Silakan masukkan nilai positif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
            }
        }
    }

    // QRIS mendapatkan diskon 5%
    @Override
    public double diskonMenu(double totalHarga) {
        return totalHarga * 0.95;
    }

    // Memeriksa apakah saldo cukup untuk pembayaran
    @Override
    public boolean saldoCukup(double totalHarga) {
        return saldo >= diskonMenu(totalHarga);
    }

    // QRIS tidak dikenakan biaya admin
    @Override
    public double hitungBiayaAdmin() {
        return 0;
    }

    // Mendapatkan nama metode pembayaran
    @Override
    public String getNamaMetode() {
        return "QRIS";
    }

}
