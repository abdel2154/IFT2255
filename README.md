# Projet Choix de Cours - Université de Montréal

## Description

Ce projet vise à créer une **plateforme web** pour aider les étudiants de l’Université de Montréal à faire des **choix de cours éclairés**.  
La plateforme centralise et combine :

- **Données officielles** : API Planifium (catalogue des programmes et cours, horaires), résultats académiques agrégés (moyenne finale, nombre d’inscrits, nombre d’échecs)  
- **Avis étudiants** : collectés via un bot Discord, comprenant difficulté perçue, charge de travail estimée, et commentaires optionnels  

La plateforme permet aux étudiants de :

- Rechercher des cours par code, titre ou mot-clé
- Vérifier l’éligibilité à un cours (prérequis, co-requis, cycle, contraintes de programme)
- Consulter les résultats académiques agrégés
- Afficher et modérer les avis étudiants
- Comparer plusieurs cours pour estimer la charge totale
- Créer un profil et personnaliser les résultats selon leurs préférences
- Gérer leurs favoris
- Créer une simulation d’horaire
- Visualiser l’intégration des cours dans leur programme de diplôme

> La phase 1 du projet se concentre sur l’**analyse des besoins**, la **spécification testable** et la **modélisation du système**. L’implémentation complète et l’UI finale sont prévues pour les phases suivantes.

---

## Objectifs de la Phase 1

- Définir le **cadre du projet** : acteurs, objectifs, contraintes et hypothèses  
- Analyser et transformer la **liste de souhaits en exigences vérifiables**  
- Identifier les **risques et besoins non fonctionnels**  
- Produire les **diagrammes de cas d’utilisation et d’activités**  
- Élaborer le **modèle C4** pour illustrer l’architecture du système  
- Documenter les **décisions initiales** et limites techniques  

---

## Équipe

| Nom complet | Matricule | Discord |
|------------|-----------|--------|
| Membre 1-Ring Thomas   | 20162157     | @papi_speedrun |
| Membre 2-Bencheikh El Atmani Ayoub   | 20237691    | @yubito04 |
| Membre 3-Messaad Abdelmouhcine   | 20287581     | @abdel_kb99 |
| Membre 4-Ujineza Ursuline   | 20272011     | @boredGenie |

---

## Cas d’utilisation (CU) principaux

La plateforme couvre les **CU suivants** :

1.Exporter les données souhaitées
2.Modérer les avis étudiants
3.Consulter un profil
4.Gérer ses favoris
5.Afficher tableau de bord
6.Comparer cours
7.Créer profil
8.Afficher avis étudiant
9.Rechercher cours
10.Afficher résultats académiques d’un cours
11.Consulter la fiche d’un cours
12.Créer une simulation d’horaire
13. Consulter la place d’un cours dans le programme 

> La documentation contient la **description détaillée de 5 CU clés** avec acteurs, préconditions, postconditions, déclencheurs, dépendances et scénarios complets.

---

## Diagrammes

- **Diagramme de cas d’utilisation global** : montre tous les CU et leurs interactions avec les acteurs  
- **Diagramme d’activités** : représente le flux principal du système, de la recherche de cours à la consultation des résultats et avis  
- **Modèle C4 (niveau 1 et 2)** : illustre les composants principaux (frontend, API REST, base de données, bot Discord) et leurs communications

---

> La phase 2 du projet se concentre sur la **conception technique détaillée**, la **première implémentation** du backend et la **création d'une suite de tests**. L'intégration complète des données et l'interface utilisateur avancée sont prévues pour les phases ultérieures.

## Objectifs de la Phase 2

- **Intégrer le feedback** de la Phase 1 et réviser les artefacts existants  
- **Concevoir une architecture robuste** : modèle C4 niveau 3, diagramme de classes MVC, diagrammes de séquence  
- **Implémenter les fonctionnalités clés** : recherche de cours, consultation des détails, comparaison de cours  
- **Produire des tests unitaires** avec JUnit et documenter les oracles de test  
- **Appliquer les bonnes pratiques** : gestion avancée de Git, modularité, faible couplage  


---

## Diagrammes (Phase 2)

- **Modèle C4 (niveau 3)** : découpe détaillée des composants internes du backend (contrôleurs, services, modèles, repositories) et leurs dépendances  
- **Diagramme de classes** : structure MVC du backend avec abstractions (classes abstraites, interfaces), focalisé sur la couche métier  
- **Diagrammes de séquence** : illustrent 3 interactions clés (recherche, comparaison, soumission d'avis) incluant le traitement des cas limites et des erreurs

---

## Implémentation et tests (Phase 2)

### Fonctionnalités implémentées
- **Recherche de cours** : par code, titre ou mots-clés avec vérification d'éligibilité  
- **Voir les détails d'un cours** : informations Planifium, horaires, résultats académiques  
- **Comparer des cours** : tableau comparatif, estimation charge de travail, détection conflits

### Tests
- **Tests unitaires** : minimum 3 par membre avec JUnit, couvrant les services et contrôleurs  
- **Oracle de tests** : spécification des retours attendus et effets de bord pour chaque test

---
