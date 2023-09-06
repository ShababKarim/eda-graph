package com.edagraph.marketingservice.core.event;

public interface Event<T> {
    String getName();

    T getData();
}
