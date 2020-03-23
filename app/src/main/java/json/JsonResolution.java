package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import response.GetPropertyResponse;

public class JsonResolution {

    public void toListManager(GetPropertyResponse response, ArrayList<String> pManager , ArrayList<String> pName) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            for (int i = 0; response.getInfos().size() > i; i++) {
                PropertyInfoJson info = mapper.readValue(response.getInfos().get(i).getProperty(), PropertyInfoJson.class);
                pManager.add(info.mPropertyManager);
                pName.add(info.mProductName);
            }
        }catch (IOException e){
        }
    }

    public void toListUser(GetPropertyResponse response, ArrayList<String> pManager , ArrayList<String> pName) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            for (int i = 0; response.getInfos().size() > i; i++) {
                PropertyInfoJson info = mapper.readValue(response.getInfos().get(i).getProperty(), PropertyInfoJson.class);
                pManager.add(info.mPropertyUser);
                pName.add(info.mProductName);
            }
        }catch (IOException e){
        }
    }

    public void toListAll(GetPropertyResponse response , ArrayList<String> productName) {

        //JSON文字列にキーを指定して値を取得
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (int i = 0; response.getInfos().size() > i; i++) {
                PropertyInfoJson info = mapper.readValue(response.getInfos().get(i).getProperty(), PropertyInfoJson.class);
                productName.add(info.mProductName);
            }
        }catch (IOException e){
        }
    }
}
