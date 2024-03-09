#include "kmeans.cc"

using namespace std;

int main() {
    Kmeans km;

    km.load_data("../data/Dataset4.csv");
    km.print_data();
    km.execute();
}