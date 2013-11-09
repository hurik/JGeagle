# JGeagle

## Information
If you are working on an [CadSoft EAGLE](http://www.cadsoftusa.com/eagle-pcb-design-software/) project with a team and you are tracking your progress with [Git](http://git-scm.com/), this little program can help you to see what your team has changed. It makes an diff image which shows the changes on the schematics and boards between the commits.

You can test it with the [testrepo](https://github.com/hurik/visual-diffs-for-eagle-and-git_testrepo).


## Notes
* Git repo must be in a directory! This will be the repo name!
* Renames only working when commited. Can't get this working [http://stackoverflow.com/a/17302068/2246865](http://stackoverflow.com/a/17302068/2246865)!


## Download
Download: [Latest version](http://www.andreasgiemza.de/wp-content/uploads/2013/11/jgeagle.zip)


## Requirements
* Java 7


## Example image
**Description**
* Brightened elements were not changed
* Red elements were deleted
* Green elements are new
* Red and green elements which are connected, can be moved elements

### Schematic diff image
![Schematic diff image](https://raw.github.com/hurik/JGeagle/master/images/schematic-diff.png)

### Board diff image
![Board diff image](https://raw.github.com/hurik/JGeagle/master/images/board-diff.png)


## Program screen shots
### JGeagle
![JGeagle](https://raw.github.com/hurik/JGeagle/master/images/jgeagle.png)

### Preferences
![JGeagle](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-preferences.png)

### Image viewer
* With mouse zoom and pan!

![JGeagle](https://raw.github.com/hurik/JGeagle/master/images/jgeagle-imageviewer.png)
