// Class abstract MataUang yang akan diperjelas lewat konkrit jenis mata uang
public abstract class MataUang {
    private final String kode;
    private final String nama;
    private final double nilaiTukar;
    
    public MataUang(String kode, String nama, double nilaiTukar) {
        this.kode = kode;
        this.nama = nama;
        this.nilaiTukar = nilaiTukar;
    }
    
    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }
    
    public double getNilaiTukar() {
        return nilaiTukar;
    }
    
    public double konversi(double jumlahUang) {
        return jumlahUang / nilaiTukar;
    }
    
    public double konversiDariIDR(double nilaiIDR) {
        return nilaiIDR / nilaiTukar;
    }
    
    public double konversiKeIDR(double nilaiMataUang) {
        return nilaiMataUang * nilaiTukar;
    }
    
    @Override
    public String toString() {
        return kode + " - " + nama + " (1 " + kode + " = " + nilaiTukar + " IDR)";
    }
}