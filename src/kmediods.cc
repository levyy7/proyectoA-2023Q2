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
    public:

        Kmeans() {
        }

        bool member(const vector<PointND>& clusters, PointND p){
            for (int i = 0; i < clusters.size(); ++i) {
                if (clusters[i] == p) return true;
            }
            return false;
        }
        
        virtual void execute(int num_clusters) {
            k = num_clusters;
            vector<PointND> clusters = initialize_clusters("kpp");    
            vector<int> assignation(data.size(), -1);   
            double totalCost = assign_cluster(clusters, assignation);

            bool changed = true;
            cout << data.size() << endl;
            while (changed) {
                changed = false;
                vector<PointND> new_clusters = clusters;
                for (int j = 0; j < k; ++j) {
                    for (int i = 0; i < data.size(); ++i) {
                        PointND p = data[i];
                        if (!member(clusters, p)) {
                            swap(new_clusters[j], p);
                            vector<int> new_assignation = assignation;
                            double new_cost = assign_cluster(new_clusters, new_assignation);
                            if (new_cost < totalCost) {
                                changed = true;
                                totalCost = new_cost;
                                clusters = new_clusters;
                                assignation = new_assignation;
                            } else {
                                swap(new_clusters[j], p);
                            }
                        }
                        cout << i << ", ";
                    }
                    cout << j << endl;
                }
            }
            final_assignation = assignation;
            final_clusters = clusters;
            for (PointND p : final_clusters) {
                for (int i = 0; i < p.size(); ++i) {
                    if (i != 0) cout << ',';
                    cout << p[i];
                }
                cout << "\n";
            }
            
        }

        void load_data(string filename) {

            ifstream file(FILE_INPUT + filename);

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

        void write_results(string filename) {

            fstream file;

            file.open(FILE_OUTPUT + filename, ios::out | ios::trunc);

            if (!file.is_open()) throw runtime_error("Output File not opened");

            file << data.size()<< "," << k << "\n";

            for (PointND p : final_clusters) {
                for (int i = 0; i < p.size(); ++i) {
                    if (i != 0) file << ',';
                    file << p[i];
                }
                file << "\n";
            }

            for (int x : final_assignation) file << x << "\n";

            file.close();
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

        


    protected:
        const int MAX_ITER = 100;
        const string FILE_INPUT = "../data/input/";
        const string FILE_OUTPUT = "../data/output/kmeans/";

        int k; //Num Clusters
        vector<PointND> data;
        
        vector<int> final_assignation;
        vector<PointND> final_clusters;

 


        //Squared euclidean distance for n dimensional points
        double sed(const PointND& p1, const PointND& p2) {
            double sum = 0;
            for (int i = 0; i < p1.size(); ++i) sum += abs(p1[i] - p2[i]);
            return sum;
        }

        virtual vector<PointND> initialize_clusters(string method) {
            if (method == "forgy") return forgy_initialization();
            else if (method == "kpp") return kpp_initialization();
        }

        virtual double assign_cluster(const vector<PointND>& clusters, vector<int>& assignation) {
            double total_cost = 0.0;
            for (int i = 0; i < data.size(); ++i) {
                double min_dis = DBL_MAX;
                int min_cluster = -1;
                for (int j = 0; j < clusters.size(); ++j) {
                    double dis = sed(data[i], clusters[j]);
                    
                    if (dis < min_dis) {
                        min_dis = dis;
                        min_cluster = j;
                    }
                }
                assignation[i] = min_cluster;
                total_cost += min_dis;
           }
            return total_cost;
        } 
/*
        virtual void update_cluster(vector<PointND>& clusters) {
            
            for (int p = 0; p < k; ++p) {
                double totalCost = 0.0;
                int newMedoid = clusters[p];
 
                for (uint i = 0; i < data.size(); ++i) {
                    double cost = 0.0;
                    for (uint j = 0; j < data.size(); ++j) {
                        cost += distance(data[i], data[j]);
                    }
 
                    if (cost < totalCost) {
                        totalCost = cost;
                        newMedoid = i;
                    }
                }
 
                medoids[k] = newMedoid;
            }

        }*/

    private:
        vector<PointND> forgy_initialization() {
            unordered_set<int> clusters_id;

            //Forgy Initialization method
            default_random_engine generator;
            uniform_int_distribution<int> distribution(0, data.size());
            
            while (clusters_id.size() != k) clusters_id.insert(distribution(generator));

            vector<PointND> clusters(k);
            int i = 0;
            for (int id : clusters_id) clusters[i++] = data[id];

            return vector<PointND>(clusters.begin(), clusters.end());
        }

        vector<PointND> kpp_initialization() {
            vector<PointND> clusters;

            //K++ Initialization method
            default_random_engine generator;
            uniform_int_distribution<int> distributionA(0, data.size());
            
            clusters.push_back(data[distributionA(generator)]);

            vector<double> D;
            while (clusters.size() != k) {
                D = compute_distribution(clusters);
                discrete_distribution<int> distributionB(D.begin(), D.end());

                clusters.push_back(data[distributionB(generator)]);
            }

            return clusters;
        }

        vector<double> compute_distribution(const vector<PointND>& clusters) {
            vector<double> res(data.size());
            long double sum = 0;

            for (int i = 0; i < res.size(); ++i) {
                double min_dis = DBL_MAX;
                
                for (PointND c : clusters) {
                    double dist = sed(data[i], c);
                    if (dist < min_dis) min_dis = dist;
                }

                sum += min_dis*min_dis;
                res[i] = min_dis*min_dis;
            }

            for (int i = 0; i < res.size(); ++i) res[i] /= sum;

            return res;
        }

    

};