
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

This are the minimal requisites. Once we have this covered we will make better updates of the game.
