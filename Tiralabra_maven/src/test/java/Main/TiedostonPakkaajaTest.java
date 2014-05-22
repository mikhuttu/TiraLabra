package Main;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TiedostonPakkaajaTest {
    private TiedostonPakkaaja pakkaaja;
    
    @Before
    public void setUp() {
        this.pakkaaja = new TiedostonPakkaaja();
    }

    @Test
    public void pakkaaminentest() {
        pakkaaja.pakkaa("testi.txt");
    }
}