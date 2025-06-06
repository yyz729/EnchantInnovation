# EnchantInnovation

This mod adds experience and leveling mechanics for items and books. It currently targets **NeoForge 21.5.66-beta** for Minecraft 1.21.5.

## Building

Ensure you have JDK 21 installed. To build the NeoForge version run:

```bash
./gradlew :neoforge:build
```

Gradle will download NeoForge from `https://maven.neoforged.net/releases` on the first run.

## Features

- Items track experience (`exp`), current level (`level`) and XP required for the next level (`xp_next`).
- Tools gain XP for durability loss or breaking blocks.
- Weapons gain XP from durability loss and other actions, such as collecting XP or hitting entities.
- Armor and shields award XP for blocking or taking damage.
- Books can also store XP and levels.

