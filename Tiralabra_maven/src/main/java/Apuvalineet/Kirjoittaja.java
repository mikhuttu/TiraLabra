package Apuvalineet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Luokka toimii "FileWriter" -oliona, jolla ei ole tarkoitus muuta kuin kirjoittaa valittu tiedosto.
 */

public class Kirjoittaja {
    private Writer kirjoittaja;
    
    public Kirjoittaja(String polku) throws IOException {
        this(polku, false);
    }
    
    /**
     * Luo uuden tiedoston jos konstruktoria kutsuttiin arvoilla (polku, true) ja t�m�n
     * j�lkeen luo kirjoittajan.
     * @param polku
     * @param tiedostoOlemassa
     * @throws IOException 
     */
    
    public Kirjoittaja(String polku, boolean tiedostoOlemassa) throws IOException {
        if (!tiedostoOlemassa) {
            luoUusiTiedosto(polku);
        }
        
        this.kirjoittaja = new OutputStreamWriter(new FileOutputStream(polku), "UTF-8");
    }
    
    public void kirjoita(String teksti) throws IOException {
        kirjoittaja.write(teksti);
        kirjoittaja.close();
    }
    
    /**
     * Luo uuden tyhj�n tiedoston tai heitt�� poikkeuksen jos samanniminen tiedosto on jo olemassa.
     * @param polku
     * @throws IOException
     */
    
    private void luoUusiTiedosto(String polku) throws IOException {
        File tiedosto = new File(polku);
        
        if (! tiedosto.exists()) {
            tiedosto.createNewFile();
            tiedosto.setWritable(true);
            return;
        }

        throw new IOException("Haluttu tiedosto on jo olemassa. Tiedostoa ei luoda uudestaan");
    }
}
