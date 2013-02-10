package net.tabbal.SamsungKeyboardShortDelay;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ShortDelayMain implements IXposedHookLoadPackage
{

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable
	{
		if (!lpparam.packageName.equals("com.sec.android.inputmethod")) return;

		try
		{
			findAndHookMethod("com.diotek.ime.framework.view.tracker.PointerTracker", lpparam.classLoader, "getLongpressTimoutDelay", int.class, XC_MethodReplacement.returnConstant(200l));
		}
		catch (Throwable t)
		{
			XposedBridge.log(t);
		}
	}

}
