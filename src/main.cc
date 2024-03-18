#include "kmeans.cc"
#include "kmedians.cc"
#include "kmediods.cc"

using namespace std;

int main(int argc, char* argv[]) {
    Kmeans km;

    switch (stoi(argv[4]))
    {
    case 0:
        km = Kmeans();
        break;
    case 1:
        km = Kmedians();
        break;
    case 2:
        km = Kmediods();
        break;

    default:
        km = Kmeans();
        break;
    }

    km.load_data(string(argv[1]));
    km.execute(std::stoi(argv[3]), "forgy");
    km.write_results(string(argv[2]));

}