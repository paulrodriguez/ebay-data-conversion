Name: Paul Rodriguez
sid: 303675125
project 2

tables:

Users(
UserID VARCHAR(100) PRIMARY KEY,
Rating INTEGER,
Location, VARCHAR(100),
Country VARCHAR(100)
)

Items(
ItemID INTEGER PRIMARY KEY,
Name VARCHAR(100),
Currently DECIMAL(8,2),
Buy_Price DECIMAL(8,2) DEFAULT NULL,
First_Bid DECIMAL(8,2),
Number_of_Bids INTEGER,
Started TIMESTAMP,
Ends TIMESTAMP,
Seller VARCHAR(100),
Description VARCHAR(4000),
FOREIGN KEY (Seller) REFERENCES Users(UserID)
)

Categories(
ItemID INTEGER REFERENCES Items(ItemID),
Category VARCHAR(100),
PRIMARY KEY (ItemID, Category)
)

Bids(
ItemID INTEGER NOT NULL,
UserID VARCHAR(100) NOT NULL,
BidTime TIMESTAMP NOT NULL,
Amount DECIMAL(8,2) NOT NULL
PRIMARY KEY (ItemID, UserID, BidTime),
FOREIGN KEY (ItemID) REFERENCES Item(ItemID),
FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

non-trivial functional dependencies:

UserID -> Rating, Location, Country

Category -> Items

Items -> Category

ItemID, Bidder, BidTime -> Amount

ItemID -> Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Started, Ends, Seller, Description

this relation design should be in BCNF because each item and user is stored only once and the categories relation have all its attributes combined as keys.
the Bids relation should be in BCNF since there should be no redundancy of information as a bidder can bid on the same item multiple times but the acutal time that they bid must be different. 


  


