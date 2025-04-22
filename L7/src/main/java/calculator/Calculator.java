package calculator;

import java.util.Scanner;
import java.util.HashMap;

/**
 * Représente la calculatrice, gérant l'état, les opérations et l'interface utilisateur.
 */

public class Calculator {
    private final State state;
    private Operator op;

    public Calculator() {
        this.state = new State();
    }

    // region Accesseurs

    public Operator getOperator() {
        return this.op;
    }
    public void setOperator(Operator op) {
        this.op = op;
    }
    public State getState() {
        return this.state;
    }

    // endregion Accesseurs

    /**
     * Point d'entrée principal du programme.
     * Initialise et démarre la boucle principale de la calculatrice.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(this);
        HashMap<String, Operator> operatorMap = initializeOperatorMap();

        System.out.println("Welcome to the calculator! Type 'help' for help.\n");
        mainLoop(scanner, inputHandler, operatorMap);
    }

    /**
     * Initialise la carte des opérateurs utilisables dans la calculatrice.
     *
     * @return Une carte des chaînes de caractères aux opérateurs correspondants.
     */
    private HashMap<String, Operator> initializeOperatorMap() {
        HashMap<String, Operator> map = new HashMap<>();
        map.put("+", new AddOperator());
        map.put("-",new SubtractOperator());
        map.put("*",new MultiplyOperator());
        map.put("/",new DivideOperator());
        map.put("sqrt",new SquareRootOperator());
        map.put("sqr",new SquareOperator());
        map.put("1/x",new ReciprocalOperator());
        map.put("MR", new MemoryRecallOperator());
        map.put("MS", new MemoryStoreOperator());
        map.put("C",new ClearOperator());
        return map;
    }

    /**
     * Boucle principale du programme, gérant les entrées utilisateur.
     *
     * @param scanner L'outil de lecture des entrées utilisateur.
     * @param inputHandler L'objet gérant le traitement des entrées utilisateur.
     * @param map La carte des opérateurs disponibles.
     */
    private void mainLoop(Scanner scanner, InputHandler inputHandler, HashMap<String, Operator> map) {
        while (true) {
            try {
                System.out.print("Enter input > ");
                String input = scanner.nextLine();
                this.op = map.get(input);
                inputHandler.handleExecute(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("\n[Current Stack]");
            System.out.println(this.state.getStackAsString());
        }
    }

    public static void main(String... args) {
        new Calculator().start();
    }
}