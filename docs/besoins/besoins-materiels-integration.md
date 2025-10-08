# Besoins materiels, solution de stockage et d'integration

Objectif : préciser l'infrastructure (materiel), la maniere dont les donnees sont stockees et sauvegardees (stockage), ainsi que la facon dont le systeme communique avec les services externes (integration).

## 1) Besoins materiels

Serveur web hebergeant une API REST et une base de donnees relationnelle. Deploiement simple sur VM Linux.

- Systeme : Ubuntu 22.04 (VM).
- Configuration minimale : 2 vCPU, 4 Go RAM, 20 Go disque.
- Reseau : acces sortant stable vers les APIs externes, port HTTPS expose.
- Clients : navigateurs modernes (Chrome, Firefox, Edge, Safari).
- Environnements : dev (local), test/CI (automatises), prod (etudiants).
- Charge cible : 100–200 requetes/min sans depasser p90 > 2 s.

**Criteres d'acceptation**
- Deploiement OK sur VM aux specs minimales.
- Tout le trafic est en HTTPS (aucune requete HTTP en clair).
- Test de charge 10 min a 200 req/min : p90 <= 2 s, p99 <= 4 s, erreurs < 1 %.

---

## 2) Solution de stockage

Les donnees seront conservees dans une base **PostgreSQL**, structuree autour des tables `cours`, `resultats`, `avis`, `profils`.

### Modele relationnel (extrait)
- `cours(id, code, titre, credits, departement, cycle)`
- `resultats(id, cours_id, trimestre, moyenne, inscrits, echec)`
- `avis(id, cours_id, difficulte, charge, commentaire, ts, source='discord')`
- `profils(id, prefs_theorie_pratique, centres_interet, cycle)`

### Regles et performances
- Integrite : cles etrangeres (FK), NOT NULL, controles de domaine (ex. difficulte dans [1..5]).
- Index : `cours(code)`, `resultats(cours_id,trimestre)`, `avis(cours_id,ts)`.
- Formats d'entree :
  - Resultats academiques : **CSV** (import planifie).
  - Avis etudiants (Discord) : **JSON** (ingestion via webhook/worker).
  - Planifium : **API REST** (normalisation + cache avant persistance).
- Sauvegardes : `pg_dump` quotidien, retention 30 jours, procedure de restauration documentee.
- Confidentialite : minimisation des donnees (aucun identifiant personnel dans `avis`), compte DB lecture seule pour l'API publique.
- Qualite : agrégation d'avis affichee seulement si **n >= 5** (sinon message "echantillon insuffisant").

**Criteres d'acceptation**
- Import CSV 10k lignes < 60 s, lignes rejetees journalisees.
- Test de restauration reussi (< 10 min en dev).
- Aucune donnee nominative dans `avis`; regle n >= 5 appliquee en UI.
