package net.tabbal.xposed.gappscrashfix;

import android.content.res.Resources;
import android.content.res.XResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;

public class GappsCrashFix implements IXposedHookZygoteInit {

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		try {
			XposedBridge.log("****GappsCrashFix pre: " + Resources.getSystem().getString(android.R.dimen.notification_large_icon_width));
			XResources.setSystemWideReplacement("android", "dimen", "notification_large_icon_width", "64.0dip");
			XposedBridge.log("****GappsCrashFix post: " + Resources.getSystem().getString(android.R.dimen.notification_large_icon_width));
		} catch (Throwable t) {
			XposedBridge.log(t);
		}		
	}
}
