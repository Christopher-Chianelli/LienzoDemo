package com.testApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TestAppServiceAsync {

    void getMessage(String msg,
                    AsyncCallback<String> async);
}
