# circuit
Solving very interesting task;)

###Steps:

1. Read the task for the first time
2. Learned how to use github and practiced of using it in Idea
3. Learned how to use JUnit (in order to use TDD) and practiced with it in Idea, learned how to use code coverage function
4. Read about TDD
5. Thought about application architecture. Identified that statements can have 1 or 2 arguments. Decided to unify Gate by always using 2 argument variant.
Decided to use one Gate class and through delegation change algorithm of producing output result. In such way I have encapsulated what varies and added possibility to create different gate types in simple way.
6. Created new project in Idea which use Gradle. Started to implement ideas from 5th step by writing code.
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
11. Wrote README file. Made 5th commit.
12. Read about Gradle. Made 6th commit. Updated build.gradle to:
  - output test results to console as was specified in requirements
  - add Main-Class attribute to manifest
13. Created factory for ResultProducers. Made 7th commit.
14. Created WireHolder. Made 8th commit.
15. Moved code for parsing to Parser. Made 9th commit. 
16. Tests were rewritten using assertThat and Hamcrest. Made 10th commit.
17. Moved some code to Parser - made WireHolder more cohesive. Parser is now accepts only lowercase wire identifiers as specified in task. Made 11th commit.
18. Improved processing of wires. Made 12th commit.
19. Added use of Optional to Wire class. Made small changes suggested by IDE inspections. Made 13th commit.
