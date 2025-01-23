package com.apeng.filtpick.network;

import com.apeng.filtpick.FiltPick;
import com.apeng.filtpick.gui.screen.FiltPickMenu;
import com.apeng.filtpick.mixinduck.FiltListContainer;
import com.apeng.filtpick.gui.util.ExtendedMenuProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenFiltPickScreenC2SPacket() implements CustomPacketPayload{

    public static final CustomPacketPayload.Type<OpenFiltPickScreenC2SPacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(FiltPick.ID, "my_data"));

    public static final StreamCodec<ByteBuf, OpenFiltPickScreenC2SPacket> STREAM_CODEC = StreamCodec.unit(new OpenFiltPickScreenC2SPacket());

    public static void handle(final OpenFiltPickScreenC2SPacket data, final IPayloadContext context) {
        context.player().openMenu(new ExtendedMenuProvider() {

            @Override
            public boolean shouldClose() {
                return false;
            }

            @Override
            public Component getDisplayName() {
                return Component.empty();
            }

            @Override
            public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                return new FiltPickMenu(pContainerId, pPlayerInventory, ((FiltListContainer)pPlayer).getFiltList(), ((FiltListContainer)pPlayer).getFiltListPropertyDelegate());
            }

        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
