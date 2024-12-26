=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Inheritance / subtyping -- I used the ChessBoard as my parent class and implemented functionality from the Piece and the chess class
  as chess is simply a field in the ChessBoard class, and the ChessBoard extends its functionality, It does a similar thing with Piece
  as I used Piece as a child class of the ChessBoard, where I did most of my visualization.

  2. Collections -- I used LinkedLists throughout this assignment for pretty much everything I did
  all of the pieces were stored in linked lists and could be easily moved and manipulated across the game board

  3.JUnit testable component -- When implementing the piece class, which was the backbone for every piece on the board,
  I implemented jUnit testing that tested all of the different aspects of the Piece class and also used testing when I 
  approached the chess class , I did not test as much when I got to the ChessBoard because most of that could be visualized

  4.Complex Game Logic -- I attempted to implement all of the different rules of chess in order to provide as accurate of a game as possible
  I have different instances of check and check mate, and king movement that is consistent with how chess is traditionally played

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

I have the piece class which represents each of the pieces in the game, their movement, and where they can go.
I have the GameBoard class which deals with most of the actual movement and visualization of the game.
And I have the chess class that sets up the game.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

I definitely had trouble implementing some of the functions that I did not necessarily prepare for.
I feel like I had somewhat short-sighted vision in the assignment so when it came to implementing difficult 
things I was not as prepared for the implemenetation as I could have been.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I would definitely redo this assignment and make it much neater and more concise.
I found myself reusing code over and over again and definitely could have cut down on run time and 
code space if given more time.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  I used https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent for the pictures of the pieces.
