public class Kwitansi {
    private final Menu[] daftarPesanan;
    private final int jumlahPesanan;
    private final IPayment metodePembayaran;
    private final MataUang mataUang;
    private double totalHarga;
    public double totalPajak;
    private double totalDiskon;
    private double biayaAdmin;
    public double totalBayar;

    // Konstruktor untuk kelas Kwitansi
    public Kwitansi(Menu[] keranjang, int jumlahItem, IPayment metodePembayaran, MataUang mataUang) {  // Changed parameter type
        this.daftarPesanan = new Menu[jumlahItem];
        this.jumlahPesanan = jumlahItem;
        
        // Copy array keranjang ke daftarPesanan
        for (int i = 0; i < jumlahItem; i++) {
            this.daftarPesanan[i] = keranjang[i];
        }
        
        this.metodePembayaran = metodePembayaran;
        this.mataUang = mataUang;
        hitungTotal();
    }

    // Menghitung total harga, pajak, diskon, dan total bayar
    private void hitungTotal() {
        totalHarga = 0;
        totalPajak = 0;

        for (int i = 0; i < jumlahPesanan; i++) {
            Menu menu = daftarPesanan[i];
            totalHarga += menu.hitungTotalHarga();
            totalPajak += menu.hitungTotalPajak();
        }

        double totalSebelumDiskon = totalHarga + totalPajak;
        double totalSetelahDiskon = metodePembayaran.diskonMenu(totalSebelumDiskon);
        totalDiskon = totalSebelumDiskon - totalSetelahDiskon;
        biayaAdmin = metodePembayaran.hitungBiayaAdmin();
        totalBayar = totalHarga + totalPajak - totalDiskon + biayaAdmin;
    }

    // Method bantuan untuk pengurutan array
    private void sortMenuByKategori() {
        for (int i = 0; i < jumlahPesanan - 1; i++) {
            for (int j = 0; j < jumlahPesanan - i - 1; j++) {
                if (daftarPesanan[j].getKategori().compareTo(daftarPesanan[j + 1].getKategori()) > 0) {
                    Menu temp = daftarPesanan[j];
                    daftarPesanan[j] = daftarPesanan[j + 1];
                    daftarPesanan[j + 1] = temp;
                }
            }
        }
    }

    public void tampilkanKwitansi() {
        S.clear();
        System.out.println("\n\n");
        System.out.println("==================================== KOHISHOP ==========================================\n");
        System.out.println("                                 KWITANSI PEMBAYARAN                              ");
        System.out.println("========================================================================================");
    
        // Urutkan pesanan berdasarkan kategori menggunakan bubble sort
        sortMenuByKategori();
    
        // Tampilkan rincian pesanan per kategori
        System.out.println("Kode  Menu                              Harga     Qty     Total     Pajak(%)     Pajak");
        System.out.println("----------------------------------------------------------------------------------------");
    
        String kategoriSebelumnya = "";
        for (int i = 0; i < jumlahPesanan; i++) {
            Menu menu = daftarPesanan[i];
            if (!menu.getKategori().equals(kategoriSebelumnya)) {
                System.out.println(menu.getKategori().toUpperCase() + ":");
                kategoriSebelumnya = menu.getKategori();
            }
    
            String kode = menu.getKode();
            String nama = menu.getNama();
            double harga = menu.getHarga();
            int kuantitas = menu.getKuantitas();
            double total = harga * kuantitas;
            
            double pajak = Pajak.hitungPajak(menu);
            double totalPajakItem = kuantitas * pajak;
            double persenPajak = (pajak / harga) * 100;
    
            System.out.printf("%-5s  %-30s %8.0f  x %2d  %10.0f   %8.0f%%   %10.0f\n",
                    kode, nama, harga, kuantitas, total, persenPajak, totalPajakItem);
        }
    
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("Total Harga Sebelum Pajak               = Rp %,19.0f\n", totalHarga);
        System.out.printf("Total Pajak                             = Rp %,19.0f\n", totalPajak);
        System.out.printf("Total Harga Setelah Pajak               = Rp %,19.0f\n", (totalHarga + totalPajak));
    
        // Hitung persentase diskon
        double persentaseDiskon = (totalDiskon / (totalHarga + totalPajak)) * 100;
    
        System.out.println();
        System.out.printf("( Metode Pembayaran %s dengan Mata Uang %s)\n", metodePembayaran.getNamaMetode(), mataUang.getNama());
    
        if (biayaAdmin > 0) {
            System.out.printf("Biaya Admin %-27s = Rp %,19.0f\n",
                    metodePembayaran.getNamaMetode(), biayaAdmin);
        }
    
        System.out.printf("Total Akhir (IDR)                       = Rp %,19.0f\n", totalBayar);
    
        // Konversi mata uang
        double totalMataUang = mataUang.konversiDariIDR(totalBayar);
        System.out.printf("Total Bayar (%s)                       = %s %,18.2f\n",
                mataUang.getKode(), mataUang.getKode(), totalMataUang);
    
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("                       Terima kasih dan silakan datang kembali!                        ");
        System.out.println("----------------------------------------------------------------------------------------");
    }

    // Method untuk mendapatkan total bayar
    public double getTotalBayar() {
        return totalBayar;
    }
}