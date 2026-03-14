package com.drathonix.gtbr.mixin;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.Locale;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.drathonix.gtbr.Config;
import com.drathonix.gtbr.GTBR;
import com.drathonix.gtbr.bridge.IMixinLocale;

@Mixin(LanguageManager.class)
public class MixinLanguageManager {

    @Shadow
    @Final
    protected static Locale currentLocale;

    @Inject(method = "onResourceManagerReload", at = @At("TAIL"))
    public void intercept(IResourceManager p_110549_1_, CallbackInfo ci) {
        if (Config.doExport) {
            GTBR.LOG.info("Exporting Locales to export.lang!");
            ((IMixinLocale) currentLocale).gtbr$export();
        }
    }
}
