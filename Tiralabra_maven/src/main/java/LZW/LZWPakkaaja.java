package LZW;

import Apuvalineet.BinaariMuuntaja;
import Apuvalineet.Kirjoittaja;
import java.io.IOException;

/**
 * Suorittaa tiedoston pakkaamisen.
 * T�m� tapahtuu k�yt�nn�ss� pyyt�m�ll� LZWLukijaa lukemaan tiedosto ja
 * k��nt�m�ll� se ascii -koodiksi.
 * 
 * T�m�n j�lkeen pyydet��n Kirjoittajaa kirjoittamaan ko. ascii- koodi.
*/

public class LZWPakkaaja {
    private BinaariMuuntaja muuntaja;
    private LZWLukija lukija;
    
    public LZWPakkaaja() {
        this.muuntaja = new BinaariMuuntaja();
        this.lukija = new LZWLukija();
    }
    
    /**
     * Lukee tiedoston ja muuttaa sis�ll�n ascii -koodiksi ja kirjoittaa t�m�n.
     * @param polku
     * @throws IOException 
     */
    
    public void suoritaPakkaaminen(String polku) throws IOException {
        lukija.lue(polku);

        Kirjoittaja kirjoittaja = new Kirjoittaja(polku + ".lzw");
        String kirjoitettava = kirjoitettava(lukija.getTeksti());
        kirjoittaja.kirjoita(kirjoitettava);
    }
    
    /**
     * Muuntaa bin��rimuotoisen tekstin ascii -koodiksi.
     * @param ilmanEtuNollia
     * @return 
     */
    
    public String kirjoitettava(String ilmanEtuNollia) {
        StringBuilder pakattavaTeksti = new StringBuilder();
        String pakattuna = pakattuna(ilmanEtuNollia);
        
        pakattavaTeksti.append((char) muuntaja.getLisatytEtuNollat());
        pakattavaTeksti.append(pakattuna);
        
        return pakattavaTeksti.toString();
    }
    
    /**
     * Lis�� etunollia kunnes koko on 8:lla jaollinen ja muuntaa t�m�n
     * j�lkeen asciiksi.
     * @param ilmanEtuNollia
     * @return 
     */
    
    protected String pakattuna(String ilmanEtuNollia) {
        String ykkosinaJaNollina = muuntaja.lisaaEtuNollat(ilmanEtuNollia);
        return muuntaja.asciiKoodiksi(ykkosinaJaNollina);
    }
}
