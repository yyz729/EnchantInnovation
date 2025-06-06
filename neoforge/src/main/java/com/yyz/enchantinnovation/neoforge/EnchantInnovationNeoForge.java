package com.yyz.enchantinnovation.neoforge;

import com.mojang.serialization.Codec;
import com.yyz.enchantinnovation.EnchantInnovation;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(EnchantInnovation.MOD_ID)
public final class EnchantInnovationNeoForge {
    public static final DeferredRegister.DataComponents DATA_COMPONENT = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,EnchantInnovation.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> EXP_COMPONENT_TYPE = DATA_COMPONENT.registerComponentType(
            "exp",
            builder -> builder
                    .persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> LEVEL_COMPONENT_TYPE = DATA_COMPONENT.registerComponentType(
            "level",
            builder -> builder
                    .persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> XP_NEXT_COMPONENT_TYPE = DATA_COMPONENT.registerComponentType(
            "xp_next",
            builder -> builder
                    .persistent(Codec.INT)
    );
    public EnchantInnovationNeoForge(IEventBus modEventBus) {

        EnchantInnovation.init();
        DATA_COMPONENT.register(modEventBus);
    }

}
