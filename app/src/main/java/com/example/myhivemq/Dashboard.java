package com.example.myhivemq;

import static java.nio.charset.StandardCharsets.UTF_8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.UUID;

public class Dashboard extends AppCompatActivity {
    final String host = "1904448cf01a4564947dae8e889f5fee.s2.eu.hivemq.cloud";
    final String username = "client";
    final String password = "Tranconghai2017";
    int timesPerformedleg = 0;
    int timesPerformedarm = 0;
    int timesPerformedStretch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Building the client with ssl.
        Mqtt5Client client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(host)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .simpleAuth()
                .username(username)
                .password(password.getBytes())
                .applySimpleAuth()
                .build();
        Log.d("MQTT","Create successfully");

        //Connect to the HiveMQ Cloud
        String connAck = connect(client);
        if (connAck.equals("SUCCESS")) {
            Log.d("MQTT", "Connect successfully");
            // Subscribe to a topic
            subscribe(client);
        } else {
            Log.d("MQTT","Connect failure");
        }
    }

    public String connect(Mqtt5Client client) {
        try {
            Mqtt5ConnAck connAckMessage = client.toBlocking().connect();
            //success
            return connAckMessage.getReasonCode().toString();

        } catch (Exception e) {
            //failure
            return e.getMessage();
        }
    }

    public void subscribe(Mqtt5Client client)
    {
        try {
            // Subscribe to the topic "predict" with qos = 1 and print the received message.
            client.toAsync().subscribeWith()
                    .topicFilter("predict")
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .callback(publish -> { // executed when the message is received
                        Log.d("MQTT", "Received message: " + publish.getTopic() + " -> " + UTF_8.decode(publish.getPayload().get()));
                        // Get current time in UTC+7
                        String timeStamp = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(new Timestamp(System.currentTimeMillis()));
                        Log.d("MQTT", "Time: " + timeStamp);
                        updateUI(UTF_8.decode(publish.getPayload().get()).toString(), timeStamp);
                    })
                    .send();
            Log.d("MQTT", "Subscribe successfully");
        } catch (Exception e) {
            Log.d("MQTT", Objects.requireNonNull(e.getMessage()));
            Log.d("MQTT", "Subscribe failure");
        }
    }

    public void updateUI(String message, String timeStamp)
    {
        switch (message) {
            case "0":
                Log.d("MQTT", "Raise left leg");
                TextView timesPerformed0 = findViewById(R.id.timesPerformed0);
                TextView lastTime0 = findViewById(R.id.lastTime0);
                timesPerformedleg++;
                timesPerformed0.setText("Times performed: " + timesPerformedleg);
                lastTime0.setText("Last time: " + timeStamp);
                break;
            case "1":
                Log.d("MQTT", "Raise right hand");
                TextView timesPerformed1 = findViewById(R.id.timesPerformed1);
                TextView lastTime1 = findViewById(R.id.lastTime1);
                timesPerformedarm++;
                timesPerformed1.setText("Times performed: " + timesPerformedarm);
                lastTime1.setText("Last time: " + timeStamp);
                break;
            case "2":
                Log.d("MQTT", "Stretch out");
                TextView timesPerformed2 = findViewById(R.id.timesPerformed2);
                TextView lastTime2 = findViewById(R.id.lastTime2);
                timesPerformedStretch++;
                timesPerformed2.setText("Times performed: " + timesPerformedStretch);
                lastTime2.setText("Last time: " + timeStamp);
                break;
            default:
                Log.d("MQTT", "Unknown activity");
                break;
        }
    }
}