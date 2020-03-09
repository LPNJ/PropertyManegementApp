package task.mock;

import java.util.ArrayList;

import task.AsyncTaskListener.CallbackListener;
import task.response.GetNameResponse;

public class GetNameTaskMock{

    CallbackListener<GetNameResponse> listener;

    public GetNameTaskMock(CallbackListener<GetNameResponse> listener) {
        this.listener = listener;
    }

    public void execute() {

        GetNameResponse response = null;

        ArrayList<String> names = null;
        String error = "0";

        names.add("komiyama");
        names.add("maeda");
        names.add("kimura");

        response = new GetNameResponse(error,names);

        listener.onPostExecute(response);

    }
}
