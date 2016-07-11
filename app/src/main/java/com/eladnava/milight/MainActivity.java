package com.eladnava.milight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eladnava.milight.api.MilightAPI;
import com.eladnava.milight.api.lib.MilightColors;
import com.eladnava.milight.config.Logging;

public class MainActivity extends AppCompatActivity {
    // Set to desired light bulb zone (1 to 4, or 0 for all zones)
    public static final int MILIGHT_ZONE = -1;

    // Milight router address (you can most likely leave these as is)
    public static final int MILIGHT_PORT = 8899;
    public static final String MILIGHT_HOST = "255.255.255.255";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure the MILIGHT_ZONE to test the API
        if (MILIGHT_ZONE > -1) {
            // Test out the API (use a background thread to execute network operations)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Send various commands to the bulb
                    testMilightAPI();
                }
            }).start();
        }
    }

    private void testMilightAPI() {
        try {
            // Create a new instance of the Milight API
            MilightAPI api = new MilightAPI(MainActivity.this, MILIGHT_HOST, MILIGHT_PORT, MILIGHT_ZONE);

            // Turn on the light bulb
            api.turnOn();

            // Set color to "FUCHSIA"
            api.setColorRGB(MilightColors.FUCHSIA);

            // Set brightness to 100%
            api.setBrightness(100);

            // Fade the bulb out and turn it off
            api.fadeOut();
        }
        catch (Exception exc) {
            // Log errors to logcat
            Log.e(Logging.TAG, "Milight API Error", exc);
        }
    }
}
