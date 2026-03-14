package com.drathonix.gtbr;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;

import com.github.bsideup.jabel.Desugar;

/**
 * Automatically replaces specific tokens with brainrot
 */
public class Brainrottify {

    public static List<BiFunction<String, String, String>> transformers = new ArrayList<>();

    public static void main(String[] args) {
        transformers.add(new Replacement("toilet", "Skibidi Toilet"));
        transformers.add(new Replacement("tungsten", "Tung Tung Tungsten Sahur"));
        transformers.add(new Replacement("combustion", "Goofy Ah Hawk Tuah"));
        transformers.add(new Replacement("gas", "Yung Vape"));
        transformers.add(new Replacement("steam", "Unc Vape"));
        transformers.add(new Replacement("turbine", "Smoker"));
        transformers.add(new Replacement("generator", "Exploder"));
        transformers.add(new Replacement("field", "Grass"));
        transformers.add(new Replacement("salty", "Malding"));
        transformers.add(new Replacement("salt", "Mald"));
        transformers.add(new Replacement("heart", "Rizz"));
        transformers.add(new Replacement("chrome", "Clankium"));
        transformers.add(new Replacement("tpv-alloy", "Pink Lean"));
        transformers.add(new Replacement("hot", "Rizzed"));
        transformers.add(new Replacement("fluid", "Slop"));
        transformers.add(new Replacement("pipe", "Tube"));
        transformers.add(new Replacement("melon", "Cocomelon"));
        transformers.add(new Replacement("iron", "Gyatt Dog"));
        transformers.add(new Replacement("gold", "Stacked Skibidi Rizz"));
        transformers.add(new Replacement("extreme", "Maxed"));
        transformers.add(new Replacement("hss", "Huzz Sigma Skibidi"));
        transformers.add(new Replacement("-g", "-Green"));
        transformers.add(new Replacement("-s", "-Sussy"));
        transformers.add(new Replacement("-e", "-Erm"));
        transformers.add(new Replacement("diamond", "Ultrainstinct Goku"));
        transformers.add(new Replacement("black", "Black-Pilled"));
        transformers.add(new Replacement("carbon", "Goku Black"));
        transformers.add(new Replacement("carbide", "igmoid"));
        transformers.add(new Replacement("incoloy", "Generic"));
        transformers.add(new Replacement("potin", "Put In"));
        transformers.add(new Replacement("electrotine", "Crystal Meth"));
        transformers.add(new Replacement("stargate", "Bussin' Portal"));
        transformers.add(new Replacement("camera", "Palantir"));
        transformers.add(new Replacement("blast", "Boom"));
        transformers.add(new Replacement("pvc", "Flexible Skibidi"));
        transformers.add(
            new Replacement(
                "giga chad token",
                "Giga Sigma Skibidi Hawk Tuah Rizz God Essence of the Skibidi Goated Hawk Tuah Sigma Lord"));

        brainrot("langfiles/GregTech.lang", "src/main/resources/GregTech_gt_BR.lang");
        brainrot("langfiles/export.lang", "src/main/resources/assets/gtbr/lang/gt_BR.lang");
    }

    /**
     * Takes a language file and changes tokens to brainrot.
     *
     * @param target      the original file
     * @param destination the new file
     */
    private static void brainrot(String target, String destination) {
        Map<String, String> translations = new HashMap<>();

        boolean isGTLangFile = false;

        try {
            Scanner scan = new Scanner(new FileReader(target));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (!isGTLangFile && line.equals("languagefile {")) {
                    isGTLangFile = true;
                    translations.clear();
                }
                if (isGTLangFile && line.equals("}")) {
                    break;
                }
                String[] keyValue = line.split("=");
                translations.put(keyValue[0], keyValue.length > 1 ? keyValue[1] : "");
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (String key : translations.keySet()) {
            String value = translations.get(key);
            for (BiFunction<String, String, String> transformer : transformers) {
                value = transformer.apply(key, value);
            }
            translations.put(key, value);
        }
        try {
            FileWriter writer = new FileWriter(destination);
            if (isGTLangFile) {
                writer.append("# Configuration file");
                writer.append("\n");
                writer.append("\nlanguagefile {\n");
            }
            boolean finalIsGTLangFile = isGTLangFile;
            translations.forEach((key, value) -> {
                try {
                    writer.append(key)
                        .append("=")
                        .append(value)
                        .append("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (isGTLangFile) {
                writer.append("}");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Desugar
    private record Replacement(String target, String result) implements BiFunction<String, String, String> {

        @Override
        public String apply(String key, String value) {
            String lwr = value.toLowerCase();
            int idx = lwr.indexOf(target);
            if (idx > -1) {
                value = value.substring(0, idx) + caseMatch(value, result, idx)
                    + value.substring(idx + target.length());
            }
            return value;
        }

        public String caseMatch(String original, String replacement, int idx) {
            if (Character.isLowerCase(original.charAt(idx))) {
                return Character.toLowerCase(replacement.charAt(0))
                    + (replacement.length() > 1 ? replacement.substring(1) : "");
            }
            return replacement;
        }
    }

    /*
     * private static class T1 implements BiFunction<String,String,String>{
     * public List<String> targets;
     * public List<String> skipConditions = new ArrayList<>();
     * public T1(String... targets) {
     * this.targets = Arrays.asList(targets);
     * }
     * public T1 skip(String... skipConditions) {
     * this.skipConditions.addAll(Arrays.asList(skipConditions));
     * return this;
     * }
     * public static T1 targets(String... targets) {
     * return new T1(targets);
     * }
     * @Override
     * public String apply(String key, String value) {
     * return value;
     * }
     * }
     */
}
