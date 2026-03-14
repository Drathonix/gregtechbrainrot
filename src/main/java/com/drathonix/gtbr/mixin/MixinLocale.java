package com.drathonix.gtbr.mixin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import net.minecraft.client.resources.Locale;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.drathonix.gtbr.CommonProxy;
import com.drathonix.gtbr.bridge.IMixinLocale;

@Mixin(Locale.class)
public class MixinLocale implements IMixinLocale {

    @Shadow
    private Map<String, String> field_135032_a;

    @Override
    public void gtbr$export() {
        try {
            FileWriter writer = new FileWriter(new File(CommonProxy.root, "export.lang"));
            field_135032_a.forEach((k, v) -> {
                try {
                    writer.append(k)
                        .append("=")
                        .append(v)
                        .append("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
