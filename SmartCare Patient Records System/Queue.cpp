/* 
 * Queue.cpp
 *
 * Description: Queue data collection ADT class.
 *              Implementation of an int sequence with enqueue/dequeue ...
 * Class Invariant: ... in FIFO order
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 02/10/2019
 */                                              
#include "Queue.h"


// Description: Constructor
Queue::Queue() : elementCount(0), capacity(INITIAL_SIZE), frontindex(0), backindex(0) 
{
	element = new int[capacity];
} 


// Description: Inserts newElement at the back (O(1))
void Queue::enqueue(int newElement)
 {
 	if(elementCount == capacity )
 	{
 		int* temp = new int [2*capacity];
 		for(int i = 0; i < elementCount ; i++)
 			temp[i] = element[(frontindex + i) % capacity];
 		capacity = 2*capacity;
 		delete element;
 		element = temp;
 		frontindex= 0;
 		backindex = elementCount;
 	}
    element[backindex] = newElement;
    elementCount++;
    backindex = (backindex+1) % capacity;
}


// Description: Removes the frontmost element (O(1))
// Precondition: Queue not empty
void Queue::dequeue() 
{
	if(elementCount == 0)
		return;

	int size;
    if(elementCount - 1 < 0.25 * capacity && 0.5*capacity >= INITIAL_SIZE)
    {
    	if(capacity % 2 == 0)
    		size = 0.5 * capacity;
    	else
    		size = 0.5 * (capacity + 1);
    	int* temp = new int [size];
    	for(int i = 0; i < elementCount ; i++)
 			temp[i] = element[(frontindex + i) % capacity];
 		delete element;
 		element = temp;
 		capacity = size;
 		frontindex = 0;
 		backindex = elementCount;
    }
    frontindex = (frontindex + 1) % capacity;
    elementCount--;
    backindex = (frontindex + elementCount) % capacity;
}


// Description: Returns a copy of the frontmost element (O(1))
// Precondition: Queue not empty
int Queue::peek() const 
{
	if(isEmpty())
		return -1;
    return element[frontindex];
} 


// Description: Returns true if and only if queue empty (O(1))
bool Queue::isEmpty() const {
    return elementCount == 0;
} 




