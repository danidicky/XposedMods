package net.tabbal.xposed.dataiconsfix;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findField;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class DataIconsFix implements IXposedHookLoadPackage  {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.equals("com.android.systemui"))
	        return;
		
		findAndHookMethod("com.android.systemui.statusbar.policy.NetworkController", lpparam.classLoader, "updateTMODataNetType", new XC_MethodReplacement() {
			
			@SuppressWarnings("rawtypes")
			@Override
			protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
				// Class reference to clean up code....
				Class c = param.thisObject.getClass();
				
				// Some fields we need to read, loaded into local vars for easy access
				Field f = findField(c, "mDataNetType");
				int mDataNetType = f.getInt(param.thisObject);
				
				f = findField(c, "mHspaDataDistinguishable");
				boolean mHspaDataDistinguishable = f.getBoolean(param.thisObject);
				
				f = findField(c, "mInetCondition");
				int mInetCondition = f.getInt(param.thisObject);
				
				f = findField(c, "mContext");
				Context mContext = (Context) f.get(param.thisObject);
				
				// Fields we need to set on the original object
				// int
				Field dataTypeIconId = findField(c, "mDataTypeIconId");
				
				//String
				Field mContentDescriptionDataType = findField(c, "mContentDescriptionDataType");
				
				// int[]
				Field dataIconList = findField(c, "mDataIconList");
				
				// Icons, these are 2D arrays in another class... 
				Class icons = Class.forName("com.android.systemui.statusbar.policy.TelephonyIcons", true, c.getClassLoader());
				Field DATA_G = findField(icons, "DATA_G");
				Field DATA_E = findField(icons, "DATA_E");
				Field DATA_3G = findField(icons, "DATA_3G");
				Field DATA_1X = findField(icons, "DATA_1X");
				Field DATA_H = findField(icons, "DATA_H");
				Field DATA_H_PLUS = findField(icons, "DATA_H_PLUS");
				Field DATA_LTE = findField(icons, "DATA_LTE");
				
				
		        switch (mDataNetType)
		        {
		        default:
		            break;

		        case 0: // '\0'
		        	dataIconList.set(param.thisObject, Array.get(DATA_G.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a0068));
		            break;

		        case 1: // '\001'
		        case 11: // '\013'
		        	dataIconList.set(param.thisObject, Array.get(DATA_G.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0x7f0200a0);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a0068));
		            break;

		        case 2: // '\002'
		        	dataIconList.set(param.thisObject, Array.get(DATA_E.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0x7f02009f);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a006d));
		            break;

		        case 3: // '\003'
		        	dataIconList.set(param.thisObject, Array.get(DATA_3G.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0x7f020097);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a0069));
		            break;

		        case 4: // '\004'
		        	dataIconList.set(param.thisObject, Array.get(DATA_1X.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0x7f020096);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a006c));
		            break;

		        case 5: // '\005'
		        case 6: // '\006'
		        case 12: // '\f'
		        case 14: // '\016'
		        	dataIconList.set(param.thisObject, Array.get(DATA_3G.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0x7f020097);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a0069));
		            break;

		        case 7: // '\007'
		        	dataIconList.set(param.thisObject, Array.get(DATA_1X.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0x7f020096);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a006c));
		            break;

		        case 8: // '\b'
		        case 9: // '\t'
		        case 10: // '\n'
		            if (!mHspaDataDistinguishable)
		            {
		            	dataIconList.set(param.thisObject, Array.get(DATA_3G.get(icons), mInetCondition));
			        	dataTypeIconId.set(param.thisObject, 0x7f020097);
			            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a0069));
		            } else
		            {
		            	dataIconList.set(param.thisObject, Array.get(DATA_H.get(icons), mInetCondition));
			        	dataTypeIconId.set(param.thisObject, 0x7f0200a1);
			            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a006a));
		            }
		            break;

		        case 13: // '\r'
	            	dataIconList.set(param.thisObject, Array.get(DATA_LTE.get(icons), mInetCondition));
		        	dataTypeIconId.set(param.thisObject, 0x7f0200a3);
		            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a00b6));
		            break;

		        case 15: // '\017'
		            if (!mHspaDataDistinguishable)
		            {
		            	dataIconList.set(param.thisObject, Array.get(DATA_3G.get(icons), mInetCondition));
			        	dataTypeIconId.set(param.thisObject, 0x7f020097);
			            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a0069));
		            } else
		            {
		            	dataIconList.set(param.thisObject, Array.get(DATA_H_PLUS.get(icons), mInetCondition));
			        	dataTypeIconId.set(param.thisObject, 0x7f0200a2);
			            mContentDescriptionDataType.set(param.thisObject, mContext.getString(0x7f0a006a));
		            }
		            break;
		        }
				return null;
			}
		});		
	}
}
