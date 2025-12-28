package com.diro.ift2255.cli;

import com.diro.ift2255.serveur.DemarreurServeur;
import io.javalin.Javalin;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
            }
        }

    }
}
