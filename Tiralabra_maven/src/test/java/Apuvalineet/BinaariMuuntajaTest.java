package Apuvalineet;

import Tietorakenteet.HajautusTaulu;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BinaariMuuntajaTest {
    private BinaariMuuntaja muuntaja;
    private HajautusTaulu bittijonot;
    private String teksti;
    private final String n = (char) 0 + "";
    private final String y = (char) 1 + "";
    
    @Before
    public void setUp() {
        this.muuntaja = new BinaariMuuntaja();
        
        this.bittijonot = new HajautusTaulu();
        bittijonot.lisaa("a", n+y);
        bittijonot.lisaa("b", n+n+n);
        bittijonot.lisaa("c", n+n+y);
        bittijonot.lisaa("d", y+n);
        bittijonot.lisaa("e", y+y);
        
        this.teksti = "aecdbead";
    }    
    
    @Test
    public void pakattuTekstiOnOikeanlainen() {
        tekstiIlmanEtuNollia();
        assertEquals("01010100", muuntaja.lisaaEtuNollat("01010100"));
        assertEquals(n + "1010100", muuntaja.lisaaEtuNollat("1010100"));
        
        assertEquals(n+n+n+n+n+n + n+y+y+y+n+n+y+y+n+n+n+n+y+y+n+y+y+n, muuntaja.ykkosinaJaNollina(teksti, bittijonot));
    }

    private void tekstiIlmanEtuNollia() {
        String ilmanEtuNollia = muuntaja.ilmanEtuNollia(teksti, bittijonot);
        assertEquals(ilmanEtuNollia, n+y+y+y+n+n+y+y+n+n+n+n+y+y+n+y+y+n);
    }
    
    @Test
    public void etuNollienLisaaminenKasvattaaNiidenLaskettuaMaaraa() {
        muuntaja = new BinaariMuuntaja();
        assertEquals(0, muuntaja.getLisatytEtuNollat());
        muuntaja.lisaaEtuNollat("011001");
        assertEquals(2, muuntaja.getLisatytEtuNollat());
    }
    
    @Test
    public void asciiMerkkinaPalauttaaOikeanMerkin() {
        char merkki = muuntaja.asciiMerkkina(y+n+y+n+y);
        assertTrue(merkki == 21);
        
        merkki = muuntaja.asciiMerkkina(n+y+y+n+n+y+y+n);
        assertTrue(merkki == 102);
        
        merkki = muuntaja.asciiMerkkina("");
        assertTrue(merkki == 0);
        
        merkki = muuntaja.asciiMerkkina(y+y+y+y+y+y+y);
        assertTrue(merkki == 127);
        
        merkki = muuntaja.asciiMerkkina(y+n+n+n+n+y+y+y);
        assertTrue(merkki == 135);
        
        merkki = muuntaja.asciiMerkkina(y+n+y+n+y+y+n+n);
        assertTrue(merkki == 172);
    }
    
    @Test
    public void seuraavanTavunHakuOnnistuu() {
        String ykkosinaJaNollina = muuntaja.ykkosinaJaNollina(teksti, bittijonot);
        char merkki = muuntaja.seuraavaTavuAsciiMerkkina(ykkosinaJaNollina, 0);
        assertTrue(merkki == 1);
        
        merkki = muuntaja.seuraavaTavuAsciiMerkkina(ykkosinaJaNollina, 8);
        assertTrue(merkki == 204);
    }
    
    @Test
    public void tekstinPakkaaminenTavuiksiToimii() {
        assertEquals("", muuntaja.asciiKoodiksi(""));
        assertEquals("f", muuntaja.asciiKoodiksi(n+y+y+n+n+y+y+n));
        assertEquals("moi", muuntaja.asciiKoodiksi(n+y+y+n+y+y+n+y+n+y+y+n+y+y+y+y+n+y+y+n+y+n+n+y));
    }
    
    @Test
    public void binaariEsitys8BitEtuNollilla() {
        assertEquals(n+n+n+n+n+n+n+n, muuntaja.binaariEsitys8Bit(0));
        assertEquals(n+n+n+n+n+n+y+n, muuntaja.binaariEsitys8Bit(2));
        assertEquals(y+y+y+y+y+y+y+y, muuntaja.binaariEsitys8Bit(255));
    }
    
    @Test
    public void binaariEsitys() {
        assertEquals(n, muuntaja.binaariEsitys(0));
        assertEquals(y+y+y+n+y+n+n+y+y+n+n+y, muuntaja.binaariEsitys(3737));
        assertEquals(y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y+y, muuntaja.binaariEsitys(2147483647));
    }
    
    @Test
    public void tekstiBinaarinaTavallisillaAsciiMerkeilla() {
        String text = "8a�" + (char) 2 + ".?,*)";
        assertEquals(y+n+y+y+y+n+n+n+y+y+y+y+y+y+n+n+y+n+y+y+n+n+n+n+y+n+y+n+y+n+n+n+y+n+y+n+n+y, 
                     muuntaja.puraBinaariEsitykseksi(text, 3));
    }
    
    @Test
    public void tavuIlmanEtuNolliaTavallisillaAsciiMerkeilla() {
        String text = (char) 0 + "+/";
        assertEquals(n+n+y+n+y+n+y+y, muuntaja.tavuIlmanEtuNollia(text, 0));
        
        text = "abc" + (char) 5 + "" + (char) 7 + "!D";
        assertEquals(y+y+y, muuntaja.tavuIlmanEtuNollia(text, 3));
    }
    
    @Test
    public void lisaaMuuTekstiTavallisillaAsciiMerkeilla() {
        String text = "tty56B4";
        assertTrue(muuntaja.lisaaMuuTeksti(text, 5).isEmpty());
        assertEquals(n+y+n+n+n+n+y+n+n+n+y+y+n+y+n+n, muuntaja.lisaaMuuTeksti(text, 3));
    }
}