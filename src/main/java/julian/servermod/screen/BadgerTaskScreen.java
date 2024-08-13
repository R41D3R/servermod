package julian.servermod.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.component.TextAreaComponent;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import julian.servermod.ServerMod;
import julian.servermod.sound.ModSounds;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class BadgerTaskScreen extends BaseOwoScreen<FlowLayout> {
    private TextureComponent totalCompleteTexture;
    private TextureComponent nextRewardTexture;
    private TextureComponent currentStreakTexture;


    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    public void updateStats(int totalComplete, int nextReward, int currentStreak, int pauseDays) {
        totalCompleteTexture.tooltip(Text.of("Total Tasks: " + totalComplete));
        nextRewardTexture.tooltip(Text.of("Next Key in " + nextReward + " days"));


        if (currentStreak == 0) {
            currentStreakTexture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger_icons.png"),
                    32 , 0,16, 16, 48, 48);
            currentStreakTexture.tooltip(Text.of("Current Streak: 0"));
        } else {
            if (pauseDays > 0 ) {
                currentStreakTexture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger_icons.png"),
                        16 , 32,16, 16, 48, 48);
                currentStreakTexture.tooltip(Text.of("Current Streak: " + currentStreak + " (Paused for only" + pauseDays + " more days)"));
            } else {
                currentStreakTexture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger_icons.png"),
                        32 , 32,16, 16, 48, 48);
                currentStreakTexture.tooltip(Text.of("Current Streak: " + currentStreak));
            }
        }


    }

//    public void updateTask() {
//
//    }
//
//    private FlowLayout createTaskContainer() {
//
//    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        FlowLayout windowContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        windowContainer.padding(Insets.of(3));
        windowContainer.surface(Surface.PANEL);
        windowContainer.verticalAlignment(VerticalAlignment.CENTER);
        windowContainer.horizontalAlignment(HorizontalAlignment.CENTER);
        rootComponent.child(windowContainer);

        FlowLayout leftSide = Containers.verticalFlow(Sizing.content(), Sizing.content());
        leftSide.padding(Insets.of(10));
        leftSide.surface(Surface.PANEL);
        leftSide.verticalAlignment(VerticalAlignment.TOP);
        leftSide.horizontalAlignment(HorizontalAlignment.CENTER);
        windowContainer.child(leftSide);

        FlowLayout rightSide = Containers.verticalFlow(Sizing.content(), Sizing.content());
        rightSide.padding(Insets.of(3));
        rightSide.surface(Surface.PANEL);
        rightSide.verticalAlignment(VerticalAlignment.TOP);
        rightSide.horizontalAlignment(HorizontalAlignment.CENTER);
        windowContainer.child(rightSide);

        TextureComponent badgerPicture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger.png"),
                0 , 0,42, 42, 42, 42);
        leftSide.child(badgerPicture);

        FlowLayout statsContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        statsContainer.padding(Insets.of(5));
        statsContainer.surface(Surface.PANEL);
        statsContainer.verticalAlignment(VerticalAlignment.CENTER);
        statsContainer.horizontalAlignment(HorizontalAlignment.CENTER);
        leftSide.child(statsContainer);



        totalCompleteTexture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger_icons.png"),
                0 , 16,16, 16, 48, 48);
        totalCompleteTexture.tooltip(Text.of("Total Tasks: 150"));
        statsContainer.child(totalCompleteTexture);

        ;
        nextRewardTexture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger_icons.png"),
                0 , 32,16, 16, 48, 48);
        nextRewardTexture.tooltip(Text.of("Next Key in 3 days"));
        statsContainer.child(nextRewardTexture);

        currentStreakTexture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger_icons.png"),
                32 , 32,16, 16, 48, 48);
        currentStreakTexture.tooltip(Text.of("Current Streak: 4"));
        statsContainer.child(currentStreakTexture);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_O || keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.client.setScreen(null);
            new Thread(() -> {
                try {
                    Thread.sleep(250);
                    client.player.playSound(ModSounds.ENDING_JINGLE, 0.2F, 1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
