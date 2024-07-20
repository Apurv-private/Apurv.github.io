/*
 * walkIn.cpp
 *
 * Author: Apurv Nerurkar & Preetinder Singh
 * Date: 09/14/2019
 */

#include <iostream>
#include <string>
#include "Patient.h"
#include "List.h"
using namespace std;

//Description: Helps the user to input the contents of the patient's record
istream& operator >> (istream& fin, Patient& p)
{
	string fname1,lname1,address1,phone1,email1;
	cout<<"ENTER FIRST NAME: ";
	fin>>fname1;
	cout<<"ENTER LAST NAME: ";
	fin>>lname1;
	cout<<"ENTER Address: ";
	fin>>address1;
	cout<<"ENTER Phone number: ";
	fin>>phone1;
	cout<<"ENTER Email id: ";
	fin>>email1;
	p.setfName(fname1);
	p.setlName(lname1);
	p.setAddress(address1);
	p.setPhone(phone1);
	p.setEmail(email1);
	return fin;
}

//Description: Helps to modify the contents of the patient's record
void modify (Patient& p)
{
		char c = 0;
		bool done = false;
		while(!done)
		{
			cout<<"Press n to Modify the first name followed by last name"<<endl;
			cout<<"Press a to Modify the address"<<endl;
			cout<<"Press m to Modify the email"<<endl;
			cout<<"Press p to Modify the phone number"<<endl;
			cout<<"Press e to exit"<<endl;

			cout<<"YOUR OPTION: ";
			cin >> c;
			string a;
			switch (c)
				{
					case 'n' : cout<<"ENTER YOUR Name ";cin >> a; p.setName(a);continue;
					case 'a' : cout<<"ENTER YOUR ADDRESS ";cin >> a; p.setAddress(a);continue;
					case 'm' : cout<<"ENTER YOUR EMAIL ";cin >> a; p.setEmail(a);continue;
					case 'p' : cout<<"ENTER YOUR PHONE NUMBER ";cin >> a; p.setPhone(a);continue;
					case 'e' : done = true;break;
					default: cout<<"Enter again";
				}
		}
}

int main()
{
List l;
cout<<"---->Welcome to the CMPT225 Clinic<---- "<<endl;
string s;
Patient* temp;
bool b;
bool done = false;
char ch = 0;
	while(s.length() > 10 || s.length() < 10 || s == "0000000000")
	{
		cout<<"Please Enter Your 10Digit CareCard Number: ";
		cin >>s;
	}
	Patient p(s);

	cout<<"Are you already registered with our clinic ";
	cin >> b;
while( !done)
{
	if(b == 0)
	{
		cin >> p;
		l.insert(p);
		l.printList();
		done = true;
	}
	else
	{
		temp = l.search(p);
		cout<<"Press m to Modify the record"<<endl;
		cout<<"Press r to remove the record"<<endl;
		cout<<"Press s to show the record" <<endl;
		cout<<"press e to exit"<<endl;

		cout<<"YOUR OPTION: ";
		cin >> ch;
			switch(ch)
			{
				case 'm' : modify(p); break;
				case 'r' : l.remove(p);cout<<"Patient record removed from the system"<<endl;done = true; break;
				case 's' : cout<<p;done = true;break;
				case 'e' : done = true; break;
				default: cout<<"Enter again"<<endl;
		}
	}
}
cout<<"Today's Patients list is as follows "<<endl;
l.printList();//Tells us the list of patients who came todays and still are members of the clinic

cout<<"Press l to make new list for tomorrow"<<endl;
cout<<"Your Option: ";
cin>> ch;
	switch(ch)
	{
		case 'l' :l.removeAll();break;
		default: cout<<"Enter again "<<endl ;
	}
cout<<"New list is created for tomorrow"<<endl;
system("pause");
return 0;
}