package com.yyz.enchantinnovation.neoforge;

import net.minecraft.core.component.DataComponentType;

public class EnchantInnovationPlatformImpl {


    public static DataComponentType<Integer> getExp() {
        return EnchantInnovationNeoForge.EXP_COMPONENT_TYPE.get();
    }

    public static DataComponentType<Integer> getLevel() {
        return EnchantInnovationNeoForge.LEVEL_COMPONENT_TYPE.get();
    }

    public static DataComponentType<Integer> getXpNext() {
        return EnchantInnovationNeoForge.XP_NEXT_COMPONENT_TYPE.get();
    }
}
