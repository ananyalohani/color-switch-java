# color-switch-java

#### Members:

- Ananya Lohani, 2019018
- Mihir Chaturvedi, 2019061

## Instructions

This project uses Gradle to build the files, create executables and run the compiled code. It must be installed on your system.

To install Gradle, please see [gradle.org/install](https://gradle.org/install/).

For development:

- Run: `gradle run`
- Build (normal JAR): `gradle build`
- Clean: `gradle clean`

The JAR files can be found in `app/build/libs/` directory.

## TODO

- [x] Add collision detections
  - [x] w/ obstacles
  - [x] w/ stars
  - [x] w/ color changer
- [x] obstacles pause on pause menu (and timertask)
- [x] restart game with stars minus
- [x] increase difficulty
- [x] pause game hotkey !!
- [x] dynamically add new objects
  - [x] stars
  - [x] obstacles
  - [x] color changer
- [x] update score
- [x] implement serializable
  - [x] ColorSwitch
  - [x] saved games
- [x] restoring game
  - [x] restore ball color
  - [x] restore star value (??)
  - [x] position ball
  - [x] position obstacles more precisely
  - [x] remove stars/colorchangers
  - [x] keep starCount/prevStarValue
- [x] statistics !!
- [x] help scene !!
- [x] settings scene !!
  - [x] turn music on/off
- [ ] ~~adding music/sound (that old banger) !!~~
