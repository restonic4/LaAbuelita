package me.restonic4.abuelita.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.restonic4.abuelita.Abuelita;
import me.restonic4.abuelita.creative_tab.CreativeTabRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

@SuppressWarnings("UnstableApiUsage")
public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Abuelita.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> COOKIE_WAND =
            ITEMS.register(
                    "cookie_wand",
                    () -> new Item(new Item.Properties().arch$tab(CreativeTabRegister.ABUELITA_TAB))
            );

    public static void register() {
        ITEMS.register();
    }
}
