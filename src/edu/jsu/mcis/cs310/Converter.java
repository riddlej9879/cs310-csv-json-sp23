package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

public class Converter {
   {
    /*      DOOR CODE   4235
        Consider the following CSV data, a portion of a database of episodes of
        the classic "Star Trek" television series:
        
        "ProdNum","Title","Season","Episode","Stardate","OriginalAirdate","RemasteredAirdate"
        "6149-02","Where No Man Has Gone Before","1","01","1312.4 - 1313.8","9/22/1966","1/20/2007"
        "6149-03","The Corbomite Maneuver","1","02","1512.2 - 1514.1","11/10/1966","12/9/2006"
        
        (For brevity, only the header row plus the first two episodes are shown
        in this sample.)
    
        The corresponding JSON data would be similar to the following; tabs and
        other whitespace have been added for clarity.  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings and which values should be encoded as integers, as
        well as the overall structure of the data:
        
        {
            "ProdNums": [
                "6149-02",
                "6149-03"
            ],
            "ColHeadings": [
                "ProdNum",
                "Title",
                "Season",
                "Episode",
                "Stardate",
                "OriginalAirdate",
                "RemasteredAirdate"
            ],
            "Data": [
                [
                    "Where No Man Has Gone Before",
                    1,
                    1,
                    "1312.4 - 1313.8",
                    "9/22/1966",
                    "1/20/2007"
                ],
                [
                    "The Corbomite Maneuver",
                    1,
                    2,
                    "1512.2 - 1514.1",
                    "11/10/1966",
                    "12/9/2006"
                ]
            ]
        }
        
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
        
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including examples.
    */
}
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        String result = "{}"; // default return value; replace later!;
        
        try {
            // INSERT YOUR CODE HERE
            CSVReader reader = new CSVReader(new StringReader(csvString));
            String[] headers = reader.readNext();
            
            JsonObject obj = new JsonObject();
            JsonArray col_head = new JsonArray();
            JsonArray total_data = new JsonArray();
            String[] row;
            obj.put("ColHeadings", headers);
            
            while ((row = reader.readNext()) != null) {
                String[] rows = reader.readNext();
                col_head.add(row[0]);
                JsonArray row_data = new JsonArray();
                
                for (int i = 0; i < headers.length; i++) {
                    try {
                        int num = Integer.parseInt(row[i]);
                        row_data.add(num);
                    } catch (NumberFormatException e) {
                        row_data.add(row[i]);
                    }
                    
                }
                total_data.add(row_data);
            }
            obj.put("Prodnums", col_head);
            obj.put("Data", total_data);
            
            result = obj.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.trim();
    }
    
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
        String result = ""; // default return value; replace later!
        
        try {
            // INSERT YOUR CODE HERE
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
}
