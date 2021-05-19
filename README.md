<div align="center">
  <img src="images/logo.png" width="80">
  <h1>Color Switch</h1>
</div>

### Table of Contents

- [Overview](#overview)
  - [Built With](#built-with)
- [Instructions](#instructions)
- [Features](#features)
  - [Other Miscellaneous Features](#other-miscellaneous-features)
- [Demo](#demo)
- [Creators](#creators)

### Overview

The popular game Color Switch, built for desktop.

#### Built With

- JavaFX
- FXML
- CSS
- Gradle

### Instructions

This project uses Gradle to build the files, create executables and run the compiled code. It must be installed on your system.

To install Gradle, please see [gradle.org/install](https://gradle.org/install/).

For development:

- Run: `gradle run`
- Build (normal JAR): `gradle build`
- Clean: `gradle clean`

### Features

- **Obstacles**:
  We implemented 4 types of obstacles.

<table>
  <tr>
    <th width="50%">Circle</th>
    <th width="50%">Square</th>
  </tr>
  <tr>
    <td> <img src ="images/circle.gif">
    <td> <img src ="images/square.gif">
  </tr>
  <tr>
    <th width="50%">Horizontal Bar</th>
    <th width="50%">Gears</th>
  </tr>
  <tr>
    <td> <img src ="images/bar.gif">
    <td> <img src ="images/gears.gif">
  </tr>
</table>

A new obstacle is dynamically added when the ball collides with a color changer.

- **Collision**: Collision of the ball with the obstacles, stars and color changers has been detected through a combination of `BoundsIntersects` and `BoundsTransformation`.

- **Speed Calculation**:

  - We use the duration since the last user input to calculate the ball’s new position.
  - Acceleration due to gravity is a constant, and ball’s velocity depends on game score/difficulty.
  - The track shifts down once the ball reaches halfway on the screen.

- **Difficulty**:
  The game increases in difficulty as the user collects more points, after a threshold of 5 points.

  - **Gears** obstacle is introduced, which is relatively more difficult to cross than the other obstacles.
  - The displacement of the ball at every jump also linearly increases as the user collects more points.

- **Saving the Game**: The game is saved in 2 ways:
  - Serializing the state of the gameplay screen every time the user saves the game.
    - Ball color and position
    - Obstacle types, position and orientation
    - Star values
    - Game score
  - Serializing the instance of `ColorSwitch` which contains the statistics of the game and the list of saved games.

#### Other Miscellaneous Features

- Two types of stars
  - One point
  - Three points
- Statistics
  - Total stars collected
  - Highscore
  - No. of games played
  - Total time played
  - No. of revivals
  - No. of games saved
- Instructions
- Settings
- Gameplay Music
- End Game Animation

### Demo

<table>
  <tr>
    <th width="50%">Gameplay</th>
    <th width="50%">More Gameplay</th>
  </tr>
  <tr>
    <td> <img src ="images/gameplay.gif">
    <td> <img src ="images/more_gameplay.gif">
  </tr>
  <tr>
    <th width="50%">Saved Games</th>
    <th width="50%">Instructions</th>
  </tr>
  <tr>
    <td> <img src ="images/saved_games.gif">
    <td> <img src ="images/instructions.gif">
  </tr>
    <tr>
    <th width="50%">Statistics</th>
    <th width="50%">Settings</th>
  </tr>
  <tr>
    <td> <img src ="images/statistics.gif">
    <td> <img src ="images/settings.gif">
  </tr>
</table>

### Creators

- **[Ananya Lohani](https://ananyalohani.me/)** | [GitHub](https://github.com/ananyalohani/)
- **[Mihir Chaturvedi](https://mihir.ch/)** | [GitHub](https://github.com/plibither8/)
