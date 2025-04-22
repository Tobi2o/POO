package calculator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de test pour la classe Calculator.
 * Teste les fonctionnalités et les opérations de base de la calculatrice.
 */
public class CalculatorTest {
    private Calculator calculator;

    /**
     * Configuration initiale avant chaque test.
     * Initialise une nouvelle instance de Calculator.
     */
    @Before
    public void setUp() {
        calculator = new Calculator();
    }

    /**
     * Teste l'état initial de la calculatrice.
     * Vérifie que l'état initial est correctement configuré.
     */
    @Test
    public void testInitialState() {
        assertNotNull(calculator.getState());
        assertEquals("0", calculator.getState().getCurrentValue());
    }

    /**
     * Teste une opération simple d'addition.
     * Vérifie que la calculatrice effectue correctement l'addition de deux nombres.
     */
    @Test
    public void testSimpleAddition() {
        calculator.getState().setCurrentValue("5");
        calculator.getState().pushCurrentValueToStack();
        calculator.getState().setCurrentValue("3");
        calculator.getState().pushCurrentValueToStack();
        calculator.setOperator(new AddOperator());
        calculator.getOperator().execute(calculator.getState());
        assertEquals("8.0", calculator.getState().getStackAsString());
    }
}

