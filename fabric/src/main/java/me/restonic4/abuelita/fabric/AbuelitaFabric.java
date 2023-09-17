package me.restonic4.abuelita.fabric;

import me.restonic4.abuelita.Abuelita;
import net.fabricmc.api.ModInitializer;

public class AbuelitaFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Abuelita.init();
    }
}