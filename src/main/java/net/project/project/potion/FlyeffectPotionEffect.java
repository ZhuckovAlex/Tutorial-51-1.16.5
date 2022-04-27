package net.project.project.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.project.project.procedure.FlyProcedure;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FlyeffectPotionEffect {
    @ObjectHolder("project:flyeffect")
    public static final Effect potion = null;

    @SubscribeEvent
    public static void registerEffect(RegistryEvent.Register<Effect> event) {
        event.getRegistry().register(new EffectCustom());
    }

    public static class EffectCustom extends Effect {
        public EffectCustom() {
            super(EffectType.NEUTRAL, -14080);
            setRegistryName("flyeffect");
        }

        @Override
        public String getName() {
            return "effect.flyeffect";
        }

        @Override
        public boolean isBeneficial() {
            return false;
        }

        @Override
        public boolean isInstant() {
            return false;
        }

        @Override
        public boolean shouldRenderInvText(EffectInstance effect) {
            return true;
        }

        @Override
        public boolean shouldRender(EffectInstance effect) {
            return true;
        }

        @Override
        public boolean shouldRenderHUD(EffectInstance effect) {
            return true;
        }

        @Override
        public void applyAttributesModifiersToEntity(LivingEntity entity, AttributeModifierManager attributeMapIn, int amplifier) {
            World world = entity.world;
            double x = entity.getPosX();
            double y = entity.getPosY();
            double z = entity.getPosZ();

            FlyProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("entity", entity)).collect(HashMap::new,
                    (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
        }

        @Override
        public void performEffect(LivingEntity entity, int amplifier) {
            World world = entity.world;
            double x = entity.getPosX();
            double y = entity.getPosY();
            double z = entity.getPosZ();

            FlyProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("entity", entity)).collect(HashMap::new,
                    (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
        }

        @Override
        public void removeAttributesModifiersFromEntity(LivingEntity entity, AttributeModifierManager attributeMapIn, int amplifier) {
            super.removeAttributesModifiersFromEntity(entity, attributeMapIn, amplifier);
            World world = entity.world;
            double x = entity.getPosX();
            double y = entity.getPosY();
            double z = entity.getPosZ();

            FlyProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("entity", entity)).collect(HashMap::new,
                    (_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
        }

        @Override
        public boolean isReady(int duration, int amplifier) {
            return true;
        }
    }
}