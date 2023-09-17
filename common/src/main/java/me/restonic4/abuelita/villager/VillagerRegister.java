package me.restonic4.abuelita.villager;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.restonic4.abuelita.Abuelita;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class VillagerRegister {
    private static final Map<String, ProfessionPoiType> POI_TYPES = new HashMap<>();
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(Abuelita.MOD_ID, Registries.VILLAGER_PROFESSION);
    public static final RegistrySupplier<VillagerProfession> ABUELITA = registerProfession("abuelita", () -> Blocks.BEDROCK, () -> SoundEvents.VILLAGER_WORK_CARTOGRAPHER);


    private static RegistrySupplier<VillagerProfession> registerProfession(String name, Supplier<Block> block, Supplier<SoundEvent> soundEvent) {
        POI_TYPES.put(name, new ProfessionPoiType(block, null));
        return PROFESSIONS.register(name, () -> {
            Predicate<Holder<PoiType>> poiPredicate = poiTypeHolder -> (POI_TYPES.get(name).poiType != null) && (poiTypeHolder.getClass() == POI_TYPES.get(name).poiType.getClass());
            return new VillagerProfession(name, poiPredicate, poiPredicate, ImmutableSet.of(), ImmutableSet.of(), soundEvent.get());
        });
    }
    
    private static class ProfessionPoiType {
        final Supplier<Block> block;
        Holder<PoiType> poiType;

        ProfessionPoiType(Supplier<Block> block, Holder<PoiType> poiType) {
            this.block = block;
            this.poiType = poiType;
        }
    }

    public static void register() {
        Abuelita.LOGGER.info("[" + Abuelita.MOD_ID + "] Registering villagers");

        PROFESSIONS.register();

        InteractionEvent.INTERACT_ENTITY.register((player, entity, hand) -> {
            if (entity.getType() == EntityType.VILLAGER) {
                use((Villager)entity);
            }

            return EventResult.pass();
        });

        Abuelita.LOGGER.info("[" + Abuelita.MOD_ID + "] Villagers registered");
    }

    public static InteractionResult use(Villager villager) {
        if (villager.getVillagerData().getProfession() == ABUELITA.get()) {
            addTrades(villager);
        }

        return InteractionResult.SUCCESS;
    }

    public static void addTrades(Villager villager) {
        //Offers
        ItemStack trade1S = new ItemStack(Items.EMERALD, 5);
        ItemStack trade1E = new ItemStack(Items.COOKIE, 64);
        MerchantOffer offer1 = new MerchantOffer(trade1S,trade1E,100,8,0.02F);

        ItemStack trade2S = new ItemStack(Items.COOKIE, 64);
        ItemStack trade2E = new ItemStack(Items.EMERALD, 5);
        MerchantOffer offer2 = new MerchantOffer(trade2S,trade2E,100,8,0.02F);

        ItemStack trade3S = new ItemStack(Items.EMERALD, 64);
        ItemStack trade3E = new ItemStack(Items.TOTEM_OF_UNDYING, 1);
        MerchantOffer offer3 = new MerchantOffer(trade3S,trade3E,100,8,0.02F);

        ItemStack trade4S = new ItemStack(Items.CAKE, 1);
        ItemStack trade4E = new ItemStack(Items.EMERALD, 5);
        MerchantOffer offer4 = new MerchantOffer(trade4S,trade4E,100,8,0.02F);

        ItemStack trade5S = new ItemStack(Items.AMETHYST_SHARD, 5);
        ItemStack trade5E = new ItemStack(Items.EMERALD, 1);
        MerchantOffer offer5 = new MerchantOffer(trade5S,trade5E,100,8,0.02F);

        ItemStack trade6S = new ItemStack(Items.DRAGON_BREATH, 1);
        ItemStack trade6E = new ItemStack(Items.EMERALD, 32);
        MerchantOffer offer6 = new MerchantOffer(trade6S,trade6E,100,8,0.02F);

        ItemStack trade7S = new ItemStack(Items.BOOK, 1);
        ItemStack trade7E = new ItemStack(Items.EXPERIENCE_BOTTLE, 64);
        MerchantOffer offer7 = new MerchantOffer(trade7S,trade7E,100,8,0.02F);

        ItemStack trade8S = new ItemStack(Items.EMERALD_BLOCK, 64);
        ItemStack trade8E = new ItemStack(Items.ALLAY_SPAWN_EGG, 1);
        MerchantOffer offer8 = new MerchantOffer(trade8S,trade8E,100,8,0.02F);

        ItemStack trade9S = new ItemStack(Items.EMERALD, 64);
        ItemStack trade9E = new ItemStack(Items.SNIFFER_EGG, 1);
        MerchantOffer offer9 = new MerchantOffer(trade9S,trade9E,100,8,0.02F);

        ItemStack trade10S = new ItemStack(Items.ALLAY_SPAWN_EGG, 5);
        ItemStack trade10E = new ItemStack(Items.END_PORTAL_FRAME, 1);
        MerchantOffer offer10 = new MerchantOffer(trade10S,trade10E,100,8,0.02F);

        //Offers set-up
        MerchantOffers offers = villager.getOffers();

        offers.clear();

        offers.add(offer1);
        offers.add(offer2);
        offers.add(offer3);
        offers.add(offer4);
        offers.add(offer5);
        offers.add(offer6);
        offers.add(offer7);
        offers.add(offer8);
        offers.add(offer9);
        offers.add(offer10);
    }
}
