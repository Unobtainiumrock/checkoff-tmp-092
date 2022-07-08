# Gitlet
## About
    A partial implementation of mimicking .git version control system. Written in java. The commands are

## Getting Started
1. Nagivate to the directory for the `gitlet` folder exists
2. Compile the Java files to their class files using `javac *.java`
3. Verify the *.class files for each *.java file are compiled with


## Methods Implemented, and their associated terminal commands:
Each gitlet command below corresponds to a method within Main.java
General form: `java gitlet.main <command> [args]`

- `gitlet.Main init`
- `gitlet.Main add [file name]`
- `gitlet.Main commit [message]`
- `gitlet.Main rm [file name]`
- `gitlet.Main reset log`
- `gitlet.Main glogal-log`
- `gitlet.Main find [commit message]`
- `gitlet.Main status`
- `gitlet.Main checkout --[filename] | --[commit id] --[file name] | [branch name]`
- `gitlet.Main branch [branch name]`
- `gitlet.Main rm-branch [branch name]`
- `gitlet.Main reset [commit id]`
- `gitlet.Main merge [branch name]`



