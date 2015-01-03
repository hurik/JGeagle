# JGeagle

## Information
If you are working on an [CadSoft EAGLE](http://www.cadsoftusa.com/eagle-pcb-design-software/) project with a team and you are tracking your progress with [Git](http://git-scm.com/), this little program can help you to see what your team has changed. It makes an diff image which shows the changes on the schematics and boards between the commits.

You can test it with the [JGeagle-testrepo](https://github.com/hurik/JGeagle-testrepo).

## Download
[https://github.com/hurik/JGeagle/releases/latest](https://github.com/hurik/JGeagle/releases/latest)

## Requirements
* Windows or Linux (Can't test it under Mac ...)
* [Java 7](http://www.java.com/de/download/) or OpenJDK 7
* [CadSoft EAGLE 6](http://www.cadsoftusa.com/eagle-pcb-design-software/) (Tested with 6.X.X and the EAGLE Light Edition is also enough.)

## How to use
1. Download and extract in a folder.
2. Start `JGeagle.jar`, with a doubleclick or `java -jar JGeagle.jar`.
3. Open the Preferences (Options -> Preferences) and setup the Eagle binary.
5. Open a repo (Repository -> Open).
6. Select a file, the commits and sheet or layer.
7. Click on "Make and/or show".

## Feature requests and bug report
[https://github.com/hurik/JGeagle/issues](https://github.com/hurik/JGeagle/issues)

## Contribute
Your help is greatly appreciated.

Developed with [NetBeans 8.0.2 (Java SE)](https://netbeans.org/downloads/).

## Notes
* Git repo must be in a directory, because it will the repo name for the temp images.
* Renames only working when committed. Can't get this working [http://stackoverflow.com/a/17302068/2246865](http://stackoverflow.com/a/17302068/2246865)!
* While counting sheets or exporting images, eagle opens for a second and closes right after it. Under Linux it sometimes takes some time until it closes. Don't know why ...
* The JGeagle temp files are in HOME/.JGeagle/*

## Todo
* Sometimes you get an exception. Will be fixed soon.
* The speed of the creation of the diff image must be improved.

## Example diff image
**Description:**
* Brightened elements were not changed
* Red elements were deleted
* Green elements are new
* Grey points are undefined 
* Red and green elements which are connected, can be moved elements

### Schematic diff image
![Schematic diff image](https://raw.github.com/hurik/JGeagle/master/images/schematic-diff.png)

### Board diff image
![Board diff image](https://raw.github.com/hurik/JGeagle/master/images/board-diff.png)

## Program screenshots

### JGeagle
![JGeagle](https://raw.github.com/hurik/JGeagle/master/images/jgeagle.png)

### Image viewer
With mouse zoom and pan!

![JGeagle - Image viewer](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-imageviewer.png)

### Preferences
![JGeagle - Preferences](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-preferences.png)

### Create images
You can create all images for faster usability.

![JGeagle - Create images 1](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-createimages-1.png)

![JGeagle - Create images 2](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-createimages-2.png)

### Delete images
You can remove images.

![JGeagle - Delete images 1](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-deleteimages-1.png)

![JGeagle - Delete images 2](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-deleteimages-2.png)
