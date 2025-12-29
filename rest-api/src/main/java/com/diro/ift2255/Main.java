package com.diro.ift2255;

import com.diro.ift2255.model.AcademicStats;
import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.EligibilityResult;
import com.diro.ift2255.serveur.DemarreurServeur;
import com.diro.ift2255.service.AcademicService;
import com.diro.ift2255.service.ComparisonService;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.EligibilityService;
import com.diro.ift2255.util.*;
import io.javalin.Javalin;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HttpClientApi clientApi = new HttpClientApi();
        CourseService courseService = new CourseService(clientApi);
        AcademicService academicService = new AcademicService();
        ComparisonService comparisonService = new ComparisonService(courseService);
        EligibilityService eligibilityService = new EligibilityService(courseService);

        System.out.println(System.lineSeparator() + "Bienvenue à ChoixCours: un logiciel vous permettant d'éclairer vos choix de cours universitaires." + System.lineSeparator());
        System.out.println("Commandes disponibles:");
        System.out.println(" - quitter");
        System.out.println(" - demarrerServeur");
        System.out.println(" - comparerCours");
        System.out.println(" - rechercherCours");
        System.out.println(" - verifierEligibilite");
        System.out.println(" - voirResultatsAcademiques");

        Javalin app = null;

        while(true){
            String entree = scanner.nextLine().trim();

            if (entree.equals("quitter")) {
                System.out.println("Merci d'avoir utilisé ChoixCours!");
                if (app != null) {
                    app.stop();
                }
                break;
            }

            switch(entree) {
                case "demarrerServeur":
                    app = DemarreurServeur.main();
                    break;

                case "rechercherCours":
                    System.out.println("Veuillez entrer un sigle de cours, avec les lettres en minuscule.");
                    String sigle = scanner.nextLine().trim();

                    Optional<Course> optionalCourse = courseService.getCourseById(sigle);

                    if (optionalCourse.isPresent()) {
                        Course course = optionalCourse.get();
                        System.out.println("Cours trouvé: " + course.getName() + " (" + course.getId() + ")");
                        System.out.println("Description: " + course.getDescription());
                        System.out.println("Crédits: " + course.getCredits());
                        System.out.println("Sessions: " + course.getAvailable_terms());
                        System.out.println("Périodes: " + course.getAvailable_periods());
                        System.out.println("Prérequis et concomitants: " + course.getRequirement_text());
                    }
                    break;

                case "comparerCours":
                    System.out.println("Veuillez entrer les sigles des cours que vous souhaitez comparer, avec un espace entre chaque sigle, et les lettres en minuscule.");
                    List<String> courseIds = List.of(scanner.nextLine().split(" "));

                    ComparisonService.ComparisonResult comparaison = comparisonService.compareCourses(courseIds);
                    System.out.println("Crédits totaux: " + comparaison.totalCredits);
                    System.out.println("Heures de travail estimées par semaine: " + comparaison.estimatedWorkload);
                    System.out.println(" Recommandation: " + comparaison.recommendation);
                    break;

                case "verifierEligibilite":
                    System.out.println("Veuillez entrer le sigle du cours pour lequel vous souhaitez vérifier votre éligibilité.");
                    String coursId = scanner.nextLine();
                    System.out.println("Veuillez entrer les sigles des cours que vous avez complétés.");
                    List<String> coursSuivis = List.of(scanner.nextLine().split(" "));

                    EligibilityResult eligibility = eligibilityService.checkEligibility(coursId, coursSuivis);
                    System.out.println(eligibility.getMessage());
                    break;

                case "voirResultatsAcademiques":
                    System.out.println("Veuillez entrer un sigle de cours, avec les lettres en minuscule.");
                    String sigleAcad = scanner.nextLine().trim();

                    Optional<AcademicStats> optionalAcademicStats = academicService.getStatsBySigle(sigleAcad);

                    if (optionalAcademicStats.isPresent()) {
                        AcademicStats stats = optionalAcademicStats.get();
                        System.out.println("Moyenne: " + stats.getMoyenne());
                        System.out.println("Score: " + stats.getScore());
                        System.out.println("Participant.es: " + stats.getParticipants());
                        System.out.println("Trimestres: " + stats.getTrimestres());
                    }
                    break;


                default:
                    System.out.println("Commande inconnue.");


            }
        }

    }
}


