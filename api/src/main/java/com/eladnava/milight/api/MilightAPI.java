package com.eladnava.milight.api;

import android.content.Context;
import android.util.Log;

import com.eladnava.milight.api.config.Logging;
import com.eladnava.milight.api.lib.MilightTimeouts;
import com.eladnava.milight.api.lib.MilightBindings;
import com.eladnava.milight.api.util.Networking;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MilightAPI {
    // Milight configuration
    private int port;
    private int zone;
    private String host;

    // Android app context
    private Context context;

    public MilightAPI(Context context, String host, int port, int zone) throws Exception {
        // Save for later
        this.host = host;
        this.port = port;
        this.zone = zone;
        this.context = context;

        // Validate zone (can only range from 0 to 4, inclusive)
        if (zone < 0 || zone > 4) {
            throw new Exception("The provided zone must range from 0 to 4, inclusive.");
        }
    }

    public void turnOn() throws Exception {
        // Log it
        Log.d(Logging.TAG, "Turning on and selecting zone " + zone);

        // Construct the zone selection command
        byte[] zoneSelectionCommand = new byte[]{MilightBindings.selectCommandsByZone[zone], 0x00, 0x55};

        // Broadcast it
        broadcastCommand(zoneSelectionCommand);

        // Wait a bit before sending other commands (so that the command registers successfully)
        Thread.sleep(MilightTimeouts.SELECT_ZONE_DELAY_MS);
    }

    private void selectZone() throws Exception {
        // Zone selection command is same command as turning the bulb on
        turnOn();
    }

    public void turnOff() throws Exception {
        // Log it
        Log.d(Logging.TAG, "Turning off light zone " + zone);

        // Construct the kill command
        byte[] zoneOffPacket = new byte[]{MilightBindings.turnOffCommandsByZone[zone], 0x00, 0x55};

        // Broadcast it
        broadcastCommand(zoneOffPacket);

        // Wait a bit before sending other commands (so that the command registers successfully)
        Thread.sleep(MilightTimeouts.TURN_OFF_DURATION_MS);
    }

    public void fadeOut() throws Exception {
        // Modify bulb brightness level to lowest percent value so that it fades out nicely
        setBrightness(0);

        // Wait X amount of seconds before turning off the light
        Thread.sleep(MilightTimeouts.FADE_OUT_DURATION_MS);

        // Turn off the bulb completely
        turnOff();
    }

    public void setWhiteMode() throws Exception {
        // Log it
        Log.d(Logging.TAG, "Setting white mode for zone " + zone);

        // Prepare the white mode command
        byte[] whiteModeCommand = new byte[]{MilightBindings.whiteCommandsByZone[zone], 0x00, 0x55};

        // Send it
        broadcastCommand(whiteModeCommand);

        // Wait a bit before sending other commands so it's registered
        Thread.sleep(MilightTimeouts.WHITEN_DURATION_MS);
    }

    public void setColorRGB(int color) throws Exception {
        // Validate RGB color input
        if (color < 0 || color > 255) {
            throw new Exception("The provided color must range from 0 to 255, inclusive.");
        }

        // Log it
        Log.d(Logging.TAG, "Setting bulb color to " + color + " for zone " + zone);

        // Prepare the color command
        byte[] colorCommand = {0x40, (byte) color, 0x55};

        // Send it
        broadcastCommand(colorCommand);

        // Wait a bit before sending other commands so it's registered
        Thread.sleep(MilightTimeouts.COLOR_CHANGE_MS);
    }

    public void setBrightness(int percent) throws Exception {
        // Sanitize brightness level input
        if (percent < 0 || percent > 100) {
            throw new Exception("Please specify a brightness percentage from 0 to 100.");
        }

        // Log it
        Log.d(Logging.TAG, "Setting brightness to " + percent + "% for zone " + zone);

        // Send zone selection command (and wait a bit for it to register) before sending the brightness command
        selectZone();

        // Determine the maximum index of brightness commands (the higher the index, the brighter it is)
        int maxBrightnessIndex = MilightBindings.brightnessCommands.length - 1;

        // Convert desired brightness percentage to the appropriate brightness byte
        byte brightnessByte = MilightBindings.brightnessCommands[(int) (percent / 100.0 * maxBrightnessIndex)];

        // Prepare the brightness command
        byte[] brightnessCommand = new byte[]{0x4E, brightnessByte, 0x55};

        // Send it
        broadcastCommand(brightnessCommand);

        // Wait a bit before sending other commands so it's registered
        Thread.sleep(MilightTimeouts.UPDATE_BRIGHTNESS_MS);
    }

    private void broadcastCommand(byte[] buffer) throws Exception {
        // Verify Wi-Fi network connectivity before broadcasting
        if (!Networking.isWiFiConnected(context)) {
            return;
        }

        // Get Milight router host address
        InetAddress address = InetAddress.getByName(this.host);

        // Prepare new UDP broadcast socket
        DatagramSocket socket = new DatagramSocket();

        // Prepare a packet with the buffer content, length, server address, and port
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, this.port);

        // Broadcast UDP packet
        socket.send(packet);

        // Close socket
        socket.close();
    }
}
