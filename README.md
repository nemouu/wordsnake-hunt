# Snake Hunt Word Puzzle Solver

A modernized Java application for solving "Snake Hunt" puzzles. Originally developed as part of a university programming practicum (FernUni Hagen, SS23), it has been restructured for modern development standards.

## The Problem

Each puzzle defines a letter grid (the "jungle"), a set of snake types (target words), and rules for how snakes can move between cells (orthogonal, diagonal, knight's move, etc.). Snakes must form valid continuous paths, and each grid cell has a usage limit constraining how many snakes can pass through it. The goal is to place as many snakes as possible to maximize the total score.

## How It Works

The core is a **recursive backtracking algorithm** that builds snakes character by character, validating placements against neighborhood rules and field usage constraints. It uses heuristics for snake type prioritization and starting position selection, with configurable time limits for bounded execution.

Puzzle instances are defined in **XML with DTD validation**, parsed and written using the JDOM library.

## Project Structure

The project follows an **MVC architecture** with interface-based component separation:

```
src/main/java/org/snakehunt/
├── main/           # Entry point, CLI, API interface
├── algorithm/      # Backtracking solver, puzzle generator, validation & scoring
├── model/          # Jungle grid, fields, snake types, neighborhood structures
├── ioprocessing/   # XML reading/writing (JDOM)
└── view/           # Console-based text visualization
```

Neighborhood structures use the **Strategy pattern** — `DistanceNeighborhood`, `JumpNeighborhood`, and `StarNeighborhood` are interchangeable implementations behind a common `INeighborhood` interface.

## Build and Run

### Maven
The project uses Maven for dependency management and building.

```bash
# Build the fat JAR
mvn clean package

# Run the solver
java -jar target/wordsnake-hunt-1.0.0-jar-with-dependencies.jar process=l input=res/sj_p1_probleminstanz.xml output=res/sj_p1_loesung.xml
```

### Docker
You can build and run the application using Docker for a consistent environment. Some examples are shown below.

```bash
# Build the image
docker build -t wordsnake-hunt .
```
**Solve a puzzle instance:**
```bash
docker run --rm -v ${PWD}/res:/app/res wordsnake-hunt process=l input=res/sh_p1_problem_instance.xml output=res/sh_p1_my_solution.xml
```

**Generate a puzzle instance:**
```bash
docker run --rm -v ${PWD}/res:/app/res wordsnake-hunt process=e input=res/sh_p1_incomplete.xml output=res/sh_p1_generated_puzzle.xml
```

**Generate and solve (combined workflow):**
```bash
docker run --rm -v ${PWD}/res:/app/res wordsnake-hunt process=el input=res/sh_p1_incomplete.xml output=res/sh_p1_full_result.xml
```

**Visualize a puzzle grid:**
```bash
docker run --rm wordsnake-hunt process=d input=res/sh_p1_problem_instance.xml
```

**Validate and score a solution:**
```bash
docker run --rm wordsnake-hunt process=pb input=res/sh_p1_solution.xml
```

These examples use actual files from the `res/` directory and demonstrate the most common use cases. The `--rm` flag automatically cleans up the container after execution.

## CLI Features

The CLI supports multiple operation modes, which can be chained:

| Flag | Mode | Description |
| --- | --- | --- |
| `l` | Solve | Finds solutions for the given puzzle. |
| `e` | Generate | Generates a new puzzle instance. |
| `p` | Validate | Validates an existing solution. |
| `b` | Evaluate | Scores a solution. |
| `d` | Display | Visualizes the puzzle grid. |

The operations can be combined, a few examples are shown above.

## Tech Stack

- **Java 21** (Build & Runtime)
- **Maven** (Build Tool)
- **Docker** (Containerization)
- **JDOM 2** (XML processing)
- **JUnit 5** (Testing)

## Future plans

A web-based user interface is planned to make the Snake Hunt solver more accessible and provide better visualization of puzzles and solutions.

**Technical Approach:**
- Backend: Spring Boot REST API
- Frontend: HTMX and Alpine.js or React
- Visualization: SVG/Canvas for grid rendering
