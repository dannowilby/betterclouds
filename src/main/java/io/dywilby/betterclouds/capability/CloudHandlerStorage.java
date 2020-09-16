package io.dywilby.betterclouds.capability;

import java.util.ArrayList;
import java.util.List;

import io.dywilby.betterclouds.common.Cloud;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CloudHandlerStorage implements IStorage<ICloudHandler> {

	@Override
	public INBT writeNBT(Capability<ICloudHandler> capability, ICloudHandler instance, Direction side) {
		
		CompoundNBT tag = new CompoundNBT();
		
		String uuids = "";
		
		for(Cloud cloud : instance.getClouds()) {
			
			uuids = uuids + cloud.getId().toString() + "/";
			
			tag.putLongArray("cloud_positions_" + cloud.getId().toString(), getSerializablePositionList(cloud.positions));
			tag.putInt("cloud_speed_" + cloud.getId().toString(), cloud.speed);
			tag.putInt("cloud_increment_" + cloud.getId().toString(), cloud.increment);
		}
		
		tag.putString("cloud_ids", uuids);
		
		return tag;
	}

	@Override
	public void readNBT(Capability<ICloudHandler> capability, ICloudHandler instance, Direction side, INBT nbt) {
		
		if(!(nbt instanceof CompoundNBT))
			return;
		
		CompoundNBT tag = (CompoundNBT) nbt;
		
		String[] uuids = tag.getString("cloud_ids").split("/");
		
		for(String id : uuids) {
			
			Cloud cloud = new Cloud();
			
			cloud.positions = getDeserializedPositionList(tag.getLongArray("cloud_positions_" + id));
			cloud.speed = tag.getInt("cloud_speed_" + id);
			cloud.increment = tag.getInt("cloud_increment_" + id);
			
			instance.addCloud(cloud);
		}
		
	}

	public List<BlockPos> getDeserializedPositionList(long[] pos) {
		
		List<BlockPos> output = new ArrayList<BlockPos>();
		
		for(long b : pos)
			output.add(BlockPos.fromLong(b));
		
		return output;
	}
	
	public List<Long> getSerializablePositionList(List<BlockPos> pos) {
		
		List<Long> output = new ArrayList<Long>();
		
		for(BlockPos b : pos)
			output.add(b.toLong());
		
		return output;
	}
	
	
}
