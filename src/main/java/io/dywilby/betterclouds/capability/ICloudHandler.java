package io.dywilby.betterclouds.capability;

import java.util.List;

import io.dywilby.betterclouds.common.Cloud;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICloudHandler {

	Cloud getCloud(BlockPos pos);
	List<Cloud> getClouds();
	
	void tick();
	void move(World world);
	
	void addCloud(Cloud cloud);
	void removeBlock(BlockPos pos);
	
}
