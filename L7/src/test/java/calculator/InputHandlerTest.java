package calculator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Classe de test pour InputHandler.
 * Teste la gestion des entrées utilisateur dans différentes conditions.
 */
public class InputHandlerTest {
    private Calculator calculator;
    private InputHandler inputHandler;

    /**
     * Configuration initiale avant chaque test.
     * Initialise un nouvel InputHandler et Calculator.
     */
    @Before
    public void setUp() {
        calculator = new Calculator();
        inputHandler = new InputHandler(calculator);
    }

    /**
     * Teste la gestion d'une entrée numérique.
     * Vérifie si les entrées numériques sont correctement traitées et empilées.
     */
    @Test
    public void testNumericInput() {
        inputHandler.handleExecute("5");
        assertFalse(calculator.getState().isError());
        assertEquals("5.0", calculator.getState().getStackAsString());
    }

    /**
     * Teste la réaction à une commande invalide.
     * S'attend à ce qu'une IllegalArgumentException soit lancée pour une commande inconnue.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCommand() {
        inputHandler.handleExecute("invalid");
    }


    /**
     * Teste la gestion d'une opération d'addition.
     * Vérifie si l'opérateur d'addition est correctement appliqué aux entrées numériques.
     */
    @Test
    public void testAddition() {
        inputHandler.handleExecute("5");
        inputHandler.handleExecute("3");
        calculator.setOperator(new AddOperator());
        inputHandler.handleExecute("+");
        assertFalse(calculator.getState().isError());
        assertEquals("8.0", calculator.getState().getStackAsString());
    }

}