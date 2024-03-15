#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <unordered_set>
#include <cmath>
#include <cfloat>
#include <random>
#include "kmeans.cc"

using namespace std;

typedef vector<double> PointND;

class Kmediods:public Kmeans {
    public:

        Kmediods() {
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
            double totalCost = 0.0;
            vector<vector<int>> assignation = assign_cluster(clusters, totalCost);


            bool changed = true;
            int count = 0;
            //cout << data.size() << endl;
            while (changed && count < MAX_ITER) {
                changed = false;
                vector<PointND> new_clusters = clusters;
                for (int j = 0; j < k; ++j) {
                    cout << assignation[j].size() << endl;
                    for (int i = 0; i < assignation[j].size(); ++i) {
                        PointND p = data[assignation[j][i]];
                        if (!member(clusters, p)) {
                            swap(new_clusters[j], p);
                            double new_cost = 0.0;
                            vector<vector<int>> new_assignation = assign_cluster(new_clusters, new_cost);
                            //cout << new_cost << " " << totalCost << endl;
                            if (new_cost < totalCost) {
                                changed = true;
                                totalCost = new_cost;
                                clusters = new_clusters;
                                assignation = new_assignation;
                            } else {
                                swap(new_clusters[j], p);
                            }
                        }
                        //cout << i << ", ";
                    }
                    //cout << j << endl;
                }
                ++count;
                cout << count << endl;
            }
            final_assignation = vector<int>(data.size());
            for (int i = 0; i < assignation.size(); ++i) {
                for (int j = 0; j < assignation[i].size(); ++j) 
                    final_assignation[assignation[i][j]] = i;
            }
            final_clusters = clusters;
            /*
            for (PointND p : final_clusters) {
                for (int i = 0; i < p.size(); ++i) {
                    if (i != 0) cout << ',';
                    cout << p[i];
                }
                cout << "\n";
            }
            */
            
        }

        


    protected:


        //Manhattan distance for n dimensional points
        double md(const PointND& p1, const PointND& p2) {
            double sum = 0;
            for (int i = 0; i < p1.size(); ++i) sum += abs(p1[i] - p2[i]);
            return sum;
        }

        virtual vector<vector<int>> assign_cluster(const vector<PointND>& clusters, double& cost) {
            vector<vector<int>> newAssignations(clusters.size());

            for (int i = 0; i < data.size(); ++i) {
                double min_dis = DBL_MAX;
                int min_cluster = -1;

                for (int j = 0; j < clusters.size(); ++j) {
                    double dis = md(data[i], clusters[j]);
                    
                    if (dis < min_dis) {
                        min_dis = dis;
                        min_cluster = j;
                    }
                }
                cost += min_dis;
                newAssignations[min_cluster].push_back(i);
            }

            return newAssignations;
        }


};