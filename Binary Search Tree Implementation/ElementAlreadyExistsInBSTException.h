/*
 * ElementAlreadyExistsInBSTException.h
 *
 * Class Description: Defines the exception that is thrown when the 
 *                    element already exists in the BST.
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 11/14/2019
 */
 
#pragma once

#include <stdexcept>
#include <string>

using namespace std;

class ElementAlreadyExistsInBSTException : public logic_error
{
public:
   ElementAlreadyExistsInBSTException(const string& message = "");
   
}; // end of class ElementAlreadyExistsInBSTException

ElementAlreadyExistsInBSTException::ElementAlreadyExistsInBSTException(const string& message): 
logic_error("Empty Data Collection ADT Class Exception: " + message)
{
}  // end of constructor