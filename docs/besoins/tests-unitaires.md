# Tests unitaires — Vue d'ensemble et analyse

Ce document décrit les tests unitaires ajoutés pour le backend (contrôleur `CourseController`) et explique leur objectif, la stratégie de test, les entrées/sorties et les oracles utilisés. 
**Fichier de tests modifié** : `rest-api/src/test/java/com/diro/ift2255/controller/CourseControllerTest.java`

**Frameworks et outils**
- JUnit 5 (annotations `@Test`, `@DisplayName`)
- Mockito (mocks, `when(...)`, `verify(...)`, `never()`)
- Javalin (objet `Context` simulé)

**Contexte**
Les tests ciblent l'endpoint `CourseController.compareCourses(Context)` :
- Entrée attendue : corps JSON mappé sur `CourseController.CompareRequest` (champ public `List<String> courseIds`).
- Comportement : le contrôleur valide la requête et délègue la logique métier à `ComparisonService.compareCourses(List<String>)`.

---

## Tests ajoutés 
- `testCompareCoursesWithValidIds`
- `testCompareCoursesWithEmptyList`
- `testCompareCoursesWithNullRequest`


---

## Détails des tests 

- **testCompareCoursesWithValidIds** — Vérifie le chemin  : pour une requête valide la méthode appelle le service et renvoie le `ComparisonResult` attendu (ex. : `['IFT2255','IFT2015']` → 2 cours, `totalCredits = 6`).

- **testCompareCoursesWithEmptyList** — Vérifie la validation : si `courseIds` est présent mais vide, le contrôleur renvoie `400` et un message d'erreur; le service n'est pas appelé.

- **testCompareCoursesWithNullRequest** — Vérifie le comportement face à un corps non désérialisable (`null`) : le contrôleur renvoie `400`, envoie un message d'erreur et n'appelle pas le service.


---

## Emplacement des tests
- Fichier : `rest-api/src/test/java/com/diro/ift2255/controller/CourseControllerTest.java`

## Commandes utiles (exécuter localement)
Pour lancer uniquement ces tests (Maven) :

```bash
mvn -Dtest=CourseControllerTest test
```

Pour lancer la suite de tests complète :

```bash
mvn test
```


---

## Tests supplémentaires — vérification des routes (`Routes`)

Les tests suivants vérifient la présence et le comportement de la classe de configuration des routes (`Routes`) et de sa méthode `register` qui enregistre les endpoints sur l'objet Javalin. Ces tests sont statiques ou basés sur la réflexion / mock pour garantir que l'application expose correctement les routes nécessaires (CU09, CU10, CU11).

### Test 1 : `routesClass_ExistsAndHasMethods`
- Arguments donnés : Aucun (test statique de la classe)
- Retour attendu :
  - `assertNotNull(Routes.class)` → true
  - `assertTrue(Routes.class.getDeclaredMethods().length > 0)` → true
- Effets de bord attendus : Aucun
- Cas d'utilisation lié : Tous (configuration générale API)

### Test 2 : `registerMethod_ExistsAndTakesJavalinParameter`
- Arguments donnés : Reflection sur `Routes.class`, nom méthode = `register`, type paramètre = `Javalin.class`
- Retour attendu :
  - Méthode trouvée (not null)
  - 1 paramètre exactement
  - Type paramètre = `Javalin.class`
- Effets de bord attendus : Aucun
- Cas d'utilisation lié : Tous (configuration routes)

### Test 3 : `register_AddsRoutesToApp`
- Arguments donnés : Mock de `Javalin` → `mock(Javalin.class)`
- Retour attendu :
  - `verify(mockApp, atLeast(1)).get(anyString(), any())` → true
  - `verify(mockApp, atLeast(1)).post(anyString(), any())` → true
- Effets de bord attendus : Aucun (mock)
- Cas d'utilisation lié : CU09 (Recherche cours), CU10 (Voir détails), CU11 (Comparer cours)

---

## Oracle de tests unitaires pour les fonctionnalités principales

Voici l'oracle de tests pour les 4 fonctionnalités principales implémentées dans l'API REST :

### 1. Voir les cours par programme
- **Fichier de test :** ProgramsControllerTest.java
- **Cas testés :**
  - Succès avec un ou plusieurs codes de programme valides
  - Erreur si le paramètre `programs_list` est manquant ou mal formé
  - Détail des cours si `include_courses_detail` est présent
  - Gestion de plusieurs IDs en entrée
  - Message d’erreur si aucun programme trouvé

### 2. Voir les cours par semestre
- **Fichier de test :** CourseControllerTest.java
- **Cas testés :**
  - Succès avec un code de semestre valide
  - Erreur si le code de semestre est absent ou invalide
  - Filtrage par sigle de cours
  - Erreur si le format du sigle est incorrect
  - Message si aucun cours trouvé pour le semestre

### 3. Vérifier l’éligibilité à un cours
- **Fichier de test :** EligibilityControllerTest.java
- **Cas testés :**
  - Succès si tous les prérequis sont validés
  - Erreur si la liste des cours complétées est vide
  - Erreur si un sigle est invalide
  - Erreur si la liste dépasse 100 éléments
  - Cas où des prérequis sont manquants

### 4. Voir les résultats académiques
- **Fichier de test :** AcademicControllerTest.java
- **Cas testés :**
  - Succès si le sigle existe dans le CSV
  - Erreur si le sigle est absent ou vide
  - Erreur si le sigle est null
  - Recherche insensible à la casse
  - Message d’erreur si aucune donnée trouvée

---

Chaque fonctionnalité est testée avec 5 cas distincts, couvrant :
- Les entrées valides (succès)
- Les entrées invalides (erreurs de validation)
- Les cas limites (listes vides, sigle incorrect, etc.)
- Les réponses attendues (JSON, messages d’erreur)

Ces tests garantissent la robustesse et la conformité de l’API aux exigences fonctionnelles.

---

## Tableau récapitulatif des tests unitaires

| Fonctionnalité                | Fichier de test                   | Nombre de tests | Détail des cas testés |
|-------------------------------|-----------------------------------|-----------------|----------------------|
| Recherche de cours            | CourseControllerTest.java          | 5               | - Sans paramètres<br>- Avec paramètres<br>- Liste vide<br>- Multi-valeurs<br>- Aucun résultat |
| Cours par programme           | ProgramsControllerTest.java        | 5               | - Succès<br>- Paramètre manquant<br>- Format invalide<br>- Détail des cours<br>- Multiples IDs |
| Cours par semestre            | CourseControllerTest.java          | 5               | - Succès<br>- Semestre null<br>- Sigle invalide<br>- Sigle valide<br>- Aucun résultat |
| Vérification d’éligibilité    | EligibilityControllerTest.java     | 5               | - Prérequis validés<br>- Prérequis manquants<br>- Liste vide<br>- Sigle invalide<br>- Liste > 100 |
| Résultats académiques         | AcademicControllerTest.java        | 5               | - Sigle existant<br>- Sigle absent<br>- Sigle vide<br>- Case-insensitive<br>- Sigle null |
| Comparaison de cours          | CourseControllerTest.java          | 5               | - IDs valides<br>- Liste vide<br>- Requête null<br>- Cours non trouvés<br>- Un seul cours |

**Total : 30 tests unitaires**

Chaque test vérifie la logique métier, la validation des entrées et les réponses attendues pour chaque cas. Aucun test sur les getters/setters ou constructeurs des modèles.

---

## Détail des tests unitaires (oracle)

### Recherche de cours (CourseControllerTest.java)

| Test | Entrée | Sortie attendue | Description |
|------|--------|-----------------|-------------|
| 1 | Aucun paramètre | Liste complète des cours | Vérifie le retour de tous les cours sans filtre |
| 2 | Paramètre session=A2025 | Liste filtrée | Vérifie le filtrage par session |
| 3 | Aucun résultat | Message + liste vide | Vérifie le retour si aucun cours trouvé |
| 4 | Multi-valeurs session | Première valeur prise | Vérifie la gestion des paramètres multi-valués |
| 5 | Liste vide | Liste vide | Vérifie le retour d'une liste vide |

### Cours par programme (ProgramsControllerTest.java)

| Test | Entrée | Sortie attendue | Description |
|------|--------|-----------------|-------------|
| 1 | programs_list=117510 | Liste des cours du programme | Succès avec un code valide |
| 2 | programs_list manquant | Erreur 400 | Vérifie la validation du paramètre obligatoire |
| 3 | Format invalide | Erreur 400 | Vérifie la validation du format |
| 4 | include_courses_detail=true | Liste détaillée | Vérifie le retour des détails des cours |
| 5 | Plusieurs IDs | Liste concaténée | Vérifie la gestion de plusieurs programmes |

### Cours par semestre (CourseControllerTest.java)

| Test | Entrée | Sortie attendue | Description |
|------|--------|-----------------|-------------|
| 1 | semester=a25 | Liste des cours du semestre | Succès avec un semestre valide |
| 2 | semester null | Erreur 400 | Vérifie la validation du paramètre obligatoire |
| 3 | courses_sigle=INVALID | Erreur 400 | Vérifie la validation du format du sigle |
| 4 | courses_sigle=IFT1015,IFT1025 | Liste filtrée | Vérifie le filtrage par sigle |
| 5 | Aucun résultat | Message | Vérifie le retour si aucun cours trouvé |

### Vérification d’éligibilité (EligibilityControllerTest.java)

| Test | Entrée | Sortie attendue | Description |
|------|--------|-----------------|-------------|
| 1 | completedCourses avec tous les prérequis | eligible=true | Vérifie le cas où l'étudiant est éligible |
| 2 | completedCourses manquant un prérequis | eligible=false + missingPrerequisites | Vérifie le cas où il manque des prérequis |
| 3 | Liste vide | Erreur 400 | Vérifie la validation de la liste obligatoire |
| 4 | Sigle invalide | Erreur 400 | Vérifie la validation du format du sigle |
| 5 | Liste > 100 | Erreur 400 | Vérifie la limite de taille de la liste |

### Résultats académiques (AcademicControllerTest.java)

| Test | Entrée | Sortie attendue | Description |
|------|--------|-----------------|-------------|
| 1 | sigle existant | Statistiques du cours | Vérifie le retour des stats pour un sigle valide |
| 2 | sigle absent | Message d'erreur | Vérifie le retour si le sigle n'existe pas |
| 3 | sigle vide | Erreur 400 | Vérifie la validation du sigle obligatoire |
| 4 | sigle en minuscules | Statistiques du cours | Vérifie la recherche insensible à la casse |
| 5 | sigle null | Erreur 400 | Vérifie la validation du sigle obligatoire |

### Comparaison de cours (CourseControllerTest.java)

| Test | Entrée | Sortie attendue | Description |
|------|--------|-----------------|-------------|
| 1 | IDs valides | Résultat de comparaison | Vérifie la comparaison de plusieurs cours |
| 2 | Liste vide | Erreur 400 | Vérifie la validation de la liste obligatoire |
| 3 | Requête null | Erreur 400 | Vérifie la validation de la requête |
| 4 | IDs non trouvés | Résultat avec notFound | Vérifie le retour si certains cours sont absents |
| 5 | Un seul ID | Résultat pour un seul cours | Vérifie la comparaison avec un seul cours |

---





