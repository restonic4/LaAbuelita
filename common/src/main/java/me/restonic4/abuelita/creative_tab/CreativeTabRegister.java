package me.restonic4.abuelita.creative_tab;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.restonic4.abuelita.Abuelita;
import me.restonic4.abuelita.item.ItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeTabRegister {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Abuelita.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> ABUELITA_TAB =
            TABS.register(
                    "abuelita_tab",
                    () -> CreativeTabRegistry.create(
                            Component.nullToEmpty("Abuelita"),
                            () -> new ItemStack(ItemRegister.COOKIE_WAND.get())
                    )
            );

    public static void register() {
        TABS.register();
    }
}
