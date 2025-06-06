package com.yyz.enchantinnovation;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.component.DataComponentType;

public class EnchantInnovationPlatform {

    @ExpectPlatform
    public static DataComponentType<Integer> getExp() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static DataComponentType<Integer> getLevel() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static DataComponentType<Integer> getXpNext() {
        throw new AssertionError();
    }

}
