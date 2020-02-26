package task.mock;

import java.util.ArrayList;

import task.GetNameTask;
import task.ResultListener;

public class GetNameTaskMock implements GetNameTask {

    @Override
    public void execute(ArrayList<String> names, ResultListener resultListener) {

        names.add("komiyama");
        names.add("maeda");
        names.add("kimura");

        resultListener.onResult(names);

    }
}
