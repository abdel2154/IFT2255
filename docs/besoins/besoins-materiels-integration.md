# Besoins matériels, solution de stockage et d’intégration (version simple)

Objectif : expliquer sur quelle machine tourne le site, où sont gardées les données, et comment on se connecte aux sources externes (Planifium, Discord, résultats).

## 1) Besoins matériels (où tourne le site)
- **Machine serveur :** une machine virtuelle avec Linux.
- **Taille conseillée :** 2 “cœurs” de processeur, 4 Go de mémoire, 20 Go de disque.
- **Connexion :** accès internet stable et site accessible en https (sécurisé).
- **Utilisateurs :** un simple navigateur web suffit (Chrome, Firefox, Edge, Safari).
- **But :** supporter sans problème les consultations pendant les périodes d’inscription.

**Vérification rapide :**
- Le site répond en https (pas en http simple).
- Un petit test montre que le site reste fluide avec de nombreux utilisateurs connectés en même temps (la plupart des pages en ≤ 2 s).

## 2) Solution de stockage (où sont les données)
- **Base de données :** on utilise PostgreSQL (base relationnelle classique).
- **Ce qu’on stocke :**
  - **Cours** (code, titre, crédits, etc.)
  - **Résultats** (moyenne, nombre d’inscrits, échecs, session)
  - **Avis** (difficulté perçue, charge estimée, commentaire)
  - **Profils** (préférences générales pour personnaliser l’affichage)
- **Fichiers entrants :**
  - **CSV** pour les résultats (fournis par session)
  - **JSON** pour les avis (provenant de Discord)
  - **API** Planifium pour le catalogue et les horaires
- **Sauvegardes :** une copie de la base est faite chaque jour et on sait la remettre en place rapidement si besoin.
- **Confidentialité :** on n’enregistre pas d’informations qui identifient directement un étudiant dans les avis.

**Vérification rapide :**
- On peut importer un gros fichier CSV sans erreur en un temps raisonnable.
- On peut restaurer une sauvegarde sans difficulté (testée régulièrement).
- Les avis n’affichent des statistiques que s’il y a au moins **5 avis**.

## 3) Solution d’intégration (comment on se connecte aux autres)
- **Planifium (officiel) :** on lit les informations de cours et d’horaires via leur API, puis on les met en forme pour notre base.
- **Discord (avis étudiants) :** un petit robot récupère les avis au format JSON, on vérifie que le contenu est correct, puis on les enregistre.
- **Résultats (CSV) :** on charge les fichiers fournis, on vérifie qu’ils sont bien formés, puis on met à jour la base.

**Règles de bon fonctionnement :**
- Si une source externe ne répond pas (ex. Planifium), on affiche les **dernières données connues** et on prévient l’utilisateur.
- On limite le nombre d’appels vers chaque service pour éviter les blocages.
- On garde des traces (journaux) de ce qui se passe : temps de réponse, erreurs, import réussi ou non.

**Vérification rapide :**
- Quand Planifium est indisponible, le site continue d’afficher les infos déjà enregistrées et montre un message d’avertissement.
- Si un fichier d’avis ou de résultats est invalide, il est refusé et on enregistre une explication de l’erreur.
- La description de notre API (documentation) est accessible pour ceux qui veulent s’y connecter.


