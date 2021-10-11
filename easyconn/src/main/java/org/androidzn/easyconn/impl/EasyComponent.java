package org.androidzn.easyconn.impl;


import org.androidzn.easyconn.EasyResolver;

abstract class EasyComponent implements EasyResolver {

    /**
     * Component Startup
     */
    public abstract void startup();

    /**
     * Component Shutdown
     */
    public abstract void shutdown();
}
