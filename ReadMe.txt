
Answers to Programming task 1:
My implementaion was a producer consumer pattern for the queue.

Sample output is below, the numbers in sequence are the iteration, "number" is "boolean"  is the random number generated and the boolean value if it is a prime number or not. The loop is temporarily broken if the number is found to have a number that divides evenly, then next random number is grabbed from the queue.
651
652
653
654
655
656
657
658
659
660
661 is true
918 is false
572 is false
2
3
4
905 is false

Question about all the time in the world, it is not realistic so I can not answer.

I have not worked with JMS before, I know that I can work with it but it will take more time to learn it than what I believe this test allows for. If it is absolutely needed in order to be considered for the role please allow by Friday that I can read up and practice implementing it.

Singleton is used to force one time creation of an object, This can be achieved by making a private constructor to ensure no other objects can intantiate the class.

Java String is immutable,meaning if we create a String s it has a memory location that can not be updated, so if we say s = "string" then it has an address that cant be updated, if we assign s = "newString" we get a new memmory address. Also Strings are pooled so if we hav a few different references the pool still keeps one address for references that have the same data, unless we use new operator the we get new address even though same data is in it.
