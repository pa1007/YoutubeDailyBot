package fr.pa1007.youtubedailybot.api.time;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    private Time() {

    }

    /**
     * This method will contact an API for getting the time in paris France
     *
     * @return the current date or null if API not working
     * @throws IOException    if the API isn't working
     * @throws ParseException if the Api String is bad
     */
    public static Date getTime() throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(
                "http://worldtimeapi.org/api/timezone/Europe/Paris.txt").openStream()));
        while (br.ready()) {
            String r = br.readLine();
            if (r.startsWith("datetime:")) {
                String   res = r.replace("datetime: ", "");
                String[] m   = res.split("\\.");
                res = m[0];
                System.out.println(res);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date             d          = dateFormat.parse(res);
                System.out.println(d);
                return d;
            }
        }
        return null;
    }
}
