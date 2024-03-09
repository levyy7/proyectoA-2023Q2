#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <unordered_set>
#include <cmath>
#include <cfloat>
#include <random>

using namespace std;

typedef vector<double> PointND;

class Kmeans {
    protected:
        const int MAX_ITER = 100;

        int n; //Num Points
        int d; //Num Dimensions
        int k; //Num Clusters
        vector<PointND> data;
        vector<int> expected_cluster;

        //unordered_set<PointND> clusters;



        //Squared euclidean distance for n dimensional points
        double sed(const PointND& p1, const PointND& p2) {
            double sum = 0;
            for (int i = 0; i < p1.size(); ++i) sum += (p1[i] - p2[i])*(p1[i] - p2[i]);
            return sum;
        }

        virtual vector<PointND> initialize_clusters() {
            unordered_set<int> clusters_id;

            //Forgy Initialization method
            default_random_engine generator;
            uniform_int_distribution<int> distribution(0, n);
            
            while (clusters_id.size() != k) clusters_id.insert(distribution(generator));

            vector<PointND> clusters(k);
            int i = 0;
            for (int id : clusters_id) clusters[i++] = data[id];

            return vector<PointND>(clusters.begin(), clusters.end());
        }

        virtual vector<vector<int>> assign_cluster(const vector<PointND>& clusters) {
            vector<vector<int>> newAssignations(clusters.size());

            for (int i = 0; i < data.size(); ++i) {
                double min_dis = DBL_MAX;
                for (int j = 0; j < clusters.size(); ++j) {
                    double dis = sed(data[i], clusters[j]);
                    int min_cluster = -1;

                    if (dis < min_dis) {
                        min_dis = dis;
                        min_cluster = j;
                    }

                    newAssignations[j].push_back(i);
                }
            }

            return newAssignations;
        }

        virtual bool update_cluster(const vector<vector<int>>& assignations, vector<PointND>& clusters) {
            bool no_change = true;

            for (int i = 0; i < clusters.size(); ++i) {
                PointND newCentroid(d, 0);
                
                for (int p_it : assignations[i]) {
                    PointND p = data[p_it];
                    for (int j = 0; j < p.size(); ++j) newCentroid[j] += p[j];
                } 

                for (int j = 0; j < newCentroid.size(); ++j) 
                    newCentroid[j] /= double(assignations[i].size());

                
                if (not (clusters[i] == newCentroid)) no_change = false;
                clusters[i] = newCentroid;
            }

            return no_change;
        }

    public:
        
        Kmeans() {
        }


        void load_data(string filename) {

            ifstream file(filename);

            if (!file.is_open()) throw runtime_error("File not opened");

            int c = -1;
            for (string line; getline(file, line);) {
                istringstream ss(line);

                data.push_back({});
                ++c;

                
                for (string val; getline(ss, val, ';');) {
                    data[c].push_back(stod(val));
                    //cout << val << ' ' << stod(val) << ' ';
                }
            }
        }

        void print_data() {
            //cout << "Num Points:" << n << ", ";
            //cout << "Num Dimensions:" << d << ", ";
            //cout << "Num Clusters:" << k << endl;

            for (int i = 0; i < data.size(); ++i) {
                PointND p = data[i];

                cout << "Point" << i << ": ";
                for (double x : p) cout << x << ", ";
                cout << endl;
            }
        }

        virtual void execute() {
            vector<vector<int>> assignation;
            vector<PointND> clusters = initialize_clusters();            

            bool converged = false;
            int count = 0;
            while (not converged and count != MAX_ITER) {
                assignation = assign_cluster(clusters);
                converged = update_cluster(assignation, clusters);

                ++count;
            }

            
        }

};