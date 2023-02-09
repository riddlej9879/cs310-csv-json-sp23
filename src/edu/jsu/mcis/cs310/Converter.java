package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

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
            CSVReader csv_reader = new CSVReader(new StringReader(csvString));
            
            // JsonObject to store results
            JsonObject obj = new JsonObject();
            // Json Arrays 
            JsonArray prod_num = new JsonArray();
            JsonArray col_head = new JsonArray();
            JsonArray arr_data = new JsonArray();
            String[] rows = csv_reader.readNext();

            for (String head : rows) {
                col_head.add(head);
            }
            
            rows = csv_reader.readNext();
            while (rows != null) {
                prod_num.add(rows[0]);
                
                JsonArray _data = new JsonArray();
                
                for (int i = 1; i < rows.length; i++) {
                    String _str = rows[i];
                    if (col_head.toArray()[i].equals("Episode") ||
                            col_head.toArray()[i].equals("Season")) {
                        int _num = Integer.parseInt(_str);
                        _data.add(_num);
                    } else {
                        _data.add(_str);
                    }
                }
                arr_data.add(_data);
                obj.put("ProdNums", prod_num);
                obj.put("ColHeadings", col_head);
                obj.put("Data", arr_data);
                
                rows = csv_reader.readNext();
            }
            result = Jsoner.serialize(obj);
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
            JsonObject obj = 
                    Jsoner.deserialize(jsonString, new JsonObject());
            
            // Initialize writers
            StringWriter writer = new StringWriter();
            CSVWriter csv_writer = new CSVWriter(writer,
                    ',', '"', '\\', "\n");
            
            // Get Json header data
            JsonArray prod_num = (JsonArray)obj.get("ProdNums");
            JsonArray col_head = (JsonArray)obj.get("ColHeadings");
            JsonArray arr_data = (JsonArray)obj.get("Data");
            
            String[] headings = 
                    Arrays.copyOf(col_head.toArray(),
                    col_head.toArray().length,
                    String[].class);
            
            csv_writer.writeNext(headings);
            
            for(int i = 0; i < arr_data.size(); i++) {
                String[] _arr = new String[col_head.size()];
                _arr[0] = (String)prod_num.get(i);
                JsonArray _data = (JsonArray)arr_data.get(i);

                for (int x = 1; x < (_data.size()+1); x++) {
                    String _str;
                    _str = ((JsonArray)arr_data.get(i)).get(x-1).toString();
                    
                    if(headings[x].equals("Episode")) {
                        _arr[x] = String.format("%02d",
                                Integer.parseInt(_str));
                    } else {
                        System.out.println(headings[x]);
                        _arr[x] = _str;
                    }
                }
                csv_writer.writeNext(_arr);
            }
            result = writer.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }
}
