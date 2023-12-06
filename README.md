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


## Features
- Display disaster on map
- Display disaster as list
- Filter with date range
- Filter with province area
- Filter with selected disaster
- Water level alert
- Dark mode

## Screenshots
### Light Mode
Home screen with disaster live reports | Date range filter calendar mode | Date range filter edit text input mode | Filter applied |
:----------:|:----------:|:----------:|:----------:|
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/1.png?alt=media&token=07da04f2-518b-48cf-9246-c7b8deef47e9" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/2.png?alt=media&token=83612c88-4429-45eb-9cca-043764a874e3" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/3.png?alt=media&token=5aae8948-cc8d-49bc-a695-61c936679823" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/4.png?alt=media&token=edf414da-567a-4fdf-bed2-643c63a2c195" width=200/>|

Search based on province area | Setting | Disaster Detail |
:----------:|:----------:|:----------:|
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/5.png?alt=media&token=d4a47689-df02-4182-894f-2d57c83d5262" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/6.png?alt=media&token=65972328-e01c-4fd4-bd40-fff45f52e1ca" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/7.png?alt=media&token=b95d15b8-9c8f-4c50-886f-c9ed6f501bce" width=200/>|

### Dark Mode
Home screen with disaster live reports | Date range filter calendar mode | Date range filter edit text input mode | Filter applied |
:----------:|:----------:|:----------:|:----------:|
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/8.png?alt=media&token=34219530-c803-459e-9e10-336cd7c283fb" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/9.png?alt=media&token=4d64749a-46bb-4c1c-9ddd-f1023d5f5f9d" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/10.png?alt=media&token=768f7b93-c14b-4e49-abe2-05139510af84" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/11.png?alt=media&token=26cfd88d-be26-457b-99cb-6a462eacd581" width=200/>|

Search based on province area | Setting | Disaster Detail |
:----------:|:----------:|:----------:|
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/12.png?alt=media&token=56c2a606-532d-4482-aef4-1520c85182b7" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/13.png?alt=media&token=805af87e-9979-4d9a-87ce-55af4ed378d4" width=200/>|<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/14.png?alt=media&token=6d3fb651-fca7-4020-8d46-082c7f0a426b" width=200/>|


## Architecture
### Single Activity
Dismaps are build on a single activity, which will host multiple fragments, each with distinct responsibilities.
\
\
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/15.png?alt=media&token=d73afdfa-d4f6-463b-ae70-f12211b8bbec" width="600">

Main Activity will host four fragment containers, including the header container, main container, bottom sheet container, and bottom sheet header container. These fragment containers are not limited to hosting specific fragments; instead, they are reusable depending on the context and state.
\
\
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/16.png?alt=media&token=ae94d7b8-a5c7-41a0-a43a-6f3ce0f4e8d3" width="300">

Main Container is the largest among the fragment containers, occupying the full width and height of its parent. Due to its large size, its role is to host components that require large screen space, such as maps, as well as to display important messages like errors or empty states.
\
\
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/17.png?alt=media&token=c1f4af05-14e9-45e6-a1d3-3b1f56118066" width="300">

Header Container is floating at the top of the screen, its role is to display filter and search bar. In the context of a detailed disaster view, the Header Container also takes on the responsibility of hosting navigation buttons.
\
\
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/18.png?alt=media&token=77115b50-3dcf-4ca1-b1ec-cb679652c146" width="300">

Bottom Sheet Container is located inside bottom sheet, granting it draggable behavior and three different states: expanded, half-expanded, and collapsed. This behavior makes the bottom sheet container ideal for hosting scrollable content such as list and detailed information.
\
\
<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/19.png?alt=media&token=60ef936b-0d4c-4f3e-b6a1-00e0998f818a" width="300">

Header Bottom Sheet Container will anchored to the top of bottom sheet make it easily noticeable by user, so its responsibility is to host conclusion of information and current states.

### Data Sharing Between fragments

<img src="https://firebasestorage.googleapis.com/v0/b/dhiva-storage.appspot.com/o/20.png?alt=media&token=e563106e-4b6a-473b-95da-f10dd24e1a78" width="800">

These fragments hosted within main activity share common data, which is disaster reports. In addition to the reports, each fragment also maintains states that are utilized to retrieve disaster reports. For example, the list of disasters displayed in the disaster list fragment is generated based on the filter state on search bar fragment. Based on this problem, view model serve as state holder and bridge to domain layer, while view model must be observed and utilized within each fragment, but not attached to fragment lifecycle, so view model need to be crated at fragment but using activity as view model owner so view model scoped to activity lifecycle.

Main activity also need to observe main view model since its role is utilized fragment transaction, decide which fragments are currently displayed. Beside fragment transaction, main activity also had responsibility to manage bottom sheet state. Although each fragment depends on other fragment, this approach make fragment only coupled to view model. For example, disaster list fragment only had one responsibility which is displaying disaster list, its doesn't know if data retrieval is success or not, if filter is applied or not, its only focus on one task, displaying disaster list.

Setting dialog fragment is a dialog that displayed on the top of activity, so its doesn't need fragment container. Setting dialog had different business flow, which is retrieve and utilizing setting data from data store. Since its had different business flow, setting dialog should have separate view model, but in this case fragment as view model owner, so its attached to fragment lifecycle.

### Dependency Injection

After project already had well-separated functional set, now its need construction set that will wiring everything together. Dependency injection will act as constructor to serve dependency to all part of the application with various scope. As stated on previous section, different process flow will had different view model, so its need dependency that scoped to view model. Dependency also need to injected to worker for notification alert. Based on this needs, Dismaps used Hilt as dependency injection framework since its cover all this need:

- @HiltViewModel to inject dependency to view model, so doesn't need to create view model factory.
- @HiltWorker to inject dependency to worker, so doesn't need to create worker factory.
- @InstallIn(ViewModelComponent::class) to scope component to view model.
- @InstallIn(SingletonComponent::class) to scope component to application.
