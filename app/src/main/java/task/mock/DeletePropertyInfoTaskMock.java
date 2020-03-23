package task.mock;

import task.AsyncTaskListener.CallbackListener;
import request.DeletePropertyRequest;

public class DeletePropertyInfoTaskMock{

    private CallbackListener<String> mListener;

    public DeletePropertyInfoTaskMock(CallbackListener<String> listener){
        mListener = listener;
    }

    public void execute(DeletePropertyRequest request) {
        if(request.getUserId().equals("komiyama")){
            mListener.onPostExecute("0");
        }
        if(request.getUserId().equals("")){
            mListener.onPostExecute("1");
        }
        if(request.getUserId().equals("failure")){
            mListener.onPostExecute("2");
        }
        if(request.getUserId().equals("nakayama")){
            mListener.onPostExecute("12");
        }
        if(request.getUserId().equals("katayama")){
            mListener.onPostExecute("13");
        }
        if(request.getUserId().equals("notFound")){
            mListener.onPostExecute("31");
        }
    }

}
