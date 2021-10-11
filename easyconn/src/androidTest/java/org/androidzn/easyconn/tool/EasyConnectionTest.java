package org.androidzn.easyconn.tool;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;


import org.androidzn.easyconn.mock.SimpleService;
import org.androidzn.easyconn.mock.SimpleServiceAPI;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class EasyConnectionTest {


    @Test
    public void testConnectionAutomatically() throws Exception {
        Context MOCK_CONTEXT = InstrumentationRegistry.getInstrumentation().getTargetContext();

        EasyConnection<SimpleServiceAPI> target = new EasyConnection<SimpleServiceAPI>(){
            @Override
            protected Intent newConnectionIntent(Context context) {
                return new Intent(context, SimpleService.class);
            }
        };

        CountDownLatch connectionMadeLatch = new CountDownLatch(1);
        CountDownLatch connectionResetLatch = new CountDownLatch(1);

        EasyConnection.Callback<SimpleServiceAPI> CALLBACK = new EasyConnection.Callback<SimpleServiceAPI>() {
            @Override
            public void onConnectionMade(SimpleServiceAPI mockServiceAPI) {
                assertNotNull(mockServiceAPI);
                connectionMadeLatch.countDown();
            }

            @Override
            public void onConnectionReset() {
                connectionResetLatch.countDown();
            }
        };

        target.callback(CALLBACK);

        target.make(MOCK_CONTEXT);
        connectionMadeLatch.await();
        assertEquals(0, connectionMadeLatch.getCount());

        target.reset();
        connectionResetLatch.await();
        assertEquals(0, connectionResetLatch.getCount());
    }

    @Test
    public void testConnectionManually() throws Exception {
        Context MOCK_CONTEXT = InstrumentationRegistry.getInstrumentation().getTargetContext();

        EasyConnection<SimpleServiceAPI> target = new EasyConnection<SimpleServiceAPI>(){
            @Override
            protected void makeConnectionManually(Context context, ServiceConnection connection, Handler handler) {
                Intent intent = new Intent(context, SimpleService.class);
                boolean ret = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
                if (!ret) {
                    throw new IllegalStateException("can not connect to service");
                }
            }

            @Override
            protected void resetConnectionManually(Context context, ServiceConnection connection) {
                context.unbindService(connection);
            }
        };

        CountDownLatch connectionMadeLatch = new CountDownLatch(1);
        CountDownLatch connectionResetLatch = new CountDownLatch(1);

        EasyConnection.Callback<SimpleServiceAPI> CALLBACK = new EasyConnection.Callback<SimpleServiceAPI>() {
            @Override
            public void onConnectionMade(SimpleServiceAPI mockServiceAPI) {
                assertNotNull(mockServiceAPI);
                connectionMadeLatch.countDown();
            }

            @Override
            public void onConnectionReset() {
                connectionResetLatch.countDown();
            }
        };

        target.callback(CALLBACK);

        target.make(MOCK_CONTEXT);
        connectionMadeLatch.await(3, TimeUnit.SECONDS);
        assertEquals(0, connectionMadeLatch.getCount());

        target.reset();
        connectionResetLatch.await(3, TimeUnit.SECONDS);
        assertEquals(0, connectionResetLatch.getCount());

    }

}
