package com.makotomiyamoto.nt.encampments.core.event;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakByNDEvent extends BlockEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public BlockBreakByNDEvent(@NotNull Block theBlock) {
        super(theBlock);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
