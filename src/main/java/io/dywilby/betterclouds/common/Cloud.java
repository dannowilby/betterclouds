package io.dywilby.betterclouds.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.dywilby.betterclouds.CloudRegistry;
import io.dywilby.betterclouds.capability.CloudHandlerProvider;
import io.dywilby.betterclouds.capability.ICloudHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class Cloud {
	
	public List<BlockPos> positions;
	public int speed = 240;
	
	public int x;
	public int z;
	
	public int increment = 0;
	
	private UUID id;
	
	public Cloud() {
		this(UUID.randomUUID());
	}
	
	public Cloud(UUID id) {
		this.id = id;
		this.positions = new ArrayList<BlockPos>();
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public boolean isCloudLoaded(World world) {
		
		for(BlockPos b : positions)
			if(!world.getChunkProvider().isChunkLoaded(new ChunkPos(b)))
				return false;
		
		return true;
	}
	
	// returns true if all the positions are cloud blocks
	public boolean isValid(World world) {
		
		for(BlockPos b : positions)
			if(!world.getBlockState(b).equals(CloudRegistry.CLOUD_BLOCK.getDefaultState()))
				return false;
		
		return true;
	}
	
	public static class Block extends net.minecraft.block.Block {
		
	    public Block(AbstractBlock.Properties properties) {
	        super(properties);
	        
	        this.setRegistryName("cloud");
	        
	    }
	
	    @Override
	    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
	    	
	    	if(world.isRemote) {
	    		
	    		super.onBlockHarvested(world, pos, state, player);
	    		
				return;
	    	}
				
			ICloudHandler handler = world.getCapability(CloudHandlerProvider.CLOUD_HANDLER, null).orElse(null);
			handler.removeBlock(pos);
			
			super.onBlockHarvested(world, pos, state, player);
	    }
	    
	    @Override
	    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
	    	
	    	if(world.isRemote)
	    		return ActionResultType.SUCCESS;
	    	
	    	ICloudHandler handler = world.getCapability(CloudHandlerProvider.CLOUD_HANDLER).orElse(null);
	    	
	    	Cloud cloud = new Cloud();
	    	cloud.positions.add(pos);
	    	cloud.positions.add(pos.add(0, 1, 0));
	    	cloud.positions.add(pos.add(1, 0, 0));
	    	cloud.positions.add(pos.add(0, 0, 1));
	    	
	    	handler.addCloud(cloud);
	    	
	    	return ActionResultType.SUCCESS;
	    }
	    
//	    @Override
//	    public BlockRenderType getRenderType(BlockState state) {
//	    	return BlockRenderType.ENTITYBLOCK_ANIMATED;
//	    }
	    
	}
	
}
