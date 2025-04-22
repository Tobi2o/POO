# Rapport d'implémentation : Labo 5 Rayburn Nathan, Harun Ouweis

## Description
Le module `Matrix` permet de représenter et de manipuler des matrices avec des opérations de base telles que l'addition, la soustraction et la multiplication comme demandé dans la donnée.

## Détails d'implémentation

### Classe `Matrix`

#### Attributs
- `int rows`: Nombre de lignes de la matrice.
- `int cols`: Nombre de colonnes de la matrice.
- `int mod`: Module pour les opérations matricielles.
- `int[][] data`: Tableau 2D contenant les données de la matrice.

#### Méthodes

- `String toString()`
  - **Description**: Convertit la matrice en chaîne de caractères pour l'affichage.
  - **Paramètres**: Aucun.
  - **Retour**: `String` représentant la matrice.

- `Matrix(int rows, int cols, int mod, int[][] data)`
  - **Description**: Constructeur qui crée une matrice avec les dimensions et les données spécifiées.
  - **Paramètres**:
    - `int rows`: Nombre de lignes.
    - `int cols`: Nombre de colonnes.
    - `int mod`: Module pour les opérations matricielles.
    - `int[][] data`: Données initiales de la matrice.
  - **Exceptions**: `IllegalArgumentException` si les dimensions ou le module sont invalides ou si `data` ne correspond pas aux dimensions.

- `static Matrix random(int rows, int cols, int mod)`
  - **Description**: Génère une matrice avec des valeurs aléatoires.
  - **Paramètres**:
    - `int rows`: Nombre de lignes.
    - `int cols`: Nombre de colonnes.
    - `int mod`: Module pour les opérations matricielles.
  - **Retour**: Nouvelle instance de `Matrix` avec des valeurs aléatoires.

- `Matrix operate(Matrix other, BiFunction<Integer, Integer, Integer> op)`
  - **Description**: Effectue une opération élément par élément entre deux matrices.
  - **Paramètres**:
    - `Matrix other`: Autre matrice pour l'opération.
    - `BiFunction<Integer, Integer, Integer> op`: Fonction spécifiant l'opération.
  - **Retour**: `Matrix` résultante après l'opération.
  - **Exceptions**: `IllegalArgumentException` si les modulos des deux matrices sont différents.

- `Matrix add(Matrix other)`
  - **Description**: Ajoute une autre matrice à celle-ci.
  - **Paramètres**:
    - `Matrix other`: Autre matrice à ajouter.
  - **Retour**: `Matrix` résultante après l'addition.

- `Matrix sub(Matrix other)`
  - **Description**: Soustrait une autre matrice de celle-ci.
  - **Paramètres**:
    - `Matrix other`: Autre matrice à soustraire.
  - **Retour**: `Matrix` résultante après la soustraction.

- `Matrix product(Matrix other)`
  - **Description**: Multiplie la matrice actuelle par une autre. (Note: Cela est fait comme indiqué dans la donnée et non une multiplication matricielle standard.)
  - **Paramètres**:
    - `Matrix other`: Autre matrice à multiplier.
  - **Retour**: `Matrix` résultante après la multiplication.


### Classe `MatrixTest:`

- **Description**: Les tests unitaires pour la classe Matrix, assurant la fiabilité et la robustesse de la classe Matrix.

#### Méthodes de test:

- **testNominalCase()**: Teste les cas d'utilisation standard indiqué dans la donnée, assurant que les opérations de base fonctionnent correctement.

- **testChainOperations()**: S'assure que les opérations peuvent être enchaînées, permettant des combinaisons d'opérations.

- **testAdditionWithDifferentModulus(), testSubtractionWithDifferentModulus(), testMultiplicationWithDifferentModulus()**: Ces tests s'assurent que les opérations entre matrices ayant des modulos différents lèvent une exception, garantissant ainsi la cohérence des opérations.

- **testOperationsOnEmptyMatrices()**: Teste les opérations sur des matrices vides pour s'assurer qu'elles se comportent correctement dans ce cas particulier.

- **testMatrixConstructorWithInvalidParams()**: Garantit que la construction de matrices avec des paramètres non valides lève une exception, assurant la robustesse du constructeur de la classe.

## Points clés de l'implémentation

- *Bifunction*

La classe Matrix a été implémenté autour de l'interface BiFunction.

BiFunction est une interface générique fournie par Java qui prend deux arguments et renvoie une valeur. En utilisant cette interface, nous avons créé une méthode operate générique et puissante capable de prendre en charge toute opération binaire entre deux éléments de matrices, pour autant que ces opérations renvoient un résultat du même type que les entrées.

Cette conception élimine la nécessité de répéter le code pour des opérations matricielles courantes telles que l'addition, la soustraction et la multiplication. En centralisant le processus d'opération, nous améliorons non seulement la réutilisabilité mais aussi la cohérence du code, car toute la logique opérationnelle est gérée au même endroit.

- *Fonctions Lambda*

 Les fonctions lambda sont une fonctionnalité de Java qui permet d'exprimer des instances d'interfaces fonctionnelles (interfaces avec une seule méthode abstraite) de manière concise. Dans notre classe Matrix, elles sont utilisées pour fournir l'implémentation de l'interface BiFunction directement au point d'appel de la méthode operate.

Avec une seule ligne de code, nous définissons l'opération d'addition modulaire entre deux éléments de matrices. Cela rend le code extrêmement lisible.

- Robustesse assurée par des contrôles et des exceptions pour garantir des instances toujours valides de `Matrix`.
