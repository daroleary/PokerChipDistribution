# Poker Chip Distribution Application
Poker chip distribution application, which accepts, files as indicated below and distributes chips accordingly. For this assignment the expectation was that you'd complete the main project and indicate how you'd perform Bonus 1 & 2. Of course I went above and beyond by solving both and as an anecdote I managed to beat all the considerable tests (I was informed I was the first to do so) which were built to stress out submissions. Not trying to brag but confirm that I build applications that work and as well as being maintainable.

## Problem: Poker Chip Distribution
Write a program that calculates the optimum distribution of poker chips that maximizes the number of chips that each player receives while also making sure everyone receives the SAME breakdown of poker chips.

Input: Input will consist of 3 lines. The first line will be a comma separated list of chip quantities and denominations in the form qty/$denomination. The second line is the number of players. The third line is the buy in.

Output: The breakdown of how many of each kind of chip each player should receive. Each chip breakdown should be on a separate line in the format $denomination – number of chips.

For example:
```
Input:
50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05
10
$10.00

Output:
$2.00 - 0
$1.00 - 1
$0.50 - 10
$0.25 - 10
$0.10 - 10
$0.05 - 10
```

Note: The breakdown that is calculated must add up to equal the buy in amount.

BONUS 1: Calculate the breakdown if you require that each person receive at least one chip of each denomination. To indicate Bonus1 calculations, add a line to the beginning of the input just containing “B1”. Output is exactly as for the standard question.

```
Example Input:
B1
50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05
10
$10.00
```


BONUS 2: Based on purely the quantity of each type (colour) of chip inputted have your program calculate the optimal denomination to assign to each colour (from normal currency values $0.01, $0.05, $0.10, $0.25, $0.50, $1.00, $2.00, $5.00, $10.00, $20.00, $50.00, $100.00, $1000.00) and the quantity of each chip that players should receive to maximize the number of chips while adding up to the buy-in amount. Input to this question will have “B2” on the first line. The second line will be in the format “quantity/colour”. The third line will be the number of players, and the fourth line will be the buy-in. For the output, put the quantity of each colour on its own line in the format “colour - $denomination – quantity”

```
Example Input:
B2
50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe
10
$10.00

Output:
Red - $1.00 - 4
Blue - $0.50 - 4
Black - $0.25 - 10
Green - $0.10 - 9
Yellow - $0.05 - 10
Taupe - $0.01 - 10
```

## Installation
Import the project using IntelliJ, it should just work as all dependencies will be downloaded.

## Additional Projects
Below are some projects I wrote, they're several months olds so I'm not sure if they're fully working due to dependencies etc. but they certainly were when I used them last. I think you'll note an improvement in the scheduler over the team lunch application, which was my first and second foray into Ruby. It does however showcase my ability to learn from my mistakes as well as taking on a new project.

https://github.com/daroleary/team_lunch (full application for ordering lunch)
https://github.com/daroleary/replicon_scheduler (rule based application for scheduling work hours)
https://github.com/daroleary/replicon_scheduler_client (rest client which consumes an external API)