package io.dywilby.betterclouds.network;

import java.util.Optional;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {

	private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
	    new ResourceLocation("betterclouds", "main"),
	    () -> PROTOCOL_VERSION,
	    PROTOCOL_VERSION::equals,
	    PROTOCOL_VERSION::equals
	);
	
	public static void register() {
		
		INSTANCE.registerMessage(
				1, 
				CloudUpdatePacket.class,
				CloudUpdatePacket::encode,
				CloudUpdatePacket::decode,
				CloudUpdatePacket::handle
		);
	}
	
}
