package calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Classe de test pour les opérateurs de la calculatrice.
 * Ces tests vérifient le bon fonctionnement de chaque opérateur en interaction avec l'état de la calculatrice.
 */
public class OperatorTest {
    private State state;

    // Déclaration
    private AddOperator addOperator;
    private SubtractOperator subtractOperator;
    private MultiplyOperator multiplyOperator;
    private DivideOperator divideOperator;
    private SquareRootOperator squareRootOperator;
    private ReciprocalOperator reciprocalOperator;
    private SquareOperator squareOperator;
    private MemoryRecallOperator memoryRecallOperator;
    private MemoryStoreOperator memoryStoreOperator;
    private BackspaceOperator backspaceOperator;
    private DecimalOperator decimalOperator;
    private ClearEntryOperator clearEntryOperator;
    private ClearOperator clearOperator;
    private EnterOperator enterOperator;
    private SignChangeOperator signChangeOperator;
    private ZeroOperator zeroOperator;
    private DigitOperator digitOperator;


    /**
     * Configuration initiale avant chaque test.
     * Crée un nouvel état et initialise différents opérateurs pour les tests.
     */
    @BeforeEach
    public void setUp() {
        state = new State();
        addOperator = new AddOperator();
        subtractOperator = new SubtractOperator();
        multiplyOperator = new MultiplyOperator();
        divideOperator = new DivideOperator();
        squareRootOperator = new SquareRootOperator();
        reciprocalOperator = new ReciprocalOperator();
        squareOperator = new SquareOperator();
        memoryRecallOperator = new MemoryRecallOperator();
        memoryStoreOperator = new MemoryStoreOperator();
        backspaceOperator = new BackspaceOperator();
        decimalOperator = new DecimalOperator();
        clearEntryOperator = new ClearEntryOperator();
        clearOperator = new ClearOperator();
        enterOperator = new EnterOperator();
        signChangeOperator = new SignChangeOperator();
        zeroOperator = new ZeroOperator();
        digitOperator = new DigitOperator(1); // exemple avec le chiffre 1
    }

    /**
     * Vérifie que l'addition de deux nombres fonctionne correctement.
     */
    @Test
    public void testAdditionOperator() {
        state.setCurrentValue("5");
        state.pushCurrentValueToStack();
        state.setCurrentValue("3");
        state.pushCurrentValueToStack();
        addOperator.execute(state);
        assertEquals(8.0, state.popFromStack());
    }


    /**
     * Vérifie que la soustraction de deux nombres fonctionne correctement.
     */
    @Test
    public void testSubtractionOperator() {
        state.setCurrentValue("10");
        state.pushCurrentValueToStack();
        state.setCurrentValue("5");
        state.pushCurrentValueToStack();
        subtractOperator.execute(state);
        assertEquals(5.0, state.popFromStack());
    }

    /**
     * Vérifie que la multiplication de deux nombres fonctionne correctement.
     */
    @Test
    public void testMultiplicationOperator() {
        state.setCurrentValue("10");
        state.pushCurrentValueToStack();
        state.setCurrentValue("5");
        state.pushCurrentValueToStack();
        multiplyOperator.execute(state);
        assertEquals(50.0, state.popFromStack());
    }

    /**
     * Teste la division, y compris la division par zéro.
     * Vérifie que la division par zéro définit correctement l'état d'erreur.
     */
    @Test
    public void testDivisionOperator() {
        state.setCurrentValue("10");
        state.pushCurrentValueToStack();
        state.setCurrentValue("5");
        state.pushCurrentValueToStack();
        divideOperator.execute(state);
        assertEquals(2.0, state.popFromStack());
    }
    @Test
    public void testDivisionByZero() {
        state.setCurrentValue("5");
        state.pushCurrentValueToStack();
        state.setCurrentValue("0");
        state.pushCurrentValueToStack();
        divideOperator.execute(state);
        assertTrue(state.isError());
    }

    /**
     * Teste le calcul de la racine carrée, y compris la racine carrée de 0 et d'un nombre négatif.
     * Vérifie que la racine carrée d'un nombre négatif définit l'état d'erreur.
     */
    @Test
    public void testSquareRootOperator() {
        state.setCurrentValue("16");
        state.pushCurrentValueToStack();
        squareRootOperator.execute(state);
        assertEquals(4.0, state.popFromStack());
    }
    @Test
    public void testSquareRootOfZero() {
        state.setCurrentValue("0");
        state.pushCurrentValueToStack();
        squareRootOperator.execute(state);
        assertEquals(0.0, state.popFromStack());
        assertFalse(state.isError());
    }
    @Test
    public void testSquareRootOfNegativeNumber() {
        state.setCurrentValue("-1");
        state.pushCurrentValueToStack();
        squareRootOperator.execute(state);
        assertTrue(state.isError());
    }

    /**
     * Teste le calcul de l'inverse d'un nombre, y compris l'inverse de zéro.
     * Vérifie que l'inverse de zéro définit l'état d'erreur.
     */
    @Test
    public void testReciprocalOperator() {
        state.setCurrentValue("4");
        state.pushCurrentValueToStack();
        reciprocalOperator.execute(state);
        assertEquals(0.25, state.popFromStack());
    }
    @Test
    public void testReciprocalOfZero() {
        state.setCurrentValue("0");
        state.pushCurrentValueToStack();
        reciprocalOperator.execute(state);
        assertTrue(state.isError());
    }


    /**
     * Teste l'opération d'élévation au carré sur différents cas.
     */
    @Test
    public void testSquareOperator() {
        state.setCurrentValue("4");
        state.pushCurrentValueToStack();
        squareOperator.execute(state);
        assertEquals(16.0, state.popFromStack());
    }

    @Test
    public void TestSquareofNegativeNumber() {
        state.setCurrentValue("-1");
        state.pushCurrentValueToStack();
        squareOperator.execute(state);
        assertEquals(1, state.popFromStack());
    }

    /**
     * Teste l'addition avec l'infini.
     * Ce test est pertinent car il vérifie le comportement de la calculatrice face à des valeurs extrêmes.
     * L'addition d'un nombre fini à l'infini doit théoriquement donner l'infini.
     */
    @Test
    public void testAdditionWithInfinity() {
        state.setCurrentValue(Double.toString(Double.POSITIVE_INFINITY));
        state.pushCurrentValueToStack();
        state.setCurrentValue("1");
        state.pushCurrentValueToStack();
        addOperator.execute(state);
        assertEquals(Double.POSITIVE_INFINITY, state.popFromStack());
    }

    /**
     * Teste la division par l'infini.
     * Ce test est important car il vérifie que la division d'un nombre fini par l'infini donne zéro,
     * conformément aux règles mathématiques standard.
     */
    @Test
    public void testDivisionByInfinity() {
        DivideOperator divideOperator = new DivideOperator();
        state.setCurrentValue("1");
        state.pushCurrentValueToStack();
        state.setCurrentValue(Double.toString(Double.POSITIVE_INFINITY));
        state.pushCurrentValueToStack();
        divideOperator.execute(state);
        assertEquals(0.0, state.popFromStack());
    }

    /**
     * Teste le calcul de la racine carrée de l'infini.
     * Ce test est utile pour s'assurer que la calculatrice gère correctement des cas extrêmes,
     * où la racine carrée de l'infini doit être l'infini.
     */
    @Test
    public void testSquareRootOfInfinity() {
        SquareRootOperator squareRootOperator = new SquareRootOperator();
        state.setCurrentValue(Double.toString(Double.POSITIVE_INFINITY));
        state.pushCurrentValueToStack();
        squareRootOperator.execute(state);
        assertEquals(Double.POSITIVE_INFINITY, state.popFromStack());
    }

    /**
     * Teste l'opération d'élévation au carré de l'infini.
     * Ce test vérifie que la calculatrice traite correctement l'opération carré sur une valeur infinie,
     * qui devrait théoriquement rester infinie.
     */
    @Test
    public void testSquareOfInfinity() {
        SquareOperator squareOperator = new SquareOperator();
        state.setCurrentValue(Double.toString(Double.POSITIVE_INFINITY));
        state.pushCurrentValueToStack();
        squareOperator.execute(state);
        assertEquals(Double.POSITIVE_INFINITY, state.popFromStack());
    }

    /**
     * Teste l'addition avec NaN (Not a Number).
     * Ce test est crucial pour s'assurer que la calculatrice gère correctement les situations
     * où des opérations mathématiques indéfinies sont effectuées, ce qui doit produire un NaN.
     */
    @Test
    public void testAdditionWithNaN() {
        state.setCurrentValue(Double.toString(Double.NaN));
        state.pushCurrentValueToStack();
        state.setCurrentValue("1");
        state.pushCurrentValueToStack();
        addOperator.execute(state);
        assertTrue(Double.isNaN(state.popFromStack()));
    }

    /**
     * Teste le fonctionnement de l'opérateur de stockage en mémoire.
     * Vérifie que la valeur courante est correctement stockée dans la mémoire de l'état.
     */
    @Test
    public void testMemoryStoreOperator() {
        state.setCurrentValue("42");
        memoryStoreOperator.execute(state);
        assertEquals(42.0, state.getMemory());
    }

    /**
     * Teste le fonctionnement de l'opérateur de rappel de mémoire.
     * Vérifie que la valeur stockée en mémoire est correctement rappelée et définie comme valeur courante.
     */
    @Test
    public void testMemoryRecallOperator() {
        state.setMemory(42.0);
        memoryRecallOperator.execute(state);
        assertEquals("42", state.getCurrentValue());
    }




    /**
     * Teste le rappel de la valeur en mémoire.
     * Vérifie que la valeur en mémoire est correctement rappelée.
     * Pertinent d'effectuer les tests un à un pour pouvoir trouver la source d'une erreur
     */
    @Test
    public void testMemoryStoreAndRecall() {
        state.setCurrentValue("5");
        memoryStoreOperator.execute(state);
        memoryRecallOperator.execute(state);
        assertEquals(5, Double.parseDouble(state.getCurrentValue()));
    }

    /**
     * Teste la fonctionnalité de suppression du dernier caractère.
     * Vérifie que l'opérateur de backspace fonctionne comme prévu dans tous les cas particuliers.
     */
    @Test
    public void testBackspaceOperator() {
        // Cas où la valeur actuelle a plusieurs chiffres
        state.setCurrentValue("123");
        backspaceOperator.execute(state);
        assertEquals("12", state.getCurrentValue());

        // Cas où la valeur actuelle se termine par ".0"
        state.setCurrentValue("123.0");
        backspaceOperator.execute(state);
        assertEquals("123", state.getCurrentValue());

        // Cas où la valeur actuelle est un seul chiffre
        state.setCurrentValue("1");
        backspaceOperator.execute(state);
        assertEquals("0", state.getCurrentValue());

        // Cas où la valeur actuelle est "-1"
        state.setCurrentValue("-1");
        backspaceOperator.execute(state);
        assertEquals("0", state.getCurrentValue());
    }



    /**
     * Ce test vérifie si l'opérateur décimal ajoute correctement un point décimal à la fin de la valeur courante.
     * Il s'assure également que l'opérateur ne rajoute pas de point supplémentaire si la valeur courante contient déjà un point décimal.
     */
    @Test
    public void testDecimalOperator() {
        state.setCurrentValue("123");
        decimalOperator.execute(state);
        assertEquals("123.", state.getCurrentValue());
        decimalOperator.execute(state);
        assertEquals("123.", state.getCurrentValue());
    }

    /**
     * Teste la fonctionnalité de l'opérateur d'effacement de l'entrée courante.
     * Vérifie que la valeur courante est réinitialisée à "0".
     */
    @Test
    public void testClearEntryOperator() {
        state.setCurrentValue("123");
        clearEntryOperator.execute(state);
        assertEquals("0", state.getCurrentValue());
    }

    /**
     * Teste la fonctionnalité de l'opérateur de réinitialisation complète.
     * Vérifie que l'état de la calculatrice, y compris la pile, est réinitialisé.
     */
    @Test
    public void testClearOperator() {
        state.setCurrentValue("123");
        state.pushCurrentValueToStack();
        clearOperator.execute(state);
        assertEquals("0", state.getCurrentValue());
        assertEquals(0, state.getStackAsArray().length);
    }

    /**
     * Teste la fonctionnalité de l'opérateur Enter.
     * Vérifie que la valeur courante est correctement empilée sur la pile.
     */
    @Test
    public void testEnterOperator() {
        state.setCurrentValue("123");
        enterOperator.execute(state);
        assertEquals(123, state.popFromStack());
    }
    /**
     * Teste la fonctionnalité de l'opérateur de changement de signe.
     * Vérifie que le signe de la valeur courante est correctement inversé.
     */
    @Test
    public void testSignChangeOperator() {
        state.setCurrentValue("123");
        signChangeOperator.execute(state);
        assertEquals("-123", state.getCurrentValue());
        signChangeOperator.execute(state);
        assertEquals("123", state.getCurrentValue());
    }

    /**
     * Teste la fonctionnalité de l'opérateur Zero.
     * Vérifie que le chiffre '0' est correctement ajouté à la fin de la valeur courante si nécessaire.
     */
    @Test
    public void testZeroOperator() {
        state.setCurrentValue("1");
        zeroOperator.execute(state);
        assertEquals("10", state.getCurrentValue());
    }

    /**
     * Teste la fonctionnalité de l'opérateur de chiffre.
     * Vérifie que le chiffre spécifique est correctement ajouté à la valeur courante.
     */
    @Test
    public void testDigitOperator() {
        digitOperator.execute(state); // Exécute avec le chiffre 1
        assertEquals("1", state.getCurrentValue());
    }

}

