LOAD DATA LOCAL INFILE 'r-users.txt' INTO TABLE Users FIELDS TERMINATED BY '|*|';

LOAD DATA LOCAL INFILE 'r-items.txt' INTO TABLE Items FIELDS TERMINATED BY '|*|'
(ItemID, `Name`, Currently, @Buy_Price, First_Bid, Number_of_Bids, Started, Ends, Seller, Description)
SET Buy_Price = IF(@Buy_Price='',NULL,@Buy_Price);



LOAD DATA LOCAL INFILE 'r-categories.txt' INTO TABLE Categories FIELDS TERMINATED BY '|*|';

LOAD DATA LOCAL INFILE 'r-bids.txt' INTO TABLE Bids FIELDS TERMINATED BY '|*|';