package net.plazmix.coordinator.handler;

import lombok.RequiredArgsConstructor;
import org.itzstonlex.recon.ContextHandler;
import org.itzstonlex.recon.RemoteChannel;
import org.itzstonlex.recon.adapter.ChannelListenerAdapter;
import org.itzstonlex.recon.minecraft.PendingConnection;

@RequiredArgsConstructor
public final class PendingChannelHandler extends ChannelListenerAdapter {

    private final PendingConnection pendingConnection;

    @Override
    public void onClientConnected(RemoteChannel remoteChannel, ContextHandler contextHandler) {
        remoteChannel.logger().warning("Client connection connected: " + remoteChannel.address());
    }

    @Override
    public void onClientClosed(RemoteChannel remoteChannel, ContextHandler contextHandler) {
        remoteChannel.logger().warning("Client connection closed: " + remoteChannel.address());
    }

    @Override
    public void onConnected(RemoteChannel channel, ContextHandler contextHandler) {
        pendingConnection.onConnected(channel);
    }

    @Override
    public void onBind(RemoteChannel channel, ContextHandler contextHandler) {
        pendingConnection.onConnected(channel);
    }

    @Override
    public void onClosed(RemoteChannel channel, ContextHandler contextHandler) {
        pendingConnection.onDisconnected(channel);
    }

    @Override
    public void onExceptionCaught(RemoteChannel remoteChannel, Throwable throwable) {
        throwable.printStackTrace();
    }

}
