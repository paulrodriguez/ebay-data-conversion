Name: Paul Rodriguez
sid: 303675125
project 2

tables:
Users(UserID, Rating, Location, Country) //primmery key is UserID

Items(ItemID, Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Started, Ends, Seller, Description) //prinary key is ItemID

Categories(ItemID, Category) //primary key is (ItemID, Categories)

Bids(ItemID, UserID, BidTime, Amount) //primary key is (ItemID, UserID, BidTime)


