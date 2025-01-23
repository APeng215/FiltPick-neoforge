package com.apeng.filtpick.network;

import com.apeng.filtpick.FiltPick;
import com.apeng.filtpick.gui.screen.FiltPickMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SynMenuFieldC2SPacket(int displayedRowStartIndex) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SynMenuFieldC2SPacket> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(FiltPick.ID, "syn_menu_field"));

    public static final StreamCodec<ByteBuf, SynMenuFieldC2SPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SynMenuFieldC2SPacket::displayedRowStartIndex,
            SynMenuFieldC2SPacket::new
    );

    public static void handle(final SynMenuFieldC2SPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof FiltPickMenu filtPickMenu) {
                filtPickMenu.setDisplayedRowOffsetAndUpdate(data.displayedRowStartIndex);
                filtPickMenu.broadcastFullState(); // Respond is important, making sure everything is synchronized.
            } else {
                FiltPick.LOGGER.warn("FiltPick menu is not opened but receive SynMenuFieldC2SPacket!");
            }
        }).exceptionally(e -> {
            // Handle exception
            context.disconnect(Component.translatable("filtpick.networking.failed", e.getMessage()));
            return null;
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
