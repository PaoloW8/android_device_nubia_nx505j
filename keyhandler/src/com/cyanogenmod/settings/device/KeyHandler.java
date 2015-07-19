package com.cyanogenmod.settings.device;

import android.app.ActivityManagerNative;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.internal.os.DeviceKeyHandler;
import com.android.internal.widget.LockPatternUtils;

public class KeyHandler implements DeviceKeyHandler {

    private static final String TAG = KeyHandler.class.getSimpleName();

    // Supported scancodes
    private static final int KEY_DOUBLE_TAP = 68;

    private Intent mPendingIntent;
    private LockPatternUtils mLockPatternUtils;
    private final Context mContext;
    private final PowerManager mPowerManager;

    public KeyHandler(Context context) {
        mContext = context;
        mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mLockPatternUtils = new LockPatternUtils(context);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.equals(action, Intent.ACTION_USER_PRESENT)) {
                if (mPendingIntent != null) {
                    try {
                        mContext.startActivity(mPendingIntent);
                    } catch (ActivityNotFoundException e) {
                    }
                    mPendingIntent = null;
                }
            } else if (TextUtils.equals(action, Intent.ACTION_SCREEN_OFF)) {
                mPendingIntent = null;
            }
        }
    };


    public boolean handleKeyEvent(KeyEvent event) {
        boolean consumed = false;
        switch(event.getScanCode()) {
        case KEY_DOUBLE_TAP:
            if (!mPowerManager.isScreenOn()) {
                mPowerManager.wakeUp(SystemClock.uptimeMillis());
            }
            consumed = true;
            break;
        }
        return consumed;
    }

}
