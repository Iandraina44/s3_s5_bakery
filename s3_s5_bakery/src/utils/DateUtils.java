package utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    // Function to convert datetime-local input string to Timestamp using SimpleDateFormat
    public static Timestamp convertToTimestamp(String dateAchatString) {
        if (dateAchatString != null && !dateAchatString.isEmpty()) {
            try {
                // Define the date format for 'yyyy-MM-dd'T'HH:mm'
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                
                // Parse the date string to a Date object
                Date parsedDate = dateFormat.parse(dateAchatString);
                
                // Convert Date to Timestamp
                return new Timestamp(parsedDate.getTime());
            } catch (Exception e) {
                // Handle exception if the format is incorrect
                e.printStackTrace();
            }
        }
        return null;
    }
}
