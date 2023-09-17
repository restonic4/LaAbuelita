package me.restonic4.abuelita.event;

import com.mojang.datafixers.util.Pair;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.registries.DeferredRegister;
import me.restonic4.abuelita.Abuelita;
import me.restonic4.abuelita.item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.atomic.AtomicReference;

public class RightClickItem {
    public static void registerEvent() {
        AtomicReference<Double> finalFixedX = new AtomicReference<>((double) 0);
        AtomicReference<Double> finalFixedY = new AtomicReference<>((double) 0);
        AtomicReference<Double> finalFixedZ = new AtomicReference<>((double) 0);
        AtomicReference<ServerLevel> levelStored = new AtomicReference<>((ServerLevel) null);
        InteractionEvent.RIGHT_CLICK_ITEM.register((player, hand) -> {
            if (hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).is(ItemRegister.COOKIE_WAND.get()) && player.getOffhandItem().is(Items.COOKIE)) {
                player.getOffhandItem().setCount(player.getOffhandItem().getCount() - 1);

                int x = 0;
                int y = 0;
                int z = 0;

                boolean found = false;

                try {
                    ServerLevel level = player.getServer().overworld();
                    levelStored.set(level);

                    DeferredRegister<Structure> StructureRegister = DeferredRegister.create(Abuelita.MOD_ID, Registries.STRUCTURE);

                    Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
                    Structure house = registry.get(new ResourceLocation("abuelita:abuelita_house"));

                    ResourceKey<Structure> Key = registry.wrapAsHolder(house).unwrapKey().get();

                    HolderSet<Structure> holderSet = registry.getHolder(Key).map(HolderSet::direct).orElseThrow();

                    BlockPos pos = player.blockPosition();
                    Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator().findNearestMapStructure(level, holderSet, pos, 100, false); // This always returns null
                    BlockPos structurepos = pair.getFirst();
                    int strx = structurepos.getX();
                    int stry = structurepos.getY();
                    int strz = structurepos.getZ();

                    found = true;

                    x = strx;
                    y = stry;
                    z = strz;

                    //player.displayClientMessage(Component.literal("Location " + Integer.toString(strx) + ", " + Integer.toString(stry) + ", " + Integer.toString(strz) + "!"), (false));
                } catch (Exception exception) {
                    MinecraftServer server = player.getServer();

                    if (server != null) {
                        Abuelita.LOGGER.info("[" + Abuelita.MOD_ID + "] Error location the structure: " + exception.getMessage());
                        player.displayClientMessage(Component.literal("The structure could not be found"),false);
                    }
                }

                //player.teleportTo(x, y, z);

                //double calculatedX= 20 * (double) (-Math.sin(player.getYRot()/ 180.0F * (float) Math.PI)* Math.cos(player.getXRot() / 180.0F* (float) Math.PI) * 0.4f);
                //double calculatedZ = 20 * (double) (Math.cos(player.getYRot()	/ 180.0F * (float) Math.PI)* Math.cos(player.getXRot() / 180.0F* (float) Math.PI) * 0.4f);
                //double calculatedY = 20 * (double) (-Math.sin((player.getXRot())/ 180.0F * (float) Math.PI) * 0.4f);

                double velocity = 10;

                //Vector posicion final
                Vec3 finalPos = new Vec3(x, y, z);

                //Vector posicion del jugador
                Vec3 startPos = new Vec3(player.getX(), player.getY(), player.getZ());

                //Resta entre el vector final (x, y, z) y el vector inicial
                Vec3 direction = new Vec3(finalPos.x - startPos.x, finalPos.y - startPos.y, finalPos.z - startPos.z);

                // Calcular la magnitud (longitud) actual del vector
                double calcs = Math.sqrt(finalPos.x*finalPos.x + finalPos.y*finalPos.y + finalPos.z*finalPos.z);

                // Calcular el vector unitario
                Vec3 unit = new Vec3(direction.x / calcs, direction.y / calcs, direction.z / calcs);

                // Escalar el vector unitario a la longitud deseada (velocity)
                Vec3 forceVector = new Vec3(unit.x * velocity, unit.y * velocity, unit.z * velocity);

                Abuelita.LOGGER.info("[" + Abuelita.MOD_ID + "] UNIT VECTOR FOR STRUCTURE = " + forceVector.x + ", " + forceVector.y + ", " + forceVector.z);

                boolean isXINF = (unit.x >= Double.POSITIVE_INFINITY || unit.x <= Double.NEGATIVE_INFINITY);
                boolean isYINF = (unit.y >= Double.POSITIVE_INFINITY || unit.y <= Double.NEGATIVE_INFINITY);
                boolean isZINF = (unit.z >= Double.POSITIVE_INFINITY || unit.z <= Double.NEGATIVE_INFINITY);

                boolean canLaunch = false;

                try {
                    if (!isXINF && !isYINF && !isZINF) {
                        Abuelita.LOGGER.info("[" + Abuelita.MOD_ID + "] VELOCITY VECTOR FOR STRUCTURE = " + forceVector.x + ", " + forceVector.y + ", " + forceVector.z);

                        canLaunch = true;
                    }
                    else {//force error
                        throw new IllegalArgumentException("The vector cant be infinite");
                    }
                }
                catch (Exception exception) {
                    Abuelita.LOGGER.info("[" + Abuelita.MOD_ID + "] VELOCITY VECTOR FOR STRUCTURE CANCELED = INFINITE");
                }

                if (canLaunch) {
                    Abuelita.LOGGER.info("[" + Abuelita.MOD_ID + "] CAN LAUNCH PLAYER");

                    finalFixedX.set(forceVector.x);
                    finalFixedY.set(velocity/4);
                    finalFixedZ.set(forceVector.z);
                }
            }

            if (hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).is(ItemRegister.COOKIE_WAND.get()) && player.getOffhandItem().is(Items.COOKIE)) {
                Vec3 vector = new Vec3(finalFixedX.get(), finalFixedY.get(), finalFixedZ.get());

                player.setDeltaMovement(finalFixedX.get(), finalFixedY.get(), finalFixedZ.get());

                player.getOffhandItem().releaseUsing(levelStored.get(), player, 0);
            }

            return CompoundEventResult.pass();
        });
    }
}
