package com.diro.ift2255.service;

import com.diro.ift2255.model.AcademicStats;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AcademicService {
    private final Map<String, AcademicStats> statsBySigle = new HashMap<>();

    public AcademicService() {
        loadCsv();
    }

    private void loadCsv() {
        // Path relative to repository root
        Path csv = Path.of(System.getProperty("user.dir"), "rest-api", "src", "main", "ressources", "historique_cours_prog_117510.csv");
        if (!Files.exists(csv)) {
            System.err.println("Academic CSV introuvable: " + csv);
            return;
        }

        try (BufferedReader br = Files.newBufferedReader(csv)) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; if (line.toLowerCase().contains("sigle")) continue; }
                // split into up to 6 parts (sigle, nom, moyenne, score, participants, trimestres)
                String[] parts = line.split(",", 6);
                if (parts.length < 6) continue;
                String sigle = parts[0].trim();
                String nom = parts[1].trim();
                String moyenne = parts[2].trim();
                int score = parseIntSafe(parts[3].trim());
                int participants = parseIntSafe(parts[4].trim());
                int trimestres = parseIntSafe(parts[5].trim());

                AcademicStats s = new AcademicStats(sigle, nom, moyenne, score, participants, trimestres);
                statsBySigle.put(sigle.toLowerCase(), s);
            }
        } catch (IOException e) {
            System.err.println("Erreur lecture CSV académique: " + e.getMessage());
        }
    }

    private int parseIntSafe(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }

    public Optional<AcademicStats> getStatsBySigle(String sigle) {
        if (sigle == null) return Optional.empty();
        return Optional.ofNullable(statsBySigle.get(sigle.toLowerCase()));
    }
}
