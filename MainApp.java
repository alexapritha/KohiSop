import java.util.Scanner;

public class MainApp {
    private static final int MAX_JENIS_PESANAN = 5;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Menu[] daftarMenu = new Menu[18];
    private static final Menu[] keranjang = new Menu[MAX_JENIS_PESANAN];
    private static final MataUang[] daftarMataUang = new MataUang[5];
    private static int jumlahItemKeranjang = 0;

    public static void main(String[] args) {
        while (true) {
            jumlahItemKeranjang = 0; // Reset keranjang
            inisialisasiMenu();
            inisialisasiMataUang();

            S.clear();
            tampilkanDaftarMenu();

            if (prosesInputPesanan()) {
                IPayment metodePembayaran;
                do {
                    metodePembayaran = pilihMetodePembayaran();
                } while (!validasiPembayaran(metodePembayaran));

                MataUang mataUang = pilihMataUang();
                Kwitansi kwitansi = new Kwitansi(keranjang, jumlahItemKeranjang, metodePembayaran, mataUang);
                kwitansi.tampilkanKwitansi();
                
                System.out.println("\nTekan ENTER untuk memulai pesanan baru atau ketik 'X' untuk keluar...");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("X")) {
                    break;
                }
                S.clear();
            } else {
                break;
            }
        }
        scanner.close();
    }

    private static void inisialisasiMenu() {
        // Inisialisasi menu minuman
        daftarMenu[0] = new Minuman("A1", "Caffe Latte", 46000);
        daftarMenu[1] = new Minuman("A2", "Cappuccino", 46000);
        daftarMenu[2] = new Minuman("E1", "Caffe Americano", 37000);
        daftarMenu[3] = new Minuman("E2", "Caffe Mocha", 55000);
        daftarMenu[4] = new Minuman("E3", "Caramel Macchiato", 59000);
        daftarMenu[5] = new Minuman("E4", "Asian Dolce Latte", 55000);
        daftarMenu[6] = new Minuman("E5", "Double Shots Iced Shaken Espresso", 50000);
        daftarMenu[7] = new Minuman("B1", "Freshly Brewed Coffee", 23000);
        daftarMenu[8] = new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50000);
        daftarMenu[9] = new Minuman("B3", "Cold Brew", 44000);

        // Inisialisasi menu makanan
        daftarMenu[10] = new Makanan("M1", "Petemania Pizza", 112000);
        daftarMenu[11] = new Makanan("M2", "Mie Rebus Super Mario", 35000);
        daftarMenu[12] = new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial", 72000);
        daftarMenu[13] = new Makanan("M4", "Soto Kambing Iga Guling", 124000);
        daftarMenu[14] = new Makanan("S1", "Singkong Bakar A La Carte", 37000);
        daftarMenu[15] = new Makanan("S2", "Ubi Cilembu Bakar Arang", 58000);
        daftarMenu[16] = new Makanan("S3", "Tempe Mendoan", 18000);
        daftarMenu[17] = new Makanan("S4", "Tahu Bakso Extra Telur", 28000);
    }

    // Inisialisasi daftar mata uang
    private static void inisialisasiMataUang() {
        daftarMataUang[0] = new IDR();
        daftarMataUang[1] = new USD();
        daftarMataUang[2] = new JPY();
        daftarMataUang[3] = new MYR();
        daftarMataUang[4] = new EUR();
    }

    // Menampilkan daftar menu
    private static void tampilkanDaftarMenu() {
        Menu.tampilkanDaftarMenu(daftarMenu);
    }

    // Memproses input pesanan dari pengguna
    private static boolean prosesInputPesanan() {
        while (jumlahItemKeranjang < MAX_JENIS_PESANAN) {
            System.out.println("==== SILAHKAN PESAN ====");
            System.out.print("Masukkan kode menu: ");
            String kodeMenu = scanner.nextLine().toUpperCase();

            if (kodeMenu.equals("CC")) {
                S.delay(100);
                System.out.println("Pesanan dibatalkan.");
                return false;
            }

            Menu menu = cariMenu(kodeMenu);
            if (menu == null) {
                S.delay(100);
                System.out.println("Menu dengan kode " + kodeMenu + " tidak ditemukan. Silakan coba lagi.");
                S.delay(1000);
                continue;
            } else if (cekMenuSudahAda(kodeMenu)) {
                continue;
            }

            int kuantitas = inputKuantitas(menu);
            if (kuantitas <= 0) {
                continue;
            }

            menu.setKuantitas(kuantitas);
            keranjang[jumlahItemKeranjang] = menu;
            jumlahItemKeranjang++;

            S.clear();
            tampilkanPesananSaatIni();

            if (jumlahItemKeranjang >= MAX_JENIS_PESANAN) {
                S.delay(100);
                System.out.println("Anda telah mencapai batas maksimum jenis pesanan (" + MAX_JENIS_PESANAN + ").\n");
                break;
            }

            if (!tanyaLanjutPesan()) {
                break;
            }
        }
        return true;
    }

    private static Menu cariMenu(String kode) {
        for (Menu menu : daftarMenu) {
            if (menu != null && menu.getKode().equals(kode)) {
                return menu;
            }
        }
        return null;
    }

    private static boolean cekMenuSudahAda(String kodeMenu) {
        for (int i = 0; i < jumlahItemKeranjang; i++) {
            if (keranjang[i].getKode().equals(kodeMenu)) {
                System.out.println("Menu " + keranjang[i].getNama() + " sudah ada di keranjang anda.\n");
                return true;
            }
        }
        return false;
    }

    // Input kuantitas menu
    private static int inputKuantitas(Menu menu) {
        int maxKuantitas = (menu instanceof Minuman) ? 10 : 5; // Max 10 for drinks, 5 for food

        System.out.print("Masukkan kuantitas (1-" + maxKuantitas + ", 0 atau S untuk membatalkan): ");
        String inputKuantitas = scanner.nextLine();

        if (inputKuantitas.equalsIgnoreCase("S") || inputKuantitas.equals("0")) {
            System.out.println("Pesanan untuk " + menu.getNama() + " dibatalkan.\n");
            return 0;
        }

        if (inputKuantitas.isEmpty()) {
            return 1; // Default kuantitas adalah 1
        }

        try {
            int kuantitas = Integer.parseInt(inputKuantitas);
            if (kuantitas < 1 || kuantitas > maxKuantitas) {
                System.out
                        .println("Kuantitas tidak valid. Silakan masukkan angka antara 1 dan " + maxKuantitas + ".\n");
                return 0;
            }
            return kuantitas;
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Silakan masukkan angka.\n");
            return 0;
        }
    }

    private static void tampilkanPesananSaatIni() {
        System.out.println("                             PESANAN SAAT INI");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Kode | Nama                                      | Harga    | Kuantitas");
        System.out.println("-------------------------------------------------------------------------");

        double totalSementara = 0;
        for (int i = 0; i < jumlahItemKeranjang; i++) {
            Menu m = keranjang[i];
            System.out.printf("%-4s | %-42s| Rp %-6.0f | %-9d\n",
                    m.getKode(), m.getNama(), m.getHarga(), m.getKuantitas());
            totalSementara += m.hitungTotalHarga();
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("Total Sementara: Rp %.2f\n", totalSementara);
        System.out.println("-------------------------------------------------------------------------");
    }

    // Tanya apakah ingin menambah pesanan lagi
    private static boolean tanyaLanjutPesan() {
        System.out.print("Apakah Anda ingin menambah pesanan lagi? (Y/N): ");
        String lanjut = scanner.nextLine();

        if (lanjut.equalsIgnoreCase("Y")) {
            S.clear();
            tampilkanDaftarMenu();
            return true;
        } else {
            S.clear();
            return false;
        }
    }

    // Memilih metode pembayaran
    private static IPayment pilihMetodePembayaran() {
        System.out.println("\n================================= KOHISHOP =================================");
        System.out.println("                           PILIH METODE PEMBAYARAN                          ");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("1. Tunai (Tidak ada diskon)");
        System.out.println("2. QRIS (Diskon 5%)");
        System.out.println("3. EMoney (Diskon 7% + potongan 20 IDR)");
        System.out.println("-------------------------------------------------------------------------");
        
        int pilihan = inputPilihan(0, 3, "Masukkan pilihan (1-3): ");

        switch (pilihan) {
            case 1:
                return (IPayment) new Tunai();
            case 2:
                return (IPayment) new QRIS(scanner);
            case 3:
                return (IPayment) new EMoney(scanner);
            default:
                return (IPayment) new Tunai();
        }
    }

    private static boolean validasiPembayaran(IPayment metodePembayaran) {
        double totalSementara = 0;
        double totalPajak = 0;
        for (int i = 0; i < jumlahItemKeranjang; i++) {
            totalSementara += keranjang[i].hitungTotalHarga();
            totalPajak += keranjang[i].hitungTotalPajak();
        }

        if (!metodePembayaran.saldoCukup(totalSementara + totalPajak)) {
            S.clear();
            S.delay(200);
            System.out.println("\n         ! SALDO ANDA TIDAK MENCUKUPI, SILAHKAN PILIH METODE LAIN !");
            return false;
        }
        return true;
    }

    // Method pilihMataUang
    private static MataUang pilihMataUang() {
        S.clear();
        System.out.println("================================= KOHISHOP =================================\n");
        System.out.println("                           PILIH MATA UANG PEMBAYARAN                       ");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("1. IDR (Indonesian Rupiah) - 1 IDR = 1 IDR");
        System.out.println("2. USD (US Dollar) - 1 USD = 15 IDR");
        System.out.println("3. JPY (Japanese Yen) - 10 JPY = 1 IDR");
        System.out.println("4. MYR (Malaysian Ringgit) - 1 MYR = 4 IDR");
        System.out.println("5. EUR (Euro) - 1 EUR = 14 IDR");
        System.out.println("-------------------------------------------------------------------------");

        S.delay(100);
        int pilihan = inputPilihan(1, 5, "Masukkan pilihan (1-5): ");
        return daftarMataUang[pilihan - 1];
    }

    // Input pilihan dengan validasi apakah input sudah sesuai
    private static int inputPilihan(int min, int max, String input) {
        while (true) {
            System.out.print(input);
            try {
                int pilihan = Integer.parseInt(scanner.nextLine());
                if (pilihan >= min && pilihan <= max) {
                    return pilihan;
                }
                System.out.println("Pilihan tidak valid. Silakan masukkan angka " + min + "-" + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
            }
        }
    }
}