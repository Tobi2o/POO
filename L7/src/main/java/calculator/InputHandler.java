package calculator;


/**
 * Gère l'entrée de l'utilisateur pour la calculatrice.
 * Cette classe interprète les commandes entrées par l'utilisateur et agit en conséquence.
 */
public class InputHandler {
    private final Calculator calculator;

    /**
     * Constructeur pour l'InputHandler.
     *
     * @param calculator L'instance de Calculator avec laquelle cet InputHandler interagit.
     */
    public InputHandler(Calculator calculator) {
        this.calculator = calculator;
    }

    /**
     * Traite une entrée utilisateur et exécute l'action appropriée.
     *
     * @param input La commande ou le nombre entré par l'utilisateur.
     * @throws IllegalArgumentException Si une commande inconnue est entrée.
     * @throws IllegalStateException Si une erreur est détectée dans l'état de la calculatrice.
     */
    public void handleExecute(String input) throws IllegalArgumentException, IllegalStateException {
        switch (input) {
            case "help":
                displayHelp();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                processInput(input);
                break;
        }
        if (calculator.getState().isError()) {
            throw new IllegalStateException("Error in calculator state.");
        }
    }

    /**
     * Affiche les commandes disponibles pour l'utilisateur.
     */
    private void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("exit: exit the program");
        System.out.println("+, -, *, /: set the operator");
        System.out.println("sqrt, sqr, 1/x: apply the operator to the current value");
        System.out.println("MS, MR: store the value and recall it");
        System.out.println("C: clear the current value and the stack");
        System.out.println("help: display this help message");
    }

    /**
     * Traite l'entrée, soit en l'ajoutant à la pile si c'est un nombre, soit en exécutant l'opérateur correspondant.
     *
     * @param input La commande ou le nombre à traiter.
     * @throws IllegalArgumentException Si la commande est inconnue.
     */
    private void processInput(String input) throws IllegalArgumentException {
        if (isNumeric(input)) {
            calculator.getState().clearError();
            calculator.getState().setCurrentValue(input);
            calculator.getState().pushCurrentValueToStack();
        } else if (calculator.getOperator() != null) {
            if(input.equals("MS")) {
                calculator.getState().setCurrentValue(Double.toString(calculator.getState().peekFromStack()));
            } else if(input.equals("MR")) {
                calculator.getOperator().execute(calculator.getState());
                calculator.setOperator(null);
                calculator.getState().pushCurrentValueToStack();
                return;
            }
            calculator.getOperator().execute(calculator.getState());
            calculator.setOperator(null);
        } else {
            throw new IllegalArgumentException("Unknown command: " + input);
        }
    }

    /**
     * Vérifie si la chaîne de caractères donnée est numérique.
     *
     * @param str La chaîne de caractères à vérifier.
     * @return true si la chaîne est numérique, false sinon.
     */
    private boolean isNumeric(String str) {
        return str.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}