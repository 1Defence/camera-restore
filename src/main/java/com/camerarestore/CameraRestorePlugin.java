package com.camerarestore;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Camera Restore",
	description = "Restores camera on client start"
)
public class CameraRestorePlugin extends Plugin
{
	@Inject
	private Client client;

	@Override
	protected void startUp() throws Exception
	{

	}

	@Override
	protected void shutDown() throws Exception
	{

	}

}
