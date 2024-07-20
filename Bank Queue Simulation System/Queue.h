/* 
 * Queue.h
 *
 * Description: Queue data collection ADT class.
 *              Implementation of an int sequence with enqueue/dequeue ...
// Class Invariant:  FIFO or LILO order
 *
 * Author:
 * Date:
 */
#pragma once
#include "EmptyDataCollectionException.h"
template <class ElementType>
class Queue {
    private:
        static unsigned const INITIAL_SIZE = 6;

        unsigned elementCount = 0;  // number of elements in the queue
        unsigned capacity = INITIAL_SIZE;      // number of cells in the array
        unsigned frontindex = 0;    // index the topmost element
        unsigned backindex = 0;     // index where the next element will be placed
        ElementType* element  =  new ElementType[capacity];

    public:
    // Description: Returns "true" is this Queue is empty, otherwise "false".
   // Time Efficiency: O(1)
   bool isEmpty() const;
   
   // Description: Inserts newElement at the "back" of this Queue 
   //              (not necessarily the "back" of its data structure) and 
   //              returns "true" if successful, otherwise "false".
   // Time Efficiency: O(1)
   bool enqueue(ElementType& newElement);
   
   // Description: Removes (but does not return) the element at the "front" of this Queue 
   //              (not necessarily the "front" of its data structure).
   // Precondition: This Queue is not empty.
   // Exception: Throws EmptyDataCollectionException if this Queue is empty.   
   // Time Efficiency: O(1)
   void dequeue() throw(EmptyDataCollectionException); 
   
   // Description: Returns (but does not remove) the element at the "front" of this Queue
   //              (not necessarily the "front" of its data structure).
   // Precondition: This Queue is not empty.
   // Exception: Throws EmptyDataCollectionException if this Queue is empty.
   // Time Efficiency: O(1)
   ElementType& peek() throw(EmptyDataCollectionException);
};

// Description: Returns true if and only if queue empty (O(1))
template <class ElementType>
bool Queue<ElementType>::isEmpty() const {
    return elementCount == 0;
} 

// Description: Inserts newElement at the back (O(1))
template <class ElementType>
bool Queue<ElementType>::enqueue(ElementType& newElement)
 {
  if(elementCount == capacity )
  {
    ElementType* temp = new ElementType [2*capacity];
        if(!temp)
            return false;
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
template <class ElementType>
void Queue<ElementType>::dequeue() throw (EmptyDataCollectionException)
{
  if(elementCount == 0)
    throw EmptyDataCollectionException("dequeue() called with empty queue.");

  int size;
    if(elementCount - 1 < 0.25 * capacity && 0.5*capacity >= INITIAL_SIZE)
    {
      if(capacity % 2 == 0)
        size = 0.5 * capacity;
      else
        size = 0.5 * (capacity + 1);
      ElementType* temp = new ElementType [size];
      for(ElementType i = 0; i < elementCount ; i++)
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
template <class ElementType>
ElementType& Queue<ElementType>::peek() throw(EmptyDataCollectionException)
{
  if(isEmpty())
    throw EmptyDataCollectionException("peek() called with empty queue.");
    return element[frontindex];
} 


