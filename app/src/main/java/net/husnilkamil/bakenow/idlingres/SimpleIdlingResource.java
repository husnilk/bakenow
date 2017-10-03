package net.husnilkamil.bakenow.idlingres;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by husnilk on 10/1/2017.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable private volatile ResourceCallback mCallback;

    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    public void setIdleState(boolean isIdleNow){
        mIsIdleNow.set(isIdleNow);
        if(isIdleNow && mCallback != null){
            mCallback.onTransitionToIdle();
        }
    }
}
