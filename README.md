# Snake Hunt Word Puzzle Solver (Schlangenjagd)

A Java application developed as part of a university programming practicum (FernUni Hagen, SS23). It solves "Snake Hunt" puzzles: given a grid of letters (the "jungle"), the solver finds word snakes — continuous paths through the grid that spell out target words, respecting configurable neighborhood rules and field usage limits.

## The Problem

Each puzzle defines a letter grid, a set of snake types (target words), and rules for how snakes can move between cells (orthogonal, diagonal, knight's move, etc.). Snakes must form valid continuous paths, and each grid cell has a usage limit constraining how many snakes can pass through it. The goal is to place as many snakes as possible to maximize the total score.

## How It Works

The core is a **recursive backtracking algorithm** that builds snakes character by character, validating placements against neighborhood rules and field usage constraints. It uses heuristics for snake type prioritization and starting position selection, with configurable time limits for bounded execution.

Puzzle instances are defined in **XML with DTD validation**, parsed and written using the JDOM library.

## Features

The CLI supports multiple operation modes, which can be chained:

```bash
java -jar ProPra.jar process=l  input=puzzle.xml output=solution.xml  # Solve
java -jar ProPra.jar process=e  input=template.xml output=new.xml     # Generate
java -jar ProPra.jar process=p  input=solution.xml                    # Validate
java -jar ProPra.jar process=b  input=solution.xml                    # Evaluate/Score
java -jar ProPra.jar process=d  input=puzzle.xml                      # Display
java -jar ProPra.jar process=lepb input=puzzle.xml output=result.xml  # Combined
```

## Project Structure

The project follows an **MVC architecture** with interface-based component separation:

```
src/de/fuhagen/course01584/ss23/
├── main/           # Entry point, CLI, API interface
├── algorithm/      # Backtracking solver, puzzle generator, validation & scoring
├── model/          # Jungle grid, fields, snake types, neighborhood structures
├── ioprocessing/   # XML reading/writing (JDOM)
└── view/           # Console-based text visualization
```

Neighborhood structures use the **Strategy pattern** — `DistanceNeighborhood`, `JumpNeighborhood`, and `StarNeighborhood` are interchangeable implementations behind a common `INeighborhood` interface.

## Tech Stack

- **Java 19** | **JDOM** (XML processing) | **JUnit 5** (testing)
