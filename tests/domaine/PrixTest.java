package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class PrixTest {
    Prix prixAucune, prixPub, prixSolde;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1,20);
        prixAucune.definirPrix(10,10);
        prixPub = new Prix(TypePromo.PUB, 20 );
        prixPub.definirPrix(3,15);
        prixSolde = new Prix(TypePromo.SOLDE, 50);
    }

    @Test
    @DisplayName("teste du constructeur de Prix avec TypePromo null")
    void testPrix1(){
        assertThrows(IllegalArgumentException.class, () -> new Prix(null, 20));
    }

    @Test
    @DisplayName("teste du constructeur de Prix avec valeurPromo négative")
    void testPrix2(){
        assertThrows(IllegalArgumentException.class, () -> new Prix(TypePromo.PUB, -20));
    }

    @Test
    @DisplayName("teste du getteur de TypePromo")
    void getTypePromo() {
    }

    @Test
    @DisplayName("teste de valeurPromo du constructeur de Prix sans paramètre")
    void getValeurPromo1() {
        assertEquals(0, prixAucune.getValeurPromo());
    }

    @Test
    @DisplayName("teste de valeurPromo si correspond bien à celle passée en paramètre du constructeur valeurPromo")
    void getValeurPromo2() {
        assertEquals(20,prixPub.getValeurPromo());
    }

    @Test
    @DisplayName("teste de TypePromo si null")
    void getValeurPromo3() {
        assertNull(prixAucune.getTypePromo());
    }

    @Test
    @DisplayName("teste de TypePromo à celui passé en paramètre")
    void getValeurPromo4() {
        assertEquals(TypePromo.PUB, prixPub.getTypePromo());
    }

    @Test
    @DisplayName("teste definirPrix si lance une exception si paramètre quantite <= 0")
    void definirPrix1() {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(0,10));
    }

    @Test
    @DisplayName("teste definirPrix si lance une exception si paramètre valeur <= 0")
    void definirPrix2() {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(10,0));
    }

    @Test
    @DisplayName("teste definirPrix si l'ancien prix a été remplacé")
    void definirPrix3() {
        prixAucune.definirPrix(10,6);
        assertEquals(6 ,prixAucune.getPrix(10));
    }

    @Test
    @DisplayName("teste le paramètre de getPrix si lance exception si 0")
    void getPrix1() {
        assertThrows(IllegalArgumentException.class, () -> prixPub.getPrix(0));
    }

    @Test
    @DisplayName("teste le paramètre de getPrix avec plusieurs valeurs")
    @ParameterizedTest
    @ValueSource(ints = {1,5,9,10,15,20,25})
    void getPrix2() {
        //assertEquals();
    }
}