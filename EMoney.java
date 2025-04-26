import java.util.Scanner;

public class EMoney implements IPayment {
    private double saldo;
    private static final double BIAYA_ADMIN = 20000;

    // Konstruktor yang langsung menerima input saldo
    public EMoney(Scanner scanner) {
        this.saldo = inputSaldo(scanner);
    }

    private double inputSaldo(Scanner scanner) {
        while (true) {
            System.out.print("Masukkan saldo EMoney Anda (IDR): ");
            try {
                double saldo = Double.parseDouble(scanner.nextLine());

                if (saldo >= 0) {
                    return saldo;
                } else {
                    S.delay(100);
                    System.out.println("Saldo tidak valid. Silakan masukkan nilai positif.");
                }
            } catch (NumberFormatException e) {
                S.delay(100);
                System.out.println("Input tidak valid. Silakan masukkan angka.");
            }
        }
    }

    // Menghitung total harga setelah diskon untuk metode pembayaran EMoney
    // EMoney mendapatkan diskon 7% dan potongan 20
    @Override
    public double diskonMenu(double totalHarga) {
        return totalHarga * 0.93 - 20;
    }

    // Memeriksa apakah saldo cukup untuk pembayaran
    @Override
    public boolean saldoCukup(double totalHarga) {
        double hargaSetelahDiskon = diskonMenu(totalHarga);
        return (saldo >= hargaSetelahDiskon);
    }

    @Override
    public double hitungBiayaAdmin() {
        return BIAYA_ADMIN;
    }

    @Override
    public String getNamaMetode() {
        return "EMoney";
    }
}