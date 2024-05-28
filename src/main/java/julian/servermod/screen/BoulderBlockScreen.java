package julian.servermod.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import io.netty.buffer.Unpooled;
import julian.servermod.ServerMod;
import julian.servermod.item.ModItems;
import julian.servermod.screen.util.InventoryUtil;
import julian.servermod.screen.util.BoulderNetworkUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BoulderBlockScreen extends HandledScreen<BoulderBlockScreenHandler> {
    private static final Identifier GUI_TEXTURE = new Identifier(ServerMod.MOD_ID, "textures/gui/repairsmith.png");
    private static final Identifier ICONS_TEXTURE = new Identifier(ServerMod.MOD_ID, "textures/gui/icons.png");

    protected static final Text COST_TEXT = Text.translatable("text.servermod.cost");
    private static final Text SEPARATOR_TEXT = Text.literal(" - ");

    private static final int COST_X = 104;
    private static final int COST_Y = 52;
    private static final int REPAIR_BTN_X = 104;
    private static final int REPAIR_BTN_Y = 28;

    private RepairButton repairButton;
    private boolean canAfford = false;

    public BoulderBlockScreen(BoulderBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    /* @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
		// Render title
        int lvl = ((RepairScreenHandler)this.handler).getLevelProgress();
        if (lvl > 0 && lvl <= 5) {
            MutableText text = this.title.copy().append(SEPARATOR_TEXT).append(Text.translatable("merchant.level." + lvl));
            int x = this.backgroundWidth / 2 - this.textRenderer.getWidth(text) / 2;
            context.drawText(this.textRenderer, text, x, this.titleY, 0x404040, false);
        } else {
            context.drawText(this.textRenderer, this.title, this.backgroundWidth / 2 - this.textRenderer.getWidth(this.title) / 2, this.titleY, 0x404040, false);
        }

        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 0x404040, false);
	} */

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        // Update repair btn status
        this.repairButton.active = this.handler.getRepairCost() > 0;
        if (this.repairButton.active) {
            this.canAfford = InventoryUtil.canAfford(client.player.getInventory(), this.handler.getRepairCost(), ModItems.RUBY);
            this.repairButton.active = this.canAfford;
        }

        super.render(context, mouseX, mouseY, delta);

        // Repair items text
        context.drawText(this.textRenderer, Text.translatable("text.servermod.repair_items"), this.x + 7,
                this.y + REPAIR_BTN_Y - 11, 0x404040, false);

        // Cost
        int cost = this.handler.getRepairCost();
        if (cost > 0) {
            context.drawText(this.textRenderer, COST_TEXT, this.x + COST_X, this.y + COST_Y, 0x404040, false);
            context.drawItem(new ItemStack(ModItems.RUBY, cost), this.x + COST_X, this.y + COST_Y + 8);
            context.drawItemInSlot(this.textRenderer, new ItemStack(ModItems.RUBY, cost), this.x + COST_X,
                    this.y + COST_Y + 8);
        }

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        titleY = 1000;
        playerInventoryTitleY = 1000;

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        this.repairButton = this.addDrawableChild(new RepairButton(this.x + REPAIR_BTN_X, this.y + REPAIR_BTN_Y,
                Text.translatable("text.servermod.repair"), (button) -> {
            if (!button.active)
                return;


            // send packet for handler.repairALL()
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeVarInt(this.handler.syncId);
            ClientPlayNetworking.send(BoulderNetworkUtil.REPAIR_ITEMS_PACKET, buf);
        }));
    }

    public class RepairButton extends ButtonWidget {
        public RepairButton(int x, int y, Text text, ButtonWidget.PressAction onPress) {
            super(x, y, 60, 18, text, onPress, DEFAULT_NARRATION_SUPPLIER);
        }

        @Override
        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            int v = 18;
            if (!this.active)
                v = 0;
            else if (this.isHovered())
                v = 36;
            context.drawTexture(ICONS_TEXTURE, this.getX(), this.getY(), 0, v, this.width, this.height);

            int o = this.active ? 0xFFFFFF : 0xA0A0A0;
            context.drawCenteredTextWithShadow(textRenderer, this.getMessage(), this.getX() + this.width / 2,
                    this.getY() + (this.height - 8) / 2, o | MathHelper.ceil(this.alpha * 255.0f) << 24);

            // Disabled tooltip
            if (!this.active && this.isHovered()) {
                final var handler = BoulderBlockScreen.this.handler;
                Text text = null;

//                if (!handler.hasMoreOffers())
//                    text = Text.translatable("text.repairsmith.no_more_offers");
                if (handler.getRepairCost() == 0)
                    text = Text.translatable("text.servermod.no_items");
                else if (!BoulderBlockScreen.this.canAfford)
                    text = Text.translatable("text.servermod.not_affordable");

                if (text != null)
                    context.drawTooltip(textRenderer, text, mouseX, mouseY);
            }
        }

    }
}