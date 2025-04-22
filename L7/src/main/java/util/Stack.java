package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implémentation générique d'une pile (stack) utilisant une liste chaînée.
 * Permet de stocker des éléments de type générique T et offre des opérations de pile standard.
 *
 * @param <T> Le type d'élément stocké dans la pile.
 */
public class Stack<T> implements Iterable<T> {
    private Node<T> top; // Référence au sommet de la pile
    private int size; // Nombre d'éléments dans la pile

    /**
     * Constructeur pour la pile.
     */
    public Stack() {
        top = null;
        size = 0;
    }

    /**
     * Classe interne Node pour représenter un nœud dans la liste chaînée.
     * Chaque nœud contient une donnée de type T et une référence au nœud suivant.
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * Ajoute un élément au sommet de la pile.
     *
     * @param item L'élément à ajouter à la pile.
     * @throws NullPointerException si l'élément à ajouter est null.
     */

    public void push(T item) {
        if (item == null) {
            throw new NullPointerException("Cannot push null item onto the stack");
        }
        top = new Node<>(item, top);
        size++;
    }

    /**
     * Retire et retourne l'élément au sommet de la pile.
     *
     * @return L'élément au sommet de la pile.
     * @throws NoSuchElementException si la pile est vide.
     */
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pile est vide.");
        }
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }

    /**
     * Retourne l'élément au sommet de la pile sans le retirer.
     *
     * @return L'élément au sommet de la pile.
     * @throws NoSuchElementException si la pile est vide.
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("La pile est vide.");
        }
        return top.data;
    }

    /**
     * Vérifie si la pile est vide.
     *
     * @return true si la pile est vide, false sinon.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Retourne le nombre d'éléments dans la pile.
     *
     * @return Le nombre d'éléments dans la pile.
     */
    public int size() {
        return size;
    }

    /**
     * Fournit une représentation textuelle de la pile.
     *
     * @return Une chaîne de caractères représentant les éléments de la pile.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T item : this) {
            sb.append(item).append(" ");
        }
        return sb.toString().trim();
    }


    /**
     * Convertit les éléments de la pile en un tableau d'Object.
     *
     * @return Un tableau contenant tous les éléments de la pile sous forme d'Object.
     */
    public Object[] toArray() {

        Object[] a = new Object[size];

        if (a.length < size) {
            a = (Object[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        for (Node<T> x = top; x != null; x = x.next) {
            a[i++] = x.data;
        }
        if (a.length > size) {
            a[size] = null; // Nullify end of array if original array was larger
        }
        return a;
    }

    /**
     * Crée un itérateur pour parcourir les éléments de la pile.
     *
     * @return Un itérateur pour la pile.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = top;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T item = current.data;
                current = current.next;
                return item;
            }
        };
    }
}
