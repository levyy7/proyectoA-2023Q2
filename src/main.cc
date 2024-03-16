#include "kmediods.cc"

using namespace std;

int main() {
    Kmediods km;
    string dataset = "Dataset2";


    km.load_data(dataset + ".csv");
    //for (int k = 2; k <= 10; ++k) {
        km.execute(2);
        km.write_results(dataset + "-" + to_string(2) + ".csv");
    //}
    
}