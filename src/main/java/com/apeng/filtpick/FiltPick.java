package com.apeng.filtpick;

import com.mojang.logging.LogUtils;
import com.apeng.filtpick.config.FiltPickClientConfig;
import com.apeng.filtpick.config.FiltPickServerConfig;
import com.apeng.filtpick.gui.screen.FiltPickMenu;
import com.apeng.filtpick.gui.screen.FiltPickScreen;
import com.apeng.filtpick.network.NetworkHandler;
import fuzs.forgeconfigapiport.neoforge.api.forge.v4.ForgeConfigRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FiltPick.ID)
public class FiltPick {

    public static final String ID = "filtpick";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static FiltPickClientConfig CLIENT_CONFIG;
    public static FiltPickServerConfig SERVER_CONFIG;

    public FiltPick(IEventBus modBus) {
        registerMenuType(modBus);
        modBus.addListener(FiltPick::registerScreen);
        modBus.addListener(NetworkHandler::registerAll);
        registerConfigs();
    }

    private static void registerMenuType(IEventBus modBus) {
        FiltPickMenu.MENU_TYPE_REGISTER.register(modBus);
    }

    private static void registerConfigs() {
        registerClientConfig();
        registerServerConfig();
    }

    private static void registerClientConfig() {
        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        CLIENT_CONFIG = FiltPickClientConfig.getInstance(clientBuilder); // Create singleton instance
        ForgeConfigRegistry.INSTANCE.register(ModConfig.Type.CLIENT, clientBuilder.build());
    }

    private static void registerServerConfig() {
        ForgeConfigSpec.Builder serverBuilder = new ForgeConfigSpec.Builder();
        SERVER_CONFIG = FiltPickServerConfig.getInstance(serverBuilder); // Create singleton instance
        ForgeConfigRegistry.INSTANCE.register(ModConfig.Type.SERVER, serverBuilder.build());
    }

    private static void registerScreen(RegisterMenuScreensEvent event) {
        event.register(FiltPickMenu.MENU_TYPE_SUPPLIER.get(), FiltPickScreen::new);
    }

}
