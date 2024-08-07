/*
 * List.h
 *
 * Class Description: List data collection ADT.
 * Class Invariant: Data collection with the following characteristics:
 *                   - Each element is unique (no duplicates).
 *                   - (For you to fill: Add a statement about the another characteristic of this List.)
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 09/14/2019
 */

#pragma once

// You can add #include statements if you wish.
#include <string>
#include "Patient.h"

using namespace std;


class List  {

private:

/*
 * You can add more attributes to this class,
 * but you cannot remove the attributes below
 * nor can you change them.
 */

static const int size = 10;                     // MAX_ELEMENTS
Patient p[size] ;                    // Data structure of element with capacity of MAX_ELEMENTS
int elementCount;                  // Current element count in element array
int capacity;                      // Actual maximum capacity of element array  

public:

/*
 * You can add more methods to this interface,
 * but you cannot remove the methods below
 * nor can you change their prototype.
 *
 */

// Default constructor
List();

// Description: Returns the total element count currently stored in List.
int getElementCount() const;

int getCapacity() const;

// Description: Sorts the list in ascending order according to their careCard numbers.
void sort();

// Description: Insert an element.
// Precondition: newElement must not already be in data collection.  
// Postcondition: newElement inserted and elementCount has been incremented.  
bool insert(const Patient& newElement);

// Description: Remove an element.
// Postcondition: toBeRemoved is removed and elementCount has been decremented.
bool remove( const Patient& toBeRemoved );

// Description: Remove all elements.
void removeAll();
   
   
// Description: Search for target element.
//              Returns a pointer to the element if found,
//              otherwise, returns NULL.
Patient* search(const Patient& target);

// Description: Prints all elements stored in List by descending order of search key.
void printList();

}; // end List.h