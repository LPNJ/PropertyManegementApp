package entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EditableUserInfoTest {

    @Test
    public void getUserIdSuccess() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komiyama","komiyama");
        assertThat(info.getUserId() , is("komiyama"));
    }

    @Test
    public void getPassWardSuccess() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komiyama","komiyama");
        assertThat(info.getPassword() , is("komiyama"));
    }

    @Test
    public void getConfirmationPasswordSuccess() {
        EditableUserInfo info = new EditableUserInfo("komiyama","komiyama","komiyama");
        assertThat(info.getConfirmationPassword() , is("komiyama"));
    }



}