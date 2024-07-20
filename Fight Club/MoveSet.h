#pragma once
#include <string>
using namespace std;
class MoveSet 
{
private:
	string attack1;
	string power1;
	string attack2;
	string power2;
	string attack3; // defensive attack which increases HP
	string power3; 
	string finisher;
	string powerFinisher;

public:
	//Setters for attacks
	void setA1(string A1);
	void setA2(string A2);
	void setA3(string A3);
	void setFinisher(string A4);

	//Setters for powers
	void setPower1(string p1);
	void setPower2(string p2);
	void setPower3(string p3);
	void setPowerFinisher(string powerF);

	//Getters
	string getA1();
	string getA2();
	string getA3();
	string getFinisher();

	//Getters for powers
	string getPower1();
	string getPower2();
	string getPower3();
	string getPowerFinisher();

};

