package com.testApp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.testApp.client.TestAppService;

public class TestAppServiceImpl extends RemoteServiceServlet implements TestAppService {

    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }
}