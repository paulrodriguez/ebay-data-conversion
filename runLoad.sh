#!/bin/bash


mysql CS144 < drop.sql

mysql CS144 < create.sql

ant
#ant run
ant run-all

#remove duplicates
#sort -u temp_items.csv > items.csv
#soring methods
sort -u temp_users.txt > r-users.txt
sort -u temp_categories.txt > r-categories.txt
sort -u temp_items.txt > r-items.txt
sort -u temp_bids.txt > r-bids.txt

mysql CS144 < load.sql

#rm -r ./bin
rm temp*.txt
rm r-*.txt
rm *~
