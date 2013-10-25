
/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParser {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }


    /*
	Returns a string in format for SQL TIMESTAMP, or throws error if string 
	cannot be parsed

     */
    static String convertToTimestamp(String time) throws Exception {

	SimpleDateFormat firstformat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
	
	Date parsed = (Date) firstformat.parse(time);
	SimpleDateFormat lastformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return lastformat.format(parsed);
	
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
	try {
	    //open files to output to
	    PrintWriter tempItems = new PrintWriter(new FileWriter("temp_items.csv",true));
	    PrintWriter tempUsers = new PrintWriter(new FileWriter("temp_users.csv", true));	   
	    PrintWriter tempBids = new PrintWriter(new FileWriter("temp_bids.csv",true));
	    PrintWriter tempCategories = new PrintWriter(new FileWriter("temp_categories.csv", true));
	    
	    //this is the root of the XML files: 'Items'
	    Element root = doc.getDocumentElement();

	  
	    //stings that will be used for storing info about items in an Item relation
	    String itemName = "";
	    String itemID = "";
	    String itemCurr = "";
	    String itemBuyPrice = "";
	    String itemFirstBid = "";
	    String itemTotalBids = "";
	    String itemStarted = "";
	    String itemEnds = "";
	    String itemDescription = "";
	    int totalBids = 0;
	    Element itemSeller = null;


	    //these string hold the value of a user
	    String userID = "";
	    String rating = "";
	    String location = "";
	    String country = "";
	    String category = "";


	    //store elements needed for bidders
	    Element bids = null;
	    Element bidder = null;

	    String bidderLoc = "";
	    String bidderCountry = "";
	    String bidderID = "";
	    String bidderRating = "";
	    String bidderAmount = "";
	    String bidderTime = "";

	    

	    
	    //get all Item elements
	    Element[] eItem = getElementsByTagNameNR(root, "Item");
	    
	    //go through each item element
	    for (int i = 0; i < eItem.length; i++) {
		
		//get the seller's information
		itemSeller = getElementByTagNameNR(eItem[i], "Seller");
		userID = itemSeller.getAttribute("UserID").replaceAll("\"","\\\\\"");
		rating = itemSeller.getAttribute("Rating");
		location = getElementTextByTagNameNR(eItem[i],"Location").replaceAll("\"","\\\\\"");
		country = getElementTextByTagNameNR(eItem[i],"Country").replaceAll("\"","\\\\\"");
		

		//add users to the user load data
		tempUsers.append("\""+userID+"\","+rating+",\""+location+"\",\""+country+"\"\n");

		/**************************************************************/

		//get the item info for the Items relation
		itemName = getElementTextByTagNameNR(eItem[i],"Name").replaceAll("\"","\\\\\"");
		itemID = eItem[i].getAttribute("ItemID");
		itemCurr = getElementTextByTagNameNR(eItem[i], "Currently");
		itemBuyPrice = getElementTextByTagNameNR(eItem[i], "Buy_Price");
		itemFirstBid = getElementTextByTagNameNR(eItem[i], "First_Bid");
		itemTotalBids = getElementTextByTagNameNR(eItem[i], "Number_of_Bids");
		itemStarted = convertToTimestamp(getElementTextByTagNameNR(eItem[i], "Started"));
		itemEnds = convertToTimestamp(getElementTextByTagNameNR(eItem[i],"Ends"));
		itemDescription = getElementTextByTagNameNR(eItem[i],"Description").replaceAll("\\\\","\\\\\\\\").replaceAll("\"", "\\\\\"");
		bids = getElementByTagNameNR(eItem[i], "Bids");
		//need this when we are going to get the bids
		totalBids = Integer.parseInt(itemTotalBids);

		//if buy price is empty put a null value in the file
		if (itemBuyPrice.equals("")) {
		    itemBuyPrice = "NULL";
		}
		else {
		    itemBuyPrice = strip(itemBuyPrice);
		}

		if (itemDescription.length() > 4000) {
		    itemDescription = itemDescription.substring(0,4000);
		}

		//append the items info to the csv file
		tempItems.append(itemID+",\""+itemName+"\","+strip(itemCurr)+","+itemBuyPrice+","+strip(itemFirstBid)+","+itemTotalBids+","+itemStarted+","+itemEnds+",\""+userID+"\",\""+itemDescription+"\"\n");


		/***********************************************************/
		//get the categories for this element
		Element [] categories = getElementsByTagNameNR(eItem[i], "Category");
		
		//need to loop through each category element found
		for (int k = 0; k < categories.length; k++) {
		    category = getElementText(categories[k]).replaceAll("\"","\\\\\"");

		    tempCategories.append(itemID+",\""+category+"\"\n");
		}


		/***************************************************/
		//need to obtain info of each bidder if the number of bids is 1 or greater
		if (totalBids >= 1) {
		    Element[] bid = getElementsByTagNameNR(bids, "Bid");
		    for (int j = 0; j < bid.length; j++) {
			bidder = getElementByTagNameNR(bid[j],"Bidder");
			bidderID = bidder.getAttribute("UserID").replaceAll("\"","\\\\\"");
			bidderRating = bidder.getAttribute("Rating");
			bidderLoc = getElementTextByTagNameNR(bidder, "Location").replaceAll("\"","\\\\\"");
			bidderCountry = getElementTextByTagNameNR(bidder, "Country").replaceAll("\"","\\\\\"");

			bidderTime = convertToTimestamp(getElementTextByTagNameNR(bid[j], "Time"));
			bidderAmount = getElementTextByTagNameNR(bid[j],"Amount");
			//append bidder info to users file
			tempUsers.append("\""+bidderID+"\","+bidderRating+",\""+bidderLoc+"\",\""+bidderCountry+"\"\n");

			//append the bid info to the bids file
			tempBids.append(itemID+",\""+bidderID+"\","+bidderTime+","+strip(bidderAmount)+"\n");
		    }
		}
	       
	    }
	    tempCategories.close();
	    tempBids.close();
	    tempUsers.close();
	    tempItems.close();
        } catch (Exception e) {
	    System.err.println(e.toString());
	}
        
        /**************************************************************/
        
    }
    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
