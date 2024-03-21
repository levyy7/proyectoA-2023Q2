#ifndef KMEDIODS_H
#define KMEDIODS_H

#include "kmeans.cc"

using namespace std;


class Kmediods:public Kmeans {
    public:

        Kmediods() {
        }


    protected:
        void algorithm(int num_clusters, string initialization_method) override {
            cout << "estoy kmediods" << endl;
            random_device rd;
            default_random_engine generator(rd());
            uniform_int_distribution<int> distribution(0, data.size());
            k = num_clusters;
            vector<PointND> medoids = initialize_clusters(initialization_method);     
            double best_cost = 0.0;
            vector<vector<int>> assignation = assign_medoids(medoids, best_cost);

            int count = 0;
            double cost = DBL_MAX;
            
            do {
                //cout << count++ << endl;
                best_cost = cost;
                int best_first_index, best_second_index;
                double do_cost = DBL_MAX;
                for (int i = 0; i < medoids.size(); i++)
                {
                    int random_point;
                    PointND first_swapped, second_swapped;
                    do {
                        random_point = distribution(generator);
                    }
                    while (member(medoids, data[random_point]));
                    first_swapped = medoids[i];
                    second_swapped = data[random_point];
                    medoids[i] = data[random_point];
                    //swapped
                    double local_cost = get_cost(medoids);
                    medoids[i] = first_swapped;
                    data[random_point] = second_swapped;
                    //unswapped
                    if (local_cost < cost) {
                        cost = local_cost;
                        best_first_index = i;
                        best_second_index = random_point;
                    }
                }
                //cout << cost << " " << best_cost << endl;
                if (cost < best_cost) {
                    medoids[best_first_index] = data[best_second_index];
                }
            } while (cost < best_cost);

            assignation = assign_medoids(medoids, cost);

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

        double get_cost(const vector<PointND>& clusters) {

            double cost = 0;

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
            }
            return cost;
        }

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