/*
 * List.cpp
 *
 * Class Description: List of all the patients.
 * Class Invariant: Data collection with the following characteristics:
 *                   - Each element i.e patient is unique (no duplicates).
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 09/14/2019
 */


#include <iostream>
#include <string>
#include "Patient.h"
#include "List.h"
using namespace std;

List::List()
{
	capacity = size;
	elementCount = 0;
}

int List::getElementCount() const
{
	return elementCount;
}

int List::getCapacity() const 
{
	return capacity;
}

void List:: sort() 
{
  bool appears = true;
  int j = 0;
  Patient temp;
  while (appears)
  {
    appears = false;
    j++;
    for (int i = 0; i < elementCount - j; ++i)
    {
      if (p[i].getCareCard() < p[i + 1].getCareCard())
       {
        temp = p[i];
        p[i] = p[i + 1];
        p[i + 1] = temp;
        appears = true;
      }
    }
  }
}

bool List::insert(const Patient& newElement)
{
	for(int i = 0 ; i < elementCount ; i++)
	{
		if(p[i].getCareCard() == newElement.getCareCard())
		{
			cout<<"Patient already in the list"<<endl;
			return false;
		}
	}
p[elementCount] = newElement;
elementCount++;
sort();
return true;
}

bool List::remove(const Patient& toBeRemoved)
{
int i;
for(i = 0 ; i < elementCount ;i++)
if(p[i].getCareCard() == toBeRemoved.getCareCard())
break;
if(i == elementCount)
return false;
for(;i < elementCount ;i++)
p[i] = p[i+1];
elementCount--;
return true;
}

void List::removeAll()
{
elementCount = 0;
}

Patient* List:: search (const Patient& target)
{
Patient* pp;
for(int i = 0 ; i < elementCount; i++)
{
if(p[i].getCareCard() == target.getCareCard())
{
pp=&p[i];
return pp;
}
}
return NULL;
}



void List:: printList()
{
for(int i = 0 ; i < elementCount; i++)
	cout<<p[i]<<endl;
}


// end List.cpp