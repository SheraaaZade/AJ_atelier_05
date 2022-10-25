package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProduitTest {
    Prix prixAucune, prixPub, prixSolde;
    Produit prod1, prod2;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1,20);
        prixAucune.definirPrix(10,10);
        prixPub = new Prix(TypePromo.PUB, 20 );
        prixPub.definirPrix(3,15);
        prixSolde = new Prix(TypePromo.SOLDE, 50);

        prod1 = new Produit("Bueno", "Kinder", "64");
        prod2 = new Produit("Switch", "Nintendo", "10");

        LocalDate date = LocalDate.of(2022,1,15);
        prod1.ajouterPrix(date, prixSolde);
        date = LocalDate.of(2022,11,5);
        prod1.ajouterPrix(date, prixPub);
        date = LocalDate.of(2021,6,7);
        prod1.ajouterPrix(date, prixAucune);
    }

    @Test
    @DisplayName("test of the param nom if null throws IAE")
    void testProduit1a(){
        assertThrows( IllegalArgumentException.class, ()  ->
               new Produit (null, "Kinder", "1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ","    \n   ","\t\t   "})
    @DisplayName("test of the param nom contains spaces -> throws IAE")
    void testProduit1b(String nom){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit (nom, "Kinder", "1"));
    }

    @Test
    @DisplayName("test of the param marque if null throws IAE")
    void testProduit2a(){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Switch", null, "1"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ","    \n   ","\t\t   "})
    @DisplayName("test of the param marque contains spaces -> throws IAE")
    void testProduit2b(String marque){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Switch", marque, "1"));
    }

    @Test
    @DisplayName("test of the param rayon if null throws IAE")
    void testProduit3a(){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Bueno", "Kinder", null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ","    \n   ","\t\t   "})
    @DisplayName("test of the param rayon contains spaces -> throws IAE")
    void testProduit3b(String rayon){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Bueno", "Kinder", rayon));
    }

    @Test
    @DisplayName("test of getMarque")
    void getMarque(){
        assertEquals("Nintendo", prod2.getMarque());
    }

    @Test
    @DisplayName("test of getNom")
    void getNom(){
        assertEquals("Bueno", prod1.getNom());
    }

    @Test
    @DisplayName("test of getRayon")
    void getRayon(){
        assertEquals("64", prod1.getRayon());
    }

    @Test
    @DisplayName("test ajouterPrix if throws IAE if date is null")
    void ajouterPrix1(){
        assertThrows(IllegalArgumentException.class, ()
            -> prod2.ajouterPrix(null, prixAucune));
    }

    @Test
    @DisplayName("test ajouterPrix if throws IAE if prix is null")
    void ajouterPrix2(){
        assertThrows(IllegalArgumentException.class, ()
                -> prod2.ajouterPrix(LocalDate.of(2022,5,1), null));
    }

    @Test
    @DisplayName("test ajouterPrix if throws DateDejaPresenteException if date is already present")
    void ajouterPrix3(){
        assertThrows(DateDejaPresenteException.class, ()
                -> prod1.ajouterPrix(LocalDate.of(2022,1,15), new Prix()));
    }

    @Test
    @DisplayName("test ajouterPrix if ")
    void ajouterPrix4(){
        Prix prixMoisPasse = prod1.getPrix(LocalDate.of(2022,1,15));
        Prix prixAujourdhui = prod1.getPrix(LocalDate.of(2022,2,15));
        assertAll(
                () -> assertEquals(prixPub, prod1.getPrix(LocalDate.of(2022,1,15))),
                () -> assertEquals(prixSolde, prod1.getPrix(LocalDate.of(2022,2,14)))
        );
    }

    @Test
    @DisplayName("test if product doesn't exists at given date throws PrixNonDisponibleException")
    void testGetPrix5(){
        assertThrows(PrixNonDisponibleException.class,
                () -> prod1.getPrix(LocalDate.of(2006,1,15)));
    }

    @Test
    @DisplayName("test if product doesn't have price throws PrixNonDisponibleException")
    void testPrixProduit() throws PrixNonDisponibleException{
        assertThrows(PrixNonDisponibleException.class,
                () -> prod2.getPrix(LocalDate.of(2022,1,15)));
    }

    @Test
    @DisplayName("test if date antérieur est renvoyé entre 2 dates pour lesquelles le prix a été défini")
    void testDateComprise()throws PrixNonDisponibleException{
        assertEquals(prod1.getPrix(LocalDate.of(2022,7,7)), prixSolde);
    }

    @Test
    @DisplayName("test equals method if 2 objects with same value are the same")
    void testEquals1(){
        Produit prd1 = new Produit("Bueno", "Kinder", "64");
        assertEquals(prod1, prd1);
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same name")
    void testEquals2(){
        Produit prd1 = new Produit("Chocobon", "Kinder", "64");
        assertNotEquals(prod1, prd1);
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same marque")
    void testEquals3(){
        Produit prd1 = new Produit("Bueno", "KitKat", "64");
        assertNotEquals(prod1, prd1);
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same rayons")
    void testEquals4(){
        Produit prd1 = new Produit("Bueno", "Kinder", "63");
        assertNotEquals(prod1, prd1);
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same rayons")
    void testHashcode(){
        Produit prd1 = new Produit("Bueno", "Kinder", "64");
        assertEquals(prod1.hashCode(), prd1.hashCode());
    }
}
