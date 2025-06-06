package com.yyz.enchantinnovation;


import net.minecraft.world.item.ItemStack;

public class EnchantmentUtils {


    public static int calculateLevelFromExp(ItemStack stack) {
        int exp = stack.getOrDefault(EnchantInnovationPlatform.getExp(),0);
        int[] levelInfo = EnchantmentUtils.calculateLevelAndProgress(exp);
        return levelInfo[0];
    }
    public static int addExp(ItemStack stack, int value) {
        // Считаем предыдущий опыт
        int currentExp = stack.getOrDefault(EnchantInnovationPlatform.getExp(), 0);
        // Сколько опыта добавляем
        int addedXp = value;
        // Записываем новый опыт
        int newExp = currentExp + addedXp;
        stack.set(EnchantInnovationPlatform.getExp(), newExp);

        // Пересчитываем уровень и прогресс
        int[] info = calculateLevelAndProgress(newExp);
        int level = info[0];
        int progress = info[1];

        stack.set(EnchantInnovationPlatform.getLevel(), level);
        stack.set(EnchantInnovationPlatform.getXpNext(), getExpRequiredForNextLevel(level) - progress);

        // Возвращаем фактически добавленное число XP
        return addedXp;
    }


    // 获取升级到下一级所需的经验值
    public static int getExpRequiredForNextLevel(int currentLevel) {
        if (currentLevel < 16) {
            return 2 * currentLevel + 7;
        } else if (currentLevel < 31) {
            return 5 * currentLevel - 38;
        } else {
            return 9 * currentLevel - 158;
        }
    }

    // 计算总经验对应的等级和剩余经验
    public static int[] calculateLevelAndProgress(int totalExp) {
        int level = 0;
        int remainingExp = totalExp;
        while (true) {
            int expToNext = getExpRequiredForNextLevel(level);
            if (remainingExp < expToNext) break;
            remainingExp -= expToNext;
            level++;
        }
        return new int[]{level, remainingExp};
    }

    // 计算达到目标等级所需的总经验
    public static int getTotalExpForLevel(int targetLevel) {
        int total = 0;
        for (int l = 0; l < targetLevel; l++) {
            total += getExpRequiredForNextLevel(l);
        }
        return total;
    }
}
