# DisMaps Android App

## Installation
Clone this repository and import into **Android Studio**
```bash
git@github.com:dhivatiradika/ME15-Dismaps-GG3MEGP0565-KadekDhivaTiradika.git
```

## Configuration
### API Key:
Add GoogleMaps api key at `local.properties` with the following info:
```
MAPS_API_KEY=YOUR_KEY
```

### API URL:
Change API url at NetworkModule at BaseModule.kt files
Current API source: https://data.petabencana.id/
```
private const val url = "ENDPOINT"
```

## Build variants
Use the Android Studio *Build Variants* button to choose between **production** and **staging** flavors combined with debug and release build types


## Generating signed APK
From Android Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information *(you only need to do this once manually and then let Android Studio remember it)*
