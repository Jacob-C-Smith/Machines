# Project 3: Turing Machine Simulator

## Overview

### TODO: Machine
### TODO: Step
### Tape
Representing a biinfinite tape with a single array is fraught with problems. The read head may move into negative indicies, and fall out of bounds. Both of these contingencies cause an ```IndexOutOfBounds``` exception. At first, we considered a single array approach, where the read head would start in the middle of the array. Starting with the head in the middle caused mechanical challenges. 

We opted to use two arrays. The former, for the tape cells with negative indicies, and the latter for tape cells with positive indicies. Henceforth known as "negative tape" and "positive tape". The negative tape represents the tape to the left of the tape head, at t0. The positive tape represents the tape to the right of the tape head, at t0. The sign of the read head informs which array we access. This caused another problem; two zeros. This problem has been solved with two's complement, and we followed suit. This required a little bit of index arithmetic.

The step function performs reads before writes. Inside the tape read method, we enclosed the array access in a try/catch block, such that we can catch ```IndexOutOfBounds``` exceptions. In the catch block, we resize either the negative tape or the positive tape, depending on the sign of the read head. We spent some time experementing with the growth factor, and we found that 1.5 produced the best results

#### TODO: Insert Picture of results

## Reflection

### Jon
I was a little disappointed with how the last two projects went and felt that the combined efforts of Jacob and I amounted to more than what we turned in. As such, I dedicated a lot of time to putting in the thought with Jacob about the type of data structure we wanted to use. Luckily, I have a pretty strong statistics background, so when time came to test the ideas that Jacob and I settled on, we knew how approach a quantitative analysis. In the end, I was very happy with how this turned out. Jacob and I brainstormed and analyzed a lot, and when time came to code the project and write this README, we really did a great job of filling in eachothers' gaps. 

### Jacob

#### Preface
I wasn't happy with how the last two projects turned out. I knew I could do better than those flaming trash piles. The extra credit points gave me extra incentive to try hard. Only time will tell if these efforts were fruitful, but I've got a good feeling...

```bash
$ time java TMSimulator.java 1> /dev/null
real    0m0.990s
user    0m1.022s
sys     0m0.014s
```

#### Back to the drawing board
I went back to the drawing board. Since the priority is performance, I decided to throw every object oriented container straight into ```/dev/null```. Good riddence. Instead, I based my implementation off of the venerable array. 

#### Tape interface
Divide 3 TM from HW 7

![](turing_tape_interface_test/x_data_structure_y_runtime.png)


#### 1,000 runs later...
![](1000_test_stats.png)
![](00_5_percent_lows.png)
