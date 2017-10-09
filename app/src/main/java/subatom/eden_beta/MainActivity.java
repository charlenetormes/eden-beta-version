package subatom.eden_beta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;





public class MainActivity extends AppCompatActivity {
    Button btnplay;
    TextView txtMainName;
    String vid_id  ;
    EditText txtUrl;
    Button btnPreview;


    //https://m.youtube.com/watch?v=4o1egAQ6gJQ
    //https://m.youtube.com/watch?v=4o1egAQ6gJQ
    public String video_url;

    Uri uri;
    String videoID;
    String url;
    ImageView img;

    public class transfer implements View.OnClickListener{ //Pending
        public void onClick (View v){
            Intent i = new Intent(MainActivity.this, YoutubePlayer.class);
            i.putExtra("url", videoID);
            startActivity(i);
        }
    }



    public void setPreview(){
        video_url = txtUrl.getText().toString();   //will be changed

        uri = Uri.parse(video_url);
        videoID = uri.getQueryParameter("v");

        url = "http://img.youtube.com/vi/" + videoID + "/default.jpg";
        Picasso.with(this).load(url).into(img);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img= (ImageView) findViewById(R.id.imageView);
        btnplay = (Button)findViewById(R.id.btnplay);
        txtMainName = (TextView)findViewById(R.id.txtMainName);
        txtUrl = (EditText)findViewById(R.id.txtUrl);

        btnPreview = (Button)findViewById(R.id.btnPreview);

        Intent intent = getIntent();
        String nameHandler = intent.getStringExtra("user_name");

        txtMainName.setText(nameHandler);
        btnplay.setOnClickListener(new transfer());
        txtMainName.setText(nameHandler);

        btnPreview.setOnClickListener(new OnClickPreview());
        //img.setImageResource(R.drawable.subtitles);


    }



    public class OnClickPreview implements View.OnClickListener {
       @Override
        public void onClick(View view) {
            setPreview();
        }
    }




}
