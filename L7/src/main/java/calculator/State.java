package calculator;

import util.Stack;


/**
 * La classe State représente l'état interne de la calculatrice.
 */
public class State {
    private String currentValue; // La valeur actuellement affichée sur la calculatrice
    private Stack<Double> stack; // La pile utilisée pour stocker les valeurs
    private boolean error; // Indicateur d'erreur
    private double memory; // Ajout d'un champ pour la mémoire

    /**
     * Constructeur pour l'état de la calculatrice.
     */
    public State() {
        this.currentValue = "0";
        this.stack = new Stack<>();
        this.error = false;
    }

    /**
     * Obtient la valeur courante.
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * Définit la valeur courante.
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * Vérifie si une erreur est survenue.
     */
    public boolean isError() {
        return error;
    }

    /**
     * Définit l'indicateur d'erreur.
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Réinitialise l'état d'erreur.
     */
    public void clearError() {
        this.error = false;
    }

    /**
     * Empile la valeur courante sur la pile.
     */
    public void pushCurrentValueToStack() {
        stack.push(Double.parseDouble(currentValue));
        this.currentValue = "0"; // Réinitialise la valeur courante après l'avoir empilée
    }

    /**
     * Obtient la valeur au sommet de la pile.
     * @throws IllegalStateException si la pile est vide.
     */
    public double peekFromStack() {
        if (stack.isEmpty()) {
            setError(true); // Indique une erreur si la pile est vide
            throw new IllegalStateException("La pile est vide, impossible de consulter le sommet.");
        }
        return stack.peek();
    }

    /**
     * Retire et retourne le dernier élément de la pile.
     * @throws IllegalStateException si la pile est vide.
     */
    public double popFromStack() {
        if (stack.isEmpty()) {
            setError(true); // Indique une erreur si la pile est vide
            throw new IllegalStateException("La pile est vide, impossible de désempiler.");
        }
        return stack.pop();
    }

    /**
     * Obtient la pile sous forme de chaîne de caractères.
     */
    public String getStackAsString() {
        return stack.toString();
    }

    /**
     * Obtient la pile sous forme de tableau.
     */
    public Object[] getStackAsArray() {
        return stack.toArray();
    }

    /**
     * Réinitialise l'état de la calculatrice, en vidant la pile et en réinitialisant la valeur courante.
     */
    public void clear() {
        stack = new Stack<>();
        currentValue = "0";
        memory = 0; // Réinitialisez la mémoire ici.
        clearError();
    }

    /**
     * Définit la valeur stockée en mémoire.
     *
     * @param value La valeur à stocker en mémoire.
     */
    public void setMemory(double value) {
        this.memory = value;
    }

    /**
     * Obtient la valeur stockée en mémoire.
     *
     * @return La valeur en mémoire.
     */
    public double getMemory() {
        return this.memory;
    }

    /**
     * Effectue une opération de suppression du dernier caractère de la valeur courante.
     * Gère les cas spéciaux comme la suppression d'un point décimal ou d'un signe négatif.
     */
    public void backspace() {
        // Cas spécial pour les nombres qui se terminent par .0
        if (currentValue.endsWith(".0")) {
            currentValue = currentValue.substring(0, currentValue.length() - 2);
        }
        // Cas où la longueur est de 1 mais pas un signe négatif
        else if (currentValue.length() == 1 && !currentValue.equals("-")) {
            currentValue = "0";
        }
        // Cas où la longueur est supérieure à 1 ou la valeur est juste un signe négatif
        else if (currentValue.length() > 1 || currentValue.equals("-")) {
            currentValue = currentValue.substring(0, currentValue.length() - 1);
        }

        // Si après suppression, la valeur est vide ou juste un signe négatif, réinitialiser à "0"
        if (currentValue.isEmpty() || currentValue.equals("-")) {
            currentValue = "0";
        }
    }



    // Ajouter une méthode pour gérer le point décimal
    public void addDecimalPoint() {
        String currentValueStr = String.valueOf(currentValue);
        if (!currentValueStr.contains(".")) {
            currentValueStr += ".";
            currentValue = currentValueStr;
        }
    }
}
