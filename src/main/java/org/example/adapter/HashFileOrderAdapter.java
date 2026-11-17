package org.example.adapter;

import org.example.adapter.interfaces.OrderSource;
import org.example.entity.Order;

import java.io.IOException;
import java.util.List;

public class HashFileOrderAdapter extends AbstractFileOrderAdapter {

    public HashFileOrderAdapter() {
        super("#");
    }
}
