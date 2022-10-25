package domaine;

import exceptions.QuantiteNonAutoriseeException;
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
        prixPub = new Prix(TypePromo.PUB, 10 );
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
        assertThrows(IllegalArgumentException.class, () -> new Prix(TypePromo.PUB, -1));
    }

    @Test
    @DisplayName("teste du getteur de TypePromo")
    void getTypePromo() {
        assertAll(()->assertEquals(0,prixAucune.getValeurPromo()),
                ()-> assertEquals(10,prixPub.getValeurPromo()),
                () ->assertEquals(50,prixSolde.getValeurPromo()));
    }

    @Test
    @DisplayName("teste de valeurPromo du constructeur de Prix")
    void getValeurPromo() {
        assertAll(()->assertNull(prixAucune.getTypePromo()),
                ()-> assertSame(TypePromo.PUB,prixPub.getTypePromo()),
                () ->assertSame(TypePromo.SOLDE,prixSolde.getTypePromo()));
    }

    @ParameterizedTest
    @ValueSource(ints={-1,0})
    @DisplayName("teste definirPrix si lance une exception si paramètre quantite <ou = à 0")
    void definirPrix1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(quantite,10));
    }

    @ParameterizedTest
    @ValueSource(doubles={-3,0})
    @DisplayName("teste definirPrix si lance une exception si paramètre valeur <= 0")
    void definirPrix2(double valeur) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(10,valeur));
    }

    @Test
    @DisplayName("teste definirPrix si l'ancien prix a été remplacé")
    void definirPrix3() {
        prixAucune.definirPrix(10,6);
        assertEquals(6 ,prixAucune.getPrix(10));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,0})
    @DisplayName("teste le paramètre de getPrix si lance exception si < ou = à 0")
    void getPrix1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.getPrix(quantite));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,5,9})
    @DisplayName("teste le paramètre de getPrix si lance exception si {1,5,9}")
    void getPrix2a(int quantite) throws QuantiteNonAutoriseeException {
        assertEquals(20,prixAucune.getPrix(quantite));
    }

    @DisplayName("teste le paramètre de getPrix avec valeur 15,20,25")
    @ParameterizedTest
    @ValueSource(ints = {10, 11, 15,20,25})  // conseil toujours tester les valeurs -1 et +1 de la borne afin de tester le < ou <=
    void getPrix2b(int quantite) {
        assertEquals(10, prixAucune.getPrix(quantite));
    }

    @Test
    @DisplayName("teste de la quantiteNonAutoriseeException si demande de prix pour 2 unités")
    void getPrix3(){
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixPub.getPrix(2));
    }

    @Test
    @DisplayName("Test de getPrix s'il n'y a pas de prix défini")
    void getPrix4(){
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixSolde.getPrix(1));
    }


}