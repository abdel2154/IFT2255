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
| CU04 | Gérer ses favoris | Utilisateur | L’utilisateur ajoute ou retire des cours à sa liste de favoris pour les retrouver plus facilement. |
| CU12 | Créer une simulation d’horaire | Utilisateur | L’utilisateur crée une simulation d’horaire à partir de cours choisis afin de visualiser la charge de travail et la compatibilité des plages horaires. |
| CU13 | Voir comment un cours s’inscrit dans les exigences de diplomation | Utilisateur | L’utilisateur consulte la contribution d’un cours à la diplomation selon son programme (catégories de crédits, exigences restantes, etc.). |

---

## Détail

### CU04 - Gérer ses favoris

**Acteurs** : Utilisateur (principal)  
**Préconditions** : L’utilisateur est connecté à son compte et dispose d’une liste de cours consultables.  
**Postconditions** : Le cours est ajouté ou retiré de la liste des favoris persistée dans le profil utilisateur.  
**Déclencheur** : L’utilisateur clique sur l’icône « Favori » ou accède à sa liste de favoris.  
**Dépendances** : Système de profil utilisateur et base de données des cours.  
**But** : Permettre à l’utilisateur de conserver une liste personnalisée de cours qu’il souhaite suivre, comparer ou évaluer plus tard.

---

### CU12 - Créer une simulation d’horaire

**Acteurs** : Utilisateur (principal)  
**Préconditions** : L’utilisateur est connecté et a sélectionné au moins un cours disponible pour la session à venir.  
**Postconditions** : Une simulation d’horaire est générée et enregistrée; les conflits éventuels sont signalés.  
**Déclencheur** : L’utilisateur clique sur « Créer une simulation » ou ajoute un cours à son horaire simulé.  
**Dépendances** : Données d’horaires de l’API Planifium et informations de cours dans la base locale.  
**But** : Offrir à l’étudiant une vue interactive de son horaire potentiel afin d’évaluer la charge totale et détecter d’éventuels conflits.

---

### CU13 - Voir comment un cours s’inscrit dans les exigences de diplomation

**Acteurs** : Utilisateur (principal)  
**Préconditions** : L’utilisateur a défini son programme ou son profil académique (ex. Baccalauréat en informatique).  
**Postconditions** : Le système affiche la contribution du cours choisi dans les catégories du programme (ex. cours obligatoires, à option, crédits restants).  
**Déclencheur** : L’utilisateur consulte un cours et sélectionne l’option « Voir contribution au programme ».  
**Dépendances** : Base de données des programmes, API Planifium (structure du programme et types de cours).  
**But** : Aider l’étudiant à comprendre l’impact d’un cours sur sa progression vers le diplôme, et à planifier ses choix en fonction des exigences restantes.

---
