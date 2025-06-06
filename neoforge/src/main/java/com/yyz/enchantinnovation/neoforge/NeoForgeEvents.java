package com.yyz.enchantinnovation.neoforge;

import com.yyz.enchantinnovation.EnchantmentUtils;
import com.yyz.enchantinnovation.EnchantInnovation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

@Mod.EventBusSubscriber(modid = EnchantInnovation.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NeoForgeEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        Item item = stack.getItem();
        if (item instanceof PickaxeItem || item instanceof AxeItem) {
            EnchantmentUtils.addExp(stack, 1);
        }
    }

    @SubscribeEvent
    public static void onXpPickup(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof SwordItem) {
            int xp = event.getOrb().value;
            EnchantmentUtils.addExp(stack, xp);
        }
    }

    @SubscribeEvent
    public static void onArrowHit(ProjectileImpactEvent event) {
        if (event.getProjectile() instanceof net.minecraft.world.entity.projectile.AbstractArrow arrow &&
                event.getRayTraceResult().getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
            if (arrow.getOwner() instanceof Player player) {
                ItemStack bow = player.getMainHandItem();
                if (bow.getItem() instanceof BowItem) {
                    EnchantmentUtils.addExp(bow, 10);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack stack = player.getUseItem();
            if (stack.getItem() instanceof ShieldItem) {
                EnchantmentUtils.addExp(stack, 10);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDamaged(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            int dmg = (int) event.getAmount();
            for (ItemStack armor : player.getArmorSlots()) {
                if (!armor.isEmpty()) {
                    EnchantmentUtils.addExp(armor, dmg);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onArmorBlock(ArmorHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            event.getArmorMap().forEach((slot, entry) -> {
                ItemStack armor = entry.armorItemStack;
                int blocked = Math.round(entry.originalDamage);
                if (!armor.isEmpty()) {
                    EnchantmentUtils.addExp(armor, blocked);
                }
            });
        }
    }
}
