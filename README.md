# Health In Pocket

![Health In Pocket](https://github.com/IslamKhSh/HealthInPocket/blob/master/app/src/main/res/drawable-xhdpi/logo_small.9.png)

A medical App that can get medical measurments from the patient using some sensors connected via Bluetooth and also can store with the patient account to build patinet medical history.

![Health In Pocket](https://github.com/IslamKhSh/HealthInPocket/blob/master/screenshots/IMG_2538.JPG)
#
In this App I implemented many Android topics as:
  - Firebase: Auth & Database.
  - Android Architecture components.
  - MVVM pattern design.
  - Butterknife.
  #
 
 To try this app [Download it now](https://drive.google.com/open?id=1OAC88TLtTIhKu3zKumx1sQN3w5spaGeO)
 
 <img src="https://github.com/IslamKhSh/HealthInPocket/blob/master/screenshots/Login%20Screen.png" width="360">   <img src="https://github.com/IslamKhSh/HealthInPocket/blob/master/screenshots/Sign%20up%20Screen.png" width="360">
 
<img src="https://github.com/IslamKhSh/HealthInPocket/blob/master/screenshots/CheckUp%20Fragmet.png" width="360">   <img src="https://github.com/IslamKhSh/HealthInPocket/blob/master/screenshots/History%20Fragment.png" width="360">

<img src="https://github.com/IslamKhSh/HealthInPocket/blob/master/screenshots/Drawer%20Navigation.png" width="360">   <img src="https://github.com/IslamKhSh/HealthInPocket/blob/master/screenshots/Share%20item.png" width="360">
 
 #
 To use this project (you must have the hardawre components) then follow this steps:
  
  1- Change the MAC address constant in the [Constants class](https://github.com/IslamKhSh/HealthInPocket/blob/master/app/src/main/java/islamkhsh/com/healthinpocket/common/Constants.java) with the MAC address of your bluetooth module:
  
     //bluetooth module MAC address
     public static final String DEVICE_ADDRESS = "98:D3:32:20:BF:ED";
     
  2- Pair your bluetooth module with your Mobile.
  
  3- Create your firebase peoject and configure it with your android project.
  
  4- Let's go.
