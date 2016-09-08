package com.sosapp;

/**
 * Created by MFEYET Daniel Steven on 08/09/2016.
 */

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;

import android.location.LocationListener;

import android.location.LocationManager;




import android.os.AsyncTask;

public class AlertActivity  extends Activity implements OnClickListener{

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    private LocationManager locationManager ;
    private LocationListener locationListener ;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        context = this;

        locationManager = ((LocationManager )getSystemService(Context.LOCATION_SERVICE));
        locationListener= new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        Button login = (Button) findViewById(R.id.btn_inc);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mfeyetstevi@gmail.com", "m91601740");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Alert...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("mfeyetstevi@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("olivia.landry@mapubi.com"));
                message.setSubject("Alerte: Incendie");
                message.setContent("Une alerte Venant de Dimitri : Une Incendie est en cours pres de l'IUT de Douala", "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();

            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
    private class MyLocationListener implements LocationListener

    {

        @Override

        public void onLocationChanged(Location loc) {

            if (loc != null) {

                Toast.makeText(getBaseContext(),

                        "Localisation actuelle :n Lat: " + loc.getLatitude() +

                                "  Lng: " + loc.getLongitude(),

                        Toast.LENGTH_SHORT).show();

            }

        }

        @Override

        public void onProviderDisabled(String provider) {

// TODO Auto-generated method stub

        }

        @Override

        public void onProviderEnabled(String provider) {

// TODO Auto-generated method stub

        }

        @Override

        public void onStatusChanged(String provider, int status,

                                    Bundle extras) {

// TODO Auto-generated method stub

        }

    }


}

