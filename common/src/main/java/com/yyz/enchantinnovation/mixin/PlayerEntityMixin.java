package com.yyz.enchantinnovation.mixin;

import com.yyz.enchantinnovation.EnchantInnovationPlatform;
import com.yyz.enchantinnovation.EnchantmentUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    @Shadow protected int enchantmentSeed;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }


    @Inject(method = "onEnchantmentPerformed", at = @At("HEAD"), cancellable = true)
    private void injectApplyEnchantmentCosts(ItemStack itemStack, int i, CallbackInfo ci) {
        ci.cancel();
        this.enchantmentSeed = this.random.nextInt();


        int currentExp = itemStack.getOrDefault(EnchantInnovationPlatform.getExp(),0);

        int currentLevel = itemStack.getOrDefault(EnchantInnovationPlatform.getLevel(), EnchantmentUtils.calculateLevelAndProgress(currentExp)[0]);

        // 计算目标等级（不能低于0）
        int targetLevel = Math.max(currentLevel - i, 0);

        // 计算需要扣除的经验值
        int expCost = EnchantmentUtils.getTotalExpForLevel(currentLevel)
                - EnchantmentUtils.getTotalExpForLevel(targetLevel);

        // 执行扣除并确保不为负
        int newExp = Math.max(currentExp - expCost, 0);
        itemStack.set(EnchantInnovationPlatform.getExp(),newExp);

        int[] info = EnchantmentUtils.calculateLevelAndProgress(newExp);
        int newLevel = info[0];
        int progress = info[1];
        itemStack.set(EnchantInnovationPlatform.getLevel(), newLevel);
        itemStack.set(EnchantInnovationPlatform.getXpNext(), EnchantmentUtils.getExpRequiredForNextLevel(newLevel) - progress);
    }

}