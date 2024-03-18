#ifndef KMEANS_H
#define KMEANS_H

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
        int MAX_ITER = 100;
        string FILE_INPUT = "../data/output/stats/";
        string FILE_OUTPUT = "../data/output/kmeans/";

        int k; //Num Clusters
        vector<PointND> data;
        
        vector<int> final_assignation;
        vector<PointND> final_clusters;

    public:

        Kmeans() {
        }

        virtual void execute(int num_clusters, string initialization_method) {
            k = num_clusters;
            vector<vector<int>> assignation;
            vector<PointND> clusters = initialize_clusters(initialization_method); //kpp o forgy           

            bool converged = false;
            int count = 0;
            while (not converged and count != MAX_ITER) {
                assignation = assign_cluster(clusters);
                converged = update_cluster(assignation, clusters);
                
                //cout << "Iter:" << count << endl;
                //for (vector<int> v : assignation) {
                //    cout << "Cluster: ";
                //    for (int x : v) cout << x << ' ';
                //    cout << endl;
                //}

                ++count;
            }

            
            final_assignation = vector<int>(data.size());
            for (int i = 0; i < assignation.size(); ++i) {
                for (int j = 0; j < assignation[i].size(); ++j) 
                    final_assignation[assignation[i][j]] = i;
            }

            final_clusters = clusters;
            
        }

        void load_data(string filename) {

            ifstream file(FILE_INPUT + filename);

            if (!file.is_open()) throw runtime_error("File not opened");

            int c = -1;
            for (string line; getline(file, line);) {
                istringstream ss(line);

                data.push_back({});
                ++c;

                
                for (string val; getline(ss, val, ',');) {
                    data[c].push_back(stod(val));
                    //cout << stod(val) << ' ';
                }
            }
        }

        void write_results(string filename) {

            fstream file;

            file.open(FILE_OUTPUT + filename, ios::out | ios::trunc);

            if (!file.is_open()) throw runtime_error("Output File not opened");

            file << data[0].size() << "," << k << "\n";

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

        //Squared euclidean distance for n dimensional points
        double sed(const PointND& p1, const PointND& p2) {
            double sum = 0;
            for (int i = 0; i < p1.size(); ++i) sum += (p1[i] - p2[i])*(p1[i] - p2[i]);
            return sum;
        }

        virtual vector<PointND> initialize_clusters(string method) {
            if (method == "forgy") return forgy_initialization();
            else if (method == "kpp") return kpp_initialization();
        }

        virtual vector<vector<int>> assign_cluster(const vector<PointND>& clusters) {
            vector<vector<int>> newAssignations(clusters.size());

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

                newAssignations[min_cluster].push_back(i);
            }

            return newAssignations;
        }

        virtual bool update_cluster(const vector<vector<int>>& assignations, vector<PointND>& clusters) {
            bool no_change = true;

            for (int i = 0; i < clusters.size(); ++i) {
                PointND newCentroid(data[0].size(), 0);
                
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

    private:
        vector<PointND> forgy_initialization() {
            unordered_set<int> clusters_id;

            //Forgy Initialization method
            random_device rd;
            default_random_engine generator(rd());
            uniform_int_distribution<int> distribution(0, data.size());
            
            //cout << "Randomly generated point ids:" << endl;
            while (clusters_id.size() != k) {
                int id = distribution(generator);
                clusters_id.insert(id);
                //cout << id << endl;
            }

            vector<PointND> clusters(k);
            int i = 0;
            for (int id : clusters_id) clusters[i++] = data[id];

            /*cout << "Chosen initial clusters:" << endl;
            for (PointND p : clusters) {
                cout << "size " << p.size() << ": "; 
                for (double x : p) cout << x << ' ';
                cout << endl;
            }*/

            return vector<PointND>(clusters.begin(), clusters.end());
        }

        vector<PointND> kpp_initialization() {
            vector<PointND> clusters;

            //K++ Initialization method
            random_device rd;
            default_random_engine generator(rd());
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

#endif