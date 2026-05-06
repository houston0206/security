Breaking In

1. Added a main function to Graph.java that takes the five data files and makes an array of Graph objects
2. Created a set of machines five times, removing them if they were found in any of the graph traversals
3. Manually compared the resulting sets to find the target computer

Results:
Configuration D contains all of the computers.
The computer Salton is consistently unavailable, only appearing in configs C and D


Determining Distance

Machines that connect to Salton in config D:
Fasenmeyer- 62
Stanhope- 83
Davies- 71
Goedel- 47
Even- 91
Blanch- 68

Instructor machine distances:
3815 (Noyce) is 3 edges away (Salton - Goedel - Chapanis - Noyce)
3813 (Zeus) is more than 3 edges away


Tearing Down

Total weight: 2185
Salton to Noyce: 14+47+23 = 84

We're giving up here, the website isn't accepting the answer
