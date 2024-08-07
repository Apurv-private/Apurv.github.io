/* 
 * Queue.h
 *
 * Description: Queue data collection ADT class.
 *              Implementation of an int sequence with enqueue/dequeue ...
 * Class Invariant: ... in FIFO order
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 02/10/2019
 */
  #pragma once
#include <iostream>
class Queue {
    private:
        static unsigned const INITIAL_SIZE = 6;
        int* element;  

        unsigned elementCount;  // number of elements in the queue
        unsigned capacity;      // number of cells in the array
        unsigned frontindex;    // index the topmost element
        unsigned backindex;     // index where the next element will be placed

    public:
        // Description: Constructor
        Queue();


        // Description: Inserts newElement at the back (O(1))
        void enqueue(int newElement);

 
        // Description: Removes the frontmost element (O(1))
        // Precondition: Queue not empty
        void dequeue();


        // Description: Returns a copy of the frontmost element (O(1))
        // Precondition: Queue not empty
        int peek() const;


        // Description: Returns true if and only if queue empty (O(1))
        bool isEmpty() const;
};


