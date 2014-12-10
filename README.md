Othello
=======

Our Othello implementation can be seen as two worlds that are connected with each other. The first world is the "model" world where a simple Othello game is represented by immutable classes for a board, nodes and each game state. GameModel serves as the mutable box that keeps track of all game states, along with the current one.

The second world is the "api-world" which serves as an connection to all standard interfaces provided by Tomas. In this world there are Adapters (see the adapter pattern) which can be both mutable and observable. 

