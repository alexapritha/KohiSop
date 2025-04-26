public class Tunai implements IPayment {
    
    // Konstruktor untuk kelas Tunai
    public Tunai() {
        // Tidak memerlukan saldo untuk pembayaran tunai
    }
    
    // Tunai tidak mendapatkan diskon, mengembalikan total harga yang sama
    @Override
    public double diskonMenu(double totalHarga) {
        return totalHarga;
    }
    
    // Pembayaran Tunai selalu dianggap cukup
    @Override
    public boolean saldoCukup(double totalHarga) {
        return true; 
    }
    
    // Tunai tidak dikenakan biaya admin
    @Override
    public double hitungBiayaAdmin() {
        return 0; // Tidak ada biaya admin untuk pembayaran Tunai
    }
    
    // Mendapatkan nama metode pembayaran
    @Override
    public String getNamaMetode() {
        return "Tunai";
    }
}