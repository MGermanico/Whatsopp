import java.io.File;
import java.nio.charset.StandardCharsets;

public class a {
    public static void main(String[] args) {
        String cadena = "Hola, mundo! ppppkkln bjk ugug u???' klñmkj 36466434 3";
        byte[] bytesUTF8 = cadena.getBytes(StandardCharsets.UTF_8);
        byte[] bytesUTF16 = cadena.getBytes(StandardCharsets.UTF_16);
        File f = new File("src.zip");
        System.out.println(f.getName());
        System.out.println(cadena.length());
        System.out.println("Bytes en UTF-8: " + bytesUTF8.length);
        System.out.println("Bytes en UTF-16: " + bytesUTF16.length);
    }
}
