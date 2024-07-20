#pragma once
#include"BinaryHeap.h"

template <class ElementType>
class PriorityQueue
{
private:
	BinaryHeap<ElementType> B;
public:
	/******* Priority Queue Public Interface - START - *******/

   // Description: Returns "true" is this Priority Queue is empty, otherwise "false".
   // Postcondition:  The Priority Queue is unchanged by this operation.
   // Time Efficiency: O(1)
   bool isEmpty() const;

   // Description: Inserts newElement in the Priority Queue.
   //              It returns "true" if successful, otherwise "false".
   // Time Efficiency: O(log2 n)
   bool enqueue(ElementType& newElement);

   // Description: Removes (but does not return) the element with the "highest" priority.
   // Precondition: This Priority Queue is not empty.
   // Exception: Throws EmptyDataCollectionException if this Priority Queue is empty.
   // Time Efficiency: O(log2 n)
   void dequeue() throw(EmptyDataCollectionException);
   
   // Description: Returns (but does not remove) the element with the "highest" priority.
   // Precondition: This Priority Queue is not empty.
   // Exception: Throws EmptyDataCollectionException if this Priority Queue is empty.
   // Time Efficiency: O(1)
   ElementType& peek() throw(EmptyDataCollectionException);
 
/******* Priority Queue Public Interface - END - *******/
};

template <class ElementType>
bool PriorityQueue<ElementType>::isEmpty() const
{
	return B.isEmpty();
}

template <class ElementType>
bool PriorityQueue<ElementType>::enqueue (ElementType& newElement)
{
	return B.insert(newElement);
}

template <class ElementType>
void PriorityQueue<ElementType>::dequeue() throw(EmptyDataCollectionException)
{
	B.remove();
}

template <class ElementType>
ElementType& PriorityQueue<ElementType>:: peek() throw(EmptyDataCollectionException)
{
	return  B.retrieve();
}

