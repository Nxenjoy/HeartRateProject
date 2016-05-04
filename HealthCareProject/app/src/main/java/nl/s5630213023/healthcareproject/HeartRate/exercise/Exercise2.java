package nl.s5630213023.healthcareproject.HeartRate.exercise;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import nl.s5630213023.healthcareproject.R;

public class Exercise2 extends Fragment {

    ListView listView;
    static ArrayList<Exer> arrayExercise = new ArrayList<Exer>();
    ExerciseAdapter adapter;

    public static Exercise2 newInstance() {
        return new Exercise2();
    }

    public Exercise2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exercise_fragment2, container, false);

        listView = (ListView) v.findViewById(R.id.listViewExercise);
        adapter = new ExerciseAdapter(this.getContext(), arrayExercise);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        btnShow();

        return v;
    }

    private void btnShow() {
        Uri u = Uri.parse("content://ExerCiseDB");
        Cursor c = getActivity().getContentResolver().query(u, null, null, null, null);
        while (c.moveToNext()) {
            arrayExercise.add(new Exer(c.getInt(0), c.getString(1).toString(), c.getString(2).toString(), c.getInt(3) ,c.getString(4).toString()));

        }
    }
}
