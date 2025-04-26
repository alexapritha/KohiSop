public abstract class Menu {
  protected String kode;
  protected String nama;
  protected double harga;
  protected int kuantitas;

  // Konstruktor
  public Menu(String kode, String nama, double harga) {
      this.kode = kode;
      this.nama = nama;
      this.harga = harga;
      this.kuantitas = 0;
  }

  // Getter & Setter
  public String getKode() { return kode; }
  public String getNama() { return nama; }
  public double getHarga() { return harga; }
  public int getKuantitas() { return kuantitas; }
  public void setKuantitas(int kuantitas) { this.kuantitas = kuantitas; }

  // Perhitungan total
  public double hitungTotalHarga() {
      return harga * kuantitas;
  }

  public double hitungTotalPajak() {
      return Pajak.hitungPajak(this) * kuantitas;
  }

  // Kategori ditentukan oleh subclass
  public abstract String getKategori();

  // Tampilkan daftar menu
  protected static void tampilkanDaftarMenu(Menu[] daftarMenu) {
      System.out.println("========================= SELAMAT DATANG DI KOHISOP ======================\n");

      System.out.println("                             DAFTAR MENU MINUMAN                          ");
      System.out.println("-------------------------------------------------------------------------");
      System.out.println("Kode | Nama                                       | Harga (Rp)          ");
      System.out.println("-------------------------------------------------------------------------");

      for (Menu menu : daftarMenu) {
          if (menu instanceof Minuman) {
              System.out.printf("%-4s | %-42s | %-20.0f\n", menu.getKode(), menu.getNama(), menu.getHarga());
          }
      }

      System.out.println("\n                             DAFTAR MENU MAKANAN                           ");
      System.out.println("-------------------------------------------------------------------------");
      System.out.println("Kode | Nama                                       | Harga (Rp)          ");
      System.out.println("-------------------------------------------------------------------------");

      for (Menu menu : daftarMenu) {
          if (menu instanceof Makanan) {
              System.out.printf("%-4s | %-42s | %-20.0f\n", menu.getKode(), menu.getNama(), menu.getHarga());
          }
      }
      System.out.println("-------------------------------------------------------------------------");
      System.out.println("*) Ketik 'CC' untuk membatalkan pesanan.\n");
  }

  // Cari menu berdasarkan kode
  public static Menu cariMenu(Menu[] daftarMenu, String kode) {
      for (Menu menu : daftarMenu) {
          if (menu != null && menu.getKode().equals(kode)) {
              return menu;
          }
      }
      return null;
  }
}
