---
title: Analyse des besoins - Présentation générale
---
# Description du domaine

Tout étudiant a déjà eu des difficultés pour faire son choix de cours. Chacun essaie d’optimiser son cheminement dépendamment de beaucoup de facteurs (la charge de travail, la difficulté perçue, les prérequis, la compatibilité avec leur horaire et leurs intérêts personnels) qui peuvent provoquer des interrogations et du stress. Ce projet est une réponse directe à toutes ces problématiques puisqu’il concerne la planification académique et le choix de cours des étudiants de l’Université de Montréal, en particulier ceux du Département d’informatique et de recherche opérationnelle (DIRO).  

Les étudiants basent leur jugement sur plusieurs sources qui, réunies ensemble, peuvent mener à la confusion vis-à-vis du manque de transparence quant à la charge réelle de travail, le rythme du cours ou le niveau de difficulté ressenti. On peut notamment distinguer les données officielles (plans de cours, résultats académiques agrégés, API Planifium) fournissant une base fiable, mais restent partielles. En parallèle, les sources informelles (forums, discussions sur Discord, bouche-à-oreille) regorgent d’informations précieuses mais non structurées. 

La plateforme a pour fonction d’épurer toutes ces informations dans une interface web unifiée, accessible également via une API REST. Elle combinera les données officielles de l’Université (issues de Planifium et des fichiers CSV de résultats académiques) avec les avis étudiants recueillis via un bot Discord. Au-delà du système de confidentialité qui sera mis en place, des options comme rechercher, comparer et filtrer des cours, consulter des tableaux de bord interactifs et bénéficier d’une personnalisation fondée sur le profil seront présentes pour maximiser l’efficacité et rentrer dans une nouvelle ère où le choix de cours devient trivial.

Prenant tout cela en compte, l’étudiant se connecte à la plateforme, crée un profil (intérêts, préférences).  
Il peut ensuite rechercher des cours, consulter leurs informations (officielles et issues des avis Discord), comparer plusieurs cours et obtenir des recommandations.  
Le système interagit avec l’API Planifium pour récupérer les données de cours, agrège les résultats académiques à partir d’un fichier CSV, et affiche les avis pertinents dès qu’un seuil de 5 avis est atteint.  

---

## Acteurs du domaine

**Étudiant :** utilisateur principal qui recherche, compare et consulte les cours selon ses préférences et son profil. Il peut personnaliser son expérience et soumettre des avis via Discord.  

**Modérateur :** supervise les avis publiés par les étudiants, s’assure de leur pertinence et supprime le contenu inapproprié afin de préserver la fiabilité des données.  

**TGDE :** intervenant administratif qui consulte les données agrégées (résultats, statistiques) et signale les erreurs ou incohérences liées aux programmes officiels.  

**API Planifium :** service externe fournissant les données officielles sur les cours (codes, crédits, prérequis, horaires) utilisées pour alimenter la plateforme.  

**Base de données :** composant interne stockant et centralisant toutes les informations issues des différentes sources (Planifium, résultats académiques, avis Discord, profils).  

**Bot Discord :** outil automatisé collectant les avis étudiants sur les cours et les transmettant au système sous forme structurée pour agrégation et affichage.  

---

## Dépendances du système

Le système repose sur plusieurs dépendances logicielles, techniques et organisationnelles essentielles à son fonctionnement :

1. **Dépendance à l’API Planifium**  
   - Fournit les informations officielles sur les cours (codes, crédits, horaires, pré/co-requis, structures de programmes).  
   - Toute modification ou indisponibilité de l’API peut limiter la précision des données affichées sur la plateforme.

2. **Dépendance aux fichiers CSV de résultats académiques**  
   - Source de données agrégées (moyenne, taux d’échec, nombre d’inscrits).  
   - Ces fichiers doivent être régulièrement mis à jour pour garantir l’exactitude des statistiques présentées.

3. **Dépendance au bot Discord (avis étudiants)**  
   - Outil externe chargé de collecter et transmettre les avis étudiants au système.  
   - Le fonctionnement de la plateforme dépend du volume et de la fiabilité de ces avis, qui ne sont affichés qu’à partir du seuil **n ≥ 5**.

4. **Dépendance à la base de données interne**  
   - Centralise et relie toutes les données issues des différentes sources (Planifium, CSV, Discord, profils).  
   - Une défaillance ou corruption des données pourrait impacter la cohérence du système.

5. **Dépendance au profil utilisateur**  
   - Les fonctions de personnalisation (recommandations, favoris, simulations) dépendent des informations renseignées dans le profil (programme, préférences, cheminement).  
   - Ces données doivent être protégées et synchronisées selon les exigences de confidentialité.

6. **Dépendance à l’infrastructure d’hébergement**  
   - Le système nécessite un hébergement web stable pour le frontend, l’API REST et la base de données.  
   - La disponibilité, la sécurité et les sauvegardes régulières sont indispensables à la fiabilité globale du service.

7. **Dépendance aux règles institutionnelles**  
   - Les données sur les programmes et les exigences de diplomation proviennent des structures officielles de l’Université de Montréal.  
   - Tout changement administratif (programme, cours obligatoires, crédits requis) doit être reflété dans la plateforme pour conserver la validité des recommandations.

---

En résumé, le système dépend d’un **écosystème interconnecté** entre :
- les **sources externes** (Planifium, fichiers CSV, Discord),  
- les **acteurs humains** (étudiants, TGDE, modérateurs),  
- et les **composants internes** (API REST, base de données, interface web).  

La qualité, la disponibilité et la cohérence de ces dépendances conditionnent directement la fiabilité et la pertinence de la plateforme d’aide au choix de cours.


## Hypothèses et contraintes

TODO: Liste des hypothèses de travail et des contraintes (techniques, organisationnelles, etc.).
