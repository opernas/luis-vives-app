package luis_vives.app.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import luis_vives.app.R;
import luis_vives.app.controller.GetExamModels;
import luis_vives.app.model.Course;
import luis_vives.app.model.Courses;
import luis_vives.app.model.Topic;
import luis_vives.app.model.Year;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class fragmentExamModels extends Fragment implements View.OnClickListener {

    NumberPicker yearPicker;
    NumberPicker topicPicker;
    NumberPicker coursePicker;

    private Retrofit retrofit;
    private List<Course> courses;
    ProgressDialog loadingFragment;
    private GetExamModels getExamModels;
    private static final String ENDPOINT_URL = "https://luis-vives.es";

    public fragmentExamModels() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingFragment=new ProgressDialog(getActivity());
        loadingFragment.setMessage(getString(R.string.fetching_courses_loading_string));
        loadingFragment.setCancelable(false);
        loadingFragment.setInverseBackgroundForced(false);
        loadingFragment.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeRetrofit();
        View v = inflater.inflate(R.layout.fragment_exam_models, container, false);
        Button buttonGetExam = (Button) v.findViewById(R.id.button_get_exam);
        buttonGetExam.setOnClickListener(this);
        Button buttonGetInfo = (Button) v.findViewById(R.id.button_get_info);
        buttonGetInfo.setOnClickListener(this);
        return v;
    }

    public void onStart(){
        super.onStart();
        fetchExamModels();
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
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(
                                    getActivity(), R.string.checkConnection, Toast.LENGTH_LONG).
                                    show();
                        }
                    });
                }
            }
        }).start();
    }

    private void displayCourses(Courses receivedCourses) {
        if (receivedCourses!=null){
            loadingFragment.hide();
            courses=receivedCourses.getCourses();
            showCoursePicker(courses);
        }
    }

    private void showCoursePicker( List<Course> courses){
        coursePicker = getActivity().findViewById(R.id.select_course_picker);
        coursePicker.setMinValue(0);
        coursePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        coursePicker.setMaxValue(courses.size()-1);
        coursePicker.setWrapSelectorWheel(false);
        List<String> coursesNamesList = new ArrayList<>();
        for (Course c: courses){
            coursesNamesList.add(c.getCourse());
        }
        coursePicker.setDisplayedValues(coursesNamesList.toArray(new String[coursesNamesList.size()]));
        showTopicPicker(courses.get(coursePicker.getValue()).getTopics());
    }

    private void showTopicPicker( List<Topic> topics){
        topicPicker = getActivity().findViewById(R.id.select_topic_picker);
        topicPicker.setMinValue(0);
        topicPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        topicPicker.setMaxValue(topics.size()-1);
        topicPicker.setWrapSelectorWheel(false);
        List<String> topicsNamesList = new ArrayList<>();
        for (Topic c: topics){
            topicsNamesList.add(c.getTopic());
        }
        topicPicker.setDisplayedValues(topicsNamesList.toArray(new String[topicsNamesList.size()]));
        showYearPicker(topics.get(topicPicker.getValue()).getYears());
    }

    private void showYearPicker( List<Year> years){
        yearPicker = getActivity().findViewById(R.id.select_year_picker);
        yearPicker.setMinValue(0);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        yearPicker.setMaxValue(years.size()-1);
        yearPicker.setWrapSelectorWheel(false);
        List<String> yearsNamesList = new ArrayList<>();
        for (Year y: years){
            yearsNamesList.add(y.getYear().getId());
        }
        yearPicker.setDisplayedValues(yearsNamesList.toArray(new String[yearsNamesList.size()]));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_get_exam:
                onRequestExamView();
                break;
            case R.id.button_get_info:
                onRequestInfoView();
                break;
        }
    }

    public void onRequestExamView(){
        int chosenCourse=coursePicker.getValue();
        int chosenTopic=topicPicker.getValue();
        int chosenYear = yearPicker.getValue();
        String examPath=courses.get(coursePicker.getValue()).
                getTopics().get(topicPicker.getValue()).
                getYears().get(chosenYear).
                getYear().getPath();
        Fragment fragment = null;
        String pdfTitle =   courses.get(chosenCourse).getCourse().toString()+ "/" +
                            courses.get(chosenCourse).getTopics().get(chosenTopic).getTopic().toString()+ "/" +
                            courses.get(chosenCourse).getTopics().get(chosenTopic).getYears().get(chosenYear).getYear().getId().toString();
        fragment = fragmentPdfViewer.newInstance(ENDPOINT_URL+examPath,pdfTitle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
    public void onRequestInfoView(){
        String infoPath=courses.get(coursePicker.getValue()).getTopics().get(topicPicker.getValue()).getInfo();
        Toast.makeText(
                getActivity(), infoPath, Toast.LENGTH_LONG).
                show();
    }

}