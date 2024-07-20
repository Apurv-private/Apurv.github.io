#pragma once
#include "MoveSet.h"
#include <string>
using namespace std;


/*G = Gender
	1)M = Male
	2)F = Female
H = Height
W = Weight
B = Body Type
	1)S = Slim
	2)F = Fat
3)M = Muscular
N = Name
S = Stamina*/
class Character : public MoveSet
{
private:
	char gender;//F,M
	float height; //in Meters
	float weight; //in Kg
	char bodyType; //S,F,M
	string name;
	int Hp;
	int stamina;

public:
	Character(); //Default constructor
	Character(char G,float H,float W,char  B); //used if the user wants a random character

	//Getters
	char getGender();
	float getHeight();
	float getWeight();
	char getBodyType();
	string getName();
	int getHp();
	int getStamina();

	//Setters
	void setGender(char G);
	void setHeight(float H);
	void setWeight(float W);
	void setBodyType(char B);
	void setName(string N);
	void setStamina(int S);

	//friend operators
	friend istream& operator>>(istream& fin,Character& c);
	void print();

};

