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
<img src="https://file.notion.so/f/s/e8751ba4-2a96-4304-ae54-9ea53707b253/Untitled.png?id=11d65ace-3a97-4299-8e29-681c4e92aa0d&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=g2sfyuRRNyaEJ0mWIpjJKhyBAp1AXkCVcQJAkZiKNko&downloadName=Untitled.png" width=200/>|<img src="https://file.notion.so/f/s/e6ed9bc4-1634-49f1-8bf6-64cfe3803f16/Untitled.png?id=0a41d8fe-851f-48b9-bbec-54bcca5e9a9e&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=sNFOEwsmRoRWV2zqTLis3UcQgDnAoybXxhjPxpjxDFo&downloadName=Untitled.png" width=200/>|<img src="https://file.notion.so/f/s/e3079d66-fe2e-40aa-a56b-9b40d691a97f/Untitled.png?id=3af2d85f-5574-4df3-a8db-32b016651c03&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=iKrfaDRdnHd8An2tq35-4VG0_1CupemgXF5186Kwjwk&downloadName=Untitled.png" width=200/>|<img src="https://file.notion.so/f/s/50178284-1ed7-4412-be60-247e275d045d/Untitled.png?id=973c396d-8c0d-4b8d-973a-b0a3d5ea790e&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=Jlrrn7uBjiNKYCtG4_LZ_rDrwcZ0pMLBP9U8MKp00Ro&downloadName=Untitled.png" width=200/>|

Search based on province area | Setting | Disaster Detail |
:----------:|:----------:|:----------:|
<img src="https://file.notion.so/f/s/7fa96441-f6a7-47eb-80d3-245b298ff8ad/Untitled.png?id=d6a15830-551d-4ae4-b6b7-8db6824ba1d2&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=xPkkS0uilMPGWz-joqYKj1Co0dotgZSfo5F1Te7FH4c&downloadName=Untitled.png" width=200/>|<img src="https://file.notion.so/f/s/bd89be56-e828-4098-86b8-07fd427db268/Untitled.png?id=4036a6b4-5465-48a3-8e6e-812222e23aaa&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=KMVb5yL58GwZmo712HwGv1fD7JFzz7eD2vF9C4t1opw&downloadName=Untitled.png" width=200/>|<img src="https://file.notion.so/f/s/3b4ae0cf-6443-4953-9a65-9913f415c22a/Untitled.png?id=7162952b-ffa3-4bd2-9630-d35ca6a77e76&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=lKZoixNHfJpWeoynsPVndaay-oo97XKT2NeZx-b-rI4&downloadName=Untitled.png" width=200/>|

### Dark Mode
Home screen with disaster live reports | Date range filter calendar mode | Date range filter edit text input mode | Filter applied |
:----------:|:----------:|:----------:|:----------:|
<img src="https://file.notion.so/f/s/38d01e81-3d37-458f-8a98-11f67eeddf66/Screenshot_2023-08-15_at_13.12.20.png?id=dacc1fff-6bfa-4729-a479-88648fab6e43&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=vdFWLZvURV_lj8omOvCUq3o2u06xuOo5XSWZP5QqZWs&downloadName=Screenshot+2023-08-15+at+13.12.20.png" width=200/>|<img src="https://file.notion.so/f/s/3fa53ff4-2964-425b-9c18-abf1827e3117/Screenshot_2023-08-15_at_13.12.56.png?id=4a3578e6-d544-4cb6-be6c-8e9974ad6b06&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=J1ZoyTfJF_wTgWYRagQCpHLDXozwzmrIBUdKmDphhbY&downloadName=Screenshot+2023-08-15+at+13.12.56.png" width=200/>|<img src="https://file.notion.so/f/s/8be3dc43-f26a-4f9b-ba2f-bdfb271d673c/Screenshot_2023-08-15_at_13.13.03.png?id=aece9c4d-760e-48ac-9b9a-63974336b2f3&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=jRTyEuv4jDGQXIAYKZiDD1WjUQ82WpDtWnJN4Wk-1ko&downloadName=Screenshot+2023-08-15+at+13.13.03.png" width=200/>|<img src="https://file.notion.so/f/s/f8b6b9a1-8793-48db-b151-1d8cd71f8453/Screenshot_2023-08-15_at_13.13.43.png?id=940fc02a-b1a3-49ed-9390-ac33d3d7e1c8&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=jQX5LQh_Sl5YLNUTSwO3E8ZigA8FM4Cl-yFcTPEEugU&downloadName=Screenshot+2023-08-15+at+13.13.43.png" width=200/>|

Search based on province area | Setting | Disaster Detail |
:----------:|:----------:|:----------:|
<img src="https://file.notion.so/f/s/c82ead34-f083-4006-983e-399daadaa584/Screenshot_2023-08-15_at_13.13.58.png?id=6cc93f75-65f1-4dfc-8e5f-87cdefaf665c&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=F7ialpg89mHKt2Geno7AVoO1hotFhZUyJSZ0HAZeHDw&downloadName=Screenshot+2023-08-15+at+13.13.58.png" width=200/>|<img src="https://file.notion.so/f/s/331372b1-c82e-41ca-ad5f-e38073f9be6f/Screenshot_2023-08-15_at_13.14.12.png?id=ca212fac-1644-4f8e-9293-599c7a962be6&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=9iawAWm1vHg0LbR4mGIcNqPNcYAbxDsEuQDmT3zYy2E&downloadName=Screenshot+2023-08-15+at+13.14.12.png" width=200/>|<img src="https://file.notion.so/f/s/1a75eae1-67be-45f9-ba10-e13c4876801a/Screenshot_2023-08-15_at_13.14.18.png?id=ae262298-11be-475d-bbf1-feee1cde1ffc&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=wCMoQa8SyGrqIK-7VXBLqfzrsMMg0MKcBB0JTrDWTcQ&downloadName=Screenshot+2023-08-15+at+13.14.18.png" width=200/>|


## Architecture
### Single Activity
Dismaps are build on a single activity, which will host multiple fragments, each with distinct responsibilities.
\
\
<img src="https://file.notion.so/f/s/a8e6d258-5d8c-4d5c-9119-6596237eb858/Untitled.png?id=50772717-f114-4662-a4dd-6f5ca41f2cf2&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=2Apix3PGCHh2Sqe_EQq_jiFsqUznXEa3TORrVMPkslw&downloadName=Untitled.png" width="600">

Main Activity will host four fragment containers, including the header container, main container, bottom sheet container, and bottom sheet header container. These fragment containers are not limited to hosting specific fragments; instead, they are reusable depending on the context and state.
\
\
<img src="https://file.notion.so/f/s/32daa08c-a6c7-41b8-bd8b-82107263494c/Untitled.png?id=c705c88e-adb7-43ef-8284-c09628552929&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=43FD0UGvpc0Ypu_bOukdIDu6Ko2cVEBaINOI855KzTQ&downloadName=Untitled.png" width="300">

Main Container is the largest among the fragment containers, occupying the full width and height of its parent. Due to its large size, its role is to host components that require large screen space, such as maps, as well as to display important messages like errors or empty states.
\
\
<img src="https://file.notion.so/f/s/6a0ffb14-4ffc-439c-9fca-9bbbefcb990a/Untitled.png?id=973ca816-ba10-4147-8a59-15a6ffde6056&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=AK4tqWdQ0zsnkLHTEwSQC1-wAHc6wwBSB3D7wwYRcu8&downloadName=Untitled.png" width="300">

Header Container is floating at the top of the screen, its role is to display filter and search bar. In the context of a detailed disaster view, the Header Container also takes on the responsibility of hosting navigation buttons.
\
\
<img src="https://file.notion.so/f/s/d45f8487-e16a-49d3-9e7b-642e9b98e8a5/Untitled.png?id=820766de-7776-44e8-9b7a-0af0e545d017&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=BlJKVtu5SJbCDagpyDtwFUpyJvbYKO_vqttZrHIiKEE&downloadName=Untitled.png" width="300">

Bottom Sheet Container is located inside bottom sheet, granting it draggable behavior and three different states: expanded, half-expanded, and collapsed. This behavior makes the bottom sheet container ideal for hosting scrollable content such as list and detailed information.
\
\
<img src="https://file.notion.so/f/s/ba0b42ed-d145-44a2-96c7-40e7e6cf533f/Untitled.png?id=1af71cab-4d30-414e-8bde-82c964db9e73&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=tcscDCx4F68lLsp8BoptharbsyfeBW1aemFb8npF5iA&downloadName=Untitled.png" width="300">

Header Bottom Sheet Container will anchored to the top of bottom sheet make it easily noticeable by user, so its responsibility is to host conclusion of information and current states.

### Data Sharing Between fragments

<img src="https://file.notion.so/f/s/f29f2dab-490e-4a09-97a1-35665f3c7c9f/Untitled.png?id=9b56665d-ef17-4ef8-ad48-4fe2f8c6bd5a&table=block&spaceId=cbe2ffd1-f69e-47c5-ba1d-5235b63653d2&expirationTimestamp=1699020000000&signature=E96eFgaFKx93GpX4QlpJxcrCDH1zy_MV8WYoJPb9psc&downloadName=Untitled.png" width="800">

These fragments hosted within main activity share common data, which is disaster reports. In addition to the reports, each fragment also maintains states that are utilized to retrieve disaster reports. For example, the list of disasters displayed in the disaster list fragment is generated based on the filter state on search bar fragment. Based on this problem, view model serve as state holder and bridge to domain layer, while view model must be observed and utilized within each fragment, but not attached to fragment lifecycle, so view model need to be crated at fragment but using activity as view model owner so view model scoped to activity lifecycle.

Main activity also need to observe main view model since its role is utilized fragment transaction, decide which fragments are currently displayed. Beside fragment transaction, main activity also had responsibility to manage bottom sheet state. Although each fragment depends on other fragment, this approach make fragment only coupled to view model. For example, disaster list fragment only had one responsibility which is displaying disaster list, its doesn't know if data retrieval is success or not, if filter is applied or not, its only focus on one task, displaying disaster list.

Setting dialog fragment is a dialog that displayed on the top of activity, so its doesn't need fragment container. Setting dialog had different business flow, which is retrieve and utilizing setting data from data store. Since its had different business flow, setting dialog should have separate view model, but in this case fragment as view model owner, so its attached to fragment lifecycle.

### Dependency Injection

After project already had well-separated functional set, now its need construction set that will wiring everything together. Dependency injection will act as constructor to serve dependency to all part of the application with various scope. As stated on previous section, different process flow will had different view model, so its need dependency that scoped to view model. Dependency also need to injected to worker for notification alert. Based on this needs, Dismaps used Hilt as dependency injection framework since its cover all this need:

- @HiltViewModel to inject dependency to view model, so doesn't need to create view model factory.
- @HiltWorker to inject dependency to worker, so doesn't need to create worker factory.
- @InstallIn(ViewModelComponent::class) to scope component to view model.
- @InstallIn(SingletonComponent::class) to scope component to application.
