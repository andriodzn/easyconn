package org.androidzn.easyconn.tool;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class SimpleEasyServiceProxyTest {


    @Test
    public void testInstantiation() throws Exception {
        Context MOCK_CONTEXT = InstrumentationRegistry.getInstrumentation().getTargetContext();
        EasyConnection<MockService> MOCK_CONNECTION = mock(EasyConnection.class);

        try {
            new SimpleEasyServiceProxy<MockService>(null, MockService.class, MOCK_CONNECTION);
            fail("不允许传空的 context");
        } catch (IllegalArgumentException ignored) {}

        try {
            new SimpleEasyServiceProxy<>(MOCK_CONTEXT, null, MOCK_CONNECTION);
            fail("不允许传空的 服务类型");
        } catch (IllegalArgumentException ignored) {}

        try {
            new SimpleEasyServiceProxy<>(MOCK_CONTEXT, MockService.class, null);
            fail("不允许传空的 连接器");
        } catch (IllegalArgumentException ignored) {}


        try {
            new SimpleEasyServiceProxy<>(MOCK_CONTEXT, MockService.class, MOCK_CONNECTION);
        } catch (Exception e) {
            fail("不允许出现异常: " + e.toString());
            e.printStackTrace();
        }
    }

    @Test
    public void testLifeCycle() {
        Context MOCK_CONTEXT = InstrumentationRegistry.getInstrumentation().getTargetContext();
        EasyConnection<MockService> MOCK_CONNECTION = mock(EasyConnection.class);
        MockService MOCK_SERVICE = mock(MockService.class);
        EasyConnection.Callback<MockService> MOCK_CALLBACK = mock(EasyConnection.Callback.class);


    }


    class MockService {}
}
