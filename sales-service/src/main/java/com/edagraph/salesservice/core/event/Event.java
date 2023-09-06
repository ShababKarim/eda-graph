package com.edagraph.salesservice.core.event;

public interface Event<T> {
    String getName();

    T getData();
}
