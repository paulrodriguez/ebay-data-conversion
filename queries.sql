SELECT COUNT(*) FROM Users;

SELECT COUNT(*) FROM Users WHERE Location LIKE BINARY 'New York';

SELECT COUNT(*) FROM (select ItemID FROM Categories group by ItemID having count(ItemID)=4) E;

SELECT ItemID FROM Items WHERE Ends > "2001-12-20 00:00:01" AND Number_of_Bids > 0 AND Currently >= ALL(SELECT Currently FROM Items WHERE ItemID IN (SELECT ItemID FROM Bids));

SELECT COUNT(*) FROM Users WHERE Rating > 1000 AND UserID IN (SELECT Seller FROM Items);

SELECT COUNT(*) FROM Users WHERE UserID IN (SELECT UserID FROM Bids) AND UserID IN (SELECT Seller AS UserID FROM Items);

SELECT COUNT(DISTINCT Category) FROM Categories where ItemID IN (SELECT ItemID FROM Items WHERE Currently > 100 AND Number_of_Bids > 0);