package cite.ansteph.ponda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cite.ansteph.ponda.model.Attendee;
import cite.ansteph.ponda.model.Project;
import cite.ansteph.ponda.views.attendee.AttendeeList;
import cite.ansteph.ponda.views.client.ClientList;
import cite.ansteph.ponda.views.lmeeting.StartMeeting;
import cite.ansteph.ponda.views.meeting.History;
import cite.ansteph.ponda.views.meeting.NewMeeting;
import cite.ansteph.ponda.views.project.ProjectList;

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
                    Intent intent = new Intent(getApplicationContext(), StartMeeting.class);
                    startActivity(intent);

                }
            }
        };
        timerThread.start();

    }
}
