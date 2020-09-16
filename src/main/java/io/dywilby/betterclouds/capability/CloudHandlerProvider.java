package io.dywilby.betterclouds.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CloudHandlerProvider implements ICapabilityProvider, ICapabilitySerializable<INBT> {
	
	@CapabilityInject(ICloudHandler.class)
	public static final Capability<ICloudHandler> CLOUD_HANDLER = null;
	
	private LazyOptional<ICloudHandler> holder = LazyOptional.of(CLOUD_HANDLER::getDefaultInstance);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		
		return CLOUD_HANDLER.orEmpty(cap, holder);
	}

	@Override
	public INBT serializeNBT() {
		
		return CLOUD_HANDLER.getStorage().writeNBT(CLOUD_HANDLER, holder.orElse(null), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		
		CLOUD_HANDLER.getStorage().readNBT(CLOUD_HANDLER, holder.orElse(null), null, nbt);
	}

	
}