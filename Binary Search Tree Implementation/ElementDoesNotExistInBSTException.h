/*
 * ElementDoesNotExistInBSTException.h
 *
 * Class Description: Defines the exception that is thrown when the 
 *                    element does not exist in the BST.
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 11/14/2019
 */
 
#pragma once

#include <stdexcept>
#include <string>

using namespace std;

class ElementDoesNotExistInBSTException : public logic_error
{
public:
   ElementDoesNotExistInBSTException(const string& message = "");
   
}; // end of class ElementDoesNotExistInBSTException

ElementDoesNotExistInBSTException::ElementDoesNotExistInBSTException(const string& message): 
logic_error("Empty Data Collection ADT Class Exception: " + message)
{
}  // end constructor