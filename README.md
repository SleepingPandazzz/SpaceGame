# SpaceGame
The Version 1 of the Space Game is text-based. The goal of the version 1 is to use keyboard input commands to control and display the contents of a "game world" containing a set of objects in the game.

The Version 2 of my Space Game is to extend the Version 1 to incorporate several design patterns and a Graphical User Interface(GUI). The Version 2 follows the Model-View-Controller(MVC) architecture. The Game class is the controller, which manages the flow of control in the game. The GameWorld class is the data model, which holds a collection of game objects and other state variables. The PointsView class and MapView class are views. The design patterns used in the Version 2 are Observer/Observable design pattern (to coordinate changes in the data with the various views), Iterator design pattern (to walk through all the game objects when necessary), Command design pattern (to encapsulate the various commands the player can invoke), and Proxy design pattern (to insure that views cannot modify the game world).

In Version 3, the text-based map will be replaced with an interactive graphical map.

In Version 4, the game is extended to include 2D transformations and Bezier curves.
