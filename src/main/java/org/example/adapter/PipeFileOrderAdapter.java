package org.example.adapter;

import org.example.adapter.interfaces.OrderSource;
import org.example.entity.Order;

import java.io.IOException;
import java.util.List;

public class PipeFileOrderAdapter extends  AbstractFileOrderAdapter {

    public PipeFileOrderAdapter() {
        super("\\|");
    }
}
