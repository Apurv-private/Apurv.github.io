#include <iostream>
#include <string>
#include <cstdlib>
#include <math.h>
#include <ctime>
#include "Character.h"
#include "MoveSet.h"
using namespace std;

int noOfPlayers;
template <class Player>
class Game : public Character
{
public:
	void oneToOne(Player& p1,Player& p2,Player& g);
	void TripleThreat(Player& p1,Player& p2,Player& p3);
	void HandiCap(Player& p1,Player& p2,Player& p3);
};

template <class Player>
void fight(Player& p1,Player& p2)
{
	char player1;
	char player2;

	if(p1.getHp() <= 0 || p2.getHp() <= 0)
		return;

	cout<<"First player choose your attack from 1,2,3 and 4 with 4th being the finisher"<<endl;
	cin>>player1;
	bool done = false;
	while(!done)
	{
		switch(player1)
		{
		case '1': cout<<"First player used attack1 named "<<p1.getA1()<<" against second player with power "<<p1.getPower1()<<"\nThe Hp of second player reduced to "<<p2.getHp();p1.setStamina(p1.getStamina()+20);done = true;
		case '2': cout<<"First player used attack2 named "<<p1.getA2()<<" against second player with power "<<p1.getPower1()<<"\nThe Hp of second player reduced to "<<p2.getHp();p1.setStamina(p1.getStamina()+20);done = true;
		case '3': cout<<"First player used attack3 named "<<p1.getA3()<<" against second player with power "<<p1.getPower1()<<"\nThe Hp of second player reduced to "<<p2.getHp();p1.setStamina(p1.getStamina()+20);done = true;
		case '4': if(p1.getStamina() >=50){cout<<"First player used finished named "<<p1.getFinisher()<<" against second player with power "<<p1.getPowerFinisher()<<"\nThe Hp of second player reduced to "<<p2.getHp();p1.setStamina(p1.getStamina()-50);done = true;}else {break;}
		default: cout<<"Enter again"<<endl;
		}
	}

	cout<<"Now its your turn second player, choose your attack from 1,2,3 and 4 with 4th being the finisher"<<endl;
	cin>>player2;
	done = false;
	while(!done)
	{
		switch(player2)
		{
		case '1': cout<<"Second player used attack1 named "<<p2.getA1()<<" against first player with power "<<p2.getPower1()<<"\nThe Hp of second player reduced to "<<p1.getHp();p2.setStamina(p2.getStamina()+20);done = true;
		case '2': cout<<"Second player used attack2 named "<<p2.getA2()<<" against first player with power "<<p2.getPower1()<<"\nThe Hp of second player reduced to "<<p1.getHp();p2.setStamina(p2.getStamina()+20);done = true;
		case '3': cout<<"Second player used attack3 named "<<p2.getA3()<<" against first player with power "<<p2.getPower1()<<"\nThe Hp of second player reduced to "<<p1.getHp();p2.setStamina(p2.getStamina()+20);done = true;
		case '4': if(p2.getStamina() >= 50){cout<<"Second player used finished named "<<p2.getFinisher()<<" against first player with power "<<p2.getPowerFinisher()<<"\nThe Hp of second player reduced to "<<p1.getHp();p2.setStamina(p1.getStamina()-50);done = true;}else {break;}
		default: cout<<"Enter again"<<endl;
		}
	}
	fight(p1,p2);

}

template <class Player>
void fight(Player& p1,Player& p2,Player& p3,bool& p)
{
	char player1;
	char player2;
	char player3;
	int pl1;
	int pl2;
	int pl3;

	if(p1.getHp() <= 0 || p2.getHp() <= 0)
		return;
	/*Give them the freedom to chose their opponents to whom they want to attack*/
	cout<<"First player choose your attack from 1,2,3 and 4 with 4th being the finisher"<<endl;
	cin>>player1;
	bool done = false;
	while(!done)
	{
		switch(player1)
		{
		case '1': cout<<"First player used attack1 named "<<p1.getA1()<<" against second player with power "<<p1.getPower1()<<"\nThe Hp of second player reduced to "<<p2.getHp()-p1.getPower1();p1.setStamina(p1.getStamina()+20);done = true;
		case '2': cout<<"First player used attack2 named "<<p1.getA2()<<" against second player with power "<<p1.getPower2()<<"\nThe Hp of second player reduced to "<<p2.getHp()-p1.getPower2();p1.setStamina(p1.getStamina()+20);done = true;
		case '3': cout<<"First player used attack3 named "<<p1.getA3()<<" which healed itself for "<<p1.getPower3()<<"\nThe Hp of First player is now  "<<p.getHp()+p1.getPower3();p1.setStamina(p1.getStamina()+20);done = true;
		case '4': if(p1.getStamina() >=50){cout<<"First player used finished named "<<p1.getFinisher()<<" against second player with power "<<p1.getPowerFinisher()<<"\nThe Hp of second player reduced to "<<p2.getHp()-p1.getPowerFinisher();p1.setStamina(p1.getStamina()-50);done = true;}else {break;}
		default: cout<<"Enter again"<<endl;
		}
	}

	cout<<"Second player now its your turn to choose your attack from 1,2,3 and 4 with 4th being the finisher"<<endl;
	cin>>player2;
	done = false;
	while(!done)
	{
		switch(player2)
		{
		case '1': cout<<"Second player used attack1 named "<<p2.getA1()<<" against first player with power "<<p2.getPower1()<<"\nThe Hp of second player reduced to "<<p1.getHp()-p2.getPower1();p2.setStamina(p2.getStamina()+20);done = true;
		case '2': cout<<"Second player used attack2 named "<<p2.getA2()<<" against first player with power "<<p2.getPower2()<<"\nThe Hp of second player reduced to "<<p1.getHp()-p2.getPower2();p2.setStamina(p2.getStamina()+20);done = true;
		case '3': cout<<"Second player used attack3 named "<<p2.getA3()<<" which healed itself for "<<p2.getPower3()<<"\nThe Hp of second player is now "<<p2.getHp()+p2.getPower3();p2.setStamina(p2.getStamina()+20);done = true;
		case '4': if(p2.getStamina() >= 50){cout<<"Second player used finished named "<<p2.getFinisher()<<" against first player with power "<<p2.getPowerFinisher()<<"\nThe Hp of second player reduced to "<<p1.getHp();p2.setStamina(p1.getStamina()-50);done = true;}else {break;}
		default: cout<<"Enter again"<<endl;
		}
	}

	cout<<"Now its your turn third player, choose your attack from 1,2,3 and 4 with 4th being the finisher"<<endl;
	cin>>player3;
	done = false;
	while(!done)
	{
		switch(player3)
		{
		case '1': cout<<"Third player used attack1 named "<<p3.getA1()<<" against first player with power "<<p3.getPower1()<<"\nThe Hp of second player reduced to "<<p3.getHp();p3.setStamina(p3.getStamina()+20);done = true;
		case '2': cout<<"Third player used attack2 named "<<p3.getA2()<<" against first player with power "<<p3.getPower2()<<"\nThe Hp of second player reduced to "<<p3.getHp();p3.setStamina(p3.getStamina()+20);done = true;
		case '3': cout<<"Third player used attack3 named "<<p3.getA3()<<" against first player with power "<<p3.getPower3()<<"\nThe Hp of second player reduced to "<<p3.getHp();p3.setStamina(p3.getStamina()+20);done = true;
		case '4': if(p3.getStamina() >= 50){cout<<"Second player used finished named "<<p2.getFinisher()<<" against first player with power "<<p2.getPowerFinisher()<<"\nThe Hp of second player reduced to "<<p1.getHp();p2.setStamina(p1.getStamina()-50);done = true;}else {break;}
		default: cout<<"Enter again"<<endl;
		}
	}
	fight(p1,p2,p3);

}

template <class Player>
void Game<Player>::oneToOne(Player& p1,Player& p2,Player& g)
{
	char whoIsFirst;
	cout<<"If you want to decide who plays first press F or R for random"<<endl;
	cin>>whoIsFirst;
	bool first;
	bool done = false;
	while(!done)
	{
		switch(whoIsFirst)
		{
		case 'F':cout<<"Anyone of you who wants to play first press 1";cin>>first;done = true;
		case 'R': first = rand() % 2;done = true;
		default : cout<<"ENTER AGAIN"<<endl;
		}
	}
	if(whoIsFirst == 'F')
	{
	cout<<"Whoever chose "<<whoIsFirst<<" is first player"<<endl;
	cout<<"PREPARE TO FIGHT"<<endl;
	fight(p1,p2,first);
	}
	else
	{
		cout<<"Whoever chose "<<first<<" is first player"<<endl;
		cout<<"PREPARE TO FIGHT"<<endl;
		fight(p1,p2,first);
	}

}

template <class Player>
void Game<Player>::TripleThreat(Player& p1,Player& p2,Player& p3)
{
}

template <class Player>
void Game<Player>::HandiCap(Player& p1,Player& p2,Player& p3)
{
	//different fight function for this
}

int main()
{
	srand(time(0));
	cout<<"Welcome to Fight Club"<<endl;
	cout<<"How many players are going to play today 2 or 3 players?"<<endl;
	cin>>noOfPlayers;
	Game<Character> match;
	Character* c1 = new Character[noOfPlayers];
	for(int i = 0 ; i < noOfPlayers;i++)
	{
		cout<<"Type data for "<<i+1<<" player"<<endl;
		cin>>c1[i];
		c1[i].print();
	}
	if(noOfPlayers == 2)
		match.oneToOne(c1[noOfPlayers-2],c1[noOfPlayers-1],match);
	else if(noOfPlayers == 3)
	{
		char a;
		cout<<"Press H for 1v2 Handicap match or T for triple threat"<<endl;
		cin>>a;
		string s;
		bool done = false;
		while(!done)
		{
			switch(a)
			{
			case 'H': match.HandiCap(c1[noOfPlayers-3],c1[noOfPlayers-2],c1[noOfPlayers -1]); done = true;
			case 'T': match.TripleThreat(c1[noOfPlayers-3],c1[noOfPlayers-2],c1[noOfPlayers -1]); done = true;
			default: cout<<"Enter Again"<<endl;
			}
		}
	}
	
	system("PAUSE");
	return 0;
}