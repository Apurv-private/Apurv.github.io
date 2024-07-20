/*
 * Patient.cpp
 *
 * Class Description: Models a walk-in clinic patient.
 * Class Invariant: Each patient has a unique care card number.
 *                  This care card number must have 10 digits.
 *                  This care card number cannot be modified.
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 09/14/2019
 */

// You can add #include statements if you wish.
#include <iostream>
#include <string>
#include "Patient.h"

// Default Constructor
// Description: Create a patient with a care card number of "0000000000".
// Postcondition: All data members set to "To be entered",
//                except the care card number which is set to "0000000000".      
Patient::Patient()
{
careCard = "0000000000";
firstName = lastName = address = phone = email = "To be entered";
}

// Parameterized Constructor
// Description: Create a patient with the given care card number.
// Postcondition: If aCareCard does not have 10 digits, then care card is set to "0000000000".
//                All other data members set to "To be entered".
Patient::Patient(string aCareCard)
{
careCard = aCareCard;
 name = address = phone = email = "To be entered";
}

// Description: Create a patient with the given first and last name.
// Postcondition: If name has digits, then first and last name is set to "".
//                All other data members set to "To be entered".

Patient::Patient(string fName, string lName)
{
	string numbers = "0123456789";
	int k = 0;
	for(int i = 0 ; i < fName.length(), k < lName.length() ; i++,k++)
	{
		for(int j = 0;j<numbers.length();j++)
		{
			if(fName[i] == numbers[j] || lName[j] == numbers[j])
			{
			fName = lName = "";
			break;
			}	
		}
	}
firstName = fName;
lastName = lName;
careCard = address = phone = email = "To be entered";
}
// All other parameterized constructors -> You need to implement these methods.

// Getters and setters -> You need to implement these methods.
string Patient:: getfName() const
{ return firstName;}
string Patient:: getlName() const
{ return lastName;}
string Patient:: getAddress() const
{ return address;}
string Patient:: getPhone() const
{return phone;}
string Patient :: getEmail() const
{ return email;}
string Patient:: getCareCard() const
{ return careCard;}
void Patient:: setfName(const string fName)
{firstName = fName;}
void Patient:: setlName(const string lName)
{lastName = lName;}
void Patient:: setAddress(const string anAddress)
{address = anAddress;}
void Patient:: setPhone(const string aPhone)
{
	phone = aPhone;
}
void Patient :: setEmail(const string anEmail)
{
	email = anEmail;
}
void Patient:: setName(string aName)
{
	name = aName;
}

// Overloaded Operators
// Description: Comparison operator. Compares "this" Patient object with "rhs" Patient object.
//              Returns true if both Patient objects have the same care card number.
bool Patient::operator == (const Patient & rhs)
{

// Compare both Patient objects
return this->careCard == rhs.getCareCard();

} // end of operator ==

void Patient:: operator = (const Patient& p)
{
careCard = p.getCareCard();
firstName = p.getfName();
lastName = p.getlName();
address = p.getAddress();
phone = p.getPhone();
email = p.getEmail();
name = firstName + lastName;
}

// Description: Greater than operator. Compares "this" Patient object with "rhs" Patient object.
//              Returns true if the care card number of "this" Patient object is > the care card
//              number of "rhs" Patient object
bool Patient::operator > (const Patient & rhs) {

// Compare both Patient objects
return this->careCard > rhs.getCareCard();

} // end of operator >

// For testing purposes!
// Description: Prints the content of "this".
ostream & operator<<(ostream & os, const Patient & p) {

os << p.careCard << " - Patient: " << p.name << ", "
  << p.address << ", " << p.phone << ", " << p.email << endl;  
   
return os;

} // end of operator<<
// end of Patient.cpp