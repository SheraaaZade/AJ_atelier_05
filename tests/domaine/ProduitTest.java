package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @Test
    @DisplayName("test of the param nom if empty throws IAE")
    void testProduit1b(){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("", "Kinder", "1"));
    }

    @Test
    @DisplayName("test of the param marque if null throws IAE")
    void testProduit2a(){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Switch", null, "1"));
    }

    @Test
    @DisplayName("test of the param marque if empty throws IAE")
    void testProduit2b(){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Switch", "", "1"));
    }

    @Test
    @DisplayName("test of the param rayon if null throws IAE")
    void testProduit3a(){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Bueno", "Kinder", null));
    }

    @Test
    @DisplayName("test of the param rayon if empty throws IAE")
    void testProduit3b(){
        assertThrows( IllegalArgumentException.class, ()  ->
                new Produit ("Bueno", "Kinder", ""));
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
            -> prod2.ajouterPrix(null, new Prix()));
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
        Prix prix = prod1.getPrix(LocalDate.of(2022,1,15));
        assertEquals(prod1.getPrix(LocalDate.of(2022,1,15)),prix);
    }

    @Test
    @DisplayName("test if product doesn't exists at given date throws PrixNonDisponibleException")
    void testDateAnterieur(){
        assertThrows(PrixNonDisponibleException.class,
                () -> prod1.getPrix(LocalDate.of(2006,1,15)));
    }

    @Test
    @DisplayName("test if product doesn't have price throws PrixNonDisponibleException")
    void testPrixProduit(){
        assertThrows(PrixNonDisponibleException.class,
                () -> prod2.getPrix(LocalDate.of(2022,1,15)));
    }

    @Test
    @DisplayName("test if date antérieur est renvoyé entre 2 dates pour lesquelles le prix a été défini")
    void testDateComprise(){
        assertEquals(prod1.getPrix(LocalDate.of(2022,7,7)), prixSolde);
    }

    @Test
    @DisplayName("test equals method if 2 objects with same value are the same")
    void testEquals1(){
        Produit prd1 = new Produit("Galaxy","Samsung", "12");
        Produit prd2 = new Produit("Galaxy","Samsung", "12");
        assertTrue(prd1.equals(prd2));
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same name")
    void testEquals2(){
        Produit prd1 = new Produit("Galaxy","Samsung", "12");
        Produit prd2 = new Produit("Flip","Samsung", "12");
        assertNotEquals(prd1, prd2);
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same marque")
    void testEquals3(){
        Produit prd1 = new Produit("Galaxy","Samsung", "12");
        Produit prd2 = new Produit("Galaxy","Shamshung", "12");
        assertNotEquals(prd1, prd2);
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same rayons")
    void testEquals4(){
        Produit prd1 = new Produit("Galaxy","Samsung", "12");
        Produit prd2 = new Produit("Galaxy","Samsung", "122");
        assertNotEquals(prd1, prd2);
    }

    @Test
    @DisplayName("test equals method if 2 objects with not the same rayons")
    void testHashcode(){
        Produit prd1 = new Produit("Galaxy","Samsung", "12");
        Produit prd2 = new Produit("Galaxy","Samsung", "12");
        assertEquals(prd1.hashCode(), prd2.hashCode());
    }
}
