# milight-api

An Android API client for controlling [Milight RGBW connected light bulbs](http://www.milight.com/milight-rgbw/), extracted from [Sunriser](https://github.com/eladnava/sunriser-android).

## Usage

Download [`milight-api.jar`](https://github.com/eladnava/milight-api/raw/master/api/release/milight-api.jar) and place it in your application's `libs/` folder. Alternatively, clone the repository and import the `api` module into your project.

Then, make sure you declare the following permissions in your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

Finally, start using the API by configuring the Milight router host, port, and desired zone to control:

```java
// Set to desired light bulb zone (1 to 4, or 0 for all zones)
int milightZone = -1;

// Milight router host and port (you can most likely leave these as is)
int milightPort = 8899;
String milightHost = "255.255.255.255";

// Create a new instance of the Milight API
MilightAPI api = new MilightAPI(MainActivity.this, milightHost, milightPort, milightZone);

// Turn on the light bulb
api.turnOn();

// Set color to Fuchsia
api.setColorRGB(MilightColors.FUCHSIA);

// Set brightness to 100%
api.setBrightness(100);

// Fade the bulb out and turn it off
api.fadeOut();
```

## API Reference

* `turnOn()` - turns on the light bulb
* `turnOff()` - turns off the light bulb
* `fadeOut()` - fades out and turns off the light bulb
* `setWhiteMode()` - turns on the dedicated white bulb in RGBW models
* `setBrightness(int percent)` - sets the brightness of the bulb (0% to 100%)
* `setColorRGB(int color)` - sets the color of the bulb to an RGB color (0 to 255)

## Available Colors

* `MilightColors.VIOLET`
* `MilightColors.ROYAL_BLUE`
* `MilightColors.BABY_BLUE`
* `MilightColors.AQUA`
* `MilightColors.MINT`
* `MilightColors.SEAFOAM_GREEN`
* `MilightColors.GREEN`
* `MilightColors.LIME_GREEN`
* `MilightColors.YELLOW`
* `MilightColors.YELLOW_ORANGE`
* `MilightColors.ORANGE`
* `MilightColors.RED`
* `MilightColors.PINK`
* `MilightColors.FUCHSIA`
* `MilightColors.LILAC`
* `MilightColors.LAVENDER`

## License

Apache 2.0
