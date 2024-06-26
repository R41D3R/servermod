package julian.servermod.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import julian.servermod.ServerMod;
import julian.servermod.item.custom.cratekeys.CrateKeyBadger;
import julian.servermod.item.custom.cratekeys.CrateKeyRare;
import julian.servermod.sound.ModSounds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class CrateRewardScreen extends BaseOwoScreen<FlowLayout> {
    private final ItemStack rewardStack;
    private final Item crateKeyItem;

    public CrateRewardScreen(ItemStack rewardStack, Item crateKeyItem) {
        super(Text.of("Crate Reward"));
        this.rewardStack = rewardStack;
        this.crateKeyItem = crateKeyItem;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        var menuBackground = Containers.verticalFlow(Sizing.content(), Sizing.content());
        menuBackground.surface(Surface.PANEL);
        menuBackground.horizontalAlignment(HorizontalAlignment.CENTER);
        menuBackground.verticalAlignment(VerticalAlignment.CENTER);
        menuBackground.padding(Insets.of(10));

        var stackLayout = Containers.stack(Sizing.fixed(78), Sizing.content());

        var rewardItem = Components.item(rewardStack);

        TextureComponent chestBackground;
        if (crateKeyItem instanceof CrateKeyBadger) {
            chestBackground = Components.texture(new Identifier(ServerMod.MOD_ID, "textures/gui/crates/badger_chest_gui.png"),
                    0 , 0,78, 89, 78, 89);
            rewardItem.positioning(Positioning.absolute(33-2, 30-2));
        } else if(crateKeyItem instanceof CrateKeyRare) {
            chestBackground = Components.texture(new Identifier(ServerMod.MOD_ID, "textures/gui/crates/rare_collect.png"),
                    0 , 0,78, 91, 78, 91);
            rewardItem.positioning(Positioning.absolute(33-2, 30+1));
        } else {
            chestBackground = Components.texture(new Identifier(ServerMod.MOD_ID, "textures/gui/crates/legendary_collect.png"),
                    0 , 0,78, 94, 78, 94);
            rewardItem.positioning(Positioning.absolute(33-2, 30+1+2));
        }

        rewardItem.showOverlay(true);
        rewardItem.setTooltipFromStack(true);

        stackLayout.child(chestBackground);
        stackLayout.child(rewardItem);

        var dismissButton = Components.button(Text.of("Close"), button -> {
            this.client.setScreen(null);
            this.client.player.playSound(ModSounds.CRATE_CLOSE, 1, 1);
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    this.client.player.playSound(ModSounds.ENDING_JINGLE, 0.4F, 1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
        dismissButton.margins(Insets.top(5));

        menuBackground.child(stackLayout);
        menuBackground.child(dismissButton);

        rootComponent.child(menuBackground);

    }
}
