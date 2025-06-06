package calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

//import java.awt.event.*;

public class JCalculator extends JFrame {
    private final State state;
    // Tableau representant une pile vide

    // Initialiser les opérateurs
    Operator addOperator = new AddOperator();
    Operator subtractOperator = new SubtractOperator();
    Operator divideOperator = new DivideOperator();
    Operator multiplyOperator = new MultiplyOperator();
    Operator reciprocalOperator = new ReciprocalOperator();
    Operator squareOperator = new SquareOperator();
    Operator squareRootOperator = new SquareRootOperator();
    Operator backSpaceOperator = new BackspaceOperator();
    Operator memoryRecallOperator = new MemoryRecallOperator();
    Operator memoryStoreOperator = new MemoryStoreOperator();
    Operator clearEntryOperator = new ClearEntryOperator();
    Operator clearOperator = new ClearOperator();
    Operator signChangeOperator = new SignChangeOperator();
    Operator decimalOperator = new DecimalOperator();
    Operator enterOperator = new EnterOperator();

    Operator zeroOperator = new ZeroOperator();



    private static final String[] empty = {"< empty stack >"};

    // Zone de texte contenant la valeur introduite ou resultat courant
    private final JTextField jNumber = new JTextField("0");

    // Composant liste representant le contenu de la pile
    private final JList<String> jStack = new JList<>(empty);

    // Contraintes pour le placement des composants graphiques
    private final GridBagConstraints constraints = new GridBagConstraints();


    // Mise a jour de l'interface apres une operation (jList et jStack)
    private void update() {
        // Modifier une zone de texte, JTextField.setText(string nom)
        // Modifier un composant liste, JList.setListData(Object[] tableau)
        // Si une erreur est survenue, affichez "Error"
        if (state.isError()) {
            jNumber.setText("Error");
            state.clearError();
        } else {
            jNumber.setText(String.valueOf(state.getCurrentValue()));
        }

        // Convertir le contenu de la pile en un tableau de chaînes
        Object[] stackContents = state.getStackAsArray();
        String[] stackArray = new String[stackContents.length];
        for (int i = 0; i < stackContents.length; i++) {
            stackArray[i] = String.valueOf(stackContents[i]);
        }
        // Mettre à jour l'affichage de la pile
        jStack.setListData(stackArray.length > 0 ? stackArray : empty);
    }



    // Ajout d'un bouton dans l'interface et de l'operation associee,
    // instance de la classe Operation, possedeant une methode execute()
    private void addOperatorButton(String name, int x, int y, Color color,
                                   final Operator operator) {
        JButton b = new JButton(name);
        b.setForeground(color);
        constraints.gridx = x;
        constraints.gridy = y;
        getContentPane().add(b, constraints);
        b.addActionListener(e -> {
            operator.execute(state);
            update();
        });
    }

    public JCalculator() {
        super("JCalculator");
        state = new State();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());

        // Contraintes des composants graphiques
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Nombre courant
        jNumber.setEditable(false);
        jNumber.setBackground(Color.WHITE);
        jNumber.setHorizontalAlignment(JTextField.RIGHT);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        getContentPane().add(jNumber, constraints);
        constraints.gridwidth = 1; // reset width

        // Rappel de la valeur en memoire
        addOperatorButton("MR", 0, 1, Color.RED, memoryRecallOperator);

        // Stockage d'une valeur en memoire
        addOperatorButton("MS", 1, 1, Color.RED, memoryStoreOperator);

        // Backspace
        addOperatorButton("<=", 2, 1, Color.RED, backSpaceOperator);

        // Mise a zero de la valeur courante + suppression des erreurs
        addOperatorButton("CE", 3, 1, Color.RED, clearEntryOperator);

        // Comme CE + vide la pile
        addOperatorButton("C", 4, 1, Color.RED, clearOperator);

        // Boutons 1-9
        for (int i = 1; i < 10; i++) {
            Operator digitOperator = new DigitOperator(i);
            addOperatorButton(String.valueOf(i), (i - 1) % 3, 4 - (i - 1) / 3,
                    Color.BLUE, digitOperator);
        }
        // Bouton 0
        addOperatorButton("0", 0, 5, Color.BLUE, zeroOperator);

        // Changement de signe de la valeur courante
        addOperatorButton("+/-", 1, 5, Color.BLUE, signChangeOperator);

        // Operateur point (chiffres apres la virgule ensuite)
        addOperatorButton(".", 2, 5, Color.BLUE, decimalOperator);

        // Operateurs arithmetiques a deux operandes: /, *, -, +
        addOperatorButton("/", 3, 2, Color.RED, divideOperator);
        addOperatorButton("*", 3, 3, Color.RED,multiplyOperator );
        addOperatorButton("-", 3, 4, Color.RED, subtractOperator);
        addOperatorButton("+", 3, 5, Color.RED, addOperator);

        // Operateurs arithmetiques a un operande: 1/x, x^2, Sqrt
        addOperatorButton("1/x", 4, 2, Color.RED, reciprocalOperator);
        addOperatorButton("x^2", 4, 3, Color.RED, squareOperator);
        addOperatorButton("Sqrt", 4, 4, Color.RED, squareRootOperator);

        // Entree: met la valeur courante sur le sommet de la pile
        addOperatorButton("Ent", 4, 5, Color.RED, enterOperator);

        // Affichage de la pile
        JLabel jLabel = new JLabel("Stack");
        jLabel.setFont(new Font("Dialog", 0, 12));
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 5;
        constraints.gridy = 0;
        getContentPane().add(jLabel, constraints);

        jStack.setFont(new Font("Dialog", 0, 12));
        jStack.setVisibleRowCount(8);
        JScrollPane scrollPane = new JScrollPane(jStack);
        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.gridheight = 5;
        getContentPane().add(scrollPane, constraints);
        constraints.gridheight = 1; // reset height

        setResizable(false);
        pack();
        setVisible(true);
    }
}
