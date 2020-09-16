package io.dywilby.betterclouds.capability;

import java.util.concurrent.Callable;

import net.minecraftforge.common.capabilities.Capability;

public class CloudHandlerFactory implements Callable<ICloudHandler> {
	
	@Override
	public ICloudHandler call() throws Exception {
		return new OverworldCloudHandler();
	}

}
