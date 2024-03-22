#!/bin/bash

for i in {2..10}
do 
	for j in {0..50}
	do
		./main.exe Dataset3-normalized.csv forgy_Dataset3_0/eneko-$i-$j.csv $i 0
	done
done
	
for i in {2..10}
do 
	for j in {0..50}
	do
		./main.exe Dataset3-normalized.csv forgy_Dataset3_1/eneko-$i-$j.csv $i 1
	done
done	

for i in {2..10}
do 
	for j in {0..50}
	do
		./main.exe Dataset3-normalized.csv forgy_Dataset3_2/eneko-$i-$j.csv $i 2
	done
done	
