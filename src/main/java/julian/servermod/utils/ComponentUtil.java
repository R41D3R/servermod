package julian.servermod.utils;

import julian.servermod.ServerMod;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ComponentUtil {

    public static ComponentType<?> getComponentTypeFromString(String key) {
        String namespace = "minecraft";
        Identifier id = Identifier.of(namespace, key);
        return Registries.DATA_COMPONENT_TYPE.get(id);
    }

    public static ComponentType<?> getComponentTypeFromString(String namespace, String key) {
        Identifier id = Identifier.of(namespace, key);
        return Registries.DATA_COMPONENT_TYPE.get(id);
    }

    // Method to check if a ComponentMap contains a ComponentType
    public static boolean containsComponent(ComponentMap componentMap, String key, String namespace) {
        ComponentType<?> componentType = getComponentTypeFromString(namespace, key);
        if (componentType == null) {
            System.out.println("ComponentType not found for key: " + key);
            return false;
        }

        return componentMap.contains(componentType);
    }

    // Method to get value from ComponentMap using string key
    public static Object getValueFromComponentMap(ComponentMap componentMap, String key, String namespace) {
        ComponentType<?> componentType = getComponentTypeFromString(namespace,key);
        if (componentType != null) {
            return componentMap.get(componentType);
        } else {
            System.out.println("ComponentType not found for key: " + key);
            return null;
        }
    }

    // Method to remove value from ComponentMap using string key
    public static ComponentMap removeValueFromComponentMap(ComponentMap componentMap, String key, String namespace) {
        ComponentType<?> componentType = getComponentTypeFromString(namespace, key);
        if (componentType == null) {
            System.out.println("ComponentType not found for key: " + key);
            return componentMap;
        }

        // Use Builder to recreate the ComponentMap without the specified component
        ComponentMap.Builder builder = ComponentMap.builder();
        for (Component<?> component : componentMap) {
            // Add all components except the one to be removed
            if (!component.type().equals(componentType)) {
                // Use the type-safe add method to add the component to the builder
                addComponentToBuilder(builder, component);
            }
        }

        return builder.build();
    }

    // Method to put a value into a ComponentMap using string key
    public static ComponentMap putValueToComponentMap(ComponentMap componentMap, String key, Object value,String namespace) {
        ComponentType<?> componentType = getComponentTypeFromString(namespace,key);
        if (componentType == null) {
            System.out.println("ComponentType not found for key: " + key);
            return componentMap;
        }

        // Use Builder to create a new ComponentMap with the added component
        ComponentMap.Builder builder = ComponentMap.builder();

        // Add all existing components to the builder
        for (Component<?> component : componentMap) {
            addComponentToBuilder(builder, component);
        }

        // Add the new component with the correct type handling
        addComponentToBuilder(builder, componentType, value);

        return builder.build();
    }


    // Helper method to add component to builder with correct type handling
    private static <T> void addComponentToBuilder(ComponentMap.Builder builder, Component<T> component) {
        builder.add(component.type(), component.value());
    }

    // Overloaded helper method to add new component to builder
    private static void addComponentToBuilder(ComponentMap.Builder builder, ComponentType<?> componentType, Object value) {
        // Unsafe cast, but necessary to handle the type mismatch
        addComponentToBuilderInternal(builder, componentType, value);
    }

    // Internal method to handle the type cast
    @SuppressWarnings("unchecked")
    private static <T> void addComponentToBuilderInternal(ComponentMap.Builder builder, ComponentType<T> componentType, Object value) {
        builder.add(componentType, (T) value);
    }
}
