package org.androidzn.easyconn.impl;


import android.support.test.runner.AndroidJUnit4;

import org.androidzn.easyconn.EasyFeature;
import org.androidzn.easyconn.EasyServiceProxy;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class EasyFeatureManagerTest {


    @Test
    public void testManager() {

        MockService MOCK_SERVICE = mock(MockService.class);

        EasyServiceProxy<MockService> MOCK_PROXY = mock(EasyServiceProxy.class);
        when(MOCK_PROXY.provide()).thenReturn(MOCK_SERVICE);

        EasyFeature MOCK_FEATURE = mock(EasyFeature.class);
        when(MOCK_FEATURE.contains(same(MockService.class))).thenReturn(true);
        when(MOCK_FEATURE.create(same(MockService.class), any())).thenReturn(MOCK_PROXY);

        EasyFeatureManager target = new EasyFeatureManager();
        target.use(MOCK_FEATURE);

        assertTrue(target.contains(MockService.class));

        EasyServiceProxy<MockService> proxy = target.create(MockService.class, null);
        assertEquals(proxy, MOCK_PROXY);

        verify(MOCK_FEATURE, atLeast(1)).contains(same(MockService.class));
        verify(MOCK_FEATURE, times(1)).create(same(MockService.class), any());
    }

    class MockService {}


}

