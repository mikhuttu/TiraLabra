package Huffman;

import Apuvalineet.Kirjoittaja;
import Tietorakenteet.HajautusTaulu;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HuffmanPurkajaTest {
    private HuffmanPurkaja purkaja;
    private final String n = (char) 0 + "";
    private final String y = (char) 1 + "";
    
    @Before
    public void setUp() throws IOException {
        this.purkaja = new HuffmanPurkaja();
        luoTestiTiedostoJosTarpeen();
    }
    
    @After
    public void tuhoaTestiTiedosto() {
        File testi = new File("TiedostonPurkajaTest.huff");
        testi.delete();
    }
    
    private void luoTestiTiedostoJosTarpeen() throws IOException {
        File testi = new File("TiedostonPurkajaTest.huff");
        if (! testi.exists()) {
            Kirjoittaja kirjoittaja = new Kirjoittaja(testi.getPath());
            kirjoittaja.kirjoita("a"+n+n + "b"+n+y + "c"+y+n + "d"+y+y + (char) 127 + (char) 127 + n + (char) 135) ;
        }
    }
 
    
    @Test
    public void kirjoitettavaTeksti() {
        HajautusTaulu bittijonotJaMerkit = bittijonotJaMerkit();
        
        assertTrue(purkaja.kirjoitettavaTeksti("", bittijonotJaMerkit).isEmpty());
        assertEquals("bcadabc", purkaja.kirjoitettavaTeksti(y+n+y+y+n+n+y+y+y+n+y+n+y+y+n, bittijonotJaMerkit));
    }
    
    private HajautusTaulu bittijonotJaMerkit() {
        HajautusTaulu bittijonotJaMerkit = new HajautusTaulu();
        bittijonotJaMerkit.lisaa(n, "a");
        bittijonotJaMerkit.lisaa(y+n, "b");
        bittijonotJaMerkit.lisaa(y+y+n, "c");
        bittijonotJaMerkit.lisaa(y+y+y, "d");
        
        return bittijonotJaMerkit;
    }
    
    @Test
    public void kayPuuLapi() {
        puunLapiKayntiTyhjallaTekstilla();
        puunLapiKayntiTekstillaJossaTavallisiaAsciiMerkkeja();
        
        josBittiEsitysEpaTyhjaLisataanSeHajautusTauluun();
        lisaaMerkkiJosSeOn0Tai1();
        puuOnKelattuLoppuun();
    }
    
    private void puunLapiKayntiTyhjallaTekstilla() {
        HajautusTaulu bittijonotJaMerkit = new HajautusTaulu();
        String teksti = (char) 127 + "" + (char) 127;
        
        assertEquals(2, purkaja.kayPuuLapi(teksti, bittijonotJaMerkit));
        assertTrue(bittijonotJaMerkit.onTyhja());
    }
    
    private void puunLapiKayntiTekstillaJossaTavallisiaAsciiMerkkeja() {
        HajautusTaulu bittijonotJaMerkit = new HajautusTaulu();
        
        String teksti = "c" + n + n + n + "b" + n + n + y + "a" + n + y + n + "d" + n + y + y + "f" + y + y + "e" + y + n + 
                        (char) 127 + "" + (char) 127 + "_abcdef";
        
        assertEquals(teksti.length() - 7, purkaja.kayPuuLapi(teksti, bittijonotJaMerkit));
        
        HajautusTaulu verrattava = puunLapiKayntiaVerrattavaHajTaulu();
        
        assertTrue(bittijonotJaMerkit.getKoko() == verrattava.getKoko());
        
        for (String bittijono : bittijonotJaMerkit.getAvaimet()) {
            assertTrue(bittijonotJaMerkit.getArvo(bittijono).equals(verrattava.getArvo(bittijono)));
        }
    }
    
    private HajautusTaulu puunLapiKayntiaVerrattavaHajTaulu() {
        HajautusTaulu verrattava = new HajautusTaulu();
        
        verrattava.lisaa(n+y+n, "a");
        verrattava.lisaa(n+n+y, "b");
        verrattava.lisaa(n+n+n, "c");
        verrattava.lisaa(n+y+y, "d");
        verrattava.lisaa(y+n, "e");
        verrattava.lisaa(y+y, "f");
        
        return verrattava;
    }

    private void josBittiEsitysEpaTyhjaLisataanSeHajautusTauluun() {
        HajautusTaulu bittijonotJaMerkit = new HajautusTaulu();
        String sata = y+n+n;
        
        purkaja.bittiEsitysEiTyhjaLisaaMerkki('a', sata, bittijonotJaMerkit);
        
        assertTrue(bittijonotJaMerkit.sisaltaaAvaimen(sata));
        assertTrue(bittijonotJaMerkit.getArvo(sata).equals("a"));
        assertFalse(purkaja.bittiEsitysEiTyhjaLisaaMerkki('b', "", bittijonotJaMerkit));
    }
    
    private void lisaaMerkkiJosSeOn0Tai1() {
        StringBuilder bittiEsitys = new StringBuilder();
        assertTrue(purkaja.lisaaMerkkiJosSeOn0Tai1((char) 0, bittiEsitys));
        assertTrue(purkaja.lisaaMerkkiJosSeOn0Tai1((char) 1, bittiEsitys));
        assertEquals(n+y, bittiEsitys.toString());
        
        assertFalse(purkaja.lisaaMerkkiJosSeOn0Tai1('d', bittiEsitys));
    }
    
    private void puuOnKelattuLoppuun() {
        String teksti = "abcd" + (char) 127 + "" + (char) 127;
        assertFalse(purkaja.puuOnKelattuLoppuun(teksti, 2));
        assertFalse(purkaja.puuOnKelattuLoppuun(teksti, 3));
        assertTrue(purkaja.puuOnKelattuLoppuun(teksti, 4));
    }
}
