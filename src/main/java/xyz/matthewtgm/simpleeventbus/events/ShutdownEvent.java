package xyz.matthewtgm.simpleeventbus.events;

import xyz.matthewtgm.simpleeventbus.Event;

/**
 * It's not recommended to use this event as it's unreliable and has a small chance to call multiple times if you have multiple {@link xyz.matthewtgm.simpleeventbus.SimpleEventBus} instances.
 * @author MatthewTGM
 * @since 1.0
 */
public class ShutdownEvent extends Event {}