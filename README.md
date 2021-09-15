## Features

- Can schedule WhatsApp messages for a custom date.
- Handle data through xslx file.
- Send a message to multiple contacts etc. (905xxxxxxxx1,905xxxxxxxx2)
- Send message to all of your contacts with **Herkes** boolean. (evet = true, hayır = false)
- And easy data modification with excel.

## Screenshots
![image](https://user-images.githubusercontent.com/22749769/133515655-4db442e1-60fb-4d42-b4ac-614894654751.png)
![image](https://user-images.githubusercontent.com/22749769/133515339-bd84937b-cdbc-4a1f-983c-836a81f85a3e.png)
![image](https://user-images.githubusercontent.com/22749769/133515588-37733ddd-75a4-423e-8341-1513f82522c9.png)

## Dependencies

This project requires **Java 16+**
All dependencies are managed by Maven Shade Plugin.

* WhatsApp4J
	* Version: 2.2.1
	* [Github](https://github.com/Auties00/WhatsappWeb4j "Github")
* Lombok
	* Version:  1.18.20
	* [Github](https://github.com/projectlombok/lombok "Github")
* Apache Poi
	* Version: 3.15
	* [Github](https://github.com/apache/poi "Github")
* Apache Poi ooxml
	* Version: 3.15
	* [Github](https://github.com/apache/poi/tree/ooxml "Github")
* Jetbrains Annotations
	* Version: 22.0.0
	* [Github](https://github.com/JetBrains/java-annotations "Github")
	
## TODO Feature
* ~~Whatsapp auto connect socket when task occur instead of always on.~~ 
    * ~~Taskchain to make task after socket connection~~ ❌
**(Cancelled due WhatsAppWeb new Beta program. Will be update for it when release.)**
* ~~Web authentication QRCode isn't working properly. **Bug Fix**~~ ✅
* Remote task creator with WhatsApp message to self. ⌛
