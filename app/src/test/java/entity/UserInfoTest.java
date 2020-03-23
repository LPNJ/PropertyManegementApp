package entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserInfoTest {

    @Test
    public void getUserIdSuccess() {
        UserInfo info = new UserInfo("komiyama","komiyama");
        assertThat(info.getUserId() , is("komiyama"));
    }

    @Test
    public void getPassWardSuccess() {
        UserInfo info = new UserInfo("komiyama","komiyama");
        assertThat(info.getPassword() , is("komiyama"));
    }

}