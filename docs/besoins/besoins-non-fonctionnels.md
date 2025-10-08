# Besoins non fonctionnels

Les besoins non fonctionnels décrivent les caractéristiques qualitatives du système qui influencent sa performance, sa fiabilité et sa facilité d’utilisation.  
Ils ne concernent pas les fonctionnalités directement visibles par l’utilisateur, mais les propriétés qui garantissent la qualité et la robustesse du produit final.

---

## 1. Accessibilité et ergonomie

L’application doit être accessible à tous les étudiants, quelle que soit leur situation ou leur appareil(y compris les personnes en situation de handicap (visuel, auditif, moteur, cognitif, etc.).  
Elle devra respecter les normes **WCAG 2.1** (normes internationales définies par le W3C (World Wide Web Consortium) pour rendre les sites web accessibles à tous les utilisateurs) afin d'être compatible avec les principaux navigateurs modernes (Chrome, Firefox, Edge, Safari) et offrir une interface responsive (adaptée aux téléphones et tablettes).

**Justification :**  
Les étudiants consultent fréquemment leurs outils universitaires depuis des appareils mobiles. Une interface claire, cohérente et accessible favorise l’adoption du système par l’ensemble de la communauté.

---

## 2. Performance et efficacité

Les temps de réponse doivent rester inférieurs à **2 secondes** pour les opérations courantes (recherche, consultation d’un cours, affichage d’une comparaison).  
L’API REST doit être optimisée pour éviter les surcharges, notamment lors des périodes de forte utilisation (inscriptions, choix de cours).

**Justification :**  
Un outil lent ou instable décourage l’utilisation. La performance contribue directement à la satisfaction et à la fluidité de l’expérience utilisateur.


---
## 3. Fiabilité et disponibilité
Le système doit garantir une disponibilité continue (24 h/24 et 7 j/7), en assurant une tolérance aux pannes et un plan de reprise automatique en cas d’incident. Des sauvegardes régulières (quotidiennes ou horaires selon la criticité des données) doivent être effectuées afin d’éviter toute perte d’information. En cas de défaillance matérielle, logicielle ou réseau, un mécanisme de basculement doit permettre de restaurer le service rapidement, sans interruption perceptible pour l’utilisateur.

**Justification** :
Le système sera particulièrement sollicité lors des périodes d’inscription et de choix de cours, où la disponibilité et la fiabilité sont cruciales. Une indisponibilité, même temporaire, pourrait entraîner une perte de confiance des utilisateurs, des inscriptions erronées et une dégradation de la crédibilité du service. Garantir une haute disponibilité renforce la stabilité de la plateforme et la satisfaction des étudiants et du personnel administratif.

---

## 4. Maintenabilité et évolutivité
L’architecture du système doit être modulaire et bien structurée, en séparant clairement les différentes couches (interface utilisateur, API, logique applicative, base de données). Cette organisation facilite la compréhension du code, les mises à jour et l’ajout de nouvelles fonctionnalités telles qu’un chatbot, des statistiques avancées ou des outils de recommandation personnalisée.

**Justification :**  
Une bonne maintenabilité réduit le coût d’évolution et prolonge la durée de vie du projet.

---

## 5. Sécurité et confidentialité

Le système doit garantir la protection et la confidentialité de toutes les données manipulées, notamment les profils étudiants et les avis partagés via la plateforme. Les échanges entre les différentes composantes (base de données, API, interface web) doivent être sécurisés.  

**Justification :**  
Les informations échangées peuvent contenir des éléments sensibles (opinions, identifiants de cours). Assurer la sécurité et la conformité légale renforce la confiance des utilisateurs et la crédibilité institutionnelle du projet.

---



