package io.dywilby.betterclouds;

import io.dywilby.betterclouds.capability.CloudHandlerProvider;
import io.dywilby.betterclouds.capability.ICloudHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

// TODO
// Add biome specific modifiers that can be configured in a config file

public class CloudEvents {
	
	public float rarity = 0.95f;
	public float spawnChance = 0.0001f;
	
	public int minCloudHeight = 80;
	public int maxCloudHeight = 120;
	
	public int spawnRadius = 16 * 5;
	
	@SubscribeEvent
	public void attach(AttachCapabilitiesEvent<World> event) {
		
		event.addCapability(new ResourceLocation("betterclouds", "cloud"), new CloudHandlerProvider());
	}
	
	@SubscribeEvent
	public void tick(TickEvent.PlayerTickEvent event) {

		if(event.player.world.isRemote)
			return;
		
		//System.out.println("a");
		
		ICloudHandler handler = event.player.world.getCapability(CloudHandlerProvider.CLOUD_HANDLER, null).orElse(null);
		
		handler.tick();
		handler.move(event.player.world);
		
	}
	
	@SubscribeEvent
	public void generateCloud(PlayerTickEvent event) {
		
//		World world = event.player.world;
//		
//		if(world.isRemote)
//			return;
//		
//		if(spawnChance < world.getRandom().nextFloat())
//			return;
//		
//		float angle = (float) (world.getRandom().nextFloat() * 2 * Math.PI);
//		int radius = world.getRandom().nextInt(spawnRadius);
//		
//		int x = (int) (radius * Math.cos(angle));
//		int y = world.getRandom().nextInt(maxCloudHeight - minCloudHeight) + minCloudHeight; 
//		int z = (int) (radius * Math.sin(angle));
//		
//		BlockPos potentialCloud = new BlockPos(event.player.getPosX() + x, y, event.player.getPosZ() + z);
//		
//		if(!world.getChunkProvider().isChunkLoaded(new ChunkPos(potentialCloud)))
//			return;
//			
//		if(!world.getBlockState(potentialCloud).getBlock().equals(Blocks.AIR))
//			return;
//		
//		BetterClouds.LOGGER.info("Spawned new cloud!");
//		world.setBlockState(potentialCloud, CloudRegistry.CLOUD_BLOCK.getDefaultState());
	}
	
}
