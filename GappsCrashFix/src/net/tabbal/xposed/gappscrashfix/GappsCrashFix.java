package net.tabbal.xposed.gappscrashfix;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.lang.reflect.Method;

import android.content.res.Resources;
import android.content.res.XResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class GappsCrashFix implements IXposedHookZygoteInit, IXposedHookLoadPackage
{

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable
	{
		try
		{
			XposedBridge.log("****GappsCrashFix pre: " + Resources.getSystem().getString(android.R.dimen.notification_large_icon_width));
			XResources.setSystemWideReplacement("android", "dimen", "notification_large_icon_width", "64.0dip");
			XposedBridge.log("****GappsCrashFix post: " + Resources.getSystem().getString(android.R.dimen.notification_large_icon_width));
		}
		catch (Throwable t)
		{
			XposedBridge.log(t);
		}
	}

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable
	{
		try
		{
			//if (lpparam.packageName.contains("com.google.android")) XposedBridge.log("~~~~~~~" + lpparam.packageName + ":" + lpparam.processName);
			if (!lpparam.packageName.equals("com.google.android.gsf")) return;

			findAndHookMethod("com.google.android.gsf.gtalkservice.service.StatusBarNotifier", lpparam.classLoader, "getAvatarForContact", String.class, long.class, new XC_MethodReplacement()
			{

				@Override
				protected Object replaceHookedMethod(MethodHookParam param) throws Throwable
				{
					Object ret = null;

					try
					{
						ret = XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
					}
					catch (Throwable t)
					{
						XposedBridge.log("***GappsCrashFix: Error thrown calling original getAvatarForContact");
						//XposedBridge.log(t);
					}

					if (ret == null)
					{
						try
						{
							Method method = param.thisObject.getClass().getDeclaredMethod("getGenericAvatar", new Class[] {});
							method.setAccessible(true);
							ret = method.invoke(param.thisObject, new Object[] {});
						}
						catch (Throwable t)
						{
							XposedBridge.log("***GappsCrashFix: Error thrown attempting to call getGenericAvatar");
							XposedBridge.log(t);
						}

						if (ret != null)
						{
							XposedBridge.log("***GappsCrashFix: Called getGenericAvatar and got good data");
						}
					}

					return ret;
				}

			});
		}
		catch (Throwable t)
		{
			XposedBridge.log(t);
		}
	}
}
