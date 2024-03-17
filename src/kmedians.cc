#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>
#include <unordered_set>
#include <cmath>
#include <cfloat>
#include <random>
#include "kmeans.cc"
#include <algorithm>
using namespace std;

typedef vector<double> PointND;

class Kmedians:public Kmeans {
    public:

        Kmedians() {
        }

        bool member(const vector<PointND>& clusters, PointND p){
            for (int i = 0; i < clusters.size(); ++i) {
                if (clusters[i] == p) return true;
            }
            return false;
        }

        virtual void execute(int num_clusters) {
            k = num_clusters;
            vector<vector<int>> assignation;
            vector<PointND> clusters = initialize_clusters("kpp");            

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

       protected:



        virtual bool update_cluster(const vector<vector<int>>& assignations, vector<PointND>& clusters) {
            bool no_change = true;


            for (int i = 0; i < clusters.size(); ++i) {
                PointND newCentroid(data[0].size(), 0);
                vector<double> dist;
                for (int p_it : assignations[i]) {
                    PointND p = data[p_it];
                    dist.push_back(sed(clusters[i], p));
                } 

                    nth_element(dist.begin(), dist.begin() + dist.size() / 2, dist.end());
                    double medianDistance = dist[dist.size() / 2];

                    PointND max = clusters[i];
                    // Find the point closest to the median (eager update)
                    for (int p_it : assignations[i]) {
                        PointND p = data[p_it];   
                        if ( sed(clusters[i], p) < medianDistance) {
                            max = p;
                        }
                    }

    
                    if (max != clusters[i]) {
                        swap(clusters[i], max);
                        no_change = false;
                    }
                }
            return no_change;
        } 

};