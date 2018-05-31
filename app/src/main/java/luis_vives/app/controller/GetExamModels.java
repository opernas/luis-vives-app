package luis_vives.app.controller;

import luis_vives.app.model.Courses;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetExamModels {
    @GET("/modelos_selectividad.json")
    Call<Courses> all();
}