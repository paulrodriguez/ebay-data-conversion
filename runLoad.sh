#!/bin/bash


mysql CS144 < drop.sql

mysql CS144 < create.sql

ant
#ant run
ant run-all

#remove duplicates
#sort -u temp_items.csv > items.csv
#soring methods
sort -u temp_users.csv > users.csv
sort -u temp_categories.csv > categories.csv
#sort  -u temp_items.csv > items.csv
sort -u temp_bids.csv > bids.csv

mysql CS144 < load.sql

rm -r ./bin
rm *.csv
rm *~
