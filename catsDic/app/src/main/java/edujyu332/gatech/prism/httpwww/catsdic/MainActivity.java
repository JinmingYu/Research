package edujyu332.gatech.prism.httpwww.catsdic;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {
    private ShareActionProvider mShareActionProvider;
    WebView webTxt;
    String t, id;
    int statuscode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webTxt = (WebView)(findViewById(R.id.webTxt));
        //to display the text
        Intent receivedText = getIntent();
        //get the word
        t = receivedText.getStringExtra(Intent.EXTRA_TEXT);
//        t = "cat";
        //get rid of all the junk
//        t = t.replace("'s","");
//        t = t.replace("'d","");
//        t = t.replace("~","");
//        t = t.replace("_","");
//        t = t.replace(".", "");
//        t = t.replace(",","");
//        t = t.replace(";","");
//        t = t.replace("!", "");
//        t = t.replace("?","");
//        t = t.replace(",", "");
//        t = t.replace("<","");
//        t = t.replace(">","");
//        t = t.replace("(","");
//        t = t.replace(")","");
//        t = t.replace(":","");
//        webTxt.loadUrl("http://www.handspeak.com/word/search.php?wordID=" + t + "&submitword=Find");
        getIdDaemon getIdThread = new getIdDaemon();
        getIdThread.execute();
    }

    // *************************************** Login Task *******************************************************/

    private class getIdDaemon extends AsyncTask<String, Void , String> {


        @Override
        protected String doInBackground(String... urls) {


            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpget = new HttpGet("http://smartsign.imtc.gatech.edu/videos?keywords=" + t);

            try {
                HttpResponse response = httpclient.execute(httpget);//contains the http code and the 200 or 401
                statuscode = response.getStatusLine().getStatusCode();
                HttpEntity httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                is.close();
                String jsonResponse = sb.toString();
                System.out.println(jsonResponse);
//                JSONObject json = new JSONObject(jsonResponse);
                JSONArray jsonArray = new JSONArray(jsonResponse);
                //need name and authentication token
                JSONObject json = jsonArray.getJSONObject(0);

                id = json.getString("id");
                System.out.print("done");
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {

                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "test";
        }

        @Override
        protected void onPostExecute(String result) {
            if (statuscode == 404) {
                Toast.makeText(getApplicationContext(), "Word not found in the Dictionary?", Toast.LENGTH_LONG).show();
            } else if (statuscode == 200) {
                webTxt.loadUrl("http://www.youtube.com/embed/" + id);
            }


        }


    }

}
