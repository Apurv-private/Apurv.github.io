#include <iostream>
#include "Character.h"
#include <string>



Character::Character()
{
	height = weight = 0;
	name = "";
	Hp = 200;
	stamina = 0;
}

Character::Character(char G,float H,float W,char B)
{
	this->setWeight(W);
	this->setHeight(H);
	this->setBodyType(B);
	this->setGender(G);
}

char Character::getGender()
{
	return gender;
}

float Character::getHeight()
{
	return height;
}

float Character::getWeight()
{
	return weight;
}

char Character::getBodyType()
{
	return bodyType;
}

string Character::getName()
{
	return name;
}

int Character::getHp()
{
	return Hp;
}

int Character::getStamina()
{
	return stamina;
}

void Character::setGender(char G)
{
	gender = G;
}

void Character::setBodyType(char B)
{
	bodyType = B;
}

void Character::setHeight(float H)
{
	height = H;
}

void Character::setWeight(float W)
{
	weight = W;
}

void Character::setName(string N)
{
	name = N;
}

void Character::setStamina(int S)
{
	stamina = S;
}

istream& operator>>(istream& fin,Character& c)
{
	string name;
	char gender,bodyType;
	float height,weight;
	string attack1,attack2,attack3,finisher;
	string power1,power2,power3,powerF;
	cout<<"Select One Gender from 'M' for Male or 'F' for Female:	";
	fin>>gender;
	c.setGender(gender);
	cout<<"Specify the height of your character(cm):	";
	fin>>height;
	c.setHeight(height);
	cout<<"Specify the weight of your character(kg):	";
	fin>>weight;
	c.setWeight(weight);
	cout<<"Select one bodyType of your character from 'S' for Slim,'F' for fat & 'M' for Muscular:	";
	fin>>bodyType;
	c.setBodyType(bodyType);
	cout<<"Enter the name for your character:	";
	fin>>name;
	c.setName(name);
	cout<<"Type your first attack:	";
	fin>>attack1;
	c.setA1(attack1);
	cout<<"Power of first attack(0-50): ";
	fin>>power1;
	c.setPower1(power1);
	cout<<"Type your second attack:	";
	fin>>attack2;
	c.setA2(attack2);
	cout<<"Power of second attack(0-50): ";
	fin>>power2;
	c.setPower2(power2);
	cout<<"Type your third attack:	";
	fin>>attack3;
	c.setA3(attack3);
	cout<<"Power of third attack(0-50): ";
	fin>>power3;
	c.setPower3(power3);
	cout<<"Type your Finisher:	";
	fin>>finisher;
	c.setFinisher(finisher);
	cout<<"Power of your finisher(0-100):	";
	fin>>powerF;
	c.setPowerFinisher(powerF);
	return fin;
}

void Character::print()
{
	cout<<"Your player name is "<<this->name <<endl;
	cout<<"Height is "<<this->height<<endl;
	cout<<"Weight is "<<this->weight<<endl;
	cout<<"Gender is "<<this->gender<<endl;
	cout<<"BodyType is "<<this->bodyType<<endl;
	cout<<"First Attack is "<<this->getA1()<<" & Power is "<<this->getPower1()<<endl;
	cout<<"Second Attack is "<<this->getA2()<<" & Power is "<<this->getPower2()<<endl;
	cout<<"Third Attack is "<<this->getA3()<<" & Power is "<<this->getPower3()<<endl;
	cout<<"Finisher is "<<this->getFinisher()<<" & Power is "<<this->getPowerFinisher()<<endl;
}