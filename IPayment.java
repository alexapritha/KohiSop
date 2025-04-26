public interface IPayment {
    // Menghitung diskon berdasarkan metode pembayaran
    double diskonMenu(double totalHarga);
    
    // Memeriksa apakah saldo cukup untuk pembayaran
    boolean saldoCukup(double totalHarga);
    
    // Menghitung biaya admin berdasarkan metode pembayaran
    double hitungBiayaAdmin();
    
    // Mendapatkan nama metode pembayaran
    String getNamaMetode();
}