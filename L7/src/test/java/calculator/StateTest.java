package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour la classe State de la calculatrice.
 * Ces tests vérifient le bon fonctionnement de la gestion de l'état interne de la calculatrice.
 */
public class StateTest {
    private State state;
    private Operator memoryRecallOperator;

    /**
     * Configuration initiale avant chaque test.
     * Crée un nouvel état et un opérateur de rappel de mémoire pour les tests.
     */
    @BeforeEach
    public void setUp() {
        state = new State();
        memoryRecallOperator = new MemoryRecallOperator();
    }

    /**
     * Teste la méthode clear pour vérifier si elle réinitialise correctement l'état.
     * Après l'appel de clear, la valeur courante devrait être "0", la pile vide, et aucun état d'erreur.
     */
    @Test
    public void testClear() {
        state.pushCurrentValueToStack();
        state.setCurrentValue("10");
        state.clear();
        assertEquals("0", state.getCurrentValue());
        assertArrayEquals(new Double[]{}, state.getStackAsArray());
        assertFalse(state.isError());
    }

    /**
     * Teste l'ajout et le retrait de la valeur courante sur la pile.
     * Vérifie si la pile contient la valeur courante après l'avoir ajoutée et si elle peut être retirée correctement.
     */
    @Test
    public void testPushPopCurrentValue() {
        state.setCurrentValue("5.0");
        state.pushCurrentValueToStack();
        assertEquals(5.0, state.popFromStack());
    }

    /**
     * Teste la gestion des erreurs dans l'état de la calculatrice.
     * Vérifie si l'état d'erreur est correctement défini et réinitialisé.
     */
    @Test
    public void testErrorHandling() {
        state.setError(true);
        assertTrue(state.isError());
        state.clearError();
        assertFalse(state.isError());
    }

    /**
     * Teste le comportement lors de la tentative de retrait d'un élément d'une pile vide.
     * S'attend à ce qu'une exception soit lancée et que l'état d'erreur soit défini.
     */
    @Test
    public void testPopFromEmptyStack() {
        assertThrows(IllegalStateException.class, () -> state.popFromStack());
        assertTrue(state.isError());
    }

    /**
     * Teste l'ajout et le retrait de plusieurs valeurs sur la pile.
     * Vérifie si les valeurs sont ajoutées et retirées dans le bon ordre (LIFO).
     */
    @Test
    public void testPushAndPopMultipleValues() {
        state.setCurrentValue("10");
        state.pushCurrentValueToStack();
        state.setCurrentValue("20");
        state.pushCurrentValueToStack();
        assertEquals(20, state.popFromStack());
        assertEquals(10, state.popFromStack());
    }

    /**
     * Teste l'ordre des éléments dans la pile après plusieurs ajouts.
     * Vérifie si les éléments sont empilés et renvoyés par toArray dans le bon ordre.
     */
    @Test
    public void testStackOrderAfterMultiplePushes() {
        state.setCurrentValue("1");
        state.pushCurrentValueToStack();
        state.setCurrentValue("2");
        state.pushCurrentValueToStack();
        state.setCurrentValue("3");
        state.pushCurrentValueToStack();
        assertArrayEquals(new Double[]{3.0, 2.0, 1.0}, state.getStackAsArray());
    }

    /**
     * Teste la fonctionnalité de stockage et de rappel de la mémoire.
     * Vérifie si la valeur en mémoire est correctement définie et rappelée.
     */
    @Test
    public void testMemoryStoreAndRecall() {
        state.setMemory(42.0);
        assertEquals(42.0, state.getMemory());

        state.setCurrentValue("5.0");
        state.setMemory(Double.parseDouble(state.getCurrentValue()));
        assertEquals(5.0, state.getMemory());

        state.setMemory(10.0);
        state.setCurrentValue("0.0");
        memoryRecallOperator.execute(state);
        assertEquals("10", state.getCurrentValue());
    }

    /**
     * Teste la fonctionnalité de suppression du dernier caractère (backspace).
     * Vérifie si backspace supprime correctement les caractères et gère les cas spéciaux.
     */
    @Test
    public void testBackspace() {
        state.setCurrentValue("123.45");
        state.backspace(); // Devrait retirer le dernier chiffre
        assertEquals("123.4", state.getCurrentValue());

        state.backspace(); // Devrait conserver le point comme dans une calculatrice normale
        assertEquals("123.", state.getCurrentValue());

        state.backspace(); // Retire uniquement le point
        assertEquals("123", state.getCurrentValue());

        state.backspace(); // Retire le chiffre suivant comme en continuant
        assertEquals("12", state.getCurrentValue());

    }

    /**
     * Teste l'ajout d'un point décimal à la valeur courante.
     * Vérifie si addDecimalPoint ajoute un point décimal uniquement si nécessaire.
     */
    @Test
    public void testAddDecimalPoint() {
        state.setCurrentValue("123");
        state.addDecimalPoint();
        assertEquals("123.", state.getCurrentValue());

        state.setCurrentValue("123.45");
        state.addDecimalPoint(); // Ne devrait rien changer
        assertEquals("123.45", state.getCurrentValue());
    }

    /**
     * Teste si la méthode clear réinitialise tout l'état, y compris la mémoire.
     * Vérifie si après clear, la valeur courante, la pile, l'état d'erreur, et la mémoire sont réinitialisés.
     */
    @Test
    public void testClearResetsEverything() {
        state.setCurrentValue("123");
        state.pushCurrentValueToStack();
        state.setMemory(45);
        state.clear();
        assertEquals("0", state.getCurrentValue());
        assertArrayEquals(new Double[]{}, state.getStackAsArray());
        assertFalse(state.isError());
        assertEquals(0, state.getMemory()); // Si 'clear' doit également réinitialiser la mémoire
    }
}
