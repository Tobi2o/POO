<h1> Contraintes d’intégrité : </h1>

Pour une Catégorie donnée lors d'un ÉvénementSportif, chaque Gymnaste peut avoir un seul Score. </br>
Le nombre maximum de Gymnastes inscrits dans une Catégorie est de 16. </br>
Chaque FédérationNationale doit mentionner les Juges et les Gymnastes lors de l'inscription à un ÉvénementSportif
La taille de la liste retournée par obtenirPodium est toujours 3 (représentant les trois premières places). </br> 
Un Gymnaste ne peut être inscrit à une Catégorie que si le genre de la Discipline correspond à son genre. </br> 
Le nombre de Gymnastes inscrits à une Catégorie ne peut pas dépasser nbMaxGymnastes.
<h1> Decisions personnelles </h1>

<h2> Personne </h2>
Nous avons décidé de mettre les classes Gymnaste et Juge en tant qu'enfant de Personne, car ils ont plusieurs attributs en commun et donc fait du sens de faire une héritage.

<h2> Participation/Inscription </h2>

Nous avons vaillament choisi de faire une classe afin de pouvoir relier Gymnaste et son score. L'inscription, elle nous permet d'inscrire un gymnaste sans pourtant avoir l'obligation de participer (maladie,blessure).