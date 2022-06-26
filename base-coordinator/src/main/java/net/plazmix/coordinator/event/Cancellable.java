package net.plazmix.coordinator.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancel);
}
