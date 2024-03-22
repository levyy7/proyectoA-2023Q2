import numpy as np
import matplotlib.pyplot as plt
import json

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
tme_kmeansforgy = [
    0.0868,
    0.1796,
    0.2512,
    0.3340,
    0.3560,
    0.6718,
    0.9676,
    1.2176,
    1.3368,
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
tme_kmediansforgy = [
    0.0559,
    0.0688,
    0.0802,
    0.1011,
    0.1131,
    0.1438,
    0.1542,
    0.1522,
    0.1881,
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
tme_kmediodsforgy = [
    0.0287,
    0.0598,
    0.1250,
    0.1929,
    0.3257,
    0.5574,
    0.7425,
    1.0478,
    1.4079,
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
tme_kmeanskpp = [0.0962, 0.1625, 0.2548, 0.3125, 0.3878, 0.5826, 0.7843, 1.2750, 1.3700]

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
tme_kmedianskpp = [
    0.0562,
    0.0803,
    0.1172,
    0.1497,
    0.1821,
    0.2446,
    0.2921,
    0.3462,
    0.4065,
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
tme_kmediodskpp = [
    0.0394,
    0.0861,
    0.1569,
    0.2390,
    0.3912,
    0.5442,
    0.7899,
    1.1205,
    1.4707,
]

chi = [chi_kmeansforgy, chi_kmediansforgy, chi_kmediodsforgy, chi_kmeanskpp, chi_kmedianskpp, chi_kmediodskpp]

dbi = [dbi_kmeansforgy, dbi_kmediansforgy, dbi_kmediodsforgy, dbi_kmeanskpp, dbi_kmedianskpp, dbi_kmediodskpp]

slh = [slh_kmeansforgy, slh_kmediansforgy, slh_kmediodsforgy, slh_kmeanskpp, slh_kmedianskpp, slh_kmediodskpp]

tme = [tme_kmeansforgy, tme_kmediansforgy, tme_kmediodsforgy, tme_kmeanskpp, tme_kmedianskpp, tme_kmediodskpp]

data = [chi, dbi, slh, tme]

titles = ["CHI comparison", "DBI comparison", "Silhouette comparison", "ExecTime Comparison"]
index_type = ["chi", "dbi", "slh", "sec"]
algorithm_type = ["kmeans + forgy", "kmedians + forgy", "kmedoids + forgy", "kmeans + kpp", "kmedians + kpp", "kmedoids + kpp"]
index_max_values = [12000, 1.0]


k_list = [2, 3, 4, 5, 6, 7, 8, 9, 10]
optimum_k_list = [7, 7, 7, 6, 7, 7]

# for index in range(0, 3):
fig, axs = plt.subplots(2, 2, figsize=(12, 8))

axs = axs.flatten()

for i in range(4):
    for j in range(6):
        # plt.plot(k_list, data, '-gx', markevery=optimum_k_list, label='line with select markers', markerfacecolor='red')
        axs[i].plot(k_list, data[i][j], "-o", label=algorithm_type[j])

        # axs[i].set_ylim(0, index_max_values[0])

        axs[i].set_title(titles[i])
        axs[i].set_ylabel(index_type[i])
        axs[i].set_xlabel("num clusters")

# plt.legend(loc="lower left")
plt.legend(bbox_to_anchor=(1.05, 1.05))

plt.tight_layout()

plt.savefig("../doc/plots/indexcomp_plot.jpg")
