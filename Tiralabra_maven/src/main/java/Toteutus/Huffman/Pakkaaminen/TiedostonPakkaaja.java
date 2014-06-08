package Toteutus.Huffman.Pakkaaminen;

import Apuvalineet.BinaariMuuntaja;
import Apuvalineet.Kirjoittaja;
import Toteutus.Huffman.BittiEsitykset;
import Toteutus.Huffman.HuffmanPuu;
import Toteutus.TekstinLukija;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Luokka suorittaa uuden tiedoston (pakkauksen) luomisen.
 * Se k�ytt�� apuna luokkaa "BinaariMuuntaja", joka tekee muunnoksia int -ja String arvojen v�lill�.
 */

public class TiedostonPakkaaja {
    private BinaariMuuntaja muuntaja;
    
    public TiedostonPakkaaja() {
        this.muuntaja = new BinaariMuuntaja();
    }
    
    /**
     * Luo pakkauksen sek� siihen kirjoitettavan tekstin ja lopuksi kirjoittaa ko. tekstin pakkaukseen.
     * @param lukija
     * @param puu
     * @param esitykset
     * @param polku
     * @throws IOException 
     */
    
    public void pakkaaTiedosto(TekstinLukija lukija, HuffmanPuu puu, BittiEsitykset esitykset, String polku) throws IOException {
        File tiedosto = luoUusiTiedosto(polku);
        String teksti = muodostaKirjoitettavaTeksti(esitykset, puu, lukija.getTeksti());
        kirjoitaTiedostoon(tiedosto, teksti);
    }
    
    /**
     * Luo uuden tyhj�n tiedoston tai heitt�� poikkeuksen jos samanniminen tiedosto on jo olemassa (ts. haluttu tiedosto
     * on jo pakattu).
     * @param polku - pakattavan tiedoston polku
     * @return
     * @throws IOException - pakkaus on jo olemassa 
     */
    
    protected File luoUusiTiedosto(String polku) throws IOException {
        File tiedosto = new File(polku + ".hemi");
        
        if (! tiedosto.exists()) {
            tiedosto.createNewFile();
            tiedosto.setWritable(true);
            return tiedosto;
        }

        throw new IOException("Tiedostoa vastaava pakkaus on jo olemassa. Tiedostoa ei pakata uudestaan.");
    }
    
    /**
     * Kirjoittaaa parametrina annettuun tiedostoon parametrina annetun tekstin.
     * @param tiedosto
     * @param teksti
     * @throws IOException
     */
    
    protected void kirjoitaTiedostoon(File tiedosto, String teksti) throws IOException {
        Kirjoittaja kirjoittaja = new Kirjoittaja(tiedosto.getPath());
        kirjoittaja.kirjoita(teksti);
    }
    
    /**
     * Muodostaa tekstin, joka pakattavaan tiedostoon kirjoitetaan (ts. koko pakkauksen sis�ll�n).
     * Ensin muodostetaan tekstist� pakattava "0/1" -versio, jonka j�lkeen luodaan StringBuilder- olio, johon
     * lis�t��n kaikki tarpeellinen yksi kerrallaan.
     * @param esitykset
     * @param puu
     * @param teksti
     * @return 
     */
    
    protected String muodostaKirjoitettavaTeksti(BittiEsitykset esitykset, HuffmanPuu puu, String teksti) {
        String pakattuna = tekstiPakattuna(esitykset.getEsitykset(), teksti);
        StringBuilder kirjoitettava = new StringBuilder();
        lisaaTeksti(kirjoitettava, pakattuna, esitykset);

        return kirjoitettava.toString();
    }
    
    /**
     * Muodostaa tekstist� pakattavan 0/1 -version.
     * @param bittijonot
     * @param teksti
     * @return 
     */
    protected String tekstiPakattuna(HashMap<String, String> bittijonot, String teksti) {
        String ykkosinaJaNollina = muuntaja.ykkosinaJaNollina(teksti, bittijonot);
        return muuntaja.pakatuksiTekstiksi(ykkosinaJaNollina);
    }
    
    /**
     * Lis�� tyhj�lle StringBuilder -oliolle koko pakattavan tiedoston sis�ll�n.
     * Ensin tulee Huffman -puun tekstiesitys. Sitten 1 tavu joka kertoo, kuinka monta
     * etunollaa tekstin bin��riesityksen eteen lis�ttiin ja lopuksi tekstin pakattu bin��riesitys.
     * @param kirjoitettava
     * @param pakattuna
     * @param esitykset
     */
    
    protected void lisaaTeksti(StringBuilder kirjoitettava, String pakattuna, BittiEsitykset esitykset) {
        kirjoitettava.append(esitykset.huffmanPuunTekstiEsitys());
        kirjoitettava.append((char) muuntaja.getLisatytEtuNollat());
        kirjoitettava.append(pakattuna);
    }
}