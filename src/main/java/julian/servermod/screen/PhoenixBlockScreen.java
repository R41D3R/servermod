package julian.servermod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import julian.servermod.ServerMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PhoenixBlockScreen extends HandledScreen<PhoenixBlockScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ServerMod.MOD_ID, "textures/gui/ashes_menu.png");
    private static final int GUI_WIDTH = 175;
    private static final int GUI_HEIGHT = 209;

    public PhoenixBlockScreen(PhoenixBlockScreenHandler handler, PlayerInventory inventory, Text title) {
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
        context.drawTexture(TEXTURE, x, y, 0, 29, GUI_WIDTH, GUI_HEIGHT);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
