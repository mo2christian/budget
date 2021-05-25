package com.mo2christian.common;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

import javax.enterprise.context.ApplicationScoped;

public class DatastoreConfig {

    @ApplicationScoped
    public Datastore datastore(){
        return DatastoreOptions.getDefaultInstance()
                .getService();
    }
}
