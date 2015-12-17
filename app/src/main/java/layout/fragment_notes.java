package layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.carlosvarela.uninote.AddClass;
import com.example.carlosvarela.uninote.AddClassmate;
import com.example.carlosvarela.uninote.ClassOverview;
import com.example.carlosvarela.uninote.Home;
import com.example.carlosvarela.uninote.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_notes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_notes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_notes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public  GridView gridView;

    private OnFragmentInteractionListener mListener;
    private ListAdapter adapter;
    public ArrayList<ParseObject> notes;
    public String objId;
    public fragment_notes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_notes.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_notes newInstance(String param1, String param2) {
        fragment_notes fragment = new fragment_notes();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        objId="";
    }

    public void fillGrid(ListAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);

    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        gridView = (GridView) getActivity().findViewById(R.id.gridView);
        if (gridView != null) {
            System.out.println(adapter.getCount());
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    try {
                        ParseObject note = notes.get(position);
                        objId = note.getObjectId();
;
                        if( note.get("Type").equals("VoiceNote"))
                            PlayAudioFile(note, v);
                        else if(note.get("Type").equals("ImageNote"))
                            DisplayImageFile(note, v);

                    } catch (Exception e) {
                    }
                }
            });
        }else
            System.out.println("grid is null");
    }
    private void PlayAudioFile(ParseObject note, View view) throws IOException {
        ParseFile file = (ParseFile)note.get("File");
        Uri audioFileUri = Uri.parse(file.getUrl());
        final MediaPlayer mPlayer = new MediaPlayer();

        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupaudio, null);
        ImageButton btn = (ImageButton)popupView.findViewById(R.id.audioshare);
        ImageButton btn2 = (ImageButton)popupView.findViewById(R.id.audiodelete);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AddClassmate.class);
                i.putExtra("ID",objId);
                startActivity(i);
            }
        });

        btn2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                ParseObject.createWithoutData("Note", objId).deleteEventually();

            }
        });
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                AbsListView.LayoutParams.WRAP_CONTENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        //popupWindow.showAsDropDown(view);
        final SeekBar seekBar = (SeekBar) popupView.findViewById(R.id.seekBar);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        mPlayer.setDataSource(getActivity().getApplicationContext(), audioFileUri);
        seekBar.setProgress(0);

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mPlayer.getDuration());
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {
                        long currentDuration = mPlayer.getCurrentPosition();
                        seekBar.setProgress((int)currentDuration);
                    }

                }, 0, 15);
            }
        });
        mPlayer.prepare();
        mPlayer.start();
/*
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                long currentDuration = mPlayer.getCurrentPosition();
                seekBar.setProgress((int)currentDuration);
            }

        }, 0, 15);*/
    }

    private void DisplayImageFile(ParseObject note,final View view) throws IOException {
        System.out.println("diplayImageFile");
        ParseFile file = (ParseFile)note.get("File");
        Uri imageFileUri = Uri.parse(file.getUrl());
        file.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    View popupView = getActivity().getLayoutInflater().inflate(R.layout.image_popup, null);
                    ImageButton btn = (ImageButton)popupView.findViewById(R.id.share_btn);
                    ImageButton btn2 =(ImageButton)popupView.findViewById(R.id.delete_btn);
                    btn.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(),AddClassmate.class);
                            i.putExtra("ID",objId);
                            startActivity(i);
                        }
                    });



                    btn2.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            try {
                                ParseObject.createWithoutData("Note", objId).delete();
                                Intent i = new Intent(getActivity(),Home.class);
                                Toast.makeText(getActivity(),
                                        "Successfully deleted", Toast.LENGTH_LONG)
                                        .show();
                                startActivity(i);
                            } catch (ParseException e1) {
                                Toast.makeText(getActivity(),
                                        "You do not have enough permission to delete this note", Toast.LENGTH_LONG)
                                        .show();
                            }

                        }
                    });



                    final PopupWindow popupWindow = new PopupWindow(
                            popupView,
                            AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT);
                    ImageView image =(ImageView) popupView.findViewById(R.id.popupImageView);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Bitmap newbitMap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
                    image.setImageBitmap(newbitMap);
                    //popupWindow.showAsDropDown(view);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setFocusable(true);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                } else {
                    Log.d("test", "There was a problem downloading the data.");
                }
            }
        });
        /*
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.image_popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                AbsListView.LayoutParams.WRAP_CONTENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        ImageView image =(ImageView) popupView.findViewById(R.id.popupImageView);
        System.out.println("bitmap");
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageFileUri);
        System.out.println("bitmap");
        image.setImageBitmap(bitmap);
        popupWindow.showAsDropDown(view);*/
    }
    public void share(){
        Intent i = new Intent(getActivity(),AddClassmate.class);
        i.putExtra("ID",objId);
        startActivity(i);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
