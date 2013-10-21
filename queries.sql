-- 1. get number of users
SELECT COUNT(*) FROM Users;


--2. get users with location in new york
SELECT COUNT(*) FROM Users WHERE Location LIKE BINARY 'New York';


--3. get items with belonging to four categories
SELECT COUNT(*) FROM (SELECT ItemID FROM Categories GROUP BY ItemID HAVING COUNT(ItemID)=4) E;


--4. get recent highest auction
SELECT ItemID FROM Items WHERE Ends > "2001-12-20 00:00:01" AND Number_of_Bids > 0 AND Currently >= ALL(SELECT Currently FROM Items WHERE ItemID IN (SELECT ItemID FROM Bids));


--5. get sellers with rating greater than 1000
SELECT COUNT(*) FROM Users WHERE Rating > 1000 AND UserID IN (SELECT Seller FROM Items);


--6. get users that are sellers and bidders
SELECT COUNT(*) FROM Users WHERE UserID IN (SELECT UserID FROM Bids) AND UserID IN (SELECT Seller AS UserID FROM Items);


--7. get categories that have items with price > 100
SELECT COUNT(DISTINCT Category) FROM Categories WHERE ItemID IN (SELECT ItemID FROM Items WHERE Currently > 100 AND Number_of_Bids > 0);