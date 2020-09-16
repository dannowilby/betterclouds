package io.dywilby.betterclouds.capability;

import java.util.ArrayList;
import java.util.List;

import io.dywilby.betterclouds.CloudRegistry;
import io.dywilby.betterclouds.common.Cloud;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OverworldCloudHandler implements ICloudHandler {

	public List<Cloud> clouds = new ArrayList<Cloud>();

	@Override
	public List<Cloud> getClouds() {
		return clouds;
	}

	@Override
	public void tick() {
		for(Cloud cloud : clouds)
			cloud.increment++;		
	}

	@Override
	public void move(World world) {
		
		for(Cloud cloud : clouds) {
			
			if(cloud.increment < cloud.speed)
				continue;
			
			if(!cloud.isCloudLoaded(world))
				continue;
				
			
			removeFromWorld(world, cloud);
			
			cloud.positions = moveCloud(world, cloud);
			
			addToWorld(world, cloud);
			
			
			cloud.increment = 0;
		}
		
	}

	@Override
	public void addCloud(Cloud cloud) {
		clouds.add(cloud);
	}
	
	@Override
	public Cloud getCloud(BlockPos pos) {
		
		for(Cloud cloud : clouds)
			for(BlockPos bp : cloud.positions)
				if(bp.equals(pos))
					return cloud;
		
		return null;
	}

	@Override
	public void removeBlock(BlockPos pos) {
		Cloud cloud = getCloud(pos);
		
		if(cloud == null)
			return;
		
		if(cloud.positions.contains(pos))
			cloud.positions.remove(pos);
		
		if(cloud.positions.size() == 0)
			clouds.remove(cloud);
		
		System.out.println(clouds.size());
	}
	
	/* static helper methods */
	
	public static void addToWorld(World world, Cloud cloud) {
		
		for(BlockPos b : cloud.positions)
			world.setBlockState(b, CloudRegistry.CLOUD_BLOCK.getDefaultState());
	}
	
	public static void removeFromWorld(World world, Cloud cloud) {
		
		for(BlockPos b : cloud.positions)
			world.setBlockState(b, Blocks.AIR.getDefaultState());
			
	}
	
	public static List<BlockPos> moveCloud(World world, Cloud cloud) {
		
		List<BlockPos> bp = new ArrayList<BlockPos>();
		
		for(BlockPos b : cloud.positions) {
			
			BlockPos p = b.add(1, 0, 0);
			
			if(isAir(world, p) || isCloud(world, p))
				bp.add(p);
			else
				bp.add(findNewBlockPos(world, b));
			
		}
		
		return bp;
	}
	
	public static BlockPos findNewBlockPos(World world, BlockPos b) {
		
		BlockPos p = b.add(0, 1, 0);
		
		if(isAir(world, p))
			return p;
		
		return findNewBlockPos(world, p);
	}
	
	public static boolean isAir(World world, BlockPos b) {
		return world.getBlockState(b).equals(Blocks.AIR.getDefaultState());
	}
	
	public static boolean isCloud(World world, BlockPos b) {
		return world.getBlockState(b).equals(CloudRegistry.CLOUD_BLOCK.getDefaultState());
	}

}
