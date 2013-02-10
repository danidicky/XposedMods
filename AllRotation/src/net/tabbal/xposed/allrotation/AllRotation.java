package net.tabbal.xposed.allrotation;

import android.content.res.XResources;
import de.robv.android.xposed.IXposedHookZygoteInit;

public class AllRotation implements IXposedHookZygoteInit
{
	@Override
	public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable
	{
		XResources.setSystemWideReplacement("android", "bool", "config_allowAllRotations", true);
	}
}
