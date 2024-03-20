#ifndef KMEDIODS_H
#define KMEDIODS_H

#include "kmeans.cc"

using namespace std;


class Kmediods:public Kmeans {
    public:

        Kmediods() {
        }

        void execute(int num_clusters, string initialization_method) override {
            k = num_clusters;
            vector<PointND> medoids = initialize_clusters(initialization_method);     
            double best_cost = 0.0;
            vector<vector<int>> assignation = assign_medoids(medoids, best_cost);

            int count = 0;
            double cost = DBL_MAX;
            do {

            } while (cost < best_cost);



            final_assignation = vector<int>(data.size());
            for (int i = 0; i < assignation.size(); ++i) {
                for (int j = 0; j < assignation[i].size(); ++j) 
                    final_assignation[assignation[i][j]] = i;
            }
            final_clusters = medoids;
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

        


    private:

        vector<vector<int>> assign_medoids(const vector<PointND>& clusters, double& cost) {
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

        void updateMedoids(vector<PointND>& clusters, const vector<vector<int>>& assignation) {
            for (int i = 0; i < clusters.size(); ++i) {
                double minCost = 0.0;
                int newMedoid = -1;
                for (int j = 0; j < assignation[i].size(); ++j) {
                    double cost = 0.0;
                    for (int k = 0; k < assignation[i].size(); ++k) {
                        cost += sed(data[assignation[i][k]], data[assignation[i][j]]);
                    }
                    if (newMedoid == -1 || cost < minCost) {
                        newMedoid = assignation[i][j];
                        minCost = cost;
                    }
                }
                clusters[i] = data[newMedoid];
            }
        }

        //Manhattan distance for n dimensional points
        double md(const PointND& p1, const PointND& p2) {
            double sum = 0;
            for (int i = 0; i < p1.size(); ++i) sum += abs(p1[i] - p2[i]);
            return sum;
        }

        bool member(const vector<PointND>& clusters, PointND p) {
            for (int i = 0; i < clusters.size(); ++i) {
                if (clusters[i] == p) return true;
            }
            return false;
        }

};

#endif