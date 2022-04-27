package net.project.project.potion;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FlypotionPotion {
    @ObjectHolder("project:flypotion")
    public static final Potion potionType = null;

    @SubscribeEvent
    public static void registerPotion(RegistryEvent.Register<Potion> event) {
        event.getRegistry().register(new PotionCustom());
    }

    public static class PotionCustom extends Potion {
        public PotionCustom() {
            super(new EffectInstance(FlyeffectPotionEffect.potion, 3600, 0, false, true));
            setRegistryName("flypotion");
        }
    }
}