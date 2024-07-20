/* 
 * Stack.cpp
 *
 * Description: Stack data collection ADT class.
 *              Implementation of an int sequence with push/pop ...
 * Class Invariant: ... in a LIFO order
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 02/10/2019
 */

#include<iostream>
#include"Stack.h"

/* Description: Default Constructor of the stack class
				Sets the head and tail ptr to null*/
Stack::Stack()
{
	head = tail = nullptr;
}

/*Description: The default destructor that makes the headers point to null*/
Stack::~Stack()
{
	head = tail = nullptr;
}

/*Description:  Adds the element from the back i.e from the tail of the list
  PreCondition: Stack is empty*/
void Stack::push(int newElement) //Time efficiency O(1)
{
	 StackNode* newNode = new StackNode;
	 newNode->data = newElement;
	 newNode->next = nullptr;
	 if (isEmpty())
	 {
	  head = tail = newNode;
	  return;
	 }
	 StackNode* temp = head;
	 while (temp->next != 0)
	  temp = temp->next;
	 temp->next = newNode;
	 tail = newNode;
	 return;
}

/*Description: Deletes the last element from the back i.e the tail
  PreCondition: Stack is empty*/
int Stack::pop()
{
	 int temp1 = tail->data;
	 StackNode* temp = head;
	 if (isEmpty())
	  return 0;
	 if (temp->next == nullptr)
	 {
	  head = tail = nullptr;
	  return temp1;
	 }
	 while (temp->next->next != nullptr)
	  temp = temp->next;
	 tail = temp;
	 tail->next = nullptr;
	 return temp1;
}

/*Description: Returns the data of the element at the top*/
int Stack::peek()const
{
	 if ( head == nullptr)
	  return 0;
	 return tail->data;
}

/*Description: Checks if the head is pointing to a node or is pointing to null*/
bool Stack::isEmpty()const
{
	return(head == nullptr);
}