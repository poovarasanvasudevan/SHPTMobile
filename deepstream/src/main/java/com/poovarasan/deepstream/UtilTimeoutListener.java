package com.poovarasan.deepstream;

interface UtilTimeoutListener {
    void onTimeout(Topic topic, Actions action, Event event, String name);
}
