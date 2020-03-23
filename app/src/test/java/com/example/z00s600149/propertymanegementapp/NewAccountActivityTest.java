package com.example.z00s600149.propertymanegementapp;

import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import webApi.WebApiMock;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.O, application = TestApplication.class)
public class NewAccountActivityTest {

    private NewAccountActivity mNewAccountActivity;
    /**
     * @Before がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施前に呼び出される。
     */
    @Before
    public void setup() {
        // android.utils.Log を使ったログを出力する
        ShadowLog.stream = System.out;
        // Activityを生成
        mNewAccountActivity = TestUtils.createActivity(NewAccountActivity.class, null);
    }

    /**
     * @After がついたメソッドは、すべてのテストメソッド(@Testがついたメソッド)の
     * 実施後に呼び出される。
     */
    @After
    public void teardown() {
    }

    /**
     * ユーザー新規登録成功でログイン画面に遷移するかの確認
     */
    @Test
    public void createNewAccountSuccess() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPassAndRePass("komiyama", "komiyama","komiyama");
        // NewAccountTaskで必ず成功が返るように設定
        mNewAccountActivity.setApi(new WebApiMock());
        Button button = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String nextActivityName = TestUtils.getNextActivityName(mNewAccountActivity);
        assertThat(nextActivityName, is(LoginActivity.class.getName()));
    }

    /**
     * サーバーの不調によりユーザー登録失敗してダイアログを表示するかの確認
     */
    @Test
    public void accountCreateFailedByConnectError() {
        // ●setup(準備)
        // IDとPassを設定
        // NewAccountTaskで必ず失敗が返るように設定
        enterIdAndPassAndRePass("failure", "aaaaaaaa","aaaaaaaa");
        mNewAccountActivity.setApi(new WebApiMock());
        Button button = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        button.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.cannot_connect)));
    }

    /**
     * サーバーに問い合わせて、アカウントがすでに登録されており、新規に登録できないというダイアログを表示するかの確認
     */
    @Test
    public void accountCreateFailedByAlreadyCreateAccountError() {
        // ●setup(準備)
        // IDとPassを設定
        // NewAccountTaskで必ず失敗が返るように設定
        enterIdAndPassAndRePass("nakayama", "aaaaaaaa","aaaaaaaa");
        mNewAccountActivity.setApi(new WebApiMock());
        Button loginButton = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        loginButton.performClick();
        // ダイアログのOKボタンを押下
        TestUtils.clickOKOnAlertDialog();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.already_register)));
    }

    /**
     * IDが空でユーザー登録失敗し、ダイアログを表示するかの確認
     */
    @Test
    public void createNewAccountFailedByEmptyId() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPassAndRePass("", "aaaaaaaa","aaaaaaaa");
        /// NewAccountTaskで必ず失敗が返るように設定
        Button loginButton = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_input)));
    }

    /**
     * パスワードが空でユーザー登録失敗し、ダイアログを表示するかの確認
     */
    @Test
    public void createNewAccountFailedByEmptyByPassWard() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPassAndRePass("aaaaaaaa", "","");
        /// LNewAccountTaskで必ず失敗が返るように設定
        Button loginButton = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_input)));
    }

    /**
     *　許可されてない文字を入力しユーザー登録失敗し、ダイアログを表示するかの確認
     */
    @Test
    public void createNewAccountFailedByIllegalInfo() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPassAndRePass("!!!!!!!!", "!!!!!!!!","!!!!!!!!");
        /// NewAccountTaskで必ず失敗が返るように設定
        Button loginButton = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_permit_character)));
    }

    /**
     *　IDを少ない情報で入力しユーザー登録失敗し、ダイアログを表示するかの確認
     */
    @Test
    public void createNewAccountFailedByLessId() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPassAndRePass("55555", "aaaaaaaa","aaaaaaaa");
        /// NewAccountTaskで必ず失敗が返るように設定
        Button loginButton = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.input_rule_id)));
    }

    /**
     *　パスワードを少ない情報で入力しユーザー登録失敗し、ダイアログを表示するかの確認
     */
    @Test
    public void createNewAccountFailedByLessPass() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPassAndRePass("aaaaaaaa", "7777777","7777777");
        /// NewAccountTaskで必ず失敗が返るように設定
        Button loginButton = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.input_rule_pass)));
    }

    /**
     *　 パスワードと再入力パスワードが異なった状態で入力しユーザー登録失敗し、ダイアログを表示するかの確認
     */
    @Test
    public void createNewAccountFailedByIllegalInput() {
        // ●setup(準備)
        // IDとPassを設定
        enterIdAndPassAndRePass("aaaaaaaa", "bbbbbbbb","cccccccc");
        /// NewAccountTaskで必ず失敗が返るように設定
        Button loginButton = (Button) mNewAccountActivity.findViewById(R.id.newaccount_button_regist);

        // ●execute(テストの実施)
        // 登録ボタンを押下
        loginButton.performClick();

        // ●verify(検証)
        String message = TestUtils.getLatestAlertDialogMessage();
        assertThat(message, is(RuntimeEnvironment.application.getString(R.string.not_match_pass)));
    }


    /**
     * 引数に指定したId, Passを設定します。
     * @param id
     * @param pass
     * @param confirmationPass
     */
    private void enterIdAndPassAndRePass(String id, String pass ,String confirmationPass) {
        EditText idText = (EditText) mNewAccountActivity.findViewById(R.id.newaccount_id);
        EditText passText = (EditText) mNewAccountActivity.findViewById(R.id.newaccount_passward);
        EditText comfirmationPassText = (EditText) mNewAccountActivity.findViewById(R.id.newaccount_repassward);
        idText.setText(id);
        passText.setText(pass);
        comfirmationPassText.setText(confirmationPass);
    }

}