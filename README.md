
# Bulls & Cows and Wordle Game (Java)

A console-based code-breaking game written in Java that lets you play **Bulls & Cows** against the computer with three AI difficulty levels, or enjoy a round of **Wordle** using a dictionary file.

This project was completed as part of a university-level assignment to demonstrate **object-oriented design**, **AI logic**, and **file I/O**. It features a structured architecture with UML-based design and includes various levels of game AI—easy, medium, and hard—using intelligent deduction strategies.

---

## 🎯 Game Overview

### 🔢 Bulls and Cows

- **Objective**: Guess the opponent’s 4-digit secret code (all digits unique).
- **Gameplay**: You and the computer take turns guessing each other’s code.
- **Scoring**:
    - **Bull** = Correct digit in the correct position
    - **Cow** = Correct digit in the wrong position
- **AI Levels**:
    - **Easy**: Random valid guesses
    - **Medium**: Avoids duplicate guesses
    - **Hard**: Uses feedback from previous guesses to eliminate impossible candidates (Knuth-like logic)
- **Attempts**: 7 turns per player

### 🔤 Wordle

- **Objective**: Guess a 5-letter English word (with no repeating letters).
- **Gameplay**: One-player mode, 6 attempts to guess the word.
- **Dictionary**: Uses a filtered list from `dictionary-v1.txt` (included in repo).
- **Feedback**: Based on position and presence of letters, similar to Wordle rules.

---

## 🧠 AI Strategies

### Hard AI (Bulls & Cows)
Uses an elimination-based algorithm:
1. Starts with all possible 4-digit combinations (no repeats).
2. Guesses a code and records the bulls/cows result.
3. Eliminates all codes inconsistent with **all** previous feedback.
4. Randomly selects a valid guess from the remaining possibilities.

---

## 🗃 Features

- Interactive console gameplay
- Validity checking for user input
- AI difficulty selection
- Wordle mode with dictionary loading
- Results saving to file
- Clean and modular class-based design

---
🙋‍♀️ Author
Annie Lin
Master of Information Technology @ University of Auckland

---

📌 Acknowledgments
University of Auckland Assignment Spec
Original Wordle rules by Josh Wardle
Inspiration from Knuth’s five-guess algorithm