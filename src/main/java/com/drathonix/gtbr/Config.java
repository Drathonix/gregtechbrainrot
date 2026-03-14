package com.drathonix.gtbr;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean doExport = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        doExport = configuration.getBoolean(
            "export",
            Configuration.CATEGORY_GENERAL,
            doExport,
            "Exports all of the translations in the modpack to export.lang when the language is changed");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
