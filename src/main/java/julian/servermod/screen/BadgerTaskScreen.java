package julian.servermod.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.*;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import julian.servermod.ServerMod;
import julian.servermod.ServerModClient;
import julian.servermod.item.ModItems;
import julian.servermod.sound.ModSounds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class BadgerTaskScreen extends BaseOwoScreen<FlowLayout> {
    private TextureComponent totalCompleteTexture;
    private TextureComponent nextRewardTexture;
    private TextureComponent currentStreakTexture;
    private LabelComponent todayTaskCount;

    private ArrayList<ItemComponent> taskItems = new ArrayList<>();
    private ArrayList<ButtonComponent> taskButtons = new ArrayList<>();



    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    public void updateTask(int[] itemIds, int[] itemCounts, int[] completed) {
        for (int i = 0; i < 5; i++) {
            // create item
            Item item = Item.byRawId(itemIds[i]);
            ItemStack itemStack = new ItemStack(item, itemCounts[i]);
            taskItems.get(i).stack(itemStack);
            taskButtons.get(i).active(completed[i] == 0);
        }
    }

    private void updateButtonAfterClick(ItemComponent taskItem) {
        for (int i = 0; i < 5; i++) {
            if (taskItem == taskItems.get(i)) {
                taskButtons.get(i).active(true);
            }
        }
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


    private FlowLayout createTaskContainer() {
        FlowLayout allTaskContainer = Containers.verticalFlow(Sizing.content(), Sizing.content());
        allTaskContainer.padding(Insets.of(3));
        //allTaskContainer.surface(Surface.PANEL);
        allTaskContainer.verticalAlignment(VerticalAlignment.CENTER);
        allTaskContainer.horizontalAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i < 5; i++) {
            ItemComponent taskItem = Components.item(new ItemStack(ModItems.PEBBLES_ITEM, 32));
            taskItem.setTooltipFromStack(true);
            taskItem.showOverlay(true);
            taskItem.margins(Insets.right(3));
            taskItems.add(taskItem);
            ButtonComponent taskButton = Components.button(Text.of("Complete"), pressButton -> {
                ServerMod.LOGGER.info("Clicked on task");
                ServerMod.BADGER_TASK_CHANNEL.clientHandle().send(new ServerModClient.CompleteBadgerTask(Item.getRawId(taskItem.stack().getItem())));
                updateButtonAfterClick(taskItem);
            });;
            taskButton.active(false);
            taskButtons.add(taskButton);

            FlowLayout taskContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());
            taskContainer.margins(Insets.of(4));
            taskContainer.padding(Insets.of(3));
            taskContainer.surface(Surface.PANEL);
            taskContainer.verticalAlignment(VerticalAlignment.CENTER);
            taskContainer.horizontalAlignment(HorizontalAlignment.CENTER);
            taskContainer.child(taskItem);
            taskContainer.child(taskButton);
            allTaskContainer.child(taskContainer);
        }

        return allTaskContainer;
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        FlowLayout windowContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        //windowContainer.padding(Insets.of(3));
        windowContainer.surface(Surface.PANEL);
        windowContainer.verticalAlignment(VerticalAlignment.CENTER);
        windowContainer.horizontalAlignment(HorizontalAlignment.CENTER);
        rootComponent.child(windowContainer);

        FlowLayout leftSide = Containers.verticalFlow(Sizing.content(), Sizing.content());
        leftSide.padding(Insets.of(5));
        //leftSide.surface(Surface.PANEL);
        leftSide.verticalAlignment(VerticalAlignment.TOP);
        leftSide.horizontalAlignment(HorizontalAlignment.CENTER);
        windowContainer.child(leftSide);

        FlowLayout rightSide = Containers.verticalFlow(Sizing.content(), Sizing.content());
        rightSide.padding(Insets.of(3));
        //rightSide.surface(Surface.OPTIONS_BACKGROUND);
        rightSide.verticalAlignment(VerticalAlignment.TOP);
        rightSide.horizontalAlignment(HorizontalAlignment.CENTER);
        windowContainer.child(rightSide);

        TextureComponent badgerPicture = Components.texture(Identifier.of(ServerMod.MOD_ID, "textures/gui/badger_with_border.png"),
                0 , 0,50, 50, 50, 50);
        leftSide.child(badgerPicture);

        FlowLayout statsContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        //statsContainer.padding(Insets.of(5));
        statsContainer.surface(Surface.BLANK);
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

        FlowLayout todayTaskContainer = Containers.verticalFlow(Sizing.content(), Sizing.content());
        todayTaskContainer.padding(Insets.of(3));
        todayTaskContainer.verticalAlignment(VerticalAlignment.CENTER);
        todayTaskContainer.horizontalAlignment(HorizontalAlignment.CENTER);

        FlowLayout uppperTodayTaskContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        uppperTodayTaskContainer.verticalAlignment(VerticalAlignment.CENTER);
        uppperTodayTaskContainer.horizontalAlignment(HorizontalAlignment.CENTER);
        todayTaskContainer.child(uppperTodayTaskContainer);

        FlowLayout lowerTodayTaskContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        lowerTodayTaskContainer.verticalAlignment(VerticalAlignment.CENTER);
        lowerTodayTaskContainer.horizontalAlignment(HorizontalAlignment.CENTER);
        lowerTodayTaskContainer.margins(Insets.of(3));
        todayTaskContainer.child(lowerTodayTaskContainer);

        LabelComponent todayTaskLabel = Components.label(Text.of("Tasks Today: "));
        todayTaskLabel.shadow(true);
        uppperTodayTaskContainer.child(todayTaskLabel);
        todayTaskCount = Components.label(Text.of("0"));
        todayTaskCount.shadow(true);
        lowerTodayTaskContainer.child(todayTaskCount);
        LabelComponent todayTaskCountLabel = Components.label(Text.of("/5"));
        todayTaskCountLabel.shadow(true);
        lowerTodayTaskContainer.child(todayTaskCountLabel);

        leftSide.child(todayTaskContainer);


        // RIGHT SIDE
        rightSide.child(createTaskContainer());
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
