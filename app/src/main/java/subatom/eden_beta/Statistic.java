package subatom.eden_beta;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import subatom.eden_beta.Detector.Emotion;

public class Statistic extends Activity {

    public class SendToEmail implements View.OnClickListener{
        public void onClick(View v){
            Date currentTime = Calendar.getInstance().getTime();
            String s = currentTime.toString();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            String[] to={"papelias1337@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "EDEN Results of "+s);
            intent.putExtra(Intent.EXTRA_TEXT, txtStat.getText().toString());
            intent.setType("message/rfc822");
            Intent chooser = Intent.createChooser(intent, "Send Email");
            startActivity(chooser);

        }
    }

    private Button btnSend;
    private EditText txtStat;
    private String confused = "You seem confused between ";
    private String noAttention = "You seem confused between ";
    private int fps;
    private int emotionLength;

    ArrayList<Float> valenceNew = new ArrayList<>();
    ArrayList<Float> attentionNew = new ArrayList<>();
    ArrayList<Float> brow_furrowNew = new ArrayList<>();
    ArrayList<Float> engagementNew = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        btnSend = (Button)findViewById(R.id.btnSend);
        txtStat = (EditText) findViewById(R.id.editTextStat);

        emotionLength = Emotion.attention.size();
        fps = (int)(emotionLength/60);
        assignList();

        Toast.makeText(this, Emotion.attention.size() + " ", Toast.LENGTH_SHORT).show();
        btnSend.setOnClickListener(new SendToEmail());
    }

    public void assignList(){
        for(int i=fps; i<=emotionLength; i+=fps){
            valenceNew.add(Emotion.valence.get(i));
            attentionNew.add(Emotion.attention.get(i));
            brow_furrowNew.add(Emotion.brow_furrow.get(i));
            engagementNew.add(Emotion.engagement.get(i));
        }
    }

    public void analyzeConfusion(){
        int start=0, end=0;
        boolean hasStart = false, hasEnd = false;
        int length = brow_furrowNew.size();
        for(int i=0; i<length; i++){
            if(brow_furrowNew.get(i) >= 90 && !hasStart){
                start = i;
                hasStart = true;
            }

            if(brow_furrowNew.get(i) < 90 && !hasEnd){
                end = i;
                hasEnd = true;
            }

            if(hasStart && hasEnd){
                hasStart = false;
                hasEnd = false;



                start = 0;
                end = 0;
            }

        }
    }


}
