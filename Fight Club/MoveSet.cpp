#include "MoveSet.h"
#include <string>


void MoveSet::setA1(string A1)
{
	attack1 = A1;
}

void MoveSet::setA2(string A2)
{
	attack2 = A2;
}

void MoveSet::setA3(string A3)
{
	attack3 = A3;
}

void MoveSet::setFinisher(string A4)
{
	finisher = A4;
}

void MoveSet::setPower1(string p1)
{
	power1 = p1;
}

void MoveSet::setPower2(string p2)
{
	power2 = p2;
}

void MoveSet::setPower3(string p3)
{
	power3 = p3;
}

void MoveSet::setPowerFinisher(string powerF)
{
	powerFinisher = powerF;
}


string MoveSet::getA1()
{
	return attack1;
}

string MoveSet::getA2()
{
	return attack2;
}

string MoveSet::getA3()
{
	return attack3;
}

string MoveSet::getFinisher()
{
	return finisher;
}

string MoveSet::getPower1()
{
	return power1;
}

string MoveSet::getPower2()
{
	return power2;
}

string MoveSet::getPower3()
{
	return power3;
}

string MoveSet::getPowerFinisher()
{
	return powerFinisher;
}

