/*
 * Patient.h
 *
 * Class Description: Models a walk-in clinic patient.
 * Class Invariant: Each patient has a unique care card number.
 *                  This care card number must have 10 digits.
 *                  This care card number cannot be modified.
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 09/14/2019
 */

#pragma once

// You can add #include statements if you wish.
#include <iostream>
#include <string>

using namespace std;


class Patient {

private:
string careCard;
string firstName;
string lastName;
string address;
string phone;
string email;
string name;

public:

/*
 * You can add more methods to this public interface,
 * but you cannot remove the methods below
 * nor can you change their prototype.
 *
 */

// Default Constructor
// Description: Create a patient with a care card number of "0000000000".
// Postcondition: All data members set to "To be entered",
//                except the care card number which is set to "0000000000".      
Patient();

// Parameterized Constructor
// Description: Create a patient with the given care card number.
// Postcondition: If aCareCard does not have 10 digits, then care card is set to "0000000000".
//                All other data members set to "To be entered".
Patient(string aCareCard);
// Description: Create a patient with the given Name and careCard.
// Postcondition: If Name does not have alphabets, then name is set to "0000000000".
//                All other data members set to "To be entered".
Patient(string fName,string lName);
/*// Description: Create a patient with the given Address.
// Postcondition: If given Address is not valid, then address is set to "0000000000".
//                All other data members set to "To be entered".
Patient(string anAddress);
// Description: Create a patient with the given Email.
// Postcondition: If given email does not exist, then email is set to "0000000000".
//                All other data members set to "To be entered".
Patient(string anEmail);
// Description: Create a patient with the given Phone number.
// Postcondition: If phone Number does not have 10 digits, then Phone Number is set to "0000000000".
//                All other data members set to "To be entered".
Patient(string aPhone);*/

// Add more parameterized constructors here!

// Getters and setters
// Description: Returns patient's First name.
string getfName() const;

// Description: Returns patient's Last name.
string getlName() const;

// Description: Returns patient's address.
string getAddress() const;

// Description: Returns patient's phone.
string getPhone() const;

// Description: Returns patient's email.
string getEmail() const;

// Description: Returns patient's care card.
string getCareCard() const;

// Description: Sets the patient's First name.
void setfName(const string fName);

// Description: Sets the patient's Last name.
void setlName(const string lName);

// Description: Sets the patient's address.
void setAddress(const string anAddress);

// Description: Sets the patient's phone.
void setPhone(const string aPhone);

// Description: Sets the patient's email.
void setEmail(const string anEmail);

// Description: Sets the patient's name
void setName(string aName);

// Overloaded Operators
// Description: Comparison operator. Compares "this" Patient object with "rhs" Patient object.
//              Returns true if both Patient objects have the same care card number.
bool operator == (const Patient & rhs);

// Description: Comparison operator. Copies "p" Patient object with "this" Patient object.
void operator = (const Patient& p);

// Description: Greater than operator. Compares "this" Patient object with "rhs" Patient object.
//              Returns true if the care card number of "this" Patient object is > the care card
//              number of "rhs" Patient object.
bool operator > (const Patient & rhs);

// For testing purposes!
// Description: Prints the content of "this".
friend ostream & operator<<(ostream & os, const Patient & p);

}; // end of Patient.h