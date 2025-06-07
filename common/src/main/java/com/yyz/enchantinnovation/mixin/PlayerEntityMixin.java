package com.yyz.enchantinnovation.mixin;

import com.yyz.enchantinnovation.EnchantmentUtils;
import net.minecraft.nbt.CompoundTag;
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
        if(!itemStack.isDamageableItem()) return;

        ci.cancel();
        this.enchantmentSeed = this.random.nextInt();

        CompoundTag nbt = itemStack.getOrCreateTag();
        int currentExp = nbt.getInt("exp");

        // 使用工具类计算当前等级
        int currentLevel = EnchantmentUtils.calculateLevelAndProgress(currentExp)[0];

        // 计算目标等级（不能低于0）
        int targetLevel = Math.max(currentLevel - i, 0);

        // 计算需要扣除的经验值
        int expCost = EnchantmentUtils.getTotalExpForLevel(currentLevel)
                - EnchantmentUtils.getTotalExpForLevel(targetLevel);

        // 执行扣除并确保不为负
        int newExp = Math.max(currentExp - expCost, 0);
        nbt.putInt("exp", newExp);
    }

}