package fr.pa1007.youtubedailybot.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class API {

    static final Gson GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    /**
     * This method will return a youtube link with the good time to get and key
     * from Instant.now()
     *
     * @return the URL
     * @throws MalformedURLException if the URL is malformed
     * @throws KeyException          if the key is unreadable
     */
    public static URL getUrl() throws MalformedURLException, KeyException {
        return getUrl(Date.from(Instant.now()));
    }

    /**
     * Get the key (key.txt)
     *
     * @return the key
     * @throws KeyException if the key is unreadable
     */
    public static String getKey() throws KeyException {
        String key;
        try (BufferedReader br = new BufferedReader(new FileReader("key.txt"))) {
            key = br.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new KeyException("There is a problem with the key");
        }
        return key;
    }

    public static Result getResult() throws IOException, KeyException {
        URL  url           = getUrl();
        Type clazzListType = new TypeToken<Result>() {}.getType();
        try (Reader data = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            return GSON.fromJson(data, clazzListType);
        }
    }

    /**
     * Get the result from the API, see youtube api for the result
     *
     * @param d the date before
     * @return the Result
     * @throws IOException  if The connection is impossible
     * @throws KeyException if the key is unreadable
     */
    public static Result getResult(Date d) throws IOException, KeyException {
        URL  url           = getUrl(d);
        Type clazzListType = new TypeToken<Result>() {}.getType();
        try (Reader data = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            return GSON.fromJson(data, clazzListType);
        }
    }

    /**
     * This method will return a youtube link with the good time to get and key
     *
     * @param date The date of the start
     * @return The url changed
     * @throws MalformedURLException if the url is malformed
     * @throws KeyException          if the key is unreadable
     */
    public static URL getUrl(Date date) throws MalformedURLException, KeyException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        String url =
                "https://www.googleapis.com/youtube/v3/search?part=snippet&order=viewCount&publishedAfter=*T00:00:00Z&publishedBefore=;T00:00:00Z&key=";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        url = url.replace("*", dateFormat.format(date));
        url = url.replace(";", dateFormat.format(Date.from(c.toInstant())));
        url += getKey();
        return new URL(url);
    }
}
