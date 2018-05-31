package luis_vives.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import luis_vives.app.R;
import luis_vives.app.controller.GetExamModels;
import luis_vives.app.model.Course;
import luis_vives.app.model.Courses;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragmentExamModels extends Fragment {

    private Retrofit retrofit;
    private List<Course> courses;
    private GetExamModels getExamModels;
    private NumberPicker coursePicker2;
    private static final String ENDPOINT_URL = "https://luis-vives.es";

    public fragmentExamModels() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initCoursePicker();
        initializeRetrofit();
        fetchExamModels();
        return inflater.inflate(R.layout.fragment_exam_models, container, false);
    }

    private void initCoursePicker(){
        NumberPicker coursePicker = getActivity().findViewById(R.id.select_course_picker);
        coursePicker.setMinValue(0);
        coursePicker.setMaxValue(2);
        coursePicker.setDisplayedValues( new String[] { "Belgium", "France", "United Kingdom" } );
    }

    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getExamModels = retrofit.create(GetExamModels.class);
    }

    private void fetchExamModels(){
        new Thread(new Runnable(){
            public void run() {
                Call<Courses> call = getExamModels.all();
                try {
                    Response<Courses> response = call.execute();
                    final Courses result = response.body();
                    getActivity().runOnUiThread(new Runnable(){
                        public void run() {
                            displayCourses(result);
                        }
                    });
                } catch (IOException e) {
                    Toast.makeText(getActivity(), R.string.checkConnection, Toast.LENGTH_LONG).show();
                }
            }
        }).start();
    }

    private void displayCourses(Courses receivedCourses) {
        if (receivedCourses!=null){
            courses=receivedCourses.getCourses();
        }
    }
}