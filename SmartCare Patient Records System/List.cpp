/*
 * List.cpp
 *
 * Class Description: List data collection ADT.
 * Class Invariant: Data collection with the following characteristics:
 *                   - Each element is unique (no duplicates).
 *                   - Elements are kept in descending sorted order of element's search key.
 *
 * Author: Preetinder Singh & Apurv Nerurkar
 * Date: 02/10/2019
 */

#include<iostream>
#include <string>
#include "List.h"
#include "Patient.h"

using namespace std;
List::List()
{
	for (int i = 0; i < 10; i++)
	{
		P[i] = nullptr;
		count[i] = 0;
		capacity[i] = 0;
	}
	elementcount = 0;
}
List::List(const List& lst)
{
	for (int i = 0; i < 10; i++)
	{
		this->count[i] = lst.count[i] ;
		this->capacity[i] = lst.getCapacity(i);
		for (int j = 0; j < count[i]; j++)
		{
			P[i] = new Patient[capacity[i]];
			P[i][j] = lst(i, j);
		}
	}
	this->elementcount = lst.getElementCount();
}

Patient List::operator() (int& i, int& j) const
{
	return P[i][j];
}
List::~List()
{
	for (int i = 0; i < 10; i++)
		if (P[i] != nullptr)
			delete P[i];
}
int List::getElementCount() const
{
	return elementcount;
}
int  List::getCapacity(int& i) const
{
	return capacity[i];
}
bool List::insert(const Patient& newElement)
{
	if (List::search(newElement) != nullptr)
		return false;
	string s = newElement.getCareCard();
	int i = s[0] + '0';
	if (P[i] == nullptr)
	{
		P[i] = new Patient[10];
		capacity[i] = 10;
		P[i][0] = newElement;
		count[i]++;
		elementcount++;
	}
	else
	{
		if (capacity[i] == count[i])
		{
			Patient* temp = new Patient[2 * capacity[i]];
			for (int j = 0; j < count[i]; j++)
				temp[j] = this->P[i][j];
			capacity[i] = 2 * capacity[i];
			delete[]P[i];
			P[i] = temp;
		}
		int j;
		for (j = 0; j < count[i] && P[i][j].getCareCard() < newElement.getCareCard(); j++)
			continue;
		for (int k = count[i]; k > j; k--)
			P[i][k] = P[i][k + 1];
		P[i][j] = newElement;
		count[i]++;
		elementcount++;
	}
	return true;
}
Patient* List::search(const Patient& target)
{
	string s = target.getCareCard();
	int i = s[0] + '0';
	Patient* temp = nullptr;
	for (int j = 0; j < count[i]; j++)
	{
		if (P[i][j].getCareCard() == target.getCareCard())
			temp = &(P[i][j]);
	}
	return temp;
}
bool List::remove(const Patient& toBeRemoved)
{
	if (List::search(toBeRemoved) == nullptr)
		return false;
	else
	{
		string s = toBeRemoved.getCareCard();
		int i = s[0];
		int j;
		for (j = 0; j < count[i]; j++)
			if (P[i][j].getCareCard()==toBeRemoved.getCareCard())
				break;
		for (; j + 1 < count[i]; j++)
			P[i][j] = P[i][j + 1];
		count[i]--;
		elementcount--;
		return true;
	}
}
void List::printList()
{
	for (int i = 9; i >= 0; i--)
		for (int j = count[i] - 1; j >= 0; j--)
			cout << P[i][j];
	cout<<endl;
}

void List::removeAll()
{
	for (int i = 0; i < 10; i++)
		count[i] = 0;
	elementcount = 0;
}