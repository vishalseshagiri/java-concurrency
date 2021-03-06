# [Defogtech videos](https://www.youtube.com/playlist?list=PLhfHPmPYPPRk6yMrcbfafFGSbE2EPK_A6)*

## Lecture 1

### Volatile - The Visibility Problem

#### Volatile Cache flush and refresh diagram 
![](/src/main/resources/images/volatile-cache-diagram.png)

### Code -  Lec1Volatile, Lec1VolatileTest

Explanation

#### Thread Synchronization Problem

### Code -  Lec1ThreadSynchronization, Lec1ThreadSynchronizationTest

Explanation

#### Lecture 1 Synopsis Diagram 
![](/src/main/resources/images/volatile-synchronization-usages.png)

## Lecture 2 - ThreadLocal

### Code - Lec2ThreadLocal, Lec2ThreadLocalTest

#### Use Case 1 Diagram
![](/src/main/resources/images/use-case-1.png)
- no synchronization required and efficient since each thread stores it's own copy as opposed to creating a new instance everytime

#### Use Case 2 Diagram
![](/src/main/resources/images/use-case-2.png)
- no synchronization required and saves space since we can hold the context and need not pass the object across multiple services requiring access to it
- use local variables if possible and avoid threadlocal altogether

#### Spring Context Holder Diagram
![](/src/main/resources/images/spring-context-holder.png)

Bibiliography:
1. [CustomRunner code reference](https://www.tutorialspoint.com/java/java_multithreading.htm)
2. [Interrupts - Cave of Programming](https://www.youtube.com/watch?v=6HydEu75iQI&list=PLBB24CFB073F1048E&index=14)