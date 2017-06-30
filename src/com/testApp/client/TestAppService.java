package com.testApp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TestAppService")
public interface TestAppService extends RemoteService {

    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use TestAppService.App.getInstance() to access static instance of TestAppServiceAsync
     */
    public static class App {

        private static TestAppServiceAsync ourInstance = GWT.create(TestAppService.class);

        public static synchronized TestAppServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
