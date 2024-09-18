# Project 1: Deterministic Finite Automata

[![Machines](https://github.com/Jacob-C-Smith/Machines/actions/workflows/make.yml/badge.svg)](https://github.com/Jacob-C-Smith/Machines/actions/workflows/make.yml)

## Overview

* Author: Jon Flores, Jacob Smith
* Class: CS361 002
* Semester: Fall 2024

DFA program. Takes in a series of nodes as a string and allows for the setting of transitions between given nodes and creates a map. Functionality also allows for swapping of transitions between one another for any node. 

## Reflection
### Jon:
I enjoyed this project and working with Jacob. To avoid merge conflicts, we decided to only work from one machine (Jacob's) and to only work on the project when we could meet in person. As such, all the pushes are from his machine on the github repo associated with this project. That being said, we wroked really well together in bouncing ideas and understandings back and forth between eachother. We both made sure that the other was understanding both the concepts and the code as we progressed.

### Jacob:
This assignment wasn't too bad. As such, I'll provide some highlights of the assignment

#### Hash map vs. LinkedHashMap and Set vs. HashSet
The initial implementation used HashMap and HashSet. These functioned correctly until we started testing. We found that the toString method must preserve the order that transitions and characters were added. After consulting the Java documentation, we found that the ```HashSet``` and ```LinkedHashMap``` classes could satisfy this constraint. Thank goodness for interfaces, otherwise we would have had to reimplement the class.

#### Serializable interface
The ```swap``` method required that we implement a deep copy. Deep copying is error prone, and I'm lazy. I utilized the ```Serializable``` interface to write the class to a file, and re-read it. This left me with two identical copies of the DFA. The parent DFA was left as is. The cloned DFA was modified as neccesary, and returned to the caller. 

#### Example
I added an example driver that tests a variety of strings, and prints results to standard out. 

#### CI/CD 
I set up GitHub actions to build the project when Jon or I push. The GitHub actions build the project, and run the tester. The  badge at the top of this file is green; Green is good.

#### Accepting strings
This was the most fun part of the project. I opted to write a recursive implementation, because it seemed like the right choice. I figure I could've saved a single class field if I used an iterative approach. The current state could be stored in the scope of the function, as opposed to storing the current state in the class. It's not particularly important.

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
 - Constructed DFA - 
Q = { 3 0 1 2 }
Sigma = { 0 1 }
delta = 
         0       1
3       3       3
0       1       0
1       3       2
2       1       1
q0 = 0
F = { 3 }

 - Results - 
 0   --> Rejects
 1   --> Rejects
 00  --> Accepts
 01  --> Rejects
 10  --> Rejects
 11  --> Rejects
 000 --> Accepts
 001 --> Accepts
 010 --> Rejects
 011 --> Rejects
 100 --> Accepts
 101 --> Rejects
 110 --> Rejects
 111 --> Rejects
 ```
 [Source](main.java)

## Tester
 To run the tester program, execute this command after building
 ```
 $ java -cp .:lib/junit-platform-console-standalone-1.9.2.jar org.junit.runner.JUnitCore test.dfa.DFATest
 ```
 [Source](test/dfa/DFATest.java)
 
 [Tester output](https://github.com/Jacob-C-Smith/Machines/actions/workflows/make.yml)
 ## Definitions
 ```java
 public class DFA
{

    // Constructors
    public DFA ( );
    
    // Mutators
    public boolean addState      ( String name );
    public boolean setFinal      ( String name );
    public boolean setStart      ( String name );
    public boolean addTransition ( String fromState, String toState, char onSymb );
    public void    addSigma      ( char   symbol );

    // Accessors
    public boolean        isFinal  ( String name );
    public boolean        isStart  ( String name );
    public Set<Character> getSigma ( );
    public State          getState ( String name );
    
    // Evaluators
    public boolean accepts ( String s );

    // Deep copiers
    public DFA swap ( char symb1, char symb2 );

    // Stringifiers
    public String toString ( );
}
 ```

## Sources used

[Colorful.java](Colorful.java) - ANSI colored (s)printf. I developed this last semester in CS-455.