package com.yyz.enchantinnovation.mixin;

import com.yyz.enchantinnovation.EnchantInnovationPlatform;
import com.yyz.enchantinnovation.EnchantmentUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin extends AbstractContainerScreen<EnchantmentMenu> {


    public EnchantmentScreenMixin(EnchantmentMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Redirect(
            method = "renderBg",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/player/LocalPlayer;experienceLevel:I",
                    opcode = Opcodes.GETFIELD
            )
    )
    private int injected(LocalPlayer instance) {
        ItemStack itemStack = ((EnchantmentMenu)this.menu).getSlot(0).getItem();
        return itemStack.getOrDefault(EnchantInnovationPlatform.getLevel(), EnchantmentUtils.calculateLevelFromExp(itemStack));
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/player/LocalPlayer;experienceLevel:I",
                    opcode = Opcodes.GETFIELD
            )
    )
    private int injectRender(LocalPlayer instance) {
        ItemStack itemStack = ((EnchantmentMenu)this.menu).getSlot(0).getItem();
        return itemStack.getOrDefault(EnchantInnovationPlatform.getLevel(), EnchantmentUtils.calculateLevelFromExp(itemStack));
    }
}
