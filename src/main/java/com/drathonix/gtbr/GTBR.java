package com.drathonix.gtbr;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.FMLLaunchHandler;

@Mod(
    modid = GTBR.MODID,
    version = Tags.VERSION,
    name = "GregTech: Brainrot Horizons",
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*")
public class GTBR {

    public static final String MODID = "gregtechbrainrothorizons";
    public static final Logger LOG = LogManager.getLogger(MODID);

    // This generates the GregTech lang file from the one we have in resources.
    static {
        if (FMLLaunchHandler.side()
            .isClient()) {
            File gregTechLangFile = new File(CommonProxy.root, "GregTech_gt_BR.lang");
            if (!gregTechLangFile.exists()) {
                InputStream stream = GTBR.class.getResourceAsStream("/GregTech_gt_BR.lang");
                try {
                    Files.copy(stream, gregTechLangFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    stream.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @SidedProxy(clientSide = "com.drathonix.gtbr.ClientProxy", serverSide = "com.drathonix.gtbr.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
