package julian.servermod.sound;

import julian.servermod.ServerMod;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent LOOT_VASE_BREAK = registerSoundEvent("loot_vase_break");
    public static final SoundEvent BEGINNING_SONG = registerSoundEvent("beginning_song");
    public static final SoundEvent CRATE_OPEN = registerSoundEvent("crate_open");
    public static final SoundEvent CRATE_OPEN_MAGIC = registerSoundEvent("crate_open_magic");
    public static final SoundEvent QUEST_COMPLETE = registerSoundEvent("quest_complete");
    public static final SoundEvent QUEST_OBJECTIVE_COMPLETE = registerSoundEvent("quest_objective_complete");
    public static final SoundEvent TASK_COMPLETE = registerSoundEvent("task_complete");
    public static final SoundEvent POP = registerSoundEvent("pop");
    public static final SoundEvent RUBY_GAIN = registerSoundEvent("ruby_gain");
    public static final SoundEvent RUBY_LOSE = registerSoundEvent("ruby_lose");

    public static final BlockSoundGroup LOOT_VASE_BLOCK_SOUNDS =
            new BlockSoundGroup(1f, 1f,
                    LOOT_VASE_BREAK, SoundEvents.BLOCK_DECORATED_POT_STEP, SoundEvents.BLOCK_DECORATED_POT_PLACE,
                    SoundEvents.BLOCK_DECORATED_POT_HIT, SoundEvents.BLOCK_DECORATED_POT_FALL);


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(ServerMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        ServerMod.LOGGER.info("Registering ModSounds for " + ServerMod.MOD_ID);
    }
}
