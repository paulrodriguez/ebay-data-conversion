Name: Paul Rodriguez
sid: 303675125
project 2

tables:

Users(
UserID PRIMARY KEY,
Rating,
Location,
Country
)

Items(
ItemID PRIMARY KEY,
Name,
Currently,
Buy_Price,
First_Bid,
Number_of_Bids,
Started,
Ends,
Seller,
Description,
)

Categories(
ItemID,
Category,
PRIMARY KEY (ItemID, Category)
)

Bids(
ItemID,
UserID,
BidTime,
Amount,
PRIMARY KEY (ItemID, UserID, BidTime),
)

non-trivial functional dependencies:

UserID -> Rating, Location, Country

Category -> Items

Items -> Category

ItemID, Bidder, BidTime -> Amount

ItemID -> Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Started, Ends, Seller, Description

this relation design should be in BCNF because each item and user is stored only once and the categories relation have all its attributes combined as keys.
the Bids relation should be in BCNF since there should be no redundancy of information as a bidder can bid on the same item multiple times but the acutal time that they bid must be different. 


  


