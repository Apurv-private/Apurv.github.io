#include <iostream>
#include <fstream>
#include <iomanip>
using namespace std;
int main() {
    srand((unsigned int)time(NULL));
    ofstream out("input.txt");
    float limit = 5.0;
    for (int i=0; i<20; i++) {
      float val = ((float)rand()/(float)(RAND_MAX)) * limit;
      out << fixed << setprecision(5) << val << endl;
    }
    out.close();
    return 0;
}
