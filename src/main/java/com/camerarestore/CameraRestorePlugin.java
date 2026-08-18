package com.camerarestore;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ClientShutdown;
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
	@Inject
	private ConfigManager configManager;

	private final String CONFIG_GROUP = "camerarestore";
	private final String CONFIG_SHUTDOWN_YAW = "shutdownYaw";
	private final String CONFIG_SHUTDOWN_PITCH = "shutdownPitch";


	//this is intentionally not changed on startup/shutdown, it will only change once per client launch.
	private boolean restoredCam = false;

	//only save if a login has occurred during clients lifecycle, otherwise will overwrite to default unset values
	private boolean hasLoggedIn = false;

	@Override
	protected void startUp() throws Exception
	{
		RestoreShutdownCamera();
	}

	//Not to be confused with plugin shutdown, this occurs on client close once.
	@Subscribe
	private void onClientShutdown(ClientShutdown e)
	{
		if(!hasLoggedIn)
			return;
		configManager.setConfiguration(CONFIG_GROUP, CONFIG_SHUTDOWN_YAW, client.getCameraYaw());
		configManager.setConfiguration(CONFIG_GROUP, CONFIG_SHUTDOWN_PITCH, client.getCameraPitch());
	}

	@Subscribe
	private void onGameStateChanged(GameStateChanged state){
		if(hasLoggedIn)
			return;
		if(state.getGameState() == GameState.LOGGED_IN){
			hasLoggedIn = true;
		}
	}

	//This will only ever run once per client load.
	void RestoreShutdownCamera()
	{
		if(restoredCam)
			return;

		restoredCam = true;

		//if somehow set while logged in, restoredCam will still get set and not proceed; prevents unintended use.
		if(client.getGameState() != GameState.LOGIN_SCREEN)
			return;
		

		String shutdownYaw = configManager.getConfiguration(CONFIG_GROUP, CONFIG_SHUTDOWN_YAW);
		String shutdownPitch = configManager.getConfiguration(CONFIG_GROUP, CONFIG_SHUTDOWN_PITCH);

		if(shutdownYaw != null && shutdownPitch != null){
			try
			{
				int shutdownYawI = Integer.parseInt(shutdownYaw);
				int shutdownPitchI = Integer.parseInt(shutdownPitch);
				client.setCameraYawTarget(shutdownYawI);
				client.setCameraPitchTarget(shutdownPitchI);
			} catch (NumberFormatException ignored) {}
		}

	}

}
