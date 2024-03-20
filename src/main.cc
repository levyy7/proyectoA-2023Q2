#include "kmeans.cc"
#include "kmedians.cc"
#include "kmediods.cc"

using namespace std;

int main(int argc, char* argv[]) {
    Kmeans* km;

    cout << "main" << endl;
    switch (stoi(argv[4]))
    {
    case 0:
        km = new Kmeans();
        break;
    case 1:
        km = new Kmedians();
        break;
    case 2:
        cout << "mediods" << endl;
        km = new Kmediods();
        break;

    default:
        km = new Kmeans();
        break;
    }

    cout << "mediods" << endl;
    km->load_data(string(argv[1]));
    cout << "mediods" << endl;
    km->execute(std::stoi(argv[3]), "kpp");
    cout << "mediods" << endl;
    km->write_results(string(argv[2]));

}