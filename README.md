# Essences of a Game of President (Unfinished)

This was supposed to be a recreation of the card game President, but due to time constraints, I couldn't finish it.
Created uing Java's Swing UI library

## What Has Been Implemented

- **Cards** (Card.java)
  - A card in the game representing all 54 playable cards in the real life version has its features inplemented
    - A card value
    - Comparison system which takes into account the rank and suite of the cards
    - 54 png images of every card in game

- **Combos** (Combo.java)
  - Doubles, Triples, Quadruples, and pokar combos can be made with a combination of cards from 2-5
  - this class deals with checking if the inputted combination of cards is a double, triple, quadruple, or a designated pokar combo
  - These pokar combos include
    - Streight Flushes 
    - Bombs
    - Royal Flushes
    - Full Houses
    - Flushes
    - Streights
  - These Combos all have their own ranking, comparisons between combos also implemented (the program can differentiate which combo has been played and which combo is bigger)
- **Players** (Player.java)
  - There are 4 players in the game and each of them divide the deck into equal hands
  - a player can select up to 5 cards to play by clicking on the cards displayed at the bottom of the screen
  - hitting enter will send in their combination of cards to check with the cards that are currently on the table
- **The Gameframe** (Gameframe.java)
  - This file is responsible fro running the game
  - It distributes the cards to the players
  - A Keylistener has been implemented to react when the player presses a certain key
  - Graphically speaking, the background is set to a background image of a table
  - Each player is initalized to be a JPanel and is placed on top of the jframe using a CardLayout layout manager making it easy to switch between players   
