#include <iostream>
#include"BinaryHeap.h"
#include"Event.h"
#include "PriorityQueue.h"
#include "Queue.h"
#include"EmptyDataCollectionException.h"
#include <string>
#include <fstream>
using namespace std;

int customerCount = 0;
float waitTime = 0;
float sumDepartureTime , sumArrivalTime,sumProcessingTime;
void processArrival(Event* arrivalEvent,PriorityQueue<Event>& pQueue,Queue<Event>& Q)
{
	int arrivalTime = 0;
    bool tellerAvailable = true;
    Event* customer = new Event();

    pQueue.dequeue();
    customer -> setTime(arrivalEvent -> getTime());
    customer -> setType(arrivalEvent -> getType());
    customer -> setLength(arrivalEvent -> getLength());

    if(Q.isEmpty() && tellerAvailable)
    {
    	arrivalTime =  arrivalEvent -> getTime() + arrivalEvent -> getLength();
    	Event* departureEvent = new Event();
    	departureEvent = new Event('D',arrivalTime,0);
    	pQueue.enqueue(*departureEvent);
    	tellerAvailable = false;
    	cout << "Processing an arrival event at time: " << arrivalEvent -> getTime() << endl; 
    }
    else
    	pQueue.enqueue(*customer);
    sumArrivalTime = arrivalTime;
}

void processDeparture (Event* departureEvent,PriorityQueue<Event>& pQueue,Queue<Event>& Q)
{
	bool tellerAvailable;
	Event* customer = new Event();
	Event* newDepartureEvent = new Event();

	int departureTime, currentTime , transactionTime = 0;
	currentTime = departureEvent -> getTime();
	transactionTime = departureEvent -> getLength();

	pQueue.dequeue();
	if(!Q.isEmpty())
	{
		*customer = Q.peek();
		pQueue.dequeue();
		departureTime = currentTime + transactionTime;
		newDepartureEvent -> setTime(departureTime);
		newDepartureEvent -> setType('D');
		newDepartureEvent -> setLength(0);
		pQueue.enqueue(*newDepartureEvent);
		tellerAvailable = false;
	}
	else
		tellerAvailable = true;
	cout << "Processing a departure event at time: " << departureTime << endl;
	sumDepartureTime = departureTime;

}

 
void simulate()
{
	PriorityQueue<Event>* EventListPQ = new PriorityQueue<Event>();
	Queue<Event>* BankQ = new Queue<Event>();
	bool available = true;
	int newTime , newLength , currentTime = 0;
	Event* customer = new Event();

	ifstream input ("simulationShuffled1.in");
	if(input.fail())
	{
		cout<<"ERROR";
		return;
	}

	while( input.eof() == false)
	{
		input >> newTime >> newLength;
		customer -> setType('A');
		customer -> setTime(newTime);
		customer -> setLength(newLength);
		customerCount++;

		EventListPQ -> enqueue(*customer);
		sumProcessingTime += customer -> getLength();
	}

	while( !EventListPQ->isEmpty())
	{
		Event* e1 = new Event();
		*e1 = EventListPQ -> peek();
		currentTime = e1 -> getTime();

		if(e1 -> getType() == 'A')
			processArrival(e1,*EventListPQ,*BankQ);
		else
			processDeparture(e1,*EventListPQ,*BankQ);
	}

	input.close();
}


 int main()
{
	 cout << "Simulation Begins" << endl;
    simulate();
    cout << "Simulation Finished" << endl;
     cout<<"Final Statistics:"<<endl<<endl;
     float waitTime = (float)(sumDepartureTime - sumProcessingTime - sumArrivalTime)/customerCount;
     cout<<"\t Total number of people processed: "<<customerCount<<endl;
     cout<<"\t Average amount of time spend waiting: "<<waitTime<<endl;


	system("PAuSE");
	return 0;
}