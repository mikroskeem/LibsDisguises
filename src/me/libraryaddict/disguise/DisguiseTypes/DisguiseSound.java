package me.libraryaddict.disguise.DisguiseTypes;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_6_R2.CraftSound;

public enum DisguiseSound {

    BAT(Sound.BAT_HURT, null, Sound.BAT_DEATH, Sound.BAT_IDLE, Sound.BAT_LOOP, Sound.BAT_TAKEOFF),

    BLAZE(Sound.BLAZE_HIT, null, Sound.BLAZE_DEATH, Sound.BLAZE_BREATH),

    CAVE_SPIDER(Sound.SPIDER_IDLE, Sound.SPIDER_WALK, Sound.SPIDER_DEATH, Sound.SPIDER_IDLE),

    CHICKEN(Sound.CHICKEN_HURT, Sound.CHICKEN_WALK, Sound.CHICKEN_HURT, Sound.CHICKEN_IDLE, Sound.CHICKEN_EGG_POP),

    COW(Sound.COW_HURT, Sound.COW_WALK, Sound.COW_HURT, Sound.COW_IDLE),

    CREEPER(Sound.CREEPER_HISS, Sound.STEP_GRASS, Sound.CREEPER_DEATH, null),

    DONKEY("mob.horse.donkey.hit", Sound.STEP_GRASS, "mob.horse.donkey.death", "mob.horse.donkey.idle"),

    ENDER_DRAGON(Sound.ENDERDRAGON_HIT, null, Sound.ENDERDRAGON_DEATH, Sound.ENDERDRAGON_GROWL, Sound.ENDERDRAGON_WINGS),

    ENDERMAN(Sound.ENDERMAN_HIT, Sound.STEP_GRASS, Sound.ENDERMAN_DEATH, Sound.ENDERMAN_IDLE, Sound.ENDERMAN_STARE,
            Sound.ENDERMAN_TELEPORT, Sound.ENDERMAN_SCREAM),

    GHAST(Sound.GHAST_SCREAM, null, Sound.GHAST_DEATH, Sound.GHAST_MOAN, Sound.GHAST_CHARGE, Sound.GHAST_FIREBALL,
            Sound.GHAST_SCREAM2),

    GIANT(Sound.HURT_FLESH, Sound.STEP_GRASS, null, null),

    HORSE("mob.horse.hit", Sound.STEP_GRASS, "mob.horse.death", "mob.horse.idle"),

    IRON_GOLEM(Sound.IRONGOLEM_HIT, Sound.IRONGOLEM_WALK, Sound.IRONGOLEM_DEATH, Sound.IRONGOLEM_THROW),

    MAGMA_CUBE(Sound.SLIME_ATTACK, Sound.SLIME_WALK2, null, null, Sound.SLIME_WALK),

    MULE("mob.horse.donkey.hit", Sound.STEP_GRASS, "mob.horse.donkey.death", "mob.horse.donkey.idle"),

    MUSHROOM_COW(Sound.COW_HURT, Sound.COW_WALK, Sound.COW_HURT, Sound.COW_IDLE),

    OCELOT(Sound.CAT_HIT, Sound.STEP_GRASS, Sound.CAT_HIT, Sound.CAT_MEOW, Sound.CAT_PURR, Sound.CAT_PURREOW),

    PIG(Sound.PIG_IDLE, Sound.PIG_WALK, Sound.PIG_DEATH, Sound.PIG_IDLE),

    PIG_ZOMBIE(Sound.ZOMBIE_PIG_HURT, null, Sound.ZOMBIE_PIG_DEATH, Sound.ZOMBIE_PIG_IDLE, Sound.ZOMBIE_PIG_ANGRY),

    PLAYER(Sound.HURT_FLESH, Sound.STEP_GRASS),

    SHEEP(Sound.SHEEP_IDLE, Sound.SHEEP_WALK, null, Sound.SHEEP_IDLE, Sound.SHEEP_SHEAR),

    SILVERFISH(Sound.SILVERFISH_HIT, Sound.SILVERFISH_WALK, Sound.SILVERFISH_KILL, Sound.SILVERFISH_IDLE),

    SKELETON(Sound.SKELETON_HURT, Sound.SKELETON_WALK, Sound.SKELETON_DEATH, Sound.SKELETON_IDLE),

    SKELETON_HORSE("mob.horse.skeleton.hit", Sound.STEP_GRASS, "mob.horse.skeleton.death", "mob.horse.skeleton.idle"),

    SLIME(Sound.SLIME_ATTACK, Sound.SLIME_WALK2, null, null, Sound.SLIME_WALK),

    SNOWMAN(null, null, null, null),

    SPIDER(Sound.SPIDER_IDLE, Sound.SPIDER_WALK, Sound.SPIDER_DEATH, Sound.SPIDER_IDLE),

    SQUID(null, null, null, null),

    UNDEAD_HORSE("mob.horse.zombie.hit", Sound.STEP_GRASS, "mob.horse.zombie.death", "mob.horse.zombie.idle"),

    VILLAGER("mob.villager.hit", null, "mob.villager.death", "mob.villager.idle", "mob.villager.haggle", "mob.villager.yes",
            "mob.villager.no"),

    WITCH("mob.witch.hurt", null, "mob.witch.death", "mob.witch.idle"),

    WITHER(Sound.WITHER_HURT, null, Sound.WITHER_DEATH, Sound.WITHER_IDLE, Sound.WITHER_SHOOT, Sound.WITHER_SPAWN),

    WITHER_SKELETON(Sound.SKELETON_HURT, Sound.SKELETON_WALK, Sound.SKELETON_DEATH, Sound.SKELETON_IDLE),

    WOLF(Sound.WOLF_HURT, Sound.WOLF_WALK, Sound.WOLF_DEATH, Sound.WOLF_BARK, Sound.WOLF_WHINE, Sound.WOLF_GROWL,
            Sound.WOLF_HOWL, Sound.WOLF_PANT, Sound.WOLF_SHAKE),

    ZOMBIE(Sound.ZOMBIE_HURT, Sound.STEP_GRASS, Sound.ZOMBIE_DEATH, Sound.ZOMBIE_IDLE, Sound.ZOMBIE_INFECT, Sound.ZOMBIE_METAL,
            Sound.ZOMBIE_WOODBREAK, Sound.ZOMBIE_WOOD);

    public enum SoundType {
        CANCEL, DEATH, HURT, IDLE, STEP;
    }

    public static String getSoundName(Sound sound) {
        return CraftSound.getSound(sound);
    }

    public static DisguiseSound getType(String name) {
        try {
            return valueOf(name);
        } catch (Exception ex) {
            return null;
        }
    }

    private HashSet<String> cancelSounds = new HashSet<String>();
    private float damageSoundVolume = 1F;
    private HashMap<SoundType, String> disguiseSounds = new HashMap<SoundType, String>();

    DisguiseSound(Object... sounds) {
        for (int i = 0; i < sounds.length; i++) {
            Object obj = sounds[i];
            String s;
            if (obj == null)
                continue;
            else if (obj instanceof String)
                s = (String) obj;
            else if (obj instanceof Sound)
                s = CraftSound.getSound((Sound) obj);
            else
                throw new RuntimeException("Was given a unknown object " + obj);
            switch (i) {
            case 0:
                disguiseSounds.put(SoundType.HURT, s);
                break;
            case 1:
                disguiseSounds.put(SoundType.STEP, s);
                break;
            case 2:
                disguiseSounds.put(SoundType.DEATH, s);
                break;
            case 3:
                disguiseSounds.put(SoundType.IDLE, s);
                break;
            default:
                cancelSounds.add(s);
                break;
            }
        }
    }

    public float getDamageSoundVolume() {
        return damageSoundVolume;
    }

    public String getSound(SoundType type) {
        if (type == null || !disguiseSounds.containsKey(type))
            return null;
        return disguiseSounds.get(type);
    }

    public HashSet<String> getSoundsToCancel() {
        return cancelSounds;
    }

    /**
     * Used to check if this sound name is owned by this disguise sound.
     */
    public SoundType getType(String name, boolean ignoreDamage) {
        if (isCancelSound(name))
            return SoundType.CANCEL;
        if (disguiseSounds.containsKey(SoundType.STEP) && disguiseSounds.get(SoundType.STEP).startsWith("step.")
                && name.startsWith("step."))
            return SoundType.STEP;
        for (SoundType type : SoundType.values()) {
            if (!disguiseSounds.containsKey(type) || type == SoundType.DEATH || (ignoreDamage && type == SoundType.HURT))
                continue;
            String s = disguiseSounds.get(type);
            if (s != null) {
                if (s.equals(name))
                    return type;
            }
        }
        return null;
    }

    public boolean isCancelled(String soundName) {
        return cancelSounds.contains(soundName);
    }

    public boolean isCancelSound(String sound) {
        return getSoundsToCancel().contains(sound);
    }

    public void setDamageSoundVolume(float strength) {
        this.damageSoundVolume = strength;
    }
}
