package com.oodi.godsendapp;

import android.app.Application;

public class GlobalClass extends Application {

    public String getProviderId() {
        return ProviderId;
    }

    public void setProviderId(String providerId) {
        ProviderId = providerId;
    }

    public String ProviderId;

}
