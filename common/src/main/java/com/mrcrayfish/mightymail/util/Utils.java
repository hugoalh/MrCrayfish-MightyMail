package com.mrcrayfish.mightymail.util;

import com.mrcrayfish.mightymail.Constants;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class Utils
{
    /**
     * Utility to create a ResourceLocation specific to this mod
     *
     * @param name the name of the resource. can be a path
     * @return a resource location instance
     */
    public static ResourceLocation resource(String name)
    {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static String translationKey(String category, String path)
    {
        return String.format("%s.%s.%s", category, Constants.MOD_ID, path);
    }

    /**
     * Creates a translatable Component specific to this mod. The translation key
     * uses a common format of a category, followed by the mod id, and then a path.
     *
     * @param category the category of the translation
     * @param path     the path of the translation
     * @return A translatable component
     */
    public static MutableComponent translation(String category, String path, Object ... params)
    {
        return Component.translatable("%s.%s.%s".formatted(category, Constants.MOD_ID, path), params);
    }

    /**
     * Converts a Container into a NonNullList
     *
     * @param container the container to convert
     * @return a non-null list representing the container
     */
    public static NonNullList<ItemStack> nonNullListFromContainer(Container container)
    {
        int size = container.getContainerSize();
        NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
        for(int i = 0; i < size; i++)
        {
            items.set(i, container.getItem(i).copy());
        }
        return items;
    }
}
