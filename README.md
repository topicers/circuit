# circuit
Solving very interesting task;)

###Steps:

1. Read the task for the first time
2. Learned how to use github and practiced of using it in Idea
3. Learned how to use JUnit (in order to use TDD) and practiced with it in Idea, learned how to use code coverage function
4. Read about TDD
5. Thought about application architecture. Identified that statements can have 1 or 2 arguments. Decided to unify Gate by always using 2 argument variant.
Decided to use one Gate calss and through delegation change algorithm of producing output result. In such way I have encapsulated what varies and added possibility to create different gate types in simple way.
6. Created new project in Idea which use Gradle. Started to implement ideas from 4th step by writing code.
7. Made first commit. It compiles and passes the tests. The purposes of the first commit were:
  - to show the sought process;
  - to implement the parsing code.
8. Made second commit. Added possibility for operands to be constants. Added code for calculating wire signals. Fixed bugs. Obtained correct answer for the 1 part of puzzle.
9. Made third commit only to indicate how I have solved second part of the puzzle. It was very simple task: I need only to change input file a little bit.
10. Made fourth commit:
  - Implemented pool for constant wires -> in case of many constant wires with the same value (as in input file) only one instance will be used
  - Improved tests
  - Added comments
  - Added command-line arguments
11. Wrote README file