package com.apeng.filtpick.network;

import com.apeng.filtpick.FiltPick;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static void registerAll(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToServer(
                SynMenuFieldC2SPacket.TYPE,
                SynMenuFieldC2SPacket.STREAM_CODEC,
                SynMenuFieldC2SPacket::handle
        ).playToServer(
                OpenFiltPickScreenC2SPacket.TYPE,
                OpenFiltPickScreenC2SPacket.STREAM_CODEC,
                OpenFiltPickScreenC2SPacket::handle
        );
    }

}
