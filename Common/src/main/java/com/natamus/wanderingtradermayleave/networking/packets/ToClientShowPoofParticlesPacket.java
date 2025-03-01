package com.natamus.wanderingtradermayleave.networking.packets;

import com.natamus.collective.implementations.networking.data.PacketContext;
import com.natamus.collective.implementations.networking.data.Side;
import com.natamus.wanderingtradermayleave.config.ConfigHandler;
import com.natamus.wanderingtradermayleave.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ToClientShowPoofParticlesPacket {
    public static final ResourceLocation CHANNEL = new ResourceLocation(Reference.MOD_ID, "to_client_show_poof_particles_packet");

    private final double x;
    private final double y;
    private final double z;

    public ToClientShowPoofParticlesPacket(double xIn, double yIn, double zIn) {
        this.x = xIn;
        this.y = yIn;
        this.z = zIn;
    }

    public static ToClientShowPoofParticlesPacket decode(FriendlyByteBuf buf) {
        double xIn = buf.readDouble();
        double yIn = buf.readDouble();
        double zIn = buf.readDouble();

        return new ToClientShowPoofParticlesPacket(xIn, yIn, zIn);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public static void handle(PacketContext<ToClientShowPoofParticlesPacket> ctx) {
        if (ctx.side().equals(Side.CLIENT)) {
            ToClientShowPoofParticlesPacket packet = ctx.message();

            if (!ConfigHandler.showDespawnParticles) {
                return;
            }

            for (int i = 0; i < 20; i++) {
                double offsetX = (Math.random() - 0.5);
                double offsetY = (Math.random() - 0.5);
                double offsetZ = (Math.random() - 0.5);

                double velocityX = (Math.random() - 0.5) * 0.1;
                double velocityY = (Math.random() - 0.5) * 0.1;
                double velocityZ = (Math.random() - 0.5) * 0.1;

                Minecraft.getInstance().level.addParticle(ParticleTypes.CLOUD, packet.x + offsetX, packet.y + offsetY, packet.z + offsetZ, velocityX, velocityY, velocityZ);
            }
        }
    }
}
