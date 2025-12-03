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

### Oracle de tests – ComparisonService (CU11 – Comparer des cours)

Les tests suivants valident la logique métier de la méthode
`ComparisonService.compareCourses(List<String> courseIds)`, qui implémente
le cas d'utilisation **CU11 – Comparer des cours**.

Un service factice (`FakeCourseService`) est utilisé pour simuler les réponses de `CourseService`
sans appeler l’API Planifium. Ce fake retourne :

- IFT2255 → cours de 3 crédits ("Génie logiciel")
- IFT2015 → cours de 3 crédits ("Structures de données")
- tout autre code → `Optional.empty()` (cours inconnu)

---

#### Test 1 – `compareCourses_twoValidCourses_returnsCorrectTotals`

- **Cas d’utilisation lié** : CU11 – Comparer des cours  
- **Méthode testée** : `compareCourses(List<String> courseIds)`
- **Arguments d’entrée** :
  - `courseIds = ["IFT2255", "IFT2015"]`
- **Résultat attendu** :
  - `result.courses.size() = 2` (les deux cours sont trouvés)
  - `result.totalCredits = 6` (3 crédits + 3 crédits)
  - `result.estimatedWorkload = 18` heures par semaine (6 crédits × 3 h)
  - `result.notFound` est vide
  - `result.recommendation` contient la chaîne `"6 crédits"` (recommandation cohérente avec la charge totale)
- **Effets de bord attendus** :
  - Aucun effet de bord : la méthode ne modifie pas d’état global, elle se contente de lire les cours à partir du service et de retourner un objet `ComparisonResult`.

---

#### Test 2 – `compareCourses_withUnknownCourse_ignoresUnknownCourse`

- **Cas d’utilisation lié** : CU11 – Comparer des cours  
- **Méthode testée** : `compareCourses(List<String> courseIds)`
- **Arguments d’entrée** :
  - `courseIds = ["IFT2255", "FAKE999"]`
- **Résultat attendu** :
  - `result.courses.size() = 1` (seul IFT2255 est trouvé)
  - `result.totalCredits = 3`
  - `result.estimatedWorkload = 9` heures (3 crédits × 3 h)
  - `result.notFound.size() = 1`
  - `result.notFound` contient `"FAKE999"`
- **Effets de bord attendus** :
  - Aucun : les cours sont uniquement lus depuis le fake, et le résultat est calculé en mémoire sans persistance.

---

#### Test 3 – `compareCourses_emptyList_returnsZero`

- **Cas d’utilisation lié** : CU11 – Comparer des cours  
- **Méthode testée** : `compareCourses(List<String> courseIds)`
- **Arguments d’entrée** :
  - `courseIds = []` (liste vide)
- **Résultat attendu** :
  - `result.courses` est vide
  - `result.totalCredits = 0`
  - `result.estimatedWorkload = 0`
  - `result.notFound` est vide
- **Effets de bord attendus** :
  - Aucun : la méthode retourne simplement un `ComparisonResult` vide sans interagir avec des ressources externes.



