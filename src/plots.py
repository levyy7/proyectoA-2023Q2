import numpy as np
import matplotlib.pyplot as plt
import json

wcss_meansforgy = [4366.47, 3828.25, 3265.70, 2942.71, 2700.89, 2453.24, 2083.94, 1993.83, 1909.31]
wcss_mediansforgy = [2614.58, 2024.19, 1570.84, 1084.73, 953.046, 871.68, 820.375, 757.71, 702.44]
wcss_mediodsforgy = [3590.251880048099, 2818.1059847445226, 2266.174642046194, 1988.8705349943516, 1666.083557222175, 1408.3332873731135, 1342.7715087347335, 1166.5467719854182, 1095.1982435334505]
wcss_meanskpp = [4344.667687703551, 3626.0957602328576, 3153.4470390082447, 2681.9700986412745, 2300.8485067759357, 1971.5459980963817, 1776.065325109975, 1543.839478867799, 1481.467940710256]
wcss_medianskpp = [2775.5385176135514, 1935.1607044499083, 1488.0999804758208, 1084.7323660883824, 962.12245678564, 883.4463180792504, 810.2620804588447, 753.6142382852543, 711.4587871643853]
wcss_mediodskpp = [3750.037422607089, 2814.000306718992, 2259.0126417345523, 1880.9081637102772, 1602.4708030119232, 1376.4511076733972, 1256.5376680417648, 1156.9163599730102, 1064.6469901343303]


chi_kmeansforgy = [
    10213.1,
    8499.0,
    8703.8,
    10860.8,
    10277.0,
    9567.7,
    8845.1,
    8515.8,
    8286.1,
]
dbi_kmeansforgy = [
    0.4929,
    0.5179,
    0.5144,
    0.4492,
    0.5193,
    0.5465,
    0.5670,
    0.5741,
    0.5828,
]
slh_kmeansforgy = [
    0.4161,
    0.3595,
    0.3425,
    0.3838,
    0.3365,
    0.3150,
    0.3042,
    0.2934,
    0.2885,
]

chi_kmediansforgy = [
    3792.9,
    3015.5,
    2762.6,
    2665.2,
    2512.1,
    2489.1,
    2705.1,
    2550.6,
    2382.8,
]
dbi_kmediansforgy = [
    1.2172,
    1.3937,
    1.3794,
    1.3951,
    1.4349,
    1.4699,
    1.3314,
    1.3924,
    1.3976,
]
slh_kmediansforgy = [
    0.2764,
    0.2177,
    0.1785,
    0.1755,
    0.1691,
    0.1770,
    0.1939,
    0.1850,
    0.1876,
]


chi_kmediodsforgy = [
    8006.7,
    6221.5,
    6306.6,
    5687.0,
    6068.5,
    6203.4,
    5618.6,
    5822.9,
    5463.8,
]
dbi_kmediodsforgy = [
    0.7144,
    0.7155,
    0.6799,
    0.7188,
    0.7121,
    0.7519,
    0.7853,
    0.7632,
    0.7831,
]
slh_kmediodsforgy = [
    0.3729,
    0.3003,
    0.3024,
    0.2800,
    0.2771,
    0.2777,
    0.2515,
    0.2464,
    0.2395,
]


chi_kmeanskpp = [
    9117.6,
    9211.8,
    9345.6,
    10860.8,
    10159.0,
    9431.9,
    8981.3,
    8576.2,
    8159.3,
]
dbi_kmeanskpp = [
    0.4644,
    0.4576,
    0.4725,
    0.4492,
    0.5137,
    0.5524,
    0.5717,
    0.5891,
    0.5974,
]
slh_kmeanskpp = [
    0.4329,
    0.3969,
    0.3649,
    0.3838,
    0.3425,
    0.3244,
    0.3125,
    0.3028,
    0.2982,
]

chi_kmedianskpp = [
    4809.9,
    5149.5,
    4454.9,
    4703.3,
    4586.1,
    4757.8,
    4349.3,
    4421.5,
    3914.0,
]
dbi_kmedianskpp = [
    1.0447,
    1.0407,
    1.2369,
    1.1596,
    1.1264,
    0.9846,
    1.0483,
    1.0081,
    0.9913,
]
slh_kmedianskpp = [
    0.3287,
    0.2590,
    0.2112,
    0.2016,
    0.2065,
    0.2220,
    0.2255,
    0.2339,
    0.2229,
]


chi_kmediodskpp = [
    8355.6,
    6867.1,
    6448.1,
    7101.4,
    6896.3,
    6862.8,
    6382.1,
    6263.4,
    6050.6,
]
dbi_kmediodskpp = [
    0.6560,
    0.6605,
    0.6979,
    0.6491,
    0.6849,
    0.6681,
    0.6981,
    0.7186,
    0.7330,
]
slh_kmediodskpp = [
    0.3597,
    0.3131,
    0.2996,
    0.3006,
    0.2914,
    0.2813,
    0.2658,
    0.2530,
    0.2426,
]

wcss = [
    wcss_meansforgy,
    wcss_mediansforgy,
    wcss_mediodsforgy,
    wcss_meanskpp,
    wcss_medianskpp,
    wcss_mediodskpp
]

chi = [
    chi_kmeansforgy,
    chi_kmediansforgy,
    chi_kmediodsforgy,
    chi_kmeanskpp,
    chi_kmedianskpp,
    chi_kmediodskpp
]

dbi = [
    dbi_kmeansforgy,
    dbi_kmediansforgy,
    dbi_kmediodsforgy,
    dbi_kmeanskpp,
    dbi_kmedianskpp,
    dbi_kmediodskpp
]

slh = [
    slh_kmeansforgy,
    slh_kmediansforgy,
    slh_kmediodsforgy,
    slh_kmeanskpp,
    slh_kmedianskpp,
    slh_kmediodskpp
]

algorithm_type = ["kmeans + forgy", "kmedians + forgy", "kmedoids + forgy", "kmeans + k++", "kmedians + k++", "kmedoids + k++"]
index_type = ["wcss", "chi", "dbi", "slh"]
index_max_values = [5000, 12000, 1.6, 1.0]

data = [
    wcss,
    chi,
    dbi,
    slh
]

k_list = [2, 3, 4, 5, 6, 7, 8, 9, 10]
#optimum_k_list = [8, 6, 7, 9, 7, 8]
optimum_k_list = [
    [8, 6, 7, 9, 7, 8],
    [5, 2, 2, 5, 3, 2],
    [5, 2, 4, 5, 7, 5],
    [2, 2, 2, 2, 2, 2]]

#for index in range(0, 3):


for j in range(4):
    fig, axs = plt.subplots(2, 3, figsize=(12, 8))

    axs = axs.flatten()
    for i in range(6):
        #plt.plot(k_list, data, '-gx', markevery=optimum_k_list, label='line with select markers', markerfacecolor='red')
        axs[i].plot(k_list, data[j][i], '-o')
        axs[i].plot(optimum_k_list[j][i], data[j][i][optimum_k_list[j][i] - 2], 's', color='red', markersize=7)
        axs[i].axvline(x=optimum_k_list[j][i], color='gray', linestyle="--")

        axs[i].set_ylim(0, index_max_values[j])

        axs[i].set_title(algorithm_type[i])
        axs[i].set_ylabel(index_type[j])
        axs[i].set_xlabel('num clusters')
    
    plt.tight_layout()
    plt.savefig("../doc/plots/" + index_type[j] + "_plotset.jpg")

