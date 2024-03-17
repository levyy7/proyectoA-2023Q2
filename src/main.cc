#include "kmediods.cc"

using namespace std;

int main() {
    Kmediods km;
    string dataset = "Dataset1";


    km.load_data(dataset + ".csv");
    //for (int k = 2; k <= 10; ++k) {
        km.execute(7);
        km.write_results(dataset + "-" + to_string(7) + ".csv");
    //}
    
}