#pragma once
/*
 * BinaryHeap.h
 *
 * Description: BinaryHeap ADT class.
 *              Implementation of a binary tree in an array format
 * Class Invariant:  Minimum Binary Heap
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 02/10/2019
 */

#pragma once

#include"EmptyDataCollectionException.h"
#include"math.h"
template <class ElementType>
class BinaryHeap
{
private:
 int capacity = 7;
 ElementType* arr = new ElementType[capacity];
 int elementCount = 0;
 int root = 0;
 void reHeapUp(int& IndexOfBottom);
 void reHeapDown(int& IndexOfRoot);
public:
 /******* Binary Heap Public Interface - START - *******/

   // Description: Returns "true" is this Binary Heap is empty, otherwise "false".
   // Postcondition:  The Binary Heap is unchanged by this operation.
   // Time Efficiency: O(1)
 bool isEmpty() const;
 // Description: Inserts newElement in this Binary Heap and
 //              returns "true" if successful, otherwise "false".
 // Time Efficiency: O(log2 n)
 bool insert(ElementType& newElement);
 // Description: Removes (but does not return) the element located at the root.
 // Precondition: This Binary Heap is not empty.
 // Exception: Throws EmptyDataCollectionException if Binary Heap is empty.
 // Time Efficiency: O(log2 n)
 void remove() throw(EmptyDataCollectionException);
 // Description: Returns (but does not remove) the element located at the root.
 // Precondition: This Binary Heap is not empty.  
 // Exception: Throws EmptyDataCollectionException if Binary Heap is empty.
 // Time Efficiency: O(1)
 ElementType& retrieve() throw(EmptyDataCollectionException);
 /******* Binary Heap Public Interface - END - *******/
};
template <class ElementType>
bool BinaryHeap<ElementType>::isEmpty() const
{
 if (elementCount == root)
  return true;
 return false;
}
template <class ElementType>
bool BinaryHeap<ElementType>::insert(ElementType& newElement)
{
 if (elementCount == capacity)
 {
  ElementType* temp = new ElementType[2 * capacity];
  if (!temp)
   return false;
  for (int i = 0; i < elementCount; i++)
   temp[i] = arr[i];
  capacity = 2 * capacity;
  arr = temp;
  delete temp;
 }
 arr[elementCount] = newElement;
 reHeapUp(elementCount);
 elementCount++;
 return true;
}
template <class ElementType>
void BinaryHeap<ElementType>::remove() throw(EmptyDataCollectionException)
{
 if (isEmpty())
  throw EmptyDataCollectionException("heap is empty");
 arr[root] = arr[elementCount - 1];
 --elementCount;
 reHeapDown(root);
}
template <class ElementType>
ElementType& BinaryHeap<ElementType>::retrieve() throw(EmptyDataCollectionException)
{
 if (isEmpty())
  throw EmptyDataCollectionException("heap is empty");
 return arr[root];
}
template <class ElementType>
void BinaryHeap<ElementType>::reHeapUp(int& IndexOfBottom)
{
 int IndexOfParent = floor(abs((IndexOfBottom - 1) / 2));
 if (IndexOfBottom == root)
  return;
 if (arr[IndexOfBottom] <= arr[IndexOfParent])
 {
  ElementType temp = arr[IndexOfBottom];
  arr[IndexOfBottom] = arr[IndexOfParent];
  arr[IndexOfParent] = temp;
 }
 else
  return;
 reHeapUp(IndexOfParent);
}
template <class ElementType>
void BinaryHeap<ElementType>::reHeapDown(int& IndexOfRoot)
{
 if (IndexOfRoot == elementCount-1)
  return;
 int IndexOfMinChild;
 int IndexOfLeftChild = 2 * IndexOfRoot + 1;
 int IndexOfRightChild = 2 * IndexOfRoot + 2;
 if (IndexOfRightChild >= elementCount)
 	IndexOfMinChild = IndexOfLeftChild;
 else if (arr[IndexOfLeftChild] <= arr[IndexOfRightChild])
  IndexOfMinChild = IndexOfLeftChild;
 else
  IndexOfMinChild = IndexOfRightChild;
 if (arr[IndexOfMinChild] <= arr[IndexOfRoot])
 {
  ElementType temp = arr[IndexOfRoot];
  arr[IndexOfRoot] = arr[IndexOfMinChild];
  arr[IndexOfMinChild] = temp;
 }
 else
  return;
 reHeapDown(IndexOfMinChild);
}