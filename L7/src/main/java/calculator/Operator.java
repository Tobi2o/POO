package calculator;

import java.util.Locale;


/**
 * Classe abstraite pour les opérateurs de la calculatrice.
 */
public abstract class Operator {
    /**
     * Exécute l'opération sur l'état de la calculatrice.
     * @param state État actuel de la calculatrice.
     */
    abstract void execute(State state);
}

/**
 * Opérateur pour additionner les deux valeurs au sommet de la pile.
 */
class AddOperator extends Operator {
    @Override
    void execute(State state) {
        try {
            double b = state.popFromStack();
            double a = state.popFromStack();
            state.setCurrentValue(Double.toString(a + b));
            state.pushCurrentValueToStack();
        } catch (IllegalStateException e) {
            state.setError(true);
        }
    }
}

/**
 * Opérateur pour soustraire la valeur au sommet de la pile de celle juste en dessous.
 */
class SubtractOperator extends Operator {
    @Override
    void execute(State state) {
        try {
            double b = state.popFromStack();
            double a = state.popFromStack();
            state.setCurrentValue(Double.toString(a - b));
            state.pushCurrentValueToStack();
        } catch (IllegalStateException e) {
            state.setError(true);
        }
    }
}

/**
 * Opérateur pour multiplier les deux derniers nombres sur la pile et empile le résultat.
 */
class MultiplyOperator extends Operator {
    @Override
    void execute(State state) {
        try {
            double b = state.popFromStack();
            double a = state.popFromStack();
            state.setCurrentValue(Double.toString(a * b));
            state.pushCurrentValueToStack();
        } catch (IllegalStateException e) {
            state.setError(true);
        }
    }
}

/**
 * Opérateur pour diviser l'avant-dernier nombre par le dernier nombre sur la pile et empile le résultat.
 * Gère la division par zéro en définissant l'état d'erreur.
 */
class DivideOperator extends Operator {
    @Override
    void execute(State state) {
        try {
            double b = state.popFromStack();
            double a = state.popFromStack();
            if (b == 0) {
                throw new ArithmeticException("Division par zéro.");
            }
            state.setCurrentValue(Double.toString(a / b));
            state.pushCurrentValueToStack();
        } catch (IllegalStateException | ArithmeticException e) {
            state.setError(true);
        }
    }
}

/**
 * Opérateur pour calculer la racine carrée du dernier nombre sur la pile.
 * Gère les cas où le nombre est négatif en définissant l'état d'erreur.
 */
class SquareRootOperator extends Operator {
    @Override
    void execute(State state) {
        try {
            double a = state.popFromStack();
            if (a < 0) {
                throw new ArithmeticException("Racine carrée d'un nombre négatif.");
            }
            state.setCurrentValue(Double.toString(Math.sqrt(a)));
            state.pushCurrentValueToStack();
        } catch (IllegalStateException | ArithmeticException e) {
            state.setError(true);
        }
    }
}

/**
 * Opérateur pour calculer l'inverse (1/x) du dernier nombre sur la pile.
 * Gère les cas de division par zéro en définissant l'état d'erreur.
 */
class ReciprocalOperator extends Operator {
    @Override
    void execute(State state) {
        try {
            double a = state.popFromStack();
            if (a == 0) {
                throw new ArithmeticException("Division par zéro.");
            }
            state.setCurrentValue(Double.toString(1 / a));
            state.pushCurrentValueToStack();
        } catch (IllegalStateException | ArithmeticException e) {
            state.setError(true);
        }
    }
}

/**
 * Opérateur pour élever au carré la valeur au sommet de la pile.
 */
class SquareOperator extends Operator {
    @Override
    void execute(State state) {
        try {
            double a = state.popFromStack();
            state.setCurrentValue(Double.toString(a * a));
            state.pushCurrentValueToStack();
        } catch (IllegalStateException e) {
            state.setError(true);
        }
    }
}


/**
 * Opérateur pour rappeler la valeur en mémoire et la définir comme valeur courante.
 */


class MemoryRecallOperator extends Operator {
    @Override
    void execute(State state) {
        double memoryValue = state.getMemory();
        // verifie si la valeur en mémoire contient une valeur après la virgule
        if (memoryValue % 1 == 0) {
            // conversion int
            state.setCurrentValue(Integer.toString((int) memoryValue));
        } else {
            // conversion double
            state.setCurrentValue(Double.toString(memoryValue));
        }
    }
}

/**
 * Opérateur pour stocker la valeur courante dans la mémoire de la calculatrice.
 */
class MemoryStoreOperator extends Operator {
    @Override
    void execute(State state) {
        state.setMemory(Double.parseDouble(state.getCurrentValue()));
    }
}

/**
 * Opérateur pour supprimer le dernier caractère de la valeur courante.
 */
class BackspaceOperator extends Operator {
    @Override
    void execute(State state) {
        state.backspace();
    }
}

/**
 * Opérateur pour ajouter un point décimal à la valeur courante.
 */
class DecimalOperator extends Operator {
    @Override
    void execute(State state) {
        state.addDecimalPoint();
    }
}

/**
 * Opérateur pour effacer la valeur courante sans modifier la pile.
 */
class ClearEntryOperator extends Operator {
    @Override
    void execute(State state) {
        state.setCurrentValue("0");
        // Pas besoin de modifier la pile pour CE
    }
}

/**
 * Opérateur pour réinitialiser complètement l'état de la calculatrice, y compris la pile.
 */
class ClearOperator extends Operator {
    @Override
    void execute(State state) {
        state.clear(); // Cette méthode réinitialise déjà l'état de la calculatrice
    }
}

/**
 * Opérateur pour pousser la valeur courante sur la pile.
 */
class EnterOperator extends Operator {
    @Override
    void execute(State state) {
        state.pushCurrentValueToStack();
    }
}

/**
 * Opérateur pour changer le signe de la valeur courante.
 */
class SignChangeOperator extends Operator {
    @Override
    void execute(State state) {
        String currentValue = state.getCurrentValue();

        // Vérifier si la valeur courante est négative
        if (currentValue.startsWith("-")) {
            // Si elle est négative, enlever le signe "-" pour la rendre positive
            state.setCurrentValue(currentValue.substring(1));
        } else {
            // Sinon, ajouter le signe "-" pour la rendre négative
            state.setCurrentValue("-" + currentValue);
        }
    }
}

/**
 * Opérateur pour ajouter un '0' à la valeur courante si elle n'est pas nulle.
 */
class ZeroOperator extends Operator {
    @Override
    void execute(State state) {
        String currentValue = state.getCurrentValue();
        if (!currentValue.equals("0")) {
            // Permet d'ajouter un '0' si la valeur courante n'est pas exactement "0"
            // Cela inclut les cas où la valeur est "0." ou "123.0", etc.
            state.setCurrentValue(currentValue + "0");
        }
    }
}

/**
 * Opérateur pour ajouter un chiffre spécifique à la valeur courante.
 */
class DigitOperator extends Operator {
    private final int digit;

    DigitOperator(int digit) {
        this.digit = digit;
    }

    @Override
    void execute(State state) {
        if(state.getCurrentValue().equals("0")){
            state.setCurrentValue(Integer.toString(digit));
        }else{
            state.setCurrentValue(state.getCurrentValue() + digit);
        }

    }
}
