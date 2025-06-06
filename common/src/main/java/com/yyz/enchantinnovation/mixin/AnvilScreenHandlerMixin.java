package com.yyz.enchantinnovation.mixin;

import com.yyz.enchantinnovation.EnchantInnovationPlatform;
import com.yyz.enchantinnovation.EnchantmentUtils;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {


    @Shadow @Final private DataSlot cost;

    public AnvilScreenHandlerMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
        super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
    }


    @Redirect(
            method = "mayPickup",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Player;experienceLevel:I",
                    opcode = Opcodes.GETFIELD
            )
    )
    private int injected(Player instance) {
        ItemStack itemStack = this.inputSlots.getItem(0);
        return itemStack.getOrDefault(EnchantInnovationPlatform.getLevel(), EnchantmentUtils.calculateLevelFromExp(itemStack));
    }

    @Redirect(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I"))
    private int injectOnTakeOutput(DataSlot instance) {
        return 0;
    }

    @ModifyArg(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V",ordinal = 3))
    private ItemStack injectUpdateResult(ItemStack itemStack) {

        ItemStack stack = itemStack.copy();

        int currentExp = stack.getOrDefault(EnchantInnovationPlatform.getExp(),0);

        int currentLevel = stack.getOrDefault(EnchantInnovationPlatform.getLevel(), EnchantmentUtils.calculateLevelAndProgress(currentExp)[0]);

        // 计算目标等级（不能低于0）
        int targetLevel = Math.max(currentLevel - this.cost.get(), 0);

        // 计算需要扣除的经验值
        int expCost = EnchantmentUtils.getTotalExpForLevel(currentLevel)
                - EnchantmentUtils.getTotalExpForLevel(targetLevel);

        // 执行扣除并确保不为负
        int newExp = Math.max(currentExp - expCost, 0);
        stack.set(EnchantInnovationPlatform.getExp(),newExp);

        int[] info = EnchantmentUtils.calculateLevelAndProgress(newExp);
        int newLevel = info[0];
        int progress = info[1];
        stack.set(EnchantInnovationPlatform.getLevel(), newLevel);
        stack.set(EnchantInnovationPlatform.getXpNext(), EnchantmentUtils.getExpRequiredForNextLevel(newLevel) - progress);
        return stack;
    }
}
