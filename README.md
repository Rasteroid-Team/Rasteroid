
# Rasteroid
2D topdown shooter game that runs on multiple devices at once to join screens.

## Game Definition
- A topdown 2D shooter game that runs simultaneously on multiple screens using __P2P connections__. 
- Each player can connect to the game using the __app as a remote controller__ for his ship.
- The remote controller will have a joystick and a "shoot" button.
- Ships can __rotate and boost__ to move. When a ship boost it adquires speed in the direction it is facing depending on the boost strenght (defined by the joystik). When a ship shoots the bullet will be triggered in the direction the ship is facing. ([Ship movement example](https://www.youtube.com/watch?v=WYSupJ5r2zo))
- Screen borders can be toggled as walls. If a player hits the screenborder and it has no wall, __the player will be sent to the next screen__.
- A match will have defined a __set of gamerules__. This gamerules can be changed before each match or in the midle of one. Gamerules can define the base health of the players, the speed, the match goal...
- All game objects will be able to handle diferent types of collisions.

Rasteroid is a 2D multiplayer shooter game. The game has a top down perspective and the players move simultaneously around the map. The matches can be customized in many ways using sets of gamerules to define physics, events, the match goal, etc. The players controles their ships using an android app as a controller that is going to communicate with the game. Rasteroid supports multiple screens to extend the game environment, screens communicate each other via peer to peer and objects can jump from one screen to another.

### Game Components:

- Screen: is an environment limited with 4 walls that holds all the game objects like players or bullets.

- Walls: are the limits of the screen and can be abled and disabled the way that when the player collides with one it can bounce or be sent to the next screen.
  
- Player: is an object controlled by a player. It can shoot bullets to the front, change its direction and propel in the facing direction. ([Ship movement example](https://www.youtube.com/watch?v=WYSupJ5r2zo))

### The match:

The goal of the matches will be defined by the rules of the game, but the default game mode will be an “all against all” battle where only the last one standing wins.

At first the characters will be displayed on the map so the players can find itselves on the screen (in this period no player can be damaged or deal damage). After the peaceful period ends the match will start and the players will start battling. During the match random events could appear affecting the game dynamics. When a player ends alone in the match will be shown as the winner and the match ends.

###  Controller:

Each player who wants to play needs an android device with the controller app installed. This app has a joystick that defines rotation and propel intensity and a button to shoot. The controller will be connected with the game screen where his character is located. The game screen will receive the player inputs and send signals to make the controller vibrate for example.

## Guide for git
Each team will be working on their own branches at the beginning, all of them will be clones from main.
Once a team is happy with their job and tested that it does what it's supposed to do a merge is created from the team's branch to the main branch.
This merge will only be accepted by the team leaders (aka Jaume Fullana or Joan Gil)

In each team branch there can be any number of sub-branches, for example if a team wants to divide the jobs between a few members separately they may create 2 branches from their teams branch, those new sub-branches may only join back into the teams branch NEVER into main.
The code nomenclatures, comments and everything related to the project will be in English please.
