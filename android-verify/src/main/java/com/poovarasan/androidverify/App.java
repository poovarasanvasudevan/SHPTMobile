package com.poovarasan.androidverify;

import android.content.Context;

import java.lang.ref.WeakReference;

public class App {

    private static WeakReference<Context> mContext;

    public static Context getContext() {
        return mContext.get();
    }

    public static void setContext(Context context) {
        mContext = new WeakReference<Context>(context);
    }
}
