package com.kai.aspect;

import com.kai.spy.Context;

public interface Frame {
    void enter(Context context,String frameDescription);
    void exit(Context context,String frameDescription);
}
