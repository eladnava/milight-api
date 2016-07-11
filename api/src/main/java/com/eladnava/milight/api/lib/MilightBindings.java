package com.eladnava.milight.api.lib;

public class MilightBindings {
    public static byte[] brightnessCommands = new byte[]{
            0x02, // Set 5% brightness
            0x03, // Set 10% brightness
            0x04, // Set 15% brightness
            0x05, // Set 20% brightness
            0x08, // Set 25% brightness
            0x09, // Set 30% brightness
            0x0A, // Set 35% brightness
            0x0B, // Set 40% brightness
            0x0D, // Set 45% brightness
            0x0E, // Set 50% brightness
            0x0F, // Set 55% brightness
            0x10, // Set 60% brightness
            0x11, // Set 65% brightness
            0x12, // Set 70% brightness
            0x13, // Set 75% brightness
            0x14, // Set 80% brightness
            0x15, // Set 85% brightness
            0x17, // Set 90% brightness
            0x18, // Set 95% brightness
            0x19  // Set 100% brightness
    };

    public static byte[] whiteCommandsByZone = new byte[]{
            (byte) 0xC2, // Whiten All Zones
            (byte) 0xC5, // Whiten Zone 1
            (byte) 0XC7, // Whiten Zone 2
            (byte) 0xC9, // Whiten Zone 3
            (byte) 0xCB  // Whiten Zone 4
    };

    public static byte[] selectCommandsByZone = new byte[]{
            0x42, // Select All Zones
            0x45, // Select Zone 1
            0x47, // Select Zone 2
            0x49, // Select Zone 3
            0x4B  // Select Zone 4
    };

    public static byte[] turnOffCommandsByZone = new byte[]{
            0x41, // Turn Off All Zones
            0x46, // Turn Off Zone 1
            0x48, // Turn Off Zone 2
            0x4A, // Turn Off Zone 3
            0x4C  // Turn Off Zone 4
    };
}
