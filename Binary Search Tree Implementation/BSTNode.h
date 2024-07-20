/*
 * BSTNode.h
 * 
 * Description: Consists of nodes used for the linked list based implementation
 *				of a BST
 *
 * 
 * Author: Preetinder Singh & Apurv Nerurkar
 * Date: 13/11/2019
 */

#pragma once
template <class ElementType>
class BSTNode {
private:
	ElementType element;
	BSTNode* leftChild;
	BSTNode* rightChild;
public:
	BSTNode();
	BSTNode(ElementType& element);
	BSTNode(ElementType& element, BSTNode* left , BSTNode* right);

	//Setters
	void setElement(const ElementType& element);
	void setLeftChild(const BSTNode* left);
	void setRightChild(const BSTNode* right);
	//Getters
	ElementType& getElement() const;
	BSTNode* getLeftChild() const ;
	BSTNode* getRightChild() const ;
	//Helpers
	bool isLeaf() const;
	bool hasLeftChild() const;
	bool hasRightChild() const;

};

template <class ElementType>
BSTNode<ElementType>::BSTNode()
{
	leftChild = rightChild = 0;
}

template <class ElementType>
BSTNode<ElementType>::BSTNode(ElementType& element)
{
	this->element = element;
	leftChild = rightChild = 0;
}

template <class ElementType>
BSTNode<ElementType>::BSTNode(ElementType& element, BSTNode* left , BSTNode* right)
{
	this->element = element;
	leftChild = left;
	rightChild = right;
}

template <class ElementType>
void BSTNode<ElementType>::setElement(const ElementType& element)
{
	this->element = element;
}

template <class ElementType>
void BSTNode<ElementType>::setLeftChild(const BSTNode* left)
{
	leftChild = left;
}

template <class ElementType>
void BSTNode<ElementType>::setRightChild(const BSTNode* right)
{
	rightChild = right;
}

template <class ElementType>
ElementType& BSTNode<ElementType>::getElement() const
{
	return element;
}

template <class ElementType>
BSTNode<ElementType>* BSTNode<ElementType>::getLeftChild() const
{
	return leftChild;
}

template <class ElementType>
BSTNode<ElementType>* BSTNode<ElementType>::getRightChild() const
{
	return rightChild;
}

template <class ElementType>
bool BSTNode<ElementType>::isLeaf() const
{
	return (leftChild == 0 && rightChild == 0);
}

template <class ElementType>
bool BSTNode<ElementType>::hasLeftChild() const
{
	return (leftChild!=0);
}

template <class ElementType>
bool BSTNode<ElementType>::hasRightChild() const
{
	return (rightChild!=0);
}
