package io.dywilby.betterclouds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.dywilby.betterclouds.capability.CloudHandlerFactory;
import io.dywilby.betterclouds.capability.CloudHandlerStorage;
import io.dywilby.betterclouds.capability.ICloudHandler;
import io.dywilby.betterclouds.capability.OverworldCloudHandler;
import io.dywilby.betterclouds.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("betterclouds")
public class BetterClouds
{

	public static final Logger LOGGER = LogManager.getLogger();

    public BetterClouds() {
        
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    	
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	CapabilityManager.INSTANCE.register(ICloudHandler.class, new CloudHandlerStorage(), OverworldCloudHandler::new);
    	MinecraftForge.EVENT_BUS.register(new CloudEvents());
    	//PacketHandler.register();
    }
}
