package julian.servermod.item.custom;

import net.minecraft.item.Item;

import java.util.Map;

public class ItemDesign extends Item {
    private int customModelData;
    private Class<?> targetItem;
    private String designName;


    public ItemDesign(Settings settings, Class<?> targetItem, int customModelData, String designName) {
        super(settings);
        this.designName = designName;
        this.targetItem = targetItem;
        this.customModelData = customModelData;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public Class<?> getTargetItem() {
        return targetItem;
    }

    public String getDesignName() {
        return designName;
    }
}
