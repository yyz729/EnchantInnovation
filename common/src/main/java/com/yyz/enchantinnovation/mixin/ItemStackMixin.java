package com.yyz.enchantinnovation.mixin;

import com.yyz.enchantinnovation.EnchantInnovationPlatform;
import com.yyz.enchantinnovation.EnchantmentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
        ItemStack stack = (ItemStack)(Object)this;
        ItemStack stack = (ItemStack)(Object)this;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void injectGetTooltip(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (!stack.isDamageableItem()) return;

        int exp = stack.getOrDefault(EnchantInnovationPlatform.getExp(), 0);
        int currentLevel = stack.getOrDefault(EnchantInnovationPlatform.getLevel(), EnchantmentUtils.calculateLevelFromExp(stack));
        int currentProgress = EnchantmentUtils.calculateLevelAndProgress(exp)[1];
        int requiredExp = EnchantmentUtils.getExpRequiredForNextLevel(currentLevel);
        String displayText = "Lv." + currentLevel + " (" + currentProgress + "/" + requiredExp + ")";
        cir.getReturnValue().add(1, Component.literal(displayText).withStyle(ChatFormatting.GRAY));
    }

    @Inject(method = "hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V", at = @At("RETURN"))
    private void injectDamage(int i, LivingEntity livingEntity, EquipmentSlot equipmentSlot, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        int exp = i;
        Item item = stack.getItem();

        if (item instanceof ArmorItem) {
            exp = i * 5;
        }

        EnchantmentUtils.addExp(stack, exp);
    }
}
