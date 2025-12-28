package com.diro.ift2255;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.serveur.DemarreurServeur;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.util.*;
import io.javalin.Javalin;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HttpClientApi clientApi = new HttpClientApi();
        CourseService courseService = new CourseService(clientApi);

        System.out.println(System.lineSeparator() + "Bienvenue à ChoixCours: un logiciel vous permettant d'éclairer vos choix de cours universitaires." + System.lineSeparator());
        System.out.println("Commandes disponibles:");
        System.out.println(" - quitter");
        System.out.println(" - demarrerServeur");

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
                    System.out.println("Veuillez entrer un sigle de cours.");
                    String sigle = scanner.nextLine().trim();

                    Optional<Course> optionalCourse = courseService.getCourseById(sigle);

                    if (optionalCourse.isPresent()) {
                        Course course = optionalCourse.get();
                        System.out.println("Cours trouvé: " + course.getName() + " (" + course.getId() + ")");
                        System.out.println("Description: " + course.getDescription());
                    }


            }
        }

    }
}
