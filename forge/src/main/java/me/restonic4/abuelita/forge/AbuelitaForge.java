package me.restonic4.abuelita.forge;

import dev.architectury.platform.forge.EventBuses;
import me.restonic4.abuelita.Abuelita;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Abuelita.MOD_ID)
public class AbuelitaForge {
    public AbuelitaForge() {
        EventBuses.registerModEventBus(Abuelita.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Abuelita.init();
    }
}