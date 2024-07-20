/*
 * BST.h
 * 
 * Description: Data collection Binary Search Tree ADT class.
 *              Link-based implementation.
 *
 * Class invariant: It is always a BST.
 * 
 * Author: Preetinder Singh & Apurv Nerurkar
 * Date: 13/11/2019
 */

#include "BSTNode.h"
#include "ElementDoesNotExistInBSTException.h"
#include "ElementAlreadyExistsInBSTException.h"
#pragma once



template <class ElementType>
class BST {
	
private:
	BSTNode* root;
	int elementCount;         

    // Utility methods
    bool insertR(const ElementType& element, BSTNode<ElementType>* current);
    ElementType& retrieveR(const ElementType& targetElement, BSTNode<ElementType>* current) const throw(ElementDoesNotExistInBSTException);
	void traverseInOrderR(BSTNode<ElementType>* current) const;
         

public:
    // Constructors and destructor:
	BST();                               // Default constructor
    BST(ElementType& element);           // Parameterized constructor 
	BST(const BST<ElementType>& aBST);   // Copy constructor 
    ~BST();                              // Destructor 
	
    // BST operations:

    // Time efficiency: O(1)
	int getElementCount() const;

	// Time efficiency: O(log2 n)
	void insert(const ElementType& newElement) throw(ElementAlreadyExistsInBSTException);	

	// Time efficiency: O(log2 n)
	ElementType& retrieve(const ElementType& targetElement) throw(ElementDoesNotExistInBSTException);

	// Time efficiency: O(n)
	void traverseInOrder() const;
	
};

template <class ElementType>
BST<ElementType>::BST()
{
	elementCount = 0;
	root = NULL;
}

template <class ElementType>
BST<ElementType>::BST(ElementType& element)
{
	root = element;
	elementCount = 1;
}

template <class ElementType>
BST<ElementType>::BST(const BST<ElementType>& aBST)
{
	root = aBST.root;
}

template <class ElementType>
int BST<ElementType>::getElementCount() const
{
	return elementCount;
}

template <class ElementType>
BST<ElementType>::~BST()
{
	elementCount = 0;
	if(!root.isLeaf())
		root = 0;
}

template <class ElementType>
void BST<ElementType>::insert(const ElementType& newElement) throw(ElementAlreadyExistsInBSTException)
{
	BSTNode* newNode = new BSTNode(newElement);
	int exist = insertR(root,newNode);
	if(!exist)
		throw ElementAlreadyExistsInBSTException("ElementAlreadyExistsInBSTException");
	else
		elementCount++;
}

template <class ElementType>
bool BST<ElementType>::insertR (const ElementType& element, BSTNode<ElementType>* current)
{
	if (element.isLeaf())
	{
		element = current;
		return true;
	}
	else if (current ->(this->element) == element)
		return false;
	else if (current ->(this->element) > element)
		return insertR(element,current->getRightChild());
	else
		return insertR(element,current->getLeftChild());
}

template <class ElementType>
ElementType& BST<ElementType>::retrieve(const ElementType& targetElement) throw(ElementDoesNotExistInBSTException)
{
	return retrieveR(targetElement,root);
}

template <class ElementType>
ElementType& BST<ElementType>::retrieveR(const ElementType& targetElement, BSTNode<ElementType>* current) const throw(ElementDoesNotExistInBSTException)
{
	if(current.isLeaf())
		throw ElementDoesNotExistInBSTException("Element does not exist");
	else if (current->element == targetElement)
		return current->element;
	else if (current-> element > targetElement)
		return retrieveR(targetElement,current->getRightChild());
	else
		return retrieveR(targetElement,current->getLeftChild());
	
}


template <class ElementType>
void BST<ElementType>::traverseInOrder()const
{
	if(root == 0)
		return;
	traverseInOrderR(root->getLeftChild());
	cout<<"Element at root:	"<<root->element;
	traverseInOrderR(root->getRightChild());
}

template <class ElementType>
void BST<ElementType>::traverseInOrderR(BSTNode<ElementType>* current)const
{
	if(current == 0)
		return;
	traverseInOrderR(current->getLeftChild());
	cout<<"Element at root:	"<<current->element;
	traverseInOrderR(current->getRightChild());
}

