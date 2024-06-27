package julian.servermod.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.ItemComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import julian.servermod.ServerMod;
import julian.servermod.item.ModItems;
import julian.servermod.screen.util.InventoryUtil;
import julian.servermod.sound.ModSounds;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class StoreScreen extends BaseOwoScreen<FlowLayout> {
    private final PlayerEntity player;
    private List<Quadruple<ButtonComponent, Integer, LabelComponent, Item>> buttonCosts = new ArrayList<>();
    private Map<Item, LabelComponent> labelTracker = new HashMap<>();
    private Map<Item, Integer> currencyTracker = new HashMap<>();

    public StoreScreen(PlayerEntity player) {
        super(Text.of("Store"));
        this.player = player;
        currencyTracker.put(ModItems.RUBY, 0);
        currencyTracker.put(ModItems.BADGER_COIN, 0);
    }

    public void updateLabel(Item currencyItem, int new_amount) {
        LabelComponent label = labelTracker.get(currencyItem);
        if (label != null) {
            label.text(Text.of(String.valueOf(new_amount)));
        }
    }

    public void updateCurrency(Item currencyItem, int new_amount) {
        currencyTracker.put(currencyItem, new_amount);
        updateLabel(currencyItem, new_amount);
    }

    public boolean canAfford(int cost, Item currencyItem) {
        return currencyTracker.get(currencyItem) >= cost;
    }

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    private Component createSellBox(Item currencyItem, Item sellItem, int cost) {
        var outerBox =  Containers.verticalFlow(Sizing.fixed(54), Sizing.content());
        var pricingBox = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        ItemComponent currencyItemComponent = Components.item(new ItemStack(currencyItem));
        currencyItemComponent.setTooltipFromStack();
        currencyItemComponent.showOverlay();

//        LabelComponent sellItemLabel = Components.label(Text.of("Crate Key Legendary"));
//        sellItemLabel.verticalSizing(Sizing.fixed(5));
//        sellItemLabel.shadow(true);

        LabelComponent label = Components.label(Text.of(String.valueOf(cost)));
        label.shadow(true);
        pricingBox.child(label);

        pricingBox.margins(Insets.top(5));
        pricingBox.child(currencyItemComponent);
        pricingBox.verticalAlignment(VerticalAlignment.CENTER);
        pricingBox.horizontalAlignment(HorizontalAlignment.CENTER);

        ButtonComponent button = Components.button(Text.of("Buy"), pressButton -> {
            ServerMod.STORE_BUY_CHANNEL.clientHandle().send(new ServerMod.StorePacket(cost, Item.getRawId(sellItem), Item.getRawId(currencyItem)));
            this.player.playSound(ModSounds.RUBY_LOSE, 1, 1);

            this.player.giveItemStack(new ItemStack(sellItem));
//            int remaining = cost;
//            for (final var stack : this.player.getInventory().main) {
//                if (remaining <= 0)
//                    break;
//                if (stack.getItem() == currencyItem) {
//                    int remove = Math.min(stack.getCount(), remaining);
//                    stack.decrement(remove);
//                    remaining -= remove;
//                }
//            }
        });

        button.margins(Insets.top(7));
        buttonCosts.add(new Quadruple<>(button, cost, label, currencyItem));

        ItemComponent sellItemComponent = Components.item(new ItemStack(sellItem));
        sellItemComponent.setTooltipFromStack();

        // outerBox.child(sellItemLabel);
        outerBox.child(sellItemComponent);
        outerBox.child(pricingBox);
        outerBox.child(button);
        outerBox.verticalAlignment(VerticalAlignment.CENTER);
        outerBox.horizontalAlignment(HorizontalAlignment.CENTER);
        outerBox.padding(Insets.of(10));
        outerBox.margins(Insets.of(1));
        outerBox.surface(Surface.TOOLTIP);

        return outerBox;
    }


    public Component createCurrencyComponent(Item currencyItem, int amount) {
        FlowLayout currencyComponent = Containers.horizontalFlow(Sizing.content(), Sizing.content());
        currencyComponent.verticalAlignment(VerticalAlignment.CENTER);
        currencyComponent.horizontalAlignment(HorizontalAlignment.CENTER);
        currencyComponent.margins(Insets.left(2));

        ItemComponent currencyItemComponent = Components.item(new ItemStack(currencyItem));
        LabelComponent label = Components.label(Text.of(String.valueOf(amount)));
        label.color(Color.WHITE);
        label.shadow(true);
        labelTracker.put(currencyItem, label);
        label.margins(Insets.left(5));

        currencyComponent.child(label);
        currencyComponent.child(currencyItemComponent);

        return currencyComponent;
    }

    @Override
    protected void build(FlowLayout rootComponent) {

        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);


        var exampleContainer = createSellBox(ModItems.RUBY, ModItems.CRATE_KEY_RARE, 64);
        var buttonContainer = Containers.horizontalFlow(Sizing.content(), Sizing.content());

        buttonContainer.child(createSellBox(ModItems.RUBY, ModItems.CRATE_KEY_RARE, 64));
        buttonContainer.child(createSellBox(ModItems.RUBY, ModItems.CRATE_KEY_LEGENDARY, 128));
        buttonContainer.child(createSellBox(ModItems.BADGER_COIN, ModItems.CRATE_KEY_BADGER, 32));
        buttonContainer.child(createSellBox(ModItems.BADGER_COIN, ModItems.PHOENIX_FEATHER, 32));

        FlowLayout outerMenuComponent = Containers.verticalFlow(Sizing.content(), Sizing.content());

        FlowLayout menuComponent = Containers.verticalFlow(Sizing.content(), Sizing.content());

        var scrollContainer = Containers.horizontalScroll(Sizing.fixed(170), Sizing.fixed(85), buttonContainer);
        scrollContainer.scrollStep(50);

        menuComponent.child(buttonContainer); // Add our custom button container
        menuComponent.padding(Insets.of(3));
        menuComponent.surface(Surface.DARK_PANEL);
        menuComponent.verticalAlignment(VerticalAlignment.CENTER);
        menuComponent.horizontalAlignment(HorizontalAlignment.CENTER);

        FlowLayout currencyBagComponent = Containers.horizontalFlow(Sizing.fixed(227), Sizing.content());
        currencyBagComponent.verticalAlignment(VerticalAlignment.CENTER);
        currencyBagComponent.horizontalAlignment(HorizontalAlignment.RIGHT);
        currencyBagComponent.margins(Insets.top(2));
        currencyBagComponent.child(createCurrencyComponent(ModItems.RUBY, 0));
        currencyBagComponent.child(createCurrencyComponent(ModItems.BADGER_COIN, 0));

        outerMenuComponent.child(menuComponent);
        outerMenuComponent.child(currencyBagComponent);

        var storeBanner = Components.texture(new Identifier(ServerMod.MOD_ID, "textures/gui/store_head.png"),
                0 , 0,86, 27, 86, 27);
        storeBanner.margins(Insets.bottom(8));
        rootComponent.child(storeBanner);
        rootComponent.child(outerMenuComponent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        for (var entry : buttonCosts) {
            ButtonComponent button = entry.getFirst();
            int cost = entry.getSecond();
            LabelComponent label = entry.getThird();
            Item currencyItem = entry.getFourth();
            boolean canAfford = canAfford(cost, currencyItem);
            label.color(canAfford ? Color.WHITE : Color.ofRgb(0xA0A0A0));
            button.active(canAfford);
        }

        for (var entry : labelTracker.entrySet()) {
            Item currencyItem = entry.getKey();
            LabelComponent label = entry.getValue();
            int amount = currencyTracker.get(currencyItem);
            label.text(Text.of(String.valueOf(amount)));
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_J) {
            this.client.setScreen(null);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public class Quadruple<T, U, V, K> {
        private final T first;
        private final U second;
        private final V third;
        private final K fourth;

        public Quadruple(T first, U second, V third, K fourth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
        }

        // Getters
        public T getFirst() { return first; }
        public U getSecond() { return second; }
        public V getThird() { return third; }
        public K getFourth() { return fourth; }
    }
}
