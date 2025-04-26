public class IDR extends  MataUang {
    public IDR() {
        super("IDR", "Indonesian Rupiah", 1);
    }
    
    @Override
    public double konversiDariIDR(double nilaiIDR) {
        return nilaiIDR; // Tidak perlu konversi
    }
    
    @Override
    public double konversiKeIDR(double nilaiIDR) {
        return nilaiIDR; // Tidak perlu konversi
    }
}