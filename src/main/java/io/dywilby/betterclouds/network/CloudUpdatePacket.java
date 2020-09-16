package io.dywilby.betterclouds.network;

import java.util.function.Supplier;

import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.network.NetworkEvent;

public class CloudUpdatePacket {
	
	private BlockPos te;
	private Vector3f offset;
	
	public CloudUpdatePacket(BlockPos te, Vector3f offset) {
		this.te = te;
		this.offset = offset;
	}
	
	public static void encode(CloudUpdatePacket pkt, PacketBuffer pb) {
		pb.writeBlockPos(pkt.te);
		Vector3f off = pkt.offset;
		pb.writeFloat(off.getX());
		pb.writeFloat(off.getY());
		pb.writeFloat(off.getZ());
	}
	
	public static CloudUpdatePacket decode(PacketBuffer pb) {
		BlockPos b = pb.readBlockPos();
		float x = pb.readFloat();
		float y = pb.readFloat();
		float z = pb.readFloat();
		
		return new CloudUpdatePacket(b, new Vector3f(x, y, z));
	}
	
	public static void handle(CloudUpdatePacket pkt, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			
			TileEntity te = ((ClientPlayNetHandler) ctx.get().getNetworkManager().getNetHandler()).getWorld().getTileEntity(pkt.te);
			
//			if(te instanceof CloudTileEntity)
				
		});
		
		ctx.get().setPacketHandled(true);
	}

}
