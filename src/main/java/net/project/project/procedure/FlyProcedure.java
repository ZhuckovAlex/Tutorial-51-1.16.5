package net.project.project.procedure;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.project.project.Project;
import net.project.project.potion.FlyeffectPotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FlyProcedure {
    @Mod.EventBusSubscriber
    private static class GlobalTrigger {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                Entity entity = event.player;
                World world = entity.world;
                double i = entity.getPosX();
                double j = entity.getPosY();
                double k = entity.getPosZ();
                Map<String, Object> dependencies = new HashMap<>();
                dependencies.put("x", i);
                dependencies.put("y", j);
                dependencies.put("z", k);
                dependencies.put("world", world);
                dependencies.put("entity", entity);
                dependencies.put("event", event);
                executeProcedure(dependencies);
            }
        }
    }

    public static void executeProcedure(Map<String, Object> dependencies) {
        if (dependencies.get("entity") == null) {
            if (!dependencies.containsKey("entity"))
                Project.LOGGER.warn("Failed to load dependency entity for procedure Fly!");
            return;
        }
        Entity entity = (Entity) dependencies.get("entity");
        if (!(new Object() {
            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayerEntity) {
                    return ((ServerPlayerEntity) _ent).interactionManager.getGameType() == GameType.SPECTATOR;
                } else if (_ent instanceof PlayerEntity && _ent.world.isRemote()) {
                    NetworkPlayerInfo _npi = Minecraft.getInstance().getConnection()
                            .getPlayerInfo(((AbstractClientPlayerEntity) _ent).getGameProfile().getId());
                    return _npi != null && _npi.getGameType() == GameType.SPECTATOR;
                }
                return false;
            }
        }.checkGamemode(entity)) && !(new Object() {
            public boolean checkGamemode(Entity _ent) {
                if (_ent instanceof ServerPlayerEntity) {
                    return ((ServerPlayerEntity) _ent).interactionManager.getGameType() == GameType.CREATIVE;
                } else if (_ent instanceof PlayerEntity && _ent.world.isRemote()) {
                    NetworkPlayerInfo _npi = Minecraft.getInstance().getConnection()
                            .getPlayerInfo(((AbstractClientPlayerEntity) _ent).getGameProfile().getId());
                    return _npi != null && _npi.getGameType() == GameType.CREATIVE;
                }
                return false;
            }
        }.checkGamemode(entity))) {
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity) entity).abilities.allowFlying = ((new Object() {
                    boolean check(Entity _entity) {
                        if (_entity instanceof LivingEntity) {
                            Collection<EffectInstance> effects = ((LivingEntity) _entity).getActivePotionEffects();
                            for (EffectInstance effect : effects) {
                                if (effect.getPotion() == FlyeffectPotionEffect.potion)
                                    return true;
                            }
                        }
                        return false;
                    }
                }.check(entity)) == true);
                ((PlayerEntity) entity).sendPlayerAbilities();
            }
        }
    }
}