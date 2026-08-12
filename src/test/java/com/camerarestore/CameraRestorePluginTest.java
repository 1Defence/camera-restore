package com.camerarestore;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CameraRestorePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CameraRestorePlugin.class);
		RuneLite.main(args);
	}
}