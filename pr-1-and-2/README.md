# Project 2: Deterministic Finite Automata

[![Machines](https://github.com/Jacob-C-Smith/Machines/actions/workflows/make.yml/badge.svg)](https://github.com/Jacob-C-Smith/Machines/actions/workflows/make.yml)

## Overview

* Author: Jon Flores, Jacob Smith
* Class: CS361 002
* Semester: Fall 2024

NFA program. Takes in a series of nodes as a string and allows for the setting of transitions between given nodes and creates a map. 

## Reflection
### Jon:
We worked for a long while on this project, and I am willing to concede defeat on the few of the tests that we did not pass by submission time. That being said, I feel that with enough time, we could have been able to fully complete this project, and this is not a slight against the project timeline, but rather, a miscalculation on my part regarding my workload this semester. I am happy enough with the results given the circumstances of this time in the semester.

### Jacob:
This project broke me. I need a break

#### Example
I extended the previous example driver

## Download
 To download Machines, execute the following command
 ```bash
 git clone https://github.com/Jacob-C-Smith/Machines
 ```

## Example
 To run the example program, execute this command
 ```
 $ java main
 ```
 ### Example output
 ```
  - Constructed NFA - 
Q = { a b }
Sigma = { e 0 1 }
delta = 
        e       0       1
a       null    [a]     [b]
b       [a]     null    null
q0 = a
F = { b }

 - Results - 
 0   --> Rejects
 1   --> Accepts
 00  --> Rejects
 01  --> Accepts
 10  --> Accepts
 11  --> Accepts
 000 --> Rejects
 001 --> Accepts
 010 --> Accepts
 011 --> Accepts
 100 --> Accepts
 101 --> Accepts
 110 --> Accepts
 111 --> Accepts
 ```
 [Source](main.java)

## Tester
 To run the tester program, execute this command after building
 ```
 $ java -cp .:lib/junit-platform-console-standalone-1.9.2.jar org.junit.runner.JUnitCore test.nfa.NFATest
 ```
 [Source](test/nfa/NFATest.java)
 
 [Tester output](https://github.com/Jacob-C-Smith/Machines/actions/workflows/make.yml)
 ## Definitions
 ```java
public class NFA 
{
    // Constructors
    public NFA ( );

    // Mutators
    public boolean addState      ( String name );
    public boolean setFinal      ( String name );
    public boolean setStart      ( String name );
    public boolean addTransition ( String fromState, Set<String> toStates, char onSymb );
    public void    addSigma      ( char symbol );

    // Accessors
    public boolean        isFinal  ( String name );
    public boolean        isStart  ( String name );
    public Set<Character> getSigma ( );
    public NFAState       getState ( String name );

    // Evaluators
    public boolean       accepts    ( String s );
    public boolean       accepts_r  ( String s );
    public Set<NFAState> getToState ( NFAState from, char onSymb );
    public Set<NFAState> eClosure   ( NFAState s );
    public int           maxCopies  ( String s );
    public boolean       isDFA      ( );
    
    // Deep copiers
    public NFA clone ( );

    // Stringifiers
    public String toString ( );
}

 ```

## Sources used

[Colorful.java](Colorful.java) - ANSI colored (s)printf. I developed this last semester in CS-455.
