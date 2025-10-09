---
title: Analyse des besoins - Cas d'utilisation
---

# Cas d'utilisation

## Vue d’ensemble

Les cas d’utilisation décrivent la manière dont les **acteurs interagissent avec le système** pour atteindre leurs objectifs.  
Dans le contexte de la plateforme **ChoixCours UdeM**, ils représentent les principales fonctionnalités offertes aux étudiants pour les aider à **planifier efficacement leur parcours académique**.  

Chaque cas d’utilisation correspond à une interaction concrète et observable entre un utilisateur (étudiant) et le système, comme par exemple la recherche de cours, la création d’une simulation d’horaire, la gestion de favoris ou encore la vérification du cheminement de diplomation.  
Ces cas permettent de comprendre **ce que fait le système**, sans entrer dans les détails techniques de son implémentation.  

---

## Liste des cas d’utilisation

| ID | Nom | Acteurs principaux | Description |
|----|-----|---------------------|-------------|
| CU01 |...|...|...|
| CU02 |...|...|...|
| CU03 |...|...|...|
| CU04 |Gérer ses favoris| Utilisateur | L’utilisateur ajoute ou retire des cours à sa liste de favoris pour les retrouver plus facilement. |
| CU05 |Afficher tableau de bord |Étudiant (principal), Base de données (secondaire), API Planifium (secondaire)|L’étudiant voit rapidement l’état de son parcours et ses cours favoris pour mieux planifier son prochain semestre.|
| CU06 |Comparer cours| Étudiant (principal), Base de données (secondaire) API Planifium (secondaire) | L’étudiant peut comparer plusieurs options côte à côte pour choisir le cours qui correspond le mieux à ses objectifs et à sa disponibilité.|
| CU07 |...|...|...|
| CU08 |...|...|...|
| CU09 |Rechercher cours|Étudiant (principal), API Planifium (secondaire), Base de données (secondaire)|L’étudiant peut rapidement identifier les cours pertinents à son profil et savoir s’il peut s’y inscrire.|
| CU10 |Afficher résultats académiques d'un cours|Étudiant (principal), API Planifium (secondaire), Base de données (secondaire)|L’étudiant obtient des repères sur la difficulté et la réussite des cours pour décider lesquels s’intègrent le mieux à son parcours.|
| CU11 |...|...|...|
| CU12 | Créer une simulation d’horaire | Utilisateur | L’utilisateur crée une simulation d’horaire à partir de cours choisis afin de visualiser la charge de travail et la compatibilité des plages horaires. |
| CU13 | Consulter la place d'un cours dans le programme | Utilisateur | L’utilisateur consulte la contribution d’un cours à la diplomation selon son programme (catégories de crédits, exigences restantes, etc.). |

---

## Détail


### CU01 - xxx

**Acteurs** :  
**Préconditions** :  
**Postconditions** :  
**Déclencheur** :  
**Dépendances** :  
**But** :  

---
### CU02 - xxx

**Acteurs** :   
**Préconditions** :    
**Postconditions** :  
**Déclencheur** :  
**Dépendances** :  
**But** :  

---
### CU03 - xxx

**Acteurs** :   
**Préconditions** :    
**Postconditions** :  
**Déclencheur** :  
**Dépendances** :  
**But** :  

---
### CU04 - Gérer ses favoris

**Acteurs** : Utilisateur (principal)  
**Préconditions** : L’utilisateur est connecté à son compte et dispose d’une liste de cours consultables.  
**Postconditions** : Le cours est ajouté ou retiré de la liste des favoris persistée dans le profil utilisateur.  
**Déclencheur** : L’utilisateur clique sur l’icône « Favori » ou accède à sa liste de favoris.  
**Dépendances** : Système de profil utilisateur et base de données des cours.  
**But** : Permettre à l’utilisateur de conserver une liste personnalisée de cours qu’il souhaite suivre, comparer ou évaluer plus tard.

---
### CU05 - Affficher tableau de bord
**Acteurs** : Étudiant (principal), Base de données (secondaire), API Planifium (secondaire)    
**Préconditions** : L’étudiant est connecté et a un profil existant.  
**Postconditions** : Le tableau de bord est affiché avec les informations disponibles et interactives. 
**Déclencheur** : L’étudiant clique sur le bouton ou le menu « Tableau de bord » après s’être connecté.    
**Dépendances** :  CU1 / CU7 : Profil étudiant et favoris stockés dans la base de données,API Planifium : récupération des données officielles des cours ,CU9 / CU11 : consultation et recherche de cours (si le tableau de bord affiche des liens ou résumés de cours)  
**But** : Permettre à l’étudiant de visualiser un résumé personnalisé de ses cours, favoris, et informations clés pour faciliter la prise de décision.  

**Scénario principal:**  
1) L'étudiant accède à la page d'accueil après sa connexion.  
2) Le système vérifie les informations de connexion et le type de profil.  
3) Le système récupère les informations du profil depuis la base de données.    
4) Le système interroge l’API Planifium pour mettre à jour les données des cours suivis et favoris.    
5) Le système récupère les statistiques académiques et avis liés aux cours de l’étudiant.  
6) Le système compile toutes les données (favoris, suggestions, statistiques).  
7) Le système affiche le tableau de bord personnalisé à l’écran.  
8) L’étudiant consulte et interagit avec les éléments (ex. accéder à la fiche d’un cours, modifier un favori).  

**Scénario alternatif**  
3a. Le profil de l’étudiant n’existe pas dans la base de données.  
 3a.1. Le système affiche un message invitant à créer un profil.  
 3a.2. L’étudiant choisit de créer son profil.  
 3a.3. Le système redirige vers le CU7 (Créer profil).  
 3a.4. Une fois le profil créé, le scénario principal reprend à l’étape 4.  

7a. Une erreur interne empêche l’affichage du tableau.  
 7a.1. Le système affiche un message d’erreur générique.  
 7a.2. Le système envoie un rapport automatique à l’équipe technique.  
 7a.3. L’étudiant peut réessayer ou se déconnecter.  

---
### CU06 - Comparer Cours

**Acteurs** : Étudiant (principal), Base de données (secondaire), API Planifium (secondaire)  
**Préconditions** : L’étudiant est connecté et a accès aux cours à comparer.  
**Postconditions** : L’étudiant visualise un tableau comparatif à jour des cours sélectionnés.  
**Déclencheur** : L’étudiant sélectionne l’option « Comparer cours » dans le menu ou depuis la page d’un cours.   
**Dépendances** : CU5 : le tableau de bord peut servir de point de départ pour la sélection de cours, CU11 : fiches de cours pour récupérer les informations détaillées, CU10 : résultats académiques des cours à comparer, CU8 : avis étudiants (optionnel, si affiché dans la comparaison)  
**But** : Permettre à l’étudiant de comparer plusieurs cours pour évaluer la charge de travail et faciliter son choix.

---
### CU07 - xxx

**Acteurs** :  
**Préconditions** :    
**Postconditions** :  
**Déclencheur** :  
**Dépendances** :  
**But** :  

---
### CU08 - xxx

**Acteurs** :   
**Préconditions** :    
**Postconditions** :  
**Déclencheur** :  
**Dépendances** :  
**But** :  

---
### CU09 - Rechercher cours

**Acteurs** : Étudiant (principal), API Planifium (secondaire), Base de données (secondaire)  
**Préconditions** : L’étudiant est connecté et a un profil existant.  
**Postconditions** : La liste des cours correspondant à la recherche est affichée avec indication d’éligibilité.  
**Déclencheur** : L’étudiant saisit un code, titre ou mot-clé dans la barre de recherche et appuie sur « Rechercher ».    
**Dépendances** :  API Planifium : accès aux informations des cours, CU10 : pour afficher les résultats académiques d’un cours trouvé, CU11 : pour accéder à la fiche détaillée d’un cours,Base de données : vérifier l’éligibilité de l’étudiant pour certains cours
**But** : Permettre à l’étudiant de trouver des cours selon différents critères (code, titre, mots-clés) et vérifier son éligibilité.  

---
### CU10 - Afficher résultats académiques d’un cours

**Acteurs** : Étudiant (principal), API Planifium (secondaire), Base de données (secondaire)  
**Préconditions** :  L’étudiant est connecté et consulte la fiche d’un cours existant.  
**Postconditions** : Les résultats académiques disponibles du cours sont affichés.  
**Déclencheur** : L’étudiant clique sur le bouton ou le lien « Résultats académiques » depuis la fiche d’un cours.  
**Dépendances** :  CU11 : fiche de cours contenant le lien ou bouton pour afficher les résultats,API Planifium : pour récupérer les statistiques globales du cours, Base de données : stockage et accès aux résultats agrégés (si nécessaire)  
**But** : Permettre à l’étudiant de consulter les statistiques agrégées d’un cours pour mieux évaluer la charge et le niveau de difficulté.  

---
### CU11 - xxx

**Acteurs** :  
**Préconditions** :    
**Postconditions** :  
**Déclencheur** :  
**Dépendances** :  
**But** :  

---

### CU12 - Créer une simulation d’horaire

**Acteurs** : Utilisateur (principal)  
**Préconditions** : L’utilisateur est connecté et a sélectionné au moins un cours disponible pour la session à venir.  
**Postconditions** : Une simulation d’horaire est générée et enregistrée; les conflits éventuels sont signalés.  
**Déclencheur** : L’utilisateur clique sur « Créer une simulation » ou ajoute un cours à son horaire simulé.  
**Dépendances** : Données d’horaires de l’API Planifium et informations de cours dans la base locale.  
**But** : Offrir à l’étudiant une vue interactive de son horaire potentiel afin d’évaluer la charge totale et détecter d’éventuels conflits.

---

### CU13 - Consulter la place d'un cours dans le programme

**Acteurs** : Utilisateur (principal)  
**Préconditions** : L’utilisateur a défini son programme ou son profil académique (ex. Baccalauréat en informatique).  
**Postconditions** : Le système affiche la contribution du cours choisi dans les catégories du programme (ex. cours obligatoires, à option, crédits restants).  
**Déclencheur** : L’utilisateur consulte un cours et sélectionne l’option « Voir contribution au programme ».  
**Dépendances** : Base de données des programmes, API Planifium (structure du programme et types de cours).  
**But** : Aider l’étudiant à comprendre l’impact d’un cours sur sa progression vers le diplôme, et à planifier ses choix en fonction des exigences restantes.

---
