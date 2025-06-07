package com.yyz.enchantinnovation.fabric;

import com.mojang.serialization.Codec;
import com.yyz.enchantinnovation.EnchantInnovation;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public final class EnchantinnovationFabric implements ModInitializer {
    public static final DataComponentType<Integer> EXP_COMPONENT_TYPE = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            ResourceLocation.fromNamespaceAndPath(EnchantInnovation.MOD_ID, "exp"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build()
    );
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        EnchantInnovation.init();
    }


}
