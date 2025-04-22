package util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Classe de test pour la classe Stack.
 * Elle contient divers tests unitaires pour vérifier le bon fonctionnement de la pile.
 */
public class StackTest {
    private Stack<Integer> stack;

    /**
     * Initialise une nouvelle pile avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        stack = new Stack<>();
    }

    /**
     * Teste l'ajout et la suppression d'éléments de la pile.
     * Vérifie si les éléments sont correctement ajoutés et retirés dans l'ordre LIFO.
     */
    @Test
    public void testPushAndPop() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    /**
     * Teste la consultation du sommet de la pile.
     * Vérifie que peek() retourne le bon élément et que la pile reste inchangée après peek().
     */
    @Test
    public void testPeek() {
        stack.push(1);
        assertEquals(1, stack.peek());
        assertEquals(1, stack.pop());
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }

    /**
     * Teste si la méthode isEmpty() identifie correctement une pile vide.
     */
    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    /**
     * Teste le suivi correct de la taille de la pile.
     */
    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
    }

    /**
     * Teste le comportement de la méthode pop() sur une pile vide.
     * Vérifie si pop() lance une exception lorsque la pile est vide.
     */
    @Test
    public void testPopOnEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    /**
     * Teste le fonctionnement de l'itérateur de la pile.
     * Vérifie si l'itérateur parcourt tous les éléments dans l'ordre correct.
     */
    @Test
    public void testIterator() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        int sum = 0;
        for (int n : stack) {
            sum += n;
        }
        assertEquals(6, sum);
    }

    /**
     * Teste l'ajout d'un élément null à la pile.
     * Vérifie si la méthode push() lance une NullPointerException lors de l'ajout d'un élément null.
     */
    @Test
    public void testPushNullItem() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(NullPointerException.class, () -> stack.push(null));
    }

    /**
     * Teste le comportement de la pile avec un grand nombre d'éléments.
     * Vérifie si la pile peut gérer correctement un grand nombre d'ajouts et de suppressions.
     */
    @Test
    public void testLargeNumberOfItems() {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < 10000; i++) {
            stack.push(i);
        }
        for (int i = 9999; i >= 0; i--) {
            assertEquals(Integer.valueOf(i), stack.pop());
        }
    }
    /**
     * Teste le comportement de l'itérateur sur une pile vide.
     * Vérifie si l'itérateur lance une NoSuchElementException lorsqu'il n'y a plus d'éléments à retourner.
     */
    @Test
    public void testIteratorNextOnEmptyStack() {
        Iterator<Integer> iterator = stack.iterator();
        assertThrows(NoSuchElementException.class, iterator::next);
    }
    /**
     * @brief Verifie la stack
     */
    @Test
    public void testPrintStack(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals("3 2 1", stack.toString());
    }

    /**
     * @brief Verifie les valeurs de la stack dans la creation du tableau
     */
    @Test
    public void testArrayObject(){
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.toArray()[0]);
        assertEquals(2, stack.toArray()[1]);
        assertEquals(1, stack.toArray()[2]);
    }


}
