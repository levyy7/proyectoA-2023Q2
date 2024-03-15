#include "kmeans.cc"

using namespace std;

int main(int argc, char* argv[]) {
    Kmeans km;

    km.load_data(string(argv[1]));
    km.execute(std::stoi(argv[3]));
    km.write_results(string(argv[2]));
    
}