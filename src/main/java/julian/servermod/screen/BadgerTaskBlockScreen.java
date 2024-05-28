package julian.servermod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import julian.servermod.ServerMod;
import julian.servermod.badgertasks.BadgerTaskManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector2ic;

import java.util.ArrayList;
import java.util.List;


public class BadgerTaskBlockScreen extends HandledScreen<BadgerTaskBlockScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ServerMod.MOD_ID, "textures/gui/badger_menu.png");
    private static final int GUI_WIDTH = 175;
    private static final int GUI_HEIGHT = 238-61;

    private CustomTooltip streakButton;
    private CustomTooltip rewardButton;

    public BadgerTaskBlockScreen(BadgerTaskBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - GUI_WIDTH) / 2;
        int y = (height - GUI_HEIGHT) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 61, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        // draw Tooltip for streak
        List<Text> streakTextList = List.of(Text.of("Streak: " + this.handler.streak));
        int streakX = 154;
        int streakY = 72-62;
        int streakWidth = 163 - streakX;
        int streakHeight = 83-62-streakY;
        this.streakButton = this.addDrawableChild(
                new CustomTooltip(streakX, streakY, streakWidth, streakHeight, streakTextList));

        if (this.handler.rewards != null) {
            // draw Tooltip for reward
            List<Text> rewardTextTList = new ArrayList<>();
            for (ItemStack stack : this.handler.rewards) {
                rewardTextTList.add(Text.of(stack.getCount() + "x " + stack.getName().getString()));
            }
            int rewardX = 154;
            int rewardY = 90-62;
            this.rewardButton = this.addDrawableChild(
                    new CustomTooltip(rewardX, rewardY, streakWidth, streakHeight, rewardTextTList));
        }


        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public class CustomTooltip extends ButtonWidget {
        private List<Text> message;

        public CustomTooltip(int x, int y, int width, int height, List<Text> message) {
            super(x, y, width, height, Text.of(""), new PressAction() {
                @Override
                public void onPress(ButtonWidget button) {
                    return;
                }
            }, DEFAULT_NARRATION_SUPPLIER);
            this.message = message;
        }

        @Override
        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            if (this.isHovered()) {
                context.drawTooltip(textRenderer, message, mouseX, mouseY);
            }
        }

    }
}
