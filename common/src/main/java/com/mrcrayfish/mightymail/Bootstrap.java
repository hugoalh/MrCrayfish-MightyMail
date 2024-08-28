package com.mrcrayfish.mightymail;

import com.mrcrayfish.framework.api.event.PlayerEvents;
import com.mrcrayfish.framework.api.event.ServerEvents;
import com.mrcrayfish.framework.api.event.TickEvents;
import com.mrcrayfish.mightymail.command.MigrateCommand;
import com.mrcrayfish.mightymail.core.ModItems;
import com.mrcrayfish.mightymail.item.PackageItem;
import com.mrcrayfish.mightymail.mail.DeliveryService;
import com.mrcrayfish.mightymail.network.Network;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class Bootstrap
{
    public static void init()
    {
        Network.init();
        TickEvents.START_SERVER.register(server -> DeliveryService.get(server).ifPresent(DeliveryService::serverTick));
        PlayerEvents.LOGGED_OUT.register(player -> {
            if(player instanceof ServerPlayer serverPlayer) {
                DeliveryService.get(serverPlayer.server).ifPresent(service -> service.playerLoggedOut(serverPlayer));
            }
        });
        ServerEvents.STARTING.register(server -> {
            MigrateCommand.register(server.getCommands().getDispatcher());
        });
        DispenserBlock.registerBehavior(ModItems.PACKAGE::get, (source, stack) -> {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Vec3 pos = source.getPos().relative(direction).getCenter();
            PackageItem.getPackagedItems(stack).forEach(s -> {
                Containers.dropItemStack(source.getLevel(), pos.x, pos.y, pos.z, s);
            });
            return ItemStack.EMPTY;
        });
    }
}
