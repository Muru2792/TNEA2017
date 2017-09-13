package com.in22labs.tneaapp.Notification;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.in22labs.tneaapp.Adapter.Notify;
import com.in22labs.tneaapp.Adapter.RecyclerAdapter;
import com.in22labs.tneaapp.MainActivity;
import com.in22labs.tneaapp.R;
import com.in22labs.tneaapp.Utils.ConnectionDetector;
import com.in22labs.tneaapp.Utils.DatabaseHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class NotificationCenter extends AppCompatActivity {




    ArrayList<String> notifyList, dateList, messageList, timeList;
    DatabaseHelper db;
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private List<Notify> arrayList= new ArrayList<>();
    private Paint p = new Paint();
    TextView t1;

    public String date, time;
    ConnectionDetector cd;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        Toolbar tool=(Toolbar)findViewById(R.id.toolbar_notification);
        TextView ab = (TextView) tool.findViewById(R.id.toolbar_titlenotification);

        ab.setText("Notification Center");
        ab.setTextSize(20.0f);

        String title = getIntent().getStringExtra("message");
        String message = getIntent().getStringExtra("a4ncontent");
        t1=(TextView)findViewById(R.id.noNotify);

            tool.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);
            tool.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(NotificationCenter.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        cd=new ConnectionDetector(NotificationCenter.this);
        if (  Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            if (!cd.isConnectingToInternet()) {
                // Internet Connection is not present

            }else {
                AdView mAdView = (AdView) findViewById(R.id.adViewNotiCenter);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setVisibility(View.VISIBLE);


                mAdView.loadAd(adRequest);
            }
        }
        try {
            db = new DatabaseHelper(NotificationCenter.this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if(title.equals("not")){
            notifyList=db.getNotification();
            dateList=db.getDateNotification();
            messageList=db.getMessageNotification();
            timeList=db.getTimeNotification();
//
            if(!notifyList.isEmpty()) {
                ArrayAdapter<String> adpNotification = new ArrayAdapter<String>
                        (this, android.R.layout.simple_dropdown_item_1line, notifyList);

                setRecyclerView();
            }else {
                updateText();
            }
        }else{
            date=getDate();
            time=getTime();
            db.setNotification(title, message, date, time);
            notifyList=db.getNotification();
            dateList=db.getDateNotification();
            messageList=db.getMessageNotification();
            timeList=db.getTimeNotification();
//
            if(!notifyList.isEmpty()) {
                ArrayAdapter<String> adpNotification = new ArrayAdapter<String>
                        (this, android.R.layout.simple_dropdown_item_1line, notifyList);

                setRecyclerView();
            }else{
                updateText();
            }

        }


        //Toast.makeText(NotificationCenter.this,"gi", Toast.LENGTH_SHORT).show();

    }

    public void setRecyclerView(){
        mAdapter = new RecyclerAdapter(arrayList);

        for(int i=0;i<notifyList.size();i++){
            Notify noti =new Notify(notifyList.get(i), dateList.get(i));
            arrayList.add(i, noti);
            mAdapter.notifyDataSetChanged();
        }


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.swapAdapter(mAdapter, true);
        ItemTouchHelper.Callback callback = new MovieTouchHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Notify noti = arrayList.get(position);
                String message_temp=notifyList.get(position);
                Intent i = new Intent(NotificationCenter.this, NotificationManage.class);
                i.putExtra("messageTitile", message_temp);
                startActivity(i);
//                showNotificationDescription(position, message_temp);
//                Toast.makeText(getApplicationContext(),message_temp+" "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }




    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private NotificationCenter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final NotificationCenter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public class MovieTouchHelper extends ItemTouchHelper.SimpleCallback {
        private RecyclerAdapter mMovieAdapter;

        public MovieTouchHelper(RecyclerAdapter movieAdapter){
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT);
            this.mMovieAdapter = movieAdapter;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //TODO: Not implemented here
            return false;
        }
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            Bitmap icon;
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                View itemView = viewHolder.itemView;
                float height = (float) itemView.getBottom() - (float) itemView.getTop();
                float width = height / 3;


                    p.setColor(Color.parseColor("#A6bbdefb"));

                    RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                    c.drawRect(background,p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                    RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                    c.drawBitmap(icon,null,icon_dest,p);

            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //Remove item
            mMovieAdapter.remove(viewHolder.getAdapterPosition());
            String message= notifyList.get(viewHolder.getAdapterPosition()+1);
            int i= viewHolder.getAdapterPosition();
//            Toast.makeText(NotificationCenter.this, message+"   "+i,Toast.LENGTH_SHORT).show();
            db.deleteNotification(message);

            updateRecyclerView();

        }
    }

    private void updateRecyclerView() {
        notifyList=db.getNotification();
        dateList=db.getDateNotification();
        messageList=db.getMessageNotification();
        timeList=db.getTimeNotification();

        if(notifyList.isEmpty()){
            updateText();
        }
//        Toast.makeText(NotificationCenter.this, "arraylength",Toast.LENGTH_SHORT).show();
    }
    public void updateText(){
        Typeface Ty1= Typeface.createFromAsset(getAssets(), "RobotoSlab-Bold.ttf");
        t1.setVisibility(View.VISIBLE);
        t1.setTypeface(Ty1);
    }
    public String getDate(){
        String date = null;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        date=ft.format(dNow);
//        Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
        return date;
    }

    public String getTime(){
        String time = null;
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss a");
        time=ft.format(dNow);
//        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
        return time;
    }

}
