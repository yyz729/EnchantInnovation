package com.yyz.enchantinnovation.mixin;


import com.yyz.enchantinnovation.EnchantInnovationPlatform;
import com.yyz.enchantinnovation.EnchantmentUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentMenu.class)
public class EnchantmentScreenHandlerMixin {


    @Shadow @Final private Container enchantSlots;

    @Redirect(
            method = "clickMenuButton",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Player;experienceLevel:I",
                    opcode = Opcodes.GETFIELD
            )
    )
    private int injected(Player instance) {
        ItemStack itemStack = this.enchantSlots.getItem(0);
        return itemStack.getOrDefault(EnchantInnovationPlatform.getLevel(), EnchantmentUtils.calculateLevelFromExp(itemStack));
    }


}