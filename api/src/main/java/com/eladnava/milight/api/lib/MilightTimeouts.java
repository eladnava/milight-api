package com.eladnava.milight.api.lib;

public class MilightTimeouts {
    // Amount of time it takes for the zone selection to register (in millis)
    public static final int SELECT_ZONE_DELAY_MS = 200;

    // Amount of time it takes for the bulb to fade out (in millis)
    public static final int FADE_OUT_DURATION_MS = 1000;

    // Amount of time it takes for the bulb to whiten (in millis)
    public static final int WHITEN_DURATION_MS = 1000;

    // Amount of time it takes for the bulb to change brightness (in millis)
    public static final int UPDATE_BRIGHTNESS_MS = 200;

    // Amount of time it takes for the bulb to turn off
    public static final int TURN_OFF_DURATION_MS = 200;

    // Amount of time it takes for the bulb to change color (in millis)
    public static final int COLOR_CHANGE_MS = 1000;
}
