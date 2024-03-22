import numpy as np
import matplotlib.pyplot as plt
import json

wcss_meansforgy = [1293.40, 671.92, 510.29, 385.44, 292.79, 260.99, 244.48, 223.53, 206.71]
wcss_mediansforgy = [1747.35, 1393.91, 1153.67, 855.10, 717.62, 639.27, 617.18, 537.01, 509.11]
wcss_mediodsforgy = [1169.07, 708.54, 562.70, 374.94, 320.27, 267.54, 246.09, 227.00, 214.80]
wcss_meanskpp = [1258.08, 692.57, 517.72, 368.51, 285.92, 270.00, 239.88, 219.94, 206.54]
wcss_medianskpp = [1763.61, 1359.59, 1064.98, 950.20, 713.24, 609.89, 605.48, 590.10, 576.46]
wcss_mediodskpp = [1279.16, 707.89, 517.41 ,405.38, 317.19 ,265.68, 246.88, 229.49, 216.90]


wcss = [
    wcss_meansforgy,
    wcss_mediansforgy,
    wcss_mediodsforgy,
    wcss_meanskpp,
    wcss_medianskpp,
    wcss_mediodskpp
]

algorithm_type = ["kmeans + forgy", "kmedians + forgy", "kmediods + forgy", "kmeans + k++", "kmedians + k++", "kmediods + k++"]
index_type = ["wcss", "chi", "slh"]
index_max_values = [2000, 15000, 1.0]


k_list = [2, 3, 4, 5, 6, 7, 8, 9, 10]
optimum_k_list = [7, 7, 7, 6, 7, 7]

#for index in range(0, 3):
fig, axs = plt.subplots(2, 3, figsize=(12, 8))

axs = axs.flatten()

for i in range(6):
    #plt.plot(k_list, data, '-gx', markevery=optimum_k_list, label='line with select markers', markerfacecolor='red')
    axs[i].plot(k_list, wcss[i], '-o')
    axs[i].plot(optimum_k_list[i], wcss[i][optimum_k_list[i] - 2], 's', color='red', markersize=7)
    axs[i].axvline(x=optimum_k_list[i], color='gray', linestyle="--")

    axs[i].set_ylim(0, index_max_values[0])

    axs[i].set_title(algorithm_type[i])
    axs[i].set_ylabel(index_type[0])
    axs[i].set_xlabel('num clusters')


plt.tight_layout()

plt.savefig("../doc/plots/" + index_type[0] + "_plotset.jpg")