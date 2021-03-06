package me.libraryaddict.disguise.utilities.parser.params.types.custom;

import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedParticle;
import me.libraryaddict.disguise.utilities.translations.LibsMsg;
import me.libraryaddict.disguise.utilities.parser.DisguiseParseException;
import me.libraryaddict.disguise.utilities.parser.params.types.ParamInfoEnum;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by libraryaddict on 19/09/2018.
 */
public class ParamInfoParticle extends ParamInfoEnum {
    private Material[] materials;

    public ParamInfoParticle(Class paramClass, String name, String description, Enum[] possibleValues,
            Material[] materials) {
        super(paramClass, name, description, possibleValues);

        this.materials = materials;
    }

    public Set<String> getEnums(String tabComplete) {
        Set<String> enums = getValues().keySet();

        if (tabComplete.isEmpty()) {
            return enums;
        }

        enums = new HashSet<>(enums);

        tabComplete = tabComplete.toUpperCase();

        for (Particle particle : new Particle[]{Particle.BLOCK_CRACK, Particle.BLOCK_DUST, Particle.ITEM_CRACK}) {
            for (Material mat : materials) {
                if (particle != Particle.ITEM_CRACK && !mat.isBlock()) {
                    continue;
                }

                String name = particle.name() + ":" + mat.name();

                if (!name.startsWith(tabComplete)) {
                    continue;
                }

                enums.add(name);
            }
        }

        return enums;
    }

    @Override
    public Object fromString(String string) throws DisguiseParseException {
        String[] split = string.split("[:,]"); // Split on comma or colon
        Particle particle = (Particle) super.fromString(split[0]);

        if (particle == null) {
            return null;
        }

        Object data = null;

        switch (particle) {
            case BLOCK_CRACK:
            case BLOCK_DUST:
            case FALLING_DUST:
                Material material;

                if (split.length != 2 || (material = Material.getMaterial(split[1])) == null || !material.isBlock()) {
                    throw new DisguiseParseException(LibsMsg.PARSE_PARTICLE_BLOCK, particle.name(), string);
                }

                data = WrappedBlockData.createData(material);
                break;
            case ITEM_CRACK:
                if (split.length != 1) {
                    data = ParamInfoItemStack.parseToItemstack(Arrays.copyOfRange(split, 1, split.length));
                }

                if (data == null) {
                    throw new DisguiseParseException(LibsMsg.PARSE_PARTICLE_ITEM, particle.name(), string);
                }
                break;
            case REDSTONE:
                // If it can't be a RGB color or color name
                // REDSTONE:BLUE - 2 args
                // REDSTONE:BLUE,4 - 3 args
                // REDSTONE:3,5,2 - 4 args
                // REDSTONE:3,5,6,2 - 5 args
                if (split.length < 2 || split.length > 5) {
                    throw new DisguiseParseException(LibsMsg.PARSE_PARTICLE_REDSTONE, particle.name(), string);
                }

                Color color = ParamInfoColor.parseToColor(
                        StringUtils.join(Arrays.copyOfRange(split, 1, split.length - (split.length % 2)), ","));

                if (color == null) {
                    throw new DisguiseParseException(LibsMsg.PARSE_PARTICLE_REDSTONE, particle.name(), string);
                }

                float size;

                if (split.length % 2 == 0) {
                    size = 1;
                } else if (!split[split.length - 1].matches("[0-9.]+")) {
                    throw new DisguiseParseException(LibsMsg.PARSE_PARTICLE_REDSTONE, particle.name(), string);
                } else {
                    size = Math.max(0.2f, Float.parseFloat(split[split.length - 1]));
                }

                data = new Particle.DustOptions(color, size);
                break;
        }

        if (data == null && split.length > 1) {
            return null;
        }

        return WrappedParticle.create(particle, data);
    }
}
