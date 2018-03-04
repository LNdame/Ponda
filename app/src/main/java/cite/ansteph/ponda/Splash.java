package cite.ansteph.ponda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cite.ansteph.ponda.views.meeting.NewMeeting;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    /**
                     * Call this function whenever you want to check user login
                     * This will redirect user to Login is he is not
                     * logged in
                     * */
                  //  sessionManager.checkLogin();
                     Intent intent = new Intent(getApplicationContext(), NewMeeting.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();

    }
}
