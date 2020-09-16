package io.dywilby.betterclouds;

import io.dywilby.betterclouds.common.Cloud;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CloudRegistry {

	
	public static final Block CLOUD_BLOCK = new Cloud.Block(AbstractBlock.Properties.create(Material.ANVIL));
	
	
	@SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> registry) {
        
		registry.getRegistry().register(CLOUD_BLOCK);
    }
	
	@SubscribeEvent
	public static void onItemRegistry(final RegistryEvent.Register<Item> registry) {
		
		registry.getRegistry().register((new BlockItem(CLOUD_BLOCK, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS))).asItem().setRegistryName("cloud"));
	}
	
}